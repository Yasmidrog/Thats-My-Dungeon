/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main;

import org.lwjgl.input.Keyboard;

/**
 *
 * @author Whizzpered
 */
public abstract class Active extends Ability {

    public Active(int cd, boolean dur, int dura) {
        super(cd, dur, dura);
    }

    @Override
    public void tick() {
        super.tick();
        try {
            if (Keyboard.isKeyDown(Keyboard.getKeyIndex(number+ "")) && cd.is()) {
                action();
            }
        }catch (IllegalStateException ignored){}
    }

}
