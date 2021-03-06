/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.object;

import game.creature.Creature;
import game.creature.Mob;
import game.creature.Raider;
import game.main.gui.FloatText;
import game.main.scene.Dungeon;
import static game.main.scene.Dungeon.sprites;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Whizzpered
 */
public class Bullet extends game.creature.Entity {

    Dungeon dung;
    public Creature owner, target;
    public int damage, type, tim = 0;
    public double angle, speed;
    boolean miss;

    public Bullet(int x1, int y1, Creature target, Creature creature, boolean miss) {
        x = x1;
        y = y1;
        this.miss = miss;
        this.owner = creature;
        this.target = target;
        this.dung = owner.dung;
        if (miss) {
            owner.dung.text.add(new FloatText((int) x, (int) y, "Missed", owner.dung));
        }
        damage = owner.dmg;
        if (owner.ranged) {
            type = 1;
            speed = 4;
        } else {
            type = 0;
            speed = 8;
        }
        angle = getAngle();
    }

    public void move() {
        tick();
        if (x < 0 || x > 2000 || y < 0 || y > 2000) {
            dung.objects.remove(this);
        }
    }

    @Override
    public void tick() {
        if (!miss) {
            angle = getAngle();
        }
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;

        if (!miss) {
            if (owner.enemy) {
                if (Math.abs(x - dung.player.x) < dung.player.getWidth() / 2 && Math.abs(y - dung.player.y) < dung.player.getHeight() / 2) {
                    dung.player.hp -= owner.dmg;
                    hit(owner.dung);
                }
                for (Mob mob : dung.getMobs()) {
                    if (mob != owner && !mob.dead) {
                        if (Math.abs(x - mob.x) < mob.getWidth() / 2 && Math.abs(y - mob.y) < mob.getHeight() / 2) {
                            mob.hp -= owner.dmg;
                            hit(owner.dung);
                        }
                    }
                }
                
            } else {
                for (Raider raid : dung.getRaiders()) {
                    if (raid != owner && !raid.dead) {
                        if (Math.abs(x - raid.x) < raid.getWidth() / 2 && Math.abs(y - raid.y) < raid.getHeight() / 2) {
                            raid.hp -= owner.dmg;
                            hit(owner.dung);
                        }
                    }
                }
            }
        }
        if (x < 0 || x > 2000 || y < 0 || y > 2000) {
            dung.objects.remove(this);
        }
    }

    public void hit(Dungeon dung) {
        vx = 0;
        dung.objects.remove(this);
        dung.text.add(new FloatText((int) x, (int) y, "-" + damage, owner.dung));
    }

    @Override
    public void render(Graphics g) {
        if (type == 1) {
            sprites.get(0).draw((int) x, (int) y);
        }
    }

    protected double getAngle() {
        if (target != null) {
            int dx = (int) (target.x - x);
            int dy = (int) (target.y - y);
            return Math.atan2(dy, dx);
        }
        return 0;
    }
}
