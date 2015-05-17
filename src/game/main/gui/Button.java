/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main.gui;

import static game.main.scene.Menu.sprite;
import game.main.shell.Game;
import static game.main.shell.Game.font;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Whizzpered
 */
public abstract class Button {

    public static enum buttonState {
        LEFT, RIGHT, CENTRE
    }
    public buttonState state;
    Game game = new Game();
    public int cx, y, w, wp = 1;
    String text;
    Color color;
    boolean bp;

    protected Button() {
    }

    public Button(int x, int y, int w, String text, Color c) {
        state = buttonState.CENTRE;
        cx = x + Display.getWidth() / 2;
        this.y = y;
        this.w = w;
        this.text = text;
        this.color = c;
    }

    public Button(buttonState st, int y, int w, String text, Color c) {
        state = st;
        cx = Display.getWidth() / 2 - w / 5;
        this.y = y;
        this.w = w;
        this.text = text;
        this.color = c;
    }

    public void render(Graphics g) {
        int x;
        if (state == buttonState.LEFT) {
            x = w / 2;
        } else if (state == buttonState.RIGHT) {
            x = Display.getWidth() - w / 2;
        } else if (state == buttonState.CENTRE) {
            x = cx;
        } else {
            x = 0;
        }
        int y = this.y;
        int w = this.w + wp;
        int mx = Mouse.getX();
        int my = Display.getHeight() - Mouse.getY();
        sprite[0].setImageColor(color.r, color.g, color.b);
        sprite[1].setImageColor(color.r, color.g, color.b);
        sprite[2].setImageColor(color.r, color.g, color.b);
        if (state == buttonState.CENTRE) {
            sprite[0].draw(x - w / 2 - 16, y);
            sprite[1].draw(x - w / 2, y, w, 50);
            sprite[2].draw(x + w / 2, y);
        } else if (state == buttonState.LEFT) {
            sprite[1].draw(x - w / 2, y, w, 50);
            sprite[2].draw(x + w / 2, y);
        } else if (state == buttonState.RIGHT) {
            sprite[0].draw(x - w / 2, y, sprite[0].getWidth(), 50);
            sprite[1].draw(x - w / 2 + sprite[0].getWidth(), y, w, 50);
        }
        g.setColor(Color.white);
        font.drawString(x - (text.length() * 8) / 2, y + 13, text, Color.white);

        if (Math.abs(mx - x) < (w + wp) / 2 && Math.abs(y + 25 - my) < 25) {
            if (wp < 16) {
                wp *= 2;
            }
            if (bp && !Mouse.isButtonDown(0)) {
                click();
            }
        } else {
            if (wp > 1) {
                wp /= 2;
            }
        }
        bp = Mouse.isButtonDown(0);
    }

    abstract public void click();
}
