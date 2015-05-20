/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main.gui;

import static game.main.shell.Game.font;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Whizzpered
 */
public class Bar {

    public int maxvalue;
    public static Image bar, scale;

    public Bar(int max, String bar, String scale) {
        maxvalue = max;
        try {
            this.bar = new Image("res/textures/gui/" + bar);
            this.scale = new Image("res/textures/gui/" + scale);
        } catch (SlickException ex) {
            Logger.getLogger(Bar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void render(Graphics g, int x, int y, int value) {
        g.fillRect(x + 4, y + 17, (bar.getWidth() - 10) * value / maxvalue, scale.getHeight(), scale, 1, 1);
        bar.draw(x, y);
        font.drawString(x + (bar.getWidth() / 2) - 33, y + bar.getHeight() / 2 - 5, value + "|" + maxvalue, Color.darkGray);
        font.drawString(x + (bar.getWidth() / 2) - 35, y + bar.getHeight() / 2 - 8, value + "|" + maxvalue, Color.white);
    }
}
