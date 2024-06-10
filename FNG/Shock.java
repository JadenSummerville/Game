package FNG;

import java.awt.desktop.SystemSleepEvent;
import java.io.File;

import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Game.Display;

public class Shock {
    private JLabel button;
    public Shock(Display d, Anam anam, int x) {
        String path = new File("").getAbsolutePath();
        button = d.addImage(path+"/Photo/Zap.png", 20, 20, x, 350);
        this.button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!anam.shockAttempt()) {
                    return;
                }
                button.setVisible(false);
            }
        });
    }
}
