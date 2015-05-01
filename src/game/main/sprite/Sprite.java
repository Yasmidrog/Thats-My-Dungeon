/*
 * Did by Whizzpered. 
 * All code is mine.
 */
package game.main.sprite;

import static game.main.sprite.Side.*;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import main.utils.Textures;

/**
 *
 * @author yew_mentzaki & whizzpered
 */
public class Sprite {

    String s;

    public Sprite(String s) {
        this.s = s;
    }

    Image back = null, front = null, left = null, right = null;

    public void render(Side spriteSide, int x, int y) throws SlickException {
        if (back == null) {
            back = Textures.image(s + "back.png").getScaledCopy(2f);
            back.setFilter(GL11.GL_NEAREST);
            front =Textures.image(s + "front.png").getScaledCopy(2f);
            front.setFilter(GL11.GL_NEAREST);
            left =Textures.image(s + "left.png").getScaledCopy(2f);
            left.setFilter(GL11.GL_NEAREST);
            right = Textures.image(s + "right.png").getScaledCopy(2f);
            right.setFilter(GL11.GL_NEAREST);
        }
        if (spriteSide == FRONT) {
            front.draw(x, y);
        }
        if (spriteSide == BACK) {
            back.draw(x, y);
        }
        if (spriteSide == RIGHT) {
            right.draw(x, y);
        }
        if (spriteSide == LEFT) {
            left.draw(x, y);
        }

    }
}
