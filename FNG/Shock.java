package FNG;

import java.awt.desktop.SystemSleepEvent;
import java.io.File;

import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Game.Display;

public class Shock {
    private JLabel button;
    private Fox fox;
    public Shock(Display d, Fox fox) {
        String path = new File("").getAbsolutePath();
        button = d.addImage(path+"/Photo/Zap.png", 20, 20, 888, 350);
        this.fox = fox;
        this.button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!fox.shockAttempt()) {
                    return;
                }
                button.setVisible(false);
            }
        });
    }
}
