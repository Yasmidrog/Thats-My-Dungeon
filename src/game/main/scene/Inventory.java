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

    ArrayList<Slot> slots = new ArrayList<>();
    Name[] name = new Name[3];
    private int currentMenu = 0;

    @Override
    public void init() throws SlickException {
        GL11.glLoadIdentity();
        for (int i = 0; i < 8; i++) {
            slots.add(new Slot(64 * i + 32, 32));
            slots.add(new Slot(64 * i + 32, 96));
        }
        for (int i = 0; i < 4; i++) {
            slots.add(new Slot(64 * i + 32, 96) {

                @Override
                public void render(Graphics g) {
                    System.out.println("KILL US");
                    y = Display.getHeight() - 32 - 64;
                    super.render(g); 
                }
                
            });
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g); //To change body of generated methods, choose Tools | Templates.
        for (Slot s : slots) {
            s.render(g);
        }
    }
    
    

}
