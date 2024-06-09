package FNG;

import java.io.File;
import java.util.Random;

import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Game.Display;

public class Cup {
    private Chi chi;
    private double x;
    private double y;
    public int i;
    private int velocity;  // 0=up left 1=up 2=up right 3=right 4=down right 5=down 6=down left 7=left 8=stationary
    private boolean activated;
    private static final double SPEED = 1;
    private static Random random = new Random();
    private JLabel cup;
    private Display d;
    public Cup(Chi chi, int i, Display d) {
        this.i = i;
        this.chi = chi;
        this.activated = false;
        this.d = d;
        String path = new File("").getAbsolutePath();
        cup = this.d.addImage(path+"/Photo/CupCake.png", 50, 50, 0, 0);
        cup.setVisible(false);
        cup.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cup.setVisible(false);
                activated = false;
                chi.toAttack();
                chi.pushPopCup(i);
            }
        });
        d.setZOrder(cup, 0);
    }
    public void activate() {
        x = 760;
        y = 350;
        activated = true;
        cup.setVisible(true);
        this.velocity = 8;
    }
    public void run() {
        if (!activated) {
            return;
        }
        switch (velocity) {
            case 0:
                x -= SPEED / Math.sqrt(2);
                y -= SPEED / Math.sqrt(2);
                break;
            case 1:
                y -= SPEED;
                break;
            case 2:
                x += SPEED / Math.sqrt(2);
                y -= SPEED / Math.sqrt(2);
                break;
            case 3:
                x += SPEED;
                break;
            case 4:
                x += SPEED / Math.sqrt(2);
                y += SPEED / Math.sqrt(2);
                break;
            case 5:
                y += SPEED;
                break;
            case 6:
                x -= SPEED / Math.sqrt(2);
                y += SPEED / Math.sqrt(2);
                break;
            case 7:
                x -= SPEED;
                break;
            default:
                break;
        }
        if (x < 0) {
            x = 0;
        } else if (x > 1490) {
            x = 1490;
        }
        if (y < 0) {
            y = 0;
        } else if (y > 815) {
            y = 815;
        }
        this.cup.setLocation((int)x, (int)y);
        if (random.nextDouble() < .01) {
            velocity = random.nextInt(8);
        }

    }
}
