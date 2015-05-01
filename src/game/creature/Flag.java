/*
 * Did by Whizzpered. 
 * All code is mine.
 */
package game.creature;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Юрий Whizzpered
 */
public class Flag {

    public double x, y;

    public Flag(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void render(Graphics g, Image spr) {
        spr.draw((int) x, (int) y);
    }
}
