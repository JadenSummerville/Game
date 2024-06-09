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
    private int x = 555;
    private int y = 100;
    private static Zscore zScore = new Zscore(0, 1, 5, -1);
    Chi(long time, Door door, Display display) {
        String path = new File("").getAbsolutePath();
        cups = new Cup[100];
        for (int i = 0; i != cups.length; i++) {
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
    }
    @Override
    public void startObserving() {
        observed = true;
        if (!timeUp()) {
            wall.setVisible(true);
            return;
        }
        if (attack) {
            attackPose.setVisible(true);
            wall.setVisible(true);
            return;
        }
        peek.setVisible(true);
    }
    @Override
    public void stopObserving() {
        observed = false;
        wall.setVisible(false);
        peek.setVisible(false);
        attackPose.setVisible(false);
    }
    @Override
    public void update() {
        for (int i = 0; i != numCups; i++) {
            cups[i].run();
        }
        if (this.attack) {
            attackTimer -= .1;
            if (attackTimer < 0) {
                if (door.doorIsBarricaded()) {
                    door.nock();
                    this.attack = false;
                    return;
                }
                System.out.println("Death By Chic");
                display.dispose();
                ticker.endLoop();
            }
        }
        if (!timeUp() || observed) {
            return;
        }
        send -= .1;
        if (send < 0 && numCups != cups.length) {
            send = SEND_MAX;
            cups[numCups].activate();
            numCups++;
        }
    }
    private boolean timeUp() {
        return this.ticker.getRunTime() > this.time;
    }
    public void toAttack() {
        if (this.attack) {
            attackTimer += 5;
            return;
        }
        this.attack = true;
        Random random = new Random();
        attackTimer = 150 - 150 * random.nextDouble();  //20*zScore.generate_value();
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
        swapCupCake(i, this.numCups);
        numCups--;
    }
}
