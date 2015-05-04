/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.creature;

import game.main.Ability;
import game.main.sprite.Sprite;

/**
 *
 * @author Whizzpered
 */
public class RaiderPriest extends Raider {

    @Override
    public void initImages() {
        super.initImages();
        sprite = new Sprite("priest/");
    }

    @Override
    public void init(Object... args) {
        abils = new Ability[1];
        super.init(args);
        ranged = true;
        range = 200;
    }

    @Override
    public void initAbils() {
        abils[0] = new Ability(800, false, 0) {
            @Override
            public void action() {
                for (Raider raid : dung.getRaiders()) {
                    if (!raid.dead && raid.hp < raid.maxhp) {
                        if (raid.focused) {
                            heal((Raider) raid);
                        }
                    }
                }

                if (cd.is()) {
                    for (Raider raid : dung.getRaiders()) {
                        if (raid.dead) {
                            revive((Raider) raid);
                            break;
                        }
                    }
                }

                if (cd.is()) {
                    for (Raider raid : dung.getRaiders()) {
                        if (!raid.dead && raid.hp < raid.maxhp ) {
                            heal((Raider) raid);
                        }
                    }
                }
            }

            public void heal(Raider raid) {
                raid.hp += 20;
                if (raid.hp >= raid.maxhp) {
                    raid.hp = raid.maxhp;
                }
                System.out.println("READE");
                start();
            }

            public void revive(Raider raid) {
                raid.dead = false;
                heal(raid);
            }

        };
    }
}
