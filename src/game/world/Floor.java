/*
 * Did by Whizzpered. 
 * All code is mine.
 */
package game.world;

import org.newdawn.slick.Graphics;

/**
 *
 * @author Whizzpered
 */
public class Floor {

    public int w = 12, h = 16;
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

    public void render(Graphics g) {
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                Block.block[get(x, y)].render(g, x, y);
            }
        }
    }

}
