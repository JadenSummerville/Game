package FNG;

import Game.Ticker;

import javax.swing.JLabel;

import Game.Display;

public class Anam extends Ticker.Entity {
    public JLabel image;
    public void startObserving() {}
    public void stopObserving() {}
    public boolean shockAttempt() {
        throw new RuntimeException("Method 'shockAttempt' was not Overridden");
    }
}
