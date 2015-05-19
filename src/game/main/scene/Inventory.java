/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main.scene;

import game.main.gui.Button;
import game.main.gui.Slot;
import game.main.gui.SwitchButton;
import game.main.gui.ValueButton;
import game.main.shell.Game;

import static game.main.shell.Game.dungeon;
import static game.main.shell.Game.font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.utils.Name;
import main.utils.Textures;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Whizzpered
 */
public class Inventory extends Scene {

    public boolean escape = false;
    public ArrayList<Slot> slots = new ArrayList<>();
    Name[] name = new Name[3];
    private int currentMenu = 0;
    public String key = "I";

    @Override
    public void init() throws SlickException {
        for (int i = 0; i < 4; i++) {
            slots.add(new Slot(64 * i - 140, 96) {

                @Override
                public void render(Graphics g) {
                    y = Display.getHeight() - 32 - 64;
                    super.render(g);
                }

            });
        }
        for (int i = 0; i < 8; i++) {
            slots.add(new Slot(64 * i - 268, 32));
            slots.add(new Slot(64 * i - 268, 96));
        }

    }

    @Override
    public void render(Graphics g) {
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
            Game.dungeon.player.items[i] = slots.get(i).item;
        }
        if (Keyboard.isKeyDown(Keyboard.getKeyIndex(Game.inventory.key))) {
            if (escape) {
                Game.currScene = Game.dungeon;
                escape = false;
            }
        }else{
            escape = true;
        }
    }

}
