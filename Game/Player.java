package Game;

import javax.swing.JLabel;

import Game.Ticker.Entity;

public class Player extends Ticker.Entity {
    KeyBoard keyboard;
    JLabel image;
    int x = 0;
    int y = 0;
    public Player(KeyBoard keyboard, JLabel image) {
        this.keyboard = keyboard;
        this.image = image;
    }
    @Override
    public void update() {
        if(keyboard.upPressed()) {
            y -= 3;
        }
        if(keyboard.downPressed()) {
            y += 3;
        }
        if(keyboard.leftPressed()) {
            x -= 3;
        }
        if(keyboard.rightPressed()) {
            x += 3;
        }
        image.setLocation(x, y);
    }
}
