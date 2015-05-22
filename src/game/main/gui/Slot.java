/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.main.gui;

import static game.main.scene.Scene.sprite;
import static game.main.shell.Game.font;
import game.object.Item;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author yew_mentzaki
 */
public class Slot extends Button {

    public static Item inHand;
    boolean rb;
    public Item item;

    public Slot(buttonState st, int y) {
        super(st, y, 16, "", Color.green);
    }

    public Slot(int x, int y) {
        super(x, y, 16, "", Color.green);
    }

    @Override
    public void click() {
        Item i = item;
        item = inHand;
        inHand = i;
    }

    public void rclick() {
        item = null;
    }
    
    @Override
    public void render(Graphics g) {
        int x;
        if (state == buttonState.LEFT) {
            x = cx + w / 2;
        } else if (state == buttonState.RIGHT) {
            x = cx + Display.getWidth() - w / 2;
        } else if (state == buttonState.CENTER) {
            x = cx + Display.getWidth() / 2;
        } else {
            x = cx;
        }
        int y = this.y;
        int w = this.w;
        int mx = Mouse.getX();
        int my = Display.getHeight() - Mouse.getY();
        sprite[0].setImageColor(color.r, color.g, color.b);
        sprite[1].setImageColor(color.r, color.g, color.b);
        sprite[2].setImageColor(color.r, color.g, color.b);
        if (state == buttonState.CENTER) {
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
        if (item != null) {
            item.renderIcon(g, x, y);
        }
        if (Math.abs(mx - x) < (w) && Math.abs(y + 25 - my) < 25) {
            color = Color.white;
            if (bp && !Mouse.isButtonDown(0)) {
                click();
            }
            if (rb && !Mouse.isButtonDown(1)) {
                rclick();
            }
        } else {
            color = Color.green;
            if (wp > 1) {
                wp /= 2;
            }
        }
        bp = Mouse.isButtonDown(0);
        rb = Mouse.isButtonDown(1);
    }

}