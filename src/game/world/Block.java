/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.world;

import main.utils.Textures;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Юрий
 */
public class Block {

    public static int size = 48;
    public static Block[] block;
    public Image sprite;

    public static void setBlocks() {
        block = new Block[128];
        for (int i = 0; i < block.length; i++) {
            block[i] = new Block();
        }
    }

    public static void initSprites() {
        block[1].sprite = Textures.image("floor/some.png");
        block[2].sprite = Textures.image("floor/wall.png");

        for (int i = 1; i < 2; i++) {
            block[i].sprite.setFilter(GL11.GL_NEAREST);
        }
    }

    public void render(Graphics g, int x, int y) {
        if (sprite != null) {
            GL11.glDisable(GL11.GL_BLEND);
            sprite.draw(x * size, y * size);
            GL11.glEnable(GL11.GL_BLEND);
        }
    }
}
