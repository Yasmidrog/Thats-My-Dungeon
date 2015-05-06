/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main;

/**
 *
 * @author Юрий
 */
public abstract class Passive extends Ability {

    public Passive() {
        super(0, false, 0);
    }
    
    @Override
    public void tick() {
        action();
    }

}
