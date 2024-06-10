package FNG;

import java.io.File;

import javax.swing.JLabel;

import Game.Display;

public class Prop {
    private JLabel hall;
    public Prop(Display display, String path, int scale, int x, int y) {
        String path2 = new File("").getAbsolutePath();
        hall = display.addImage(path2+path, scale, scale, x, y);
        hall.setVisible(false);
    }
    public void display() {
        hall.setVisible(true);
    }
    public void hide() {
        hall.setVisible(false);
    }
}
