/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.object;

import game.creature.Creature;
import game.creature.Raider;
import game.main.scene.Dungeon;
import static game.main.scene.Dungeon.sprites;
import static game.main.shell.Game.font;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Whizzpered
 */
public class Magic extends Bullet {

    public Image sprite;

    public void initImage() {
        sprite = Dungeon.sprites.get(4);
    }

    public Magic(int x1, int y1, Creature creature, double angle) {
        super(x1, y1, null, creature,false);
        this.angle = angle;
        type = 1;
        speed = 7;
        initImage();
    }

    @Override
    public void tick(Dungeon dung) {
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;

        if (owner.enemy) {
            if (Math.abs(x - dung.player.x) < dung.player.getWidth() / 2 && Math.abs(y - dung.player.y) < dung.player.getHeight() / 2) {
                dung.player.hp -= owner.dmg;
                hit = true;
            }
        } else {
            for (Raider raid : dung.getRaiders()) {
                if (raid != owner && !raid.dead) {
                    if (Math.abs(x - raid.x) < raid.getWidth() / 2 && Math.abs(y - raid.y) < raid.getHeight() / 2) {
                        raid.hp -= owner.dmg;
                        hit = true;
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (hit) {
            font.drawString((int) (x), (int) (y - tim), "-" + damage, Color.red);
        } else if (type == 1) {
            sprite.draw((int) x, (int) y);
        }
    }
}
