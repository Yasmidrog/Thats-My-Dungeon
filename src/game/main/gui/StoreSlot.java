/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.main.gui;

import game.main.shell.Game;
import static game.main.shell.Game.font;
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
    public void render(Graphics g) {
        super.render(g);
        if (item != null) {
            g.setColor(Color.white);
            font.drawString(cx, y + 13, item.price + "", Color.white);
            item.renderIcon(g, cx, y);
        }
    }
}
