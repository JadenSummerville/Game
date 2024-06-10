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
    private Prop hall;
    private Prop propDoor;
    public Fred(double speed, Door door, Display display, Prop hall, Prop propDoor) {
        MAX_PROGRESS = 10;
        HEIGHT = 700;
        progress = MAX_PROGRESS;
        this.speed = speed;
        this.display = display;
        this.door = door;
        this.hall = hall;
        this.propDoor = propDoor;
        String path = new File("").getAbsolutePath();
        image = this.display.addImage(path+"/Photo/Freddy.png", 100, 100, 0, HEIGHT);
        image.setVisible(false);
        this.display.setZOrder(image, 0);
    }
    @Override
    public void startObserving() {
        observed = true;
        image.setVisible(true);
        hall.display();
        propDoor.display();
    }
    @Override
    public void stopObserving() {
        observed = false;
        image.setVisible(false);
        hall.hide();
        propDoor.hide();
    }
    @Override
    public void update() {
        if (!observed) {
            progress -= speed;
        }
        if (progress <= 0) {
            attackDoor();
        }
        image.setLocation((int)(1300 - 1300*progress/MAX_PROGRESS), HEIGHT);
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
            speed = .0005;
        }
    }
    public double getSpeed() {
        return speed;
    }
    @Override
    public boolean shockAttempt() {
        if (speed == 0) {
            return false;
        }
        progress += MAX_PROGRESS/3;
        if (progress > MAX_PROGRESS) {
            progress = MAX_PROGRESS;
        }
        return true;
    }
}
