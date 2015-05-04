/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.creature;

import game.main.Ability;
import game.main.sprite.Sprite;
import game.object.Bullet;

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
        abils = new Ability[1];
        super.init(args);
        ranged = true;
        range = 200;

    }

    Raider thisClass = this;

    @Override
    public void initAbils() {
        abils[0] = new Ability(800, false, 0) {
            @Override
            public void action() {
                 System.out.println("STEADE");
                double dist = Math.sqrt(Math.pow(x - dung.player.x, 2) + Math.pow(y - dung.player.y, 2));
                Player pl = dung.player;
                if (dist < range + 96 && cd.is()) {
                    dung.bullets.add(new Bullet((int) x, (int) y, pl, thisClass));
                    dung.bullets.add(new Bullet((int) (x - 10), (int) y - 10, pl, thisClass));
                    dung.bullets.add(new Bullet((int) (x - 20), (int) y - 20, pl, thisClass));
                    start();
                }
            }
        };
    }

}
