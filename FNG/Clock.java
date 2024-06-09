package FNG;

import java.io.File;

import javax.swing.JLabel;
import java.util.Timer;
import java.util.TimerTask;

import Game.Display;
import Game.Ticker;

public class Clock extends Ticker.Entity {
        private final int SIZE = 100;
        private JLabel t12;
        private JLabel t1;
        private JLabel t2;
        private JLabel t3;
        private JLabel t4;
        private JLabel t5;

        private Fred fred;
        private Bon bon;
        private Fox fox;

    public Clock(Fred fred, Bon bon, Fox fox, Display display) {
        int x = 0;
        int y = 0;

        this.fred = fred;
        this.bon = bon;
        this.fox = fox;

        String path = new File("").getAbsolutePath();
        this.t12 = display.addImage(path+"/Photo/12am.png", SIZE, SIZE, x, y);
        this.t1 = display.addImage(path+"/Photo/1am.png", SIZE, SIZE, x, y);
        this.t1.setVisible(false);
        this.t2 = display.addImage(path+"/Photo/2am.png", SIZE, SIZE, x, y);
        this.t2.setVisible(false);
        this.t3 = display.addImage(path+"/Photo/3am.png", SIZE, SIZE, x, y);
        this.t3.setVisible(false);
        this.t4 = display.addImage(path+"/Photo/4am.png", SIZE, SIZE, x, y);
        this.t4.setVisible(false);
        this.t5 = display.addImage(path+"/Photo/5am.png", SIZE, SIZE, x, y);
        this.t5.setVisible(false);
    }
    @Override
    public void start() {
        Zscore zScoref = new Zscore(.7,1,10, -10);
        Zscore zScoreb = new Zscore(.5,3,10, -10);
        
        long timePerHour = 90_000L;//10_000L;
        //System.out.println(timePerHour);
        Timer timer = new Timer();

        // 1am
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                t12.setVisible(false);
                t1.setVisible(true);
                //
                hour(zScoref, zScoreb);
            }
        }, 1*timePerHour);
        // 2am
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                t1.setVisible(false);
                t2.setVisible(true);
                //
                hour(zScoref, zScoreb);
            }
        }, 2*timePerHour);
        // 3am
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                t2.setVisible(false);
                t3.setVisible(true);
                //
                hour(zScoref, zScoreb);
                fox.incrementSpeed(.007);
            }
        }, 3*timePerHour);
        // 4am
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                t3.setVisible(false);
                t4.setVisible(true);
                //
                hour(zScoref, zScoreb);
            }
        }, 4*timePerHour);
        // 5am
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                t4.setVisible(false);
                t5.setVisible(true);
                //
                hour(zScoref, zScoreb);
            }
        }, 5*timePerHour);
        // 6am
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                t5.setVisible(false);
                //
            }
        }, 6*timePerHour);
        //
    }
    private void hour(Zscore zScoref, Zscore zScoreb) {
        bon.incrementAccel(0.0000015*zScoreb.generate_value());
        fred.incrementSpeed(0.0007*zScoref.generate_value());
    }
}
