/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.object;

import game.main.scene.Dungeon;
import game.main.sprite.Sprite;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Whizzpered
 */
public abstract class Modificator extends game.creature.Entity {

    Image sprite;
    Dungeon dung;

    public Modificator(int x, int y, int index, Dungeon d) {
        this.x = x;
        this.y = y;
        dung = d;
        sprite = d.sprites.get(4 + index);
    }

    public abstract void aply();

    @Override
    public void tick() {
        double dist = Math.sqrt(Math.pow(dung.player.x - x, 2) + Math.pow(dung.player.y - y, 2));

        if (dist < dung.player.getWidth() / 2) {
            aply();
            dung.objects.remove(this);
        }
    }

    @Override
    public void render(Graphics g) {
        sprite.draw((float) x, (float) y);
    }
}
