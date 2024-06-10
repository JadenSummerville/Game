package FNG;

import java.io.File;
import java.util.Random;

import javax.swing.JLabel;

import Game.Display;

public class Chi extends Anam {
    private Display display;
    private Door door;
    private long time;
    private boolean attack;
    private double send;
    private boolean observed;
    private final double SEND_MAX = 30;
    private JLabel wall;
    private JLabel peek;
    private JLabel attackPose;
    private double attackTimer;
    private Cup[] cups;
    private int numCups;
    private static final int MAX_NUM_CUPS = 100;
    private static final boolean DEBUG = false;
    private int x = 555;
    private int y = 100;
    private static Zscore zScore = new Zscore(0, 1, 5, -1);
    Chi(long time, Door door, Display display) {
        String path = new File("").getAbsolutePath();
        cups = new Cup[MAX_NUM_CUPS];
        for (int i = 0; i != MAX_NUM_CUPS; i++) {
            cups[i] = new Cup(this, i, display);
        }
        numCups = 0;
        this.display = display;
        this.door = door;
        this.time = time;
        this.attack = false;
        this.observed = false;
        send = SEND_MAX;

        wall = this.display.addImage(path+"/Photo/Wall.png", 100, 100, x, y);
        peek = this.display.addImage(path+"/Photo/WallPeek.png", 100, 100, x, y);
        attackPose = this.display.addImage(path+"/Photo/ChAttack.png", 100, 100, x+10, y+155);
        wall.setVisible(false);
        peek.setVisible(false);
        attackPose.setVisible(false);
        checkRep();
    }
    public void checkRep() {
        if (!DEBUG) {
            return;
        }
        for (int i = 0; i != MAX_NUM_CUPS; i++) {
            if (cups[i] == null) {
                throw new RuntimeException("Null CupCake present at "+ i +".");
            }
        }
        for (int i = 0; i != MAX_NUM_CUPS; i++) {
            if (cups[i].i != i) {
                throw new RuntimeException("CupCake " + cups[i].i + " put in wrong index " + i + ".");
            }
        }
        for (int i = 0; i != numCups; i++) {
            if (!cups[i].activated) {
                throw new RuntimeException("Non active cup below line " + numCups + " at " + i + ".");
            }
        }
        for (int i = numCups; i != MAX_NUM_CUPS; i++) {
            if (cups[i].activated) {
                throw new RuntimeException("Active out of range cup.");
            }
        }
    }
    @Override
    public void startObserving() {
        checkRep();
        observed = true;
        if (!timeUp()) {
            wall.setVisible(true);
            checkRep();
            return;
        }
        if (attack) {
            attackPose.setVisible(true);
            wall.setVisible(true);
            checkRep();
            return;
        }
        peek.setVisible(true);
        checkRep();
    }
    @Override
    public void stopObserving() {
        checkRep();
        observed = false;
        wall.setVisible(false);
        peek.setVisible(false);
        attackPose.setVisible(false);
        checkRep();
    }
    @Override
    public void update() {
        checkRep();
        for (int i = 0; i != numCups; i++) {
            cups[i].run();
        }
        if (this.attack) {
            attackTimer -= .1;
            if (attackTimer < 0) {
                if (door.doorIsBarricaded()) {
                    door.nock();
                    this.attack = false;
                    checkRep();
                    return;
                }
                System.out.println("Death By Chic");
                display.dispose();
                ticker.endLoop();
            }
            return;
        }
        if (!timeUp() || observed) {
            checkRep();
            return;
        }
        send -= .1;
        if (send < 0 && numCups != cups.length) {
            send = SEND_MAX;
            spawnCupCake();
        }
        checkRep();
    }
    private void spawnCupCake() {
        cups[numCups].activate();
        numCups++;
    }
    private boolean timeUp() {
        return this.ticker.getRunTime() > this.time;
    }
    public void toAttack() {
        checkRep();
        if (this.attack) {
            attackTimer += 5;
            if (attackTimer > 100) {
                attackTimer = 100;
            }
            return;
        }
        send = SEND_MAX;
        this.attack = true;
        Random random = new Random();
        attackTimer = 110 - 110 * Math.pow(random.nextDouble(), 2);  //20*zScore.generate_value();
        checkRep();
    }
    public void swapCupCake(int i, int j) {
        Cup o = cups[i];
        cups[i] = cups[j];
        cups[j] = o;
        int io = cups[i].i;
        cups[i].i = cups[j].i;
        cups[j].i = io;
    }
    public void pushPopCup(int i) {
        checkRep();
        cups[i].activated = false;
        numCups--;
        swapCupCake(i, this.numCups);
        checkRep();
    }
    public int numCups() {
        checkRep();
        return numCups;
    }
    @Override
    public boolean shockAttempt() {
        if (attack) {
            attack = false;
            send = SEND_MAX;
            return true;
        }
        return false;
    }
}
