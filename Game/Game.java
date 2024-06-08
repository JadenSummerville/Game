package Game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class Game {
    public static void main(String[] args) {
        String playerPath = "../Photo/simple_game_character.png";
        //String backPath = "../photoType1/mg-n.png";
        Display d = new Display();
        /*
        Display d = new Display();
        JLabel a = d.addImage(playerPath, 1000, 1000, 0, 0);
        d.start();
        Player p = new Player(d.keyBoard, a);
        Ticker t = new Ticker();
        t.addEntity(p);

        a.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Label clicked");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("Mouse entered label");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("Mouse exited label");
            }
        });
        
        d.setZOrder(a, 0);
        t.start();
        */
        //a.get(backPath).setBounds(0, 0, 2000, 2000);
        System.out.println("null");
        
    }
}
