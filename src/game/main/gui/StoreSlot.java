/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.main.gui;

import game.main.shell.Game;
import static game.main.shell.Game.font;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Whizzpered
 */
public class StoreSlot extends Slot {

    public StoreSlot(int x, int y) {
        super(x, y);
    }

    public StoreSlot(buttonState st, int y) {
        super(st, y);
    }

    @Override
    public void click() {
        if (Slot.inHand == null && Game.dungeon.player.gold >= item.price) {
            Slot.inHand = item;
            Game.dungeon.player.gold -= item.price;
        }
    }

    @Override
    public void rclick() {

    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        if (item != null) {
            g.setColor(Color.white);
            font.drawString(cx + Display.getWidth() / 2 - 4, y + 32, item.price + "", Color.white);
        }
    }
}
