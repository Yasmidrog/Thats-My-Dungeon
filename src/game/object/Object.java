/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.object;

import game.creature.Creature;
import game.creature.Raider;
import game.main.scene.Dungeon;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import static java.lang.Math.*;

/**
 *
 * @author Whizzpered
 */
public class Object extends game.creature.Entity {

    public boolean solid;
    public Dungeon dung;
    private Image image;

    public int getSize() {
        return 64;
    }

    public Object(int x, int y, Dungeon dung, boolean solid) {
        this.dung = dung;
        this.x = x;
        this.y = y;
        this.solid = solid;
    }

    public void initImage(Image im) {
        image = im;
    }

    public void collision() {
        if (solid) {
            for (Raider r : dung.getRaiders()) {
                if (!r.dead) {
                   collide(r);
                    }
                }
            collide(dung.player);
            for(Bullet b:dung.getBullets()){
                double d = sqrt(Math.pow(b.x - x, 2) + pow(b.y - y, 2));
                if (d < getSize() / 3 ) {
                    dung.bullets.remove(b);
                }
              }
            }
        }


    @Override
    public void render(Graphics g) {
        image.draw((float) x, (float) y);
    }
    private void collide(Creature r) {
        double d = sqrt(Math.pow(r.x - x, 2) + pow(r.y - y, 2));
        if (d < getSize() / 3 + r.getHeight() / 3) {
            double a = atan2(r.y - y, r.x - x);
            r.x += cos(a) * (r.getWidth() - d) / 20;
            r.y += sin(a) * (r.getWidth() - d) / 20;
        }
    }
}
