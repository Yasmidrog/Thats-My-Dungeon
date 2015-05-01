package main.utils;

import java.io.Serializable;

/**
 *
 * @author Юрий
 */
public class Timer implements Serializable {

    public int period, tick;

    public Timer(int period) {
        this.period = period;
    }

    public void start() {
        tick = period;
    }

    public int get() {
        return tick;
    }

    public boolean is() {
        if (tick == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void tick() {
        tick--;
    }

}
