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
public class RaiderArc extends Raider {

    @Override
    public void initImages() {
        super.initImages();
        sprite = new Sprite("arc/");
    }

    @Override
    public void init(Object... args) {
        super.init(args);
        ranged = true;
        range = 200;
    }

}
