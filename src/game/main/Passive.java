/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main;

import main.utils.Textures;

/**
 *
 * @author Whizzpered
 */
public abstract class Passive extends Ability {

    @Override
    public void initImages(String name1, String name2) {
        icon = Textures.image("abilities/" + name1).getScaledCopy(1f);
        icon.setImageColor(100, 100, 100);
        strip = Textures.image("abilities/" + name2);
    }

    public Passive() {
        super(0, false, 0);
    }

    @Override
    public void tick() {
        action();
    }

}
