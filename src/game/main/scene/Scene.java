/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main.scene;

import game.main.gui.Button;
import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Whizzpered
 */
public class Scene {

    ArrayList<Button> buttons = new ArrayList<>();

    public static Image[] sprite;
    public boolean paused;

    public void init() throws SlickException {

    }

    public void maintick() {
        if (!paused) {
            tick();
        } else {
            subtick();
        }
    }

    public void subtick() {

    }

    public void tick() {

    }

    public void render(Graphics g) {

    }

}
