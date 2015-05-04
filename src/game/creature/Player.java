/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.creature;

import game.main.gui.Bar;
import game.main.sprite.Sprite;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Whizzpered
 */
public class Player extends Creature {

    public Bar healthbar;
    
    @Override
    public void initImages() {
        sprite = new Sprite("warrior/");
        healthbar = new Bar(maxhp, "Bar.png", "health.png");
    }

    @Override
    public void init(Object... args) {
        super.init(args);
        speed = 2;
        ranged = false;
        range = getWidth() / 4;
        setTimer("kick", 200);
    }

    @Override
    public void reset() {
        super.reset();
        dung.flag.done = true;
    }

    @Override
    public void tick() {
        super.tick();
        if (focus != null) {
            battle();
        }
    }

    public void focussmth(Raider r) {
        if (focus != null) {
            focus.focused = false;
            focus = null;
        }
        focus = r;
        focus.focused = true;
    }

    public void unfocus() {
        if (focus != null) {
            focus.focused = false;
            focus = null;
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
