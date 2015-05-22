/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main;

import game.creature.Creature;

/**
 *
 * @author Whizzpered
 */
public abstract class Modifier {

    public int timer;

    public abstract void aply(Creature unit);

    public void tick(Creature unit) {
        timer--;
        if (timer <= 0) {
            unit.mods.remove(this);
        }
    }

}
