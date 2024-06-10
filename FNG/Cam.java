package FNG;

import Game.Ticker;
import Game.Ticker.Entity;

import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import Game.Display;

public class Cam extends Entity {
    private final Random random = new Random();
    private Anam character;
    private Display display;
    private JLabel image;
    private JLabel brokenImage;
    private boolean onHover;
    private boolean crashed;
    private boolean toCrash = false;
    private boolean canCrashActive;
    private static Raffle<Cam> raffle = new Raffle<>();
    private static final int STUBORN = 1_000;
    private static ArrayList<Cam> cams = new ArrayList<>();
    private static double camFragility;
    private static double camRecovery;
    Cam(int x, int y, Anam character, Display display, boolean canCrashActive) {
        this.canCrashActive = canCrashActive;
        cams.add(this);
        this.character = character;
        this.display = display;
        this.onHover = false;
        raffle.add(this, STUBORN);
        String path = new File("").getAbsolutePath();
        this.image = this.display.addImage(path+"/Photo/Camera.png", 20, 20, x, y);
        this.brokenImage = this.display.addImage(path+"/Photo/CameraBroken.png", 20, 20, x, y);
        this.brokenImage.setVisible(false);
        this.image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                character.startObserving();
                onHover = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (toCrash) {
                    toCrash = false;
                    crash();
                }
                else {
                    character.stopObserving();
                }
                onHover = false;
            }
        });
        this.brokenImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                onHover = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                onHover = false;
            }
        });
    }
    public void update() {
        if (crashed) {
            chanceToFix(camRecovery);
            return;
        }
        if (onHover) {
            raffle.add(this);
            chanceToCrash(1.0 * raffle.getAmount(this) / raffle.size() * camFragility);
            if (raffle.size() > 2 * STUBORN) {
                for (int i = 0; i != cams.size(); i++) {
                    raffle.setAmount(cams.get(i), raffle.getAmount(cams.get(i)) / 2);
                }
            }
        }
    }
    public void chanceToCrash(double chance) {
        double nextDouble = random.nextDouble();
        if (chance > nextDouble) {
            if (canCrashActive) {
                crash();
            }
            toCrash = true;
        }
    }
    public void crash() {
        this.brokenImage.setVisible(true);
        this.image.setVisible(false);
        crashed = true;
        character.stopObserving();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
        @Override
        public void run() {
            fix();
        }
        }, 17_345);
    }
    public void chanceToFix(double chance) {
        if (chance > random.nextDouble()) {
            fix();
        }
    }
    public void fix() {
        if (!crashed) {
            return;
        }
        this.brokenImage.setVisible(false);
        this.image.setVisible(true);
        crashed = false;
        if (onHover) {
            character.startObserving();
        }
    }
    public static void setCamBreaks(double camFragilityP, double camRecoveryP) {
        camRecovery = camRecoveryP;
        camFragility = camFragilityP;
    }
}
