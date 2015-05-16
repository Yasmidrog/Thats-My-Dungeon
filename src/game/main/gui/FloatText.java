/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main.gui;

import game.main.scene.Dungeon;
import static game.main.shell.Game.font;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Whizzpered
 */
public class FloatText {

    public double x, y;
    public int time;
    public String message;
    public Dungeon dung;

    public FloatText(int x, int y, String s, Dungeon dung) {
        this.x = x;
        this.y = y;
        message = s;
        this.dung = dung;
    }

    public void render(Graphics g) {
        y -= 0.5;
        time++;
        font.drawString((float) x, (float) y, message);
        if (time >= 80) {
            dung.text.remove(this);
        }
    }

}
