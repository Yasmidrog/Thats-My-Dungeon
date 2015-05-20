/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.main.scene;

import game.creature.Creature;
import game.main.gui.Button;
import game.main.gui.Slot;
import game.main.gui.StoreSlot;
import game.main.shell.Game;
import game.object.Item;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Whizzpered
 */
public class Shop extends Scene {

    public ArrayList<StoreSlot> slots = new ArrayList<>();
    public boolean escape;

    @Override
    public void init() {
        int w = Display.getWidth();

        slots.add(new StoreSlot(64, 32));
        slots.get(0).item = new Item("pants") {
            @Override
            public void aply(Creature cr) {
                cr.maxhp += 20;
            }
        };
        slots.add(new StoreSlot(128, 32));
        slots.get(1).item = new Item("arms") {
            @Override
            public void aply(Creature cr) {
                cr.maxhp += 10;
            }
        };
        slots.add(new StoreSlot(172, 32));
        slots.get(2).item = new Item("braces") {
            @Override
            public void aply(Creature cr) {
                cr.dmg += 5;
            }
        };

        for (int i = 0; i < slots.size(); i++) {
            slots.get(i).state = Button.buttonState.CENTER;
        }
    }

    @Override
    public void render(Graphics g
    ) {
        Game.dungeon.render(g);
        GL11.glLoadIdentity();
        g.setColor(new Color(0, 0, 0, 0.5f));
        g.fillRect(0, 0, Display.getWidth(), Display.getHeight());
        for (Slot s : slots) {
            s.render(g);
        }
        if (Slot.inHand != null) {
            Slot.inHand.renderIcon(g, Mouse.getX(), Display.getHeight() - Mouse.getY());
        }
        for (int i = 0; i < 4; i++) {
            Game.dungeon.player.items[i] = Game.inventory.slots.get(i).item;
            Game.inventory.slots.get(i).render(g);
        }
        if (Keyboard.isKeyDown(Keyboard.getKeyIndex(Game.inventory.key))) {
            if (escape) {
                Game.dungeon.waveTimer.start();
                Game.currScene = Game.dungeon;
                escape = false;
            }
        } else {
            escape = true;
        }
    }
}
