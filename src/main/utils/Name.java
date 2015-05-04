/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package main.utils;

import game.main.shell.Game;
import static game.main.shell.Game.font;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Юрий Whizzpered
 */
public class Name {

    Game game = new Game();
    int x, y, w;
    String text, text2;
    Color color, c2;
    boolean bp;

    public Name(int x, int y, String text, Color c, String text2, Color c2) {
        this.x = x;
        this.y = y;
        w = text.length() * 10;
        this.text = text;
        this.text2 = text2;
        this.color = c;
        this.c2 = c2;
    }

    public void render(Graphics g) {
        int x = this.x + Display.getWidth() / 2;
        int y = this.y;
        int w = this.w;
        int mx = Mouse.getX();
        int my = Display.getHeight() - Mouse.getY();

        font.drawString(x - w / 2, y + 3, bp ? text2 : text, bp ? c2 : Color.white);

        if (Math.abs(mx - x) < (w) / 2 && Math.abs(y + 25 - my) < 25) {
            bp = true;
        } else {
            bp = false;
        }
    }
}
