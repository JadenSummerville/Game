package Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Display {
    private final JFrame frame;
    public final KeyBoard keyBoard;
    private final JPanel contentPane;
    /**
     * Creates a display screen.
     * Still needs to be started.
    */
    public Display() {
        frame = new JFrame();
        keyBoard = new KeyBoard();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.addKeyListener(keyBoard);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(frame);
        
        contentPane = new JPanel(null);
        contentPane.setOpaque(false);
    }
    public static <T> ArrayList<T> toArray(T... objects) {
        ArrayList<T> goal = new ArrayList<>();
        for (T obj: objects) {
            goal.add(obj);
        }
        return goal;
    }
    /**
     * Adds image with given filepath.
     * 
     * @param filepath path to png we will use.
     * @param width desired scaled width of image.
     * @param height desired scaled height of image.
     * @param x start x.
     * @param x start y.
     * @param Jlabel JLabel of image.
    */
    public JLabel addImage(String filepath, int width, int height, int x, int y) {
        ImageIcon icon = new ImageIcon(filepath);
        JLabel imageLabel = new JLabel();
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);
        imageLabel.setIcon(scaledIcon);
        imageLabel.setBounds(x, y, width, height);
        contentPane.add(imageLabel);
        return imageLabel;
    }
    /**
     * Starts the Jframe.
     * It is recommended to addImage before
     * but not after this method is run.
    */
    public void start() {
        frame.setContentPane(contentPane);
        frame.setVisible(true);
    }
    /**
     * Set z ordering of this JLabel.
     * 
     * @param jlabel jlabel we wish to adjust location of.
     * @param index index we wish to adjust.
    */
    public void setZOrder(JLabel jlabel, int index) {
        contentPane.setComponentZOrder(jlabel, index);
        contentPane.repaint();
    }
}
