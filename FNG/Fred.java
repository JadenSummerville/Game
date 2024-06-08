package FNG;

import Game.Ticker;

import javax.swing.JLabel;

import Game.Display;

public class Fred extends Ticker.Entity {
    private final int HEIGHT;
    private final double MAX_PROGRESS;
    private Display display;
    private JLabel fred;
    private double progress;
    private double unpredictibility;
    private double speed;
    private Door door;
    public Fred(double speed, Door door, Display display) {
        MAX_PROGRESS = 10;
        HEIGHT = 400;
        progress = MAX_PROGRESS;
        this.speed = speed;
        this.display = display;
        this.door = door;
        fred = this.display.addImage("../Photo/Freddy.png", 100, 100, 0, HEIGHT);
    }
    @Override
    public void update() {
        progress -= speed;
        if (progress <= 0) {
            attackDoor();
        }
        fred.setLocation((int)(500 - 500*progress/MAX_PROGRESS), HEIGHT);
    }
    public void attackDoor() {
        if (door.doorIsBarricaded()) {
            progress = MAX_PROGRESS;
            return;
        }
        ticker.endLoop();
    }
}
