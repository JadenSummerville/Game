package FNG;

import java.io.File;
import java.util.Random;

import Game.Display;

public class Fox extends Anam {
    private final int WIDTH;
    private final double MAX_PROGRESS;
    private Display display;
    private double progress;
    private double unpredictibility;
    private double speed;
    private Door door;
    private boolean observed = false;
    private boolean on = false;
    private Random random = new Random();
    public Fox(double speed, Door door, Display display) {
        MAX_PROGRESS = 10;
        WIDTH = 964;
        progress = MAX_PROGRESS;
        this.speed = speed;
        this.display = display;
        this.door = door;
        String path = new File("").getAbsolutePath();
        image = this.display.addImage(path+"/Photo/Foxy.png", 100, 100, 0, WIDTH);
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
        if (on) {
            progress -= speed;
        }
        else {
            Random random = new Random();
            if (random.nextDouble() < 0.00043 && !observed) {
                on = true;
            }
        }
        if (progress <= 0) {
            attackDoor();
        }
        image.setLocation(WIDTH, (int)(250 - 250 * progress/MAX_PROGRESS));
    }
    public void attackDoor() {
        if (door.doorIsBarricaded()) {
            progress = MAX_PROGRESS;
            door.nock();
            return;
        }
        System.out.println("Death By Fox");
        display.dispose();
        ticker.endLoop();
    }
    public void incrementSpeed(double amount) {
        speed += amount;
        if (speed < 0) {
            speed = 0;
        }
    }
    public boolean shockAttempt() {
        if(random.nextDouble() < .5) {
            on = false;
            progress = MAX_PROGRESS;
            return true;
        }
        return false;
    }
}
