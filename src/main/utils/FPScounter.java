/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package main.utils;

import static game.main.shell.Game.font;
import java.util.Calendar;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Юрий Whizzpered
 */
public class FPScounter {

    static public int fps, frames;
    static int old = Calendar.getInstance().get(Calendar.SECOND);

    public static void tick() {
       frames++;
        if (old != Calendar.getInstance().get(Calendar.SECOND)) {
            fps = frames;
            frames = 0;
            old = Calendar.getInstance().get(Calendar.SECOND);
        } 
    }
    
    public static void render(Graphics g) {
        
        g.setColor(fps < 100 ? Color.yellow : Color.green);
        font.drawString(Display.getWidth() / 2 - 80, 10, "FPS:" + fps, fps < 100 ? Color.yellow : Color.green);
    }
}
