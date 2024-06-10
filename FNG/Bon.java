package FNG;

import java.io.File;

import Game.Display;

public class Bon extends Anam {
    private final int HEIGHT;
    private final double MAX_PROGRESS;
    private Display display;
    private double progress;
    private double unpredictibility;
    private double accel;
    private double speed;
    private Door door;
    private boolean observed = false;
    private Prop hall;
    private Prop propDoor;
    public Bon(double accel, Door door, Display display, Prop hall, Prop propDoor) {
        MAX_PROGRESS = 10;
        HEIGHT = 713;
        progress = MAX_PROGRESS;
        this.accel = accel;
        this.speed = 0;
        this.display = display;
        this.door = door;
        this.hall = hall;
        this.propDoor = propDoor;
        String path = new File("").getAbsolutePath();
        image = this.display.addImage(path+"/Photo/Bonnie.png", 100, 100, 0, HEIGHT);
        image.setVisible(false);
        this.display.setZOrder(image, 0);
    }
    @Override
    public void startObserving() {
        hall.display();
        propDoor.display();
        observed = true;
        image.setVisible(true);
        speed = 0;
    }
    @Override
    public void stopObserving() {
        hall.hide();
        propDoor.hide();
        observed = false;
        image.setVisible(false);
    }
    @Override
    public void update() {
        if (!observed) {
            progress -= speed;
            speed += accel;
        }
        if (progress <= 0) {
            attackDoor();
        }
        image.setLocation((int)(1400*progress/MAX_PROGRESS), HEIGHT);
    }
    public void attackDoor() {
        if (door.doorIsBarricaded()) {
            progress = MAX_PROGRESS;
            door.nock();
            return;
        }
        System.out.println("Death By Bon");
        display.dispose();
        ticker.endLoop();
    }
    public void incrementAccel(double amount) {
        accel += amount;
        if (accel < 0) {
            accel = 0.000001;
        }
    }
    public double getAccel() {
        return accel;
    }
    @Override
    public boolean shockAttempt() {
        if (speed == 0) {
            return false;
        }
        speed = 0;
        progress += MAX_PROGRESS/7;
        if (progress > MAX_PROGRESS) {
            progress = MAX_PROGRESS;
        }
        return true;
    }
}
