/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.creature;

import game.main.sprite.Sprite;

/**
 *
 * @author Whizzpered
 */
public class RaiderWar extends Raider {

    @Override
    public void initImages() {
        super.initImages();
        sprite = new Sprite("war/");
    }

    @Override
    public void init(Object... args) {
        super.init(args);
        ranged = false;
        range = getWidth() / 4;
    }

}
