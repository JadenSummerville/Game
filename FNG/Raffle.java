package FNG;

import java.util.*;

/**
 * A Raffle allows you to insert objets and then randomly call them.
 * Afer calling the object, it is removed.
 * @param <T> objets to be randomly generated.
*/
public class Raffle<T>{
    private final boolean DEBUG = false;
    private final HashMap<T, Integer> NODEINDEX;
    private final ArrayList<Node<T>> NODES;
    private int size;
    private static final Random random = new Random();
    /**
     * Creates an empty Raffle
     */
    public Raffle(){
        this.NODEINDEX = new HashMap<>();
        this.NODES = new ArrayList<>();
        this.size = 0;
        this.checkRep();
    }
    /**
     * Creates a Raffle with all elements in 'elements'.
     */
    public Raffle(T[] elements){
        this.NODEINDEX = new HashMap<>();
        this.NODES = new ArrayList<>();
        this.size = 0;
        for(T element: elements){
            this.add(element);
        }
        this.checkRep();
    }
    /**
     * Creates a Raffle with all elements in 'elements'.
     */
    public Raffle(List<T> elements){
        this.NODEINDEX = new HashMap<>();
        this.NODES = new ArrayList<>();
        this.size = 0;
        for(T element: elements){
            this.add(element);
        }
        this.checkRep();
    }
    /**
     * Creates a Raffle with all elements in 'elements'.
     */
    public Raffle(Set<T> elements){
        this.NODEINDEX = new HashMap<>();
        this.NODES = new ArrayList<>();
        this.size = 0;
        for(T element: elements){
            this.add(element);
        }
        this.checkRep();
    }
    /**
     * Add 'element' to the raffle
     * 
     * @param element element to be added
     * @modifies this adds element to this
    */
    public void add(T element){
        this.checkRep();
        add(element, 1);
        this.checkRep();
    }
    /**
     * Add 'amount' 'element's to the raffle
     * 
     * @param element element(s) to be added
     * @param amount number of 'element' to add
     * @throws IllegalArgumentException 'amount' < 0
     * @modifies this adds 'amount' elements to this
    */
    public void add(T element, int amount){
        this.checkRep();
        if(amount == 0){
            return;
        }if(amount < 0){
            throw new IllegalArgumentException();
        }
        if(this.NODEINDEX.containsKey(element)){
            int index = NODEINDEX.get(element);
            Node<T> node = this.NODES.get(index);
            adjustNodeSize(node, amount + node.size);
            this.checkRep();
            return;
        }
        this.NODEINDEX.put(element, NODES.size());
        NODES.add(new Node(element, amount));
        adjustParentNode(NODES.size() - 1, amount);
        this.size += amount;
        this.checkRep();
    }
    /**
     * return a random 'T' with each index weighted by it's size
     * 
     * @return random 'T' with each index weighted by it's size or null iff this is empty
    */
    public T peek(){
        this.checkRep();
        if(this.isEmpty()){
            return null;
        }
        int index = getRandomIndex();
        T goal = NODES.get(index).value;
        this.checkRep();
        return goal;
    }
    /**
     * return a random 'T' with each index weighted by it's size
     * 
     * @return random 'T' with each index weighted by it's size or null iff this is empty
    */
    public List<T> peek(int numOfItems){
        this.checkRep();
        List<T> goal = new ArrayList<>();
        for(int i = 0; i != numOfItems; i++){
            goal.add(this.peek());
        }
        this.checkRep();
        return goal;
    }
    /**
     * remove and return a random 'T' with each index weighted by it's size
     * 
     * @modefies 'this' removes value that was returned from 'this'. Iff 'this' is empty, this is not modifies
     * @return random 'T' with each index weighted by it's size or null iff this is empty
    */
    public T pull(){
        this.checkRep();
        // Check is empty
        if(this.isEmpty()){
            return null;
        }
        // Record value
        int index = getRandomIndex();
        Node<T> goal = this.NODES.get(index);
        adjustNodeSize(goal, goal.size - 1);
        this.checkRep();
        return goal.value;
    }
    /**
     * remove and return a list of 'numOfItems' random 'T's with each index weighted by it's size
     * 
     * @param numOfItems numOf T to be printed
     * @modefies 'this' removes values that was returned from 'this'.
     * @throws Exception Iff numOfItems > size or numOfItems < 0.
     * @return random 'T' with each index weighted by it's size or null iff this is empty
    */
    public List<T> pull(int numOfItems){
        this.checkRep();
        if(numOfItems > this.size || numOfItems < 0){
            throw new Error();
        }
        List<T> goal = new ArrayList<>();
        for(int i = 0; i != numOfItems; i++){
            goal.add(this.pull());
        }
        this.checkRep();
        return goal;
    }
    /**
     * Return all possible values
     * 
     * @return all possible values
    */
    public Set<T> getPossibleValues(){
        return this.NODEINDEX.keySet();
    }
    /**
     * Find the number of 'value' in 'this'.
     * 
     * @param value the value whos amount we want to see
     * @return the number of 'value' in 'this'
     */
    public int getAmount(T value){
        if(!this.NODEINDEX.containsKey(value)){
            return 0;
        }
        return this.NODES.get(this.NODEINDEX.get(value)).size;
    }
    /**
     * Set the number of 'value' in 'this' to 'amount'.
     * 
     * @param value value to have amount edited
     * @param amount amount of 'value' that 'this' should now have
     * @throws IllegalArgumentException 'amount' < 0
     * @modifies 'this' set amount of 'value' to amount
     */
    public void setAmount(T value, int amount){
        this.checkRep();
        if(amount < 0){
            throw new IllegalArgumentException();
        }
        if(!this.NODEINDEX.containsKey(value)){
            add(value, amount);
            this.checkRep();
            return;
        }
        if(amount == 0){
            removeNode(this.NODEINDEX.get(value));
            this.checkRep();
            return;
        }
        int index = this.NODEINDEX.get(value);
        Node<T> node = this.NODES.get(index);
        adjustNodeSize(node, amount);
        this.checkRep();
    }
    /**
     * return number of items in 'this'
     * 
     * @return number of items in 'this'
    */
    public int size(){
        return this.size;
    }
    /**
     * return number of item types stored in 'this'
     * 
     * @return number of item types stored in 'this'
    */
    public int typeSize(){
        return this.NODES.size();
    }
    /**
     * returns true iff there are no elements in 'this'
     * 
     * @return true iff there are no elements in 'this'
    */
    public boolean isEmpty(){
        return this.NODEINDEX.isEmpty();
    }
    /**
     * Set size to 'requestedSize'. Mostly maintain same value ratios
     * 
     * @param requestedSize size to set to
     * @requires 'this' is not empty
     * @modifies 'this' scales 'this' up or down to 'requestedSize'
    */
    public void resize(int requestedSize){
        this.checkRep();
        double resizeFactor = (requestedSize *1.0) / this.size;
        for(Node<T> node: this.NODES){
            int addition = 1;
            double amount = this.getAmount(node.value) * resizeFactor;
            if(0 == amount % 1){
                addition = 0;
            }
            this.setAmount(node.value, (int)amount + addition);
        }
        if(this.size > requestedSize){
            this.pull(this.size - requestedSize);
            this.checkRep();
            return;
        }
        List<T> toAdd = peek(requestedSize - this.size);
        for(T item: toAdd){
            this.add(item);
        }
        this.checkRep();
    }
    @Override
    public Raffle<T> clone(){
        Raffle<T> goal = new Raffle<T>();
        for(Node<T> node: this.NODES){
            goal.add(node.value, node.size);
        }
        return goal;
    }
    private void checkRep(){
        if(!DEBUG){
            return;
        }
        asert(this.NODEINDEX != null, "this.nodeIndex == null");
        asert(this.NODES != null, "this.NODES == null");
        asert(this.NODEINDEX.size() == this.NODES.size(), "Type size mismatch");
        int size = 0;
        for(T nodeValue: NODEINDEX.keySet()){
            int nodeIndex = NODEINDEX.get(nodeValue);
            Node<T> node = NODES.get(nodeIndex);
            asert(node.size > 0, "Node has none positive size");
            asert(node.leftSize >= 0, "Node has none positive child");
            asert(node.rightSize >= 0, "Node has none positive child");
            asert(node.value.equals(nodeValue), "NODEINDEX leads to incorrect node");
            size += node.size;
        }
        asert(this.size == size, "Nodes do not add up to size");
        }
    private static void asert(boolean asert, String errorMessage){
        if(!asert){
            throw new RuntimeException(errorMessage);
        }
    }
    /**
     * Get a random index with each index weighted by it's size
     * 
     * @requires this is not empty
     * @return random index with each index weighted by it's size
    */
    private int getRandomIndex(){
        int current = 0;
        while(true){
            Node<T> node = NODES.get(current);
            int totalChildSize = node.leftSize + node.rightSize;
            double chanceOfStaying = (node.size * 1.0) / (totalChildSize + node.size);
            if(random.nextDouble() < chanceOfStaying){
                return current;
            }if(random.nextDouble() < (node.leftSize * 1.0) / (totalChildSize)){
                current = firstChild(current);
            }else{
                current = secondChild(current);
            }
        }
        
    }
    /**
     * Remove node at index 'index'
     * 
     * @param index index of node to be removed
     * @modifies 'this' remove node at index 'index'
    */
    private void removeNode(int index){
        Node<T> goal = NODES.get(index);
        T goalValue = goal.value;
        // Empty node
        this.adjustParentNode(index, -goal.size);
        this.size -= goal.size;
        // record last node
        int finalIndex = this.NODES.size() - 1;
        Node<T> finalNode = this.NODES.get(finalIndex);
        // copy last node info to removed node
        goal.value = finalNode.value;
        goal.size = finalNode.size;
        this.adjustParentNode(index, finalNode.size);
        this.NODEINDEX.put(finalNode.value, index);
        this.NODEINDEX.remove(goalValue);
        // remove final node
        this.adjustParentNode(finalIndex, -finalNode.size);
        this.NODES.remove(finalIndex);
    }
    /**
     * Set 'node' size to 'size'. Delet the node if we are setting size to 0.
     * 
     * @param node node to have size adjustemt or node to be removed
     * @param size size 'node' is to be set to
     * @throws IllegalArgumentException size < 0
     * @modifies 'this' sets 'node' size to 'size' if size > 0. If 'size' == 0, deletes 'node'
    */
    private void adjustNodeSize(Node<T> node, int size){
        if(size < 0){
            throw new IllegalArgumentException();
        }
        int index = this.NODEINDEX.get(node.value);
        if(size == 0){
            this.removeNode(index);
            return;
        }
        int adjustment = size - node.size;
        node.size += adjustment;
        this.size += adjustment;
        this.adjustParentNode(index, adjustment);
    }
    /**
     * Find the parent location of the 'child' index
     * 
     * @param child child index of parent we are lookin for
     * @requires 'child' > 0
     * @return parent index of 'child'
    */
    private static int parent(int child){
        return (child - 1)/2;
    }
    /**
     * Find the first child location of the 'parent' index
     * 
     * @param parent parent index of the child we are looking for
     * @requires 'parent' >= 0
     * @return first child index of 'parent'
    */
    private static int firstChild(int parent){
        return parent*2+1;
    }
    /**
     * Find the second child location of the 'parent' index
     * 
     * @param parent parent index of the child we are looking for
     * @requires 'parent' >= 0
     * @return second child index of 'parent'
    */
    private static int secondChild(int parent){
        return firstChild(parent) + 1;
    }
    /**
     * Tell parent and super parents of 'node' one of their childen 'node' has chaned size
     * 
     * @param node index of childNode
     * @param adjustment change in child size
     * @modifies this adjust parent and super parents of node so that they see 'node' has grown by 'adjustment'
     * @requires node >= 0
     */
    private void adjustParentNode(int node, int adjustment){
        if(node == 0){
            return;
        }
        int parent = parent(node);
        Node<T> parentNode = NODES.get(parent);
        int firstChild = firstChild(parent);
        if(firstChild == node){
            parentNode.leftSize += adjustment;
        }else{
            parentNode.rightSize += adjustment;
        }
        adjustParentNode(parent, adjustment);
    }
    /**
     * The 'Node' class keeps track of its index, size, totalLeftSize, TotalRightSize, and what object it stores.
     * @param <T> object type that can be stored in this node
     */
    private class Node<T>{
        T value; // Value stored in node
        int size; // number of 'value' stored here
        int leftSize; // total number of objects stored on left subtree
        int rightSize; // total number of objects stored on right subtree
        Node(T value, int amount){
            this.value = value;
            this.size = amount;
            this.leftSize = 0;
            this.rightSize = 0;
        }
    }
}