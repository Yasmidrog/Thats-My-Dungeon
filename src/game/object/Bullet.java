/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.object;

import game.creature.Creature;
import game.main.scene.Dungeon;
import static game.main.scene.Dungeon.sprite;
import static game.main.shell.Game.font;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Whizzpered
 */
public class Bullet extends game.creature.Entity {

    public Creature owner, target;
    public int damage, type, tim = 0;
    public double angle, speed;
    boolean hit;

    public Bullet(int x1, int y1, Creature target, Creature creature) {
        x = x1;
        y = y1;
        this.owner = creature;
        this.target = target;
        damage = owner.dmg;
        if (owner.ranged) {
            type = 1;
            speed = 4;
        } else {
            type = 0;
            speed = 8;
        }
    }

    public void hit() {
        hit = true;
        vx = 0;
    }

    public void move(Dungeon dung) {
        tick(dung);
        if (x < 0 || x > 2000 || y < 0 || y > 2000) {
            dung.bullets.remove(this);
        }
    }

    public void tick(Dungeon dung) {
        if (hit) {
            afterlife(dung);
        } else {
            mtick(dung);
        }
    }

    public void afterlife(Dungeon dung) {
        tim++;
        if (tim == 70) {
            hit = false;
            tim = 0;
            dung.bullets.remove(this);
        }
    }

    public void mtick(Dungeon dung) {
        int dx = (int) (target.x - x);
        int dy = (int) (target.y - y);
        this.angle = Math.atan2(dy, dx);

        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;

        if (owner.enemy) {
            if (Math.abs(x - dung.player.x) < dung.player.getWidth() / 2 && Math.abs(y - dung.player.y) < dung.player.getHeight() / 2) {
                dung.player.hp -= owner.dmg;
                hit = true;
            }
        } else {
            for (Creature raid : dung.getCreatures()) {
                if (raid != owner && !raid.dead) {
                    if (Math.abs(x - raid.x) < raid.getWidth() / 2 && Math.abs(y - raid.y) < raid.getHeight() / 2) {
                        raid.hp -= owner.dmg;
                        hit = true;
                    }
                }
            }
        }
    }

    public void render(Graphics g) {
        if (hit) {
            font.drawString((int) (x), (int) (y - tim), "-" + damage, Color.red);
        } else if (type == 1) {
            sprite.get(0).draw((int) x, (int) y);
        }
    }

}
