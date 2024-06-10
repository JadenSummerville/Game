package FNG;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;

import javax.swing.JLabel;

import Game.Display;
import Game.Ticker;


public class Door extends Ticker.Entity {
    private static int barricades = 1;

    private static int numDoors = 0;
    private int doorNum;
    private boolean barricaded;
    private JLabel open;
    private JLabel close;
    private JLabel break1;
    private JLabel break2;
    private double nockTimer;
    private double nockAnamationCycle;
    private Display display;
    private static double  CYCLE_TIME = 0.5;
    private static final Zscore zScore = new Zscore(3, 2, 15, 1);
    public Door(Display display, int x, int y) {
        if (barricades < 0) {
            throw new RuntimeException("Cannot have a negative number of berricades.");
        }
        this.display = display;
        doorNum = numDoors;
        numDoors++;
        nockTimer = 0;
        nockAnamationCycle = 0;
        String path = new File("").getAbsolutePath();
        open = display.addImage(path+"/Photo/Door.png", 200, 200, x, y);
        close = display.addImage(path+"/Photo/DoorBarricaded.png", 200, 200, x, y);
        break1 = display.addImage(path+"/Photo/DoorAttack1.png", 200, 200, x, y);
        break2 = display.addImage(path+"/Photo/DoorAttack2.png", 200, 200, x, y);
        close.setVisible(false);
        break1.setVisible(false);
        break2.setVisible(false);
        this.barricaded = false;
        open.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON3) {
                    barricadeDoor();
                }
            }
        });
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    unbarricadeDoor();
                }
            }
        });
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
    public void nock() {
        nockAnamationCycle = CYCLE_TIME;
        nockTimer = zScore.generate_value();
        close.setVisible(false);
        break1.setVisible(true);
    }
    @Override
    public void update() {
        if(!break1.isVisible() && !break2.isVisible()) {
            if (display.keyBoard.number0Pressed()) {
                unbarricadeDoor();
            }
            switch (doorNum) {
                case 0:
                    if (display.keyBoard.number1Pressed()) {
                        barricadeDoor();
                    }
                    break;
                case 1:
                    if (display.keyBoard.number2Pressed()) {
                        barricadeDoor();
                    }
                    break;
                case 2:
                    if (display.keyBoard.number3Pressed()) {
                        barricadeDoor();
                    }
                    break;
                case 3:
                    if (display.keyBoard.number4Pressed()) {
                        barricadeDoor();
                    }
                    break;
            }
            return;
        }
        nockTimer -= .01;
        nockAnamationCycle -= .01;
        if (nockTimer < 0) {
            break1.setVisible(false);
            break2.setVisible(false);
            close.setVisible(true);
        }
        if (nockAnamationCycle < 0) {
            nockAnamationCycle = CYCLE_TIME;
            if (this.break1.isVisible()) {
                break1.setVisible(false);
                break2.setVisible(true);
                return;
            }
            break1.setVisible(true);
            break2.setVisible(false);
        }
    }
    public boolean doorIsBarricaded() {
        return barricaded;
    }
    public static void main(String[] args) {
        int night = (new Random()).nextInt(6) + 1;
        Display d = new Display();
        Door[] doors = new Door[4];
        for (int i = 0; i != doors.length; i++) {
            doors[i] = new Door(d, 100 + 400*i, 270);
        }
        Prop propDoor1 = new Prop(d, "/Photo/Door.png", 100, 1300, 684);
        Prop propDoor2 = new Prop(d, "/Photo/Door.png", 100, 0, 690);
        Prop hall = new Prop(d, "/Photo/Hall.png", 1536, 0, -330);
        Fred fred;
        Bon bon;
        Fox fox;
        Chi chi;

        int foxShockX = 890;
        int bonShockX = 90;
        int fredShockX = 1290;
        int chiShockX = 490;
        switch (night) {
            case 1:
                fred = new Fred( 0, doors[3], d, hall, propDoor1);
                bon = new Bon( 0, doors[0], d, hall, propDoor2);
                fox = new Fox( 0, doors[2], d);
                chi = new Chi(480_000_000_000L-5_000_000_000L, doors[1], d);
                Cam.setCamBreaks( 0, 1);
                new Shock(d, fox, foxShockX);
                new Shock(d, bon, bonShockX);
                new Shock(d, fred, fredShockX);
                new Shock(d, chi, chiShockX);
                break;
            case 2:
                fred = new Fred( .0035, doors[3], d, hall, propDoor1);
                bon = new Bon( 0, doors[0], d, hall, propDoor2);
                fox = new Fox( 0, doors[2], d);
                chi = new Chi(11_500_000_000L, doors[1], d);
                new Shock(d, chi, chiShockX);
                new Shock(d, fred, fredShockX);
                Cam.setCamBreaks( .0025, 0.002);

                break;
            case 3:
                fred = new Fred( .0005, doors[3], d, hall, propDoor1);
                bon = new Bon( 0.00001, doors[0], d, hall, propDoor2);
                fox = new Fox( 0.002, doors[2], d);
                chi = new Chi(380_000_000_000L, doors[1], d);
                Cam.setCamBreaks( .003, 0.00155);
                new Shock(d, fox, foxShockX);
                new Shock(d, bon, bonShockX);
                
                break;
            case 4:
                fred = new Fred( .005, doors[3], d, hall, propDoor1);
                bon = new Bon( 0.000005, doors[0], d, hall, propDoor2);
                fox = new Fox( 0.0045, doors[2], d);
                chi = new Chi(180_000_000_000L, doors[1], d);
                Cam.setCamBreaks( .0035, 0.0014);
                new Shock(d, fred, fredShockX);
                break;
            case 5:
                fred = new Fred( .0045, doors[3], d, hall, propDoor1);
                bon = new Bon( 0.0000075, doors[0], d, hall, propDoor2);
                fox = new Fox( 0.005, doors[2], d);
                chi = new Chi(100_000_000_000L, doors[1], d);
                Cam.setCamBreaks( .004, 0.0012);
                new Shock(d, fox, foxShockX);
                new Shock(d, bon, bonShockX);
                new Shock(d, fred, fredShockX);
                new Shock(d, chi, chiShockX);
                break;
            case 6:
                fred = new Fred( .007, doors[3], d, hall, propDoor1);
                bon = new Bon( 0.000012, doors[0], d, hall, propDoor2);
                fox = new Fox( 0.007, doors[2], d);
                chi = new Chi(7_000_000_000L, doors[1], d);
                Cam.setCamBreaks( .006, 0.001);
                break;
            default:
                throw new RuntimeException("Invalid night selected.");
        }
        Cam cam1 = new Cam(300, 290, bon, d, true);
        Cam cam2 = new Cam(700, 290, chi, d, true);
        Cam cam3 = new Cam(1100, 290, fox, d, true);
        Cam cam4 = new Cam(1500, 290, fred, d, false);
        Clock clock = new Clock(fred, bon, fox, d);
        Ticker ticker = new Ticker();
        X x = new X(d, ticker);
        ticker.addEntity(doors[0]);
        ticker.addEntity(doors[1]);
        ticker.addEntity(doors[2]);
        ticker.addEntity(doors[3]);
        ticker.addEntity(cam1);
        ticker.addEntity(cam2);
        ticker.addEntity(cam3);
        ticker.addEntity(cam4);
        ticker.addEntity(chi);
        ticker.addEntity(fred);
        ticker.addEntity(bon);
        ticker.addEntity(fox);
        ticker.addEntity(clock);
        d.start();
        ticker.start();
    }
    
}
