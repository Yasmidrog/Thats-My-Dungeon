/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main;

import static game.main.shell.Game.font;
import main.utils.Textures;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Whizzpered
 */
public abstract class Passive extends Ability {

    @Override
    public void initImages(String name1, String name2) {
        key = "";
        icon = Textures.image("abilities/" + name1);
        strip = Textures.image("abilities/passive.png");
    }

    public Passive() {
        super(0, false, 0);
    }

    @Override
    public void tick() {
        action();
    }

    @Override
    public void renderIcon(Graphics g, int x, int y) {
        for (int i = 0; i < 31; i++) {
            strip.draw(x + 1, y - i * 2 + 62);
        }
        icon.draw(x, y);
        font.drawString(x + 32, y + 44, number + "", Color.white);
    }

}
