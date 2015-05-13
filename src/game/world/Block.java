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
    public boolean solid;

    public Block(String sprite, boolean solid) {
        this.sprite = Textures.image(sprite).getScaledCopy(2f);
        this.sprite.setFilter(GL11.GL_NEAREST);
        this.solid = solid;
        size = this.sprite.getHeight();
    }

    public Block() {
    }

    public static void setBlocks() {
        block = new Block[128];
    }

    public static void initSprites() {
        block[0] = new Block();
        block[1] = new Block("floor/some.png", false);
        block[2] = new Block("floor/wall.png", true);
    }

    public void render(Graphics g, int x, int y) {
        if (sprite != null) {
            GL11.glDisable(GL11.GL_BLEND);
            sprite.draw(x * size, y * size);
            GL11.glEnable(GL11.GL_BLEND);
        }
    }
}

