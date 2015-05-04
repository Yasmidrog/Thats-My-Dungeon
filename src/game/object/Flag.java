/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.object;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Юрий Whizzpered
 */
public class Flag {

    public double x, y;
    public boolean done;

    public Flag() {
        done = true;
    }
    
    public void set(double x, double y) {
        done = false;
        this.x = x;
        this.y = y;
    }

    public void render(Graphics g, Image spr) {
        if (!done) {
            spr.draw((int) x, (int) y);
        }
    }
}
