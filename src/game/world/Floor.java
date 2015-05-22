/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.world;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Whizzpered
 */
public class Floor {

    public int w, h;
    public int[][] floor = new int[w][h];

    public void init() {
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                set(x, y, 1);
            }
        }

        for (int x = 0; x < w; x++) {
            set(x, 0, 2);
            set(x, h - 1, 2);
        }
        for (int y = 0; y < h; y++) {
            set(0, y, 2);
            set(w - 1, y, 2);
        }
    }

    public void set(int x, int y, int type) {
        if (type < 0) {
            type = 0;
        }
        if (x >= 0 && x < w && y >= 0 && y < h) {
            floor[x][y] = type;
        }
    }

    public int get(int x, int y) {
        if (x >= 0 && x < w && y >= 0 && y < h) {
            return (floor[x][y]);
        } else {
            return 0;
        }
    }

    public void render(Graphics g, int camx, int camy) {
        for (int x = (camx) / Block.size - 1; x < (camx + Display.getWidth()) / Block.size + 1; x++) {
            for (int y = (camy) / Block.size - 1; y < (camy + Display.getHeight()) / Block.size + 1; y++) {
                Block.block[get(x, y)].render(g, x, y);
            }
        }
    }

}
