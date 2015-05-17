/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.object;

import game.creature.Creature;
import game.main.sprite.Sprite;
import main.utils.Textures;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Whizzpered
 */
public abstract class Item {

    public int type;
    public Image icon;                              //For rendering on inventory or dropped on floor
    public Sprite sprite;                           //For rendering when weared on Creature

    public Item(String name) {
        icon = Textures.image("items/" + name + "/icon.png"); // Getting textures from <Name> folder
        sprite = new Sprite("items/" + name + "/");           // Just like Creature sprite
    }

    public abstract void aply(Creature cr);                 //Method for alrdy weared item

    public void renderIcon(Graphics g, int x, int y) {
        icon.draw(x, y);                                    //Just icon render method with  non-fixed coors
    }

    public void render(Graphics g, Creature cr) throws SlickException {
        sprite.render(cr.side, (int) (cr.x - cr.getWidth() / 2), (int) (cr.y - cr.getHeight() / 2));  
        // Render on Creature's sized pic item
    }
}
