/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.object;

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

    public Dungeon dung;
    private Image image;
    public int getSize() {
        return 64;
    }

    public Object(int x, int y, Dungeon dung) {
        this.dung = dung;
        this.x = x;
        this.y = y;
    }
    public void initImage(Image im){
        image=im;
    }
    public void collision() {
        for (Raider r : dung.getRaiders()) {
            if (!r.dead) {
                double d = sqrt(Math.pow(r.x - x, 2) + pow(r.y - y, 2));
                if (d < getSize() / 2 + r.getHeight() / 2) {
                    double a = atan2(r.y - y, r.x - x);
                    r.x += cos(a) * (r.getWidth() / 2 - d) / 4;
                    r.y += sin(a) * (r.getWidth() / 2 - d) / 4;
                }
            }
        }
    }
    @Override
    public void render(Graphics g){
        image.draw((float)x,(float)y);
    }

}
