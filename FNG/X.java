package FNG;

import java.io.File;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;

import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Game.Display;
import Game.Ticker;

public class X {
    private Display d;
    private Ticker t;
    X(Display d, Ticker t) {
        this.d = d;
        String path = new File("").getAbsolutePath();
        JLabel j = d.addImage(path+"/Photo/X.png", 20, 20, 1470, 30);
        j.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                d.dispose();
                System.out.println("null_X");
                t.endLoop();
            }
        });
    }
}
