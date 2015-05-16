/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.object;

import game.creature.Creature;
import game.main.sprite.Sprite;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Whizzpered
 */
public abstract class Item {

    public int type;
    public Image icon;
    public Sprite sprite;

    abstract void aply();

    public void renderIcon(Graphics g, int x, int y) {
        icon.draw(x, y);
    }

    public void render(Graphics g, Creature cr) throws SlickException {
        sprite.render(cr.side, (int) (cr.x - cr.getWidth() / 2), (int) (cr.y - cr.getHeight() / 2));
    }
}
