/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.creature;

import game.main.Ability;
import static game.main.scene.Dungeon.sprites;
import game.main.sprite.Sprite;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Whizzpered
 */
public class RaiderWar extends Raider {

    public boolean agro;

    @Override
    public void initImages() {
        super.initImages();
        sprite = new Sprite("war/");
    }

    @Override
    public void init(Object... args) {
        abils = new Ability[1];
        super.init(args);
        ranged = false;
        range = getWidth() / 4;
    }

    public RaiderWar thisClass = this;

    @Override
    public void die() {
        super.die();
        if (agro) {
            dung.player.agr = null;
            dung.player.focus = null;
        }
    }

    @Override
    public void initAbils() {
        abils[0] = new Ability(1000, true, 200) {
            @Override
            public void action() {
                dung.player.agr = thisClass;
                agro = true;
                dung.flag.done = true;
                dung.player.focussmth(thisClass);
                start();
            }

            @Override
            public void after() {
                dung.player.agr = null;
                agro = false;
            }
        };
    }

    @Override
    public void useAbility() {
        if (abils[0].cd.is() && dung.player.agr == null) {
            abils[0].action();
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);

        if (agro) {
            if (agro) {
                sprites.get(1).draw((int) x - 14, (int) y - getHeight());
            }
        }
    }

}
