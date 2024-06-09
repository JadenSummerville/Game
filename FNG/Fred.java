package FNG;

import Game.Ticker;

import java.io.File;

import javax.swing.JLabel;

import Game.Display;

public class Fred extends Anam {
    private final int HEIGHT;
    private final double MAX_PROGRESS;
    private Display display;
    private double progress;
    private double unpredictibility;
    private double speed;
    private Door door;
    private boolean observed = false;
    public Fred(double speed, Door door, Display display) {
        MAX_PROGRESS = 10;
        HEIGHT = 700;
        progress = MAX_PROGRESS;
        this.speed = speed;
        this.display = display;
        this.door = door;
        String path = new File("").getAbsolutePath();
        image = this.display.addImage(path+"/Photo/Freddy.png", 100, 100, 0, HEIGHT);
        image.setVisible(false);
    }
    @Override
    public void startObserving() {
        observed = true;
        image.setVisible(true);
    }
    @Override
    public void stopObserving() {
        observed = false;
        image.setVisible(false);
    }
    @Override
    public void update() {
        if (!observed) {
            progress -= speed;
        }
        if (progress <= 0) {
            attackDoor();
        }
        image.setLocation((int)(1200 - 1200*progress/MAX_PROGRESS), HEIGHT);
    }
    public void attackDoor() {
        if (door.doorIsBarricaded()) {
            progress = MAX_PROGRESS;
            door.nock();
            return;
        }
        System.out.println("Death By Fred");
        display.dispose();
        ticker.endLoop();
    }
    public void incrementSpeed(double amount) {
        speed += amount;
        if (speed < 0) {
            speed = 0;
        }
    }
}
