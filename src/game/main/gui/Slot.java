/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.main.gui;

import static game.main.scene.Scene.sprite;
import game.main.shell.Game;
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

    @Override
    public void render(Graphics g) {
        int x = x = cx + w / 2;;
        int y = this.y;
        int w = this.w;
        int mx = Mouse.getX();
        int my = Display.getHeight() - Mouse.getY();
        sprite[0].setImageColor(color.r, color.g, color.b);
        sprite[1].setImageColor(color.r, color.g, color.b);
        sprite[2].setImageColor(color.r, color.g, color.b);
        sprite[0].draw(x - w / 2 - 16, y);
        sprite[1].draw(x - w / 2, y, w, 50);
        sprite[2].draw(x + w / 2, y);
        
        if (item != null) {
            item.renderIcon(g, x, y);
        }
        if (Math.abs(mx - x) < (w) && Math.abs(y + 25 - my) < 25) {
            color = Color.white;
            if (bp && !Mouse.isButtonDown(0)) {
                click();
            }
        } else {
            color = Color.green;
            if (wp > 1) {
                wp /= 2;
            }
        }
        bp = Mouse.isButtonDown(0);
    }

}
