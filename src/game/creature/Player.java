/*
 * Did by Whizzpered. 
 * All code is mine.
 */
package game.creature;

import game.main.sprite.Sprite;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Whizzpered
 */
public class Player extends Creature {

    @Override
    public void initImages() {
        sprite = new Sprite("warrior/");
        //healthbar = new Bar(maxhp, "Bar.png", "health.png");
    }

    @Override
    public void init(Object... args) {
        super.init(args);
        ranged = false;
        range = getWidth() / 4;
    }

    public void reset() {
        ex = 0;
        ey = 0;
        vx = 0;
        vy = 0;
        dung.flag = null;
    }

    @Override
    public void tick() {
        super.tick();

        double dist = Math.sqrt(Math.pow(x - ex, 2) + Math.pow(y - ey, 2));
        if (dist < 6) {
            reset();
        }
    }

    @Override
    public void render(Graphics g) {
        try {
            sprite.render(side, (int) x - getWidth() / 2, (int) y - getHeight() / 2);
        } catch (SlickException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
