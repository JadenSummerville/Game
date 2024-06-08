package FNG;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import Game.Display;
import Game.Ticker;

public class Door {
    private static int barricades = 1;
    private boolean barricaded;
    private JLabel open;
    private JLabel close;
    public Door(Display display, int x, int y) {
        if (barricades < 0) {
            throw new RuntimeException("Cannot have a negative number of berricades.");
        }
        open = display.addImage("../Photo/Door.png", 200, 200, x, y);
        close = display.addImage("../Photo/DoorBarricaded.png", 200, 200, x, y);
        close.setVisible(false);
        this.barricaded = false;

        open.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("null");
            }
        });
        /*

        open.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON3) {
                    System.out.println("left-clicked on label");
                    barricadeDoor();
                }
            }
        });
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.println("Right-clicked on label");
                    unbarricadeDoor();
                }
            }
        });*/
    }
    public boolean barricadeDoor() {
        if(barricaded || barricades == 0) {
            return false;
        }
        barricaded = true;
        barricades--;

        close.setVisible(true);
        open.setVisible(false);

        return true;
    }
    public boolean unbarricadeDoor() {
        if(!barricaded) {
            return false;
        }
        barricaded = false;
        barricades++;

        close.setVisible(false);
        open.setVisible(true);

        return true;
    }
    public boolean doorIsBarricaded() {
        return barricaded;
    }
    public static void main(String[] args) {
        Display d = new Display();
        Door[] doors = new Door[4];
        for (int i = 0; i != doors.length; i++) {
            doors[i] = new Door(d, 100 + 400*i, 0);
        }
        Fred fred = new Fred( 0.01, doors[0], d);
        Ticker ticker = new Ticker();
        ticker.addEntity(fred);
        d.start();
        ticker.start();
        System.out.println("null");
    }
}
