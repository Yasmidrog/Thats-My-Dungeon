/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.creature;

import game.main.Ability;
import game.main.shell.Game;
import game.main.sprite.Sprite;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.utils.Textures;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Whizzpered
 */
public class Mob extends Creature {

    @Override
    public void initImages() {                  //Initialization of focus Bar
        sprite = new Sprite("pries/");
    }

    @Override
    public void init(Object... args) {
        super.init(args);
        enemy = false;
        speed = 3;
        nick = "Slave" + index;           //Just case we're NIGGAS
        level = (int) args[4];
        ranged = false;
        range = getWidth()/4;
        setTimer("kick", 120);
        initAbils();
    }
    
    @Override
    public void die() {
        delete();
    }

    public void delete() {
        int s = 0;
        for (int i = 0; i < dung.mobs.length; i++) {
            if (dung.mobs[i].index == index) {
                dung.mobs[i] = null;
                s = i + 1;
                break;
            }
        }

        for (int i = s; i < dung.mobs.length; i++) {
            dung.mobs[i - 1] = dung.mobs[i];
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        if (focus == null) {
            patrool();
        } else {
            if (!focus.dead) {
                battle();
            }
        }
    }

    public void patrool() {
        for (Raider r : dung.getRaiders()) {
            if (!r.dead) {
                double dist = Math.sqrt(Math.pow(r.x - x, 2) + Math.pow(r.y - y, 2));
                if (dist < 300) {
                    focus = r;
                    return;
                }
            }
        }
        
    }

    @Override
    public void render(Graphics g) {
        try {
            sprite.render(side, (int) x - getWidth() / 2, (int) y - getHeight() / 2);
            renderItems(g);
            renderHP(g);
        } catch (SlickException ex) {
            Logger.getLogger(Raider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void renderHP(Graphics g) {                  //rendering healthbar
        g.setColor(new Color(Color.red.getRed(), Color.red.getGreen(), Color.red.getBlue(), 100));
        g.fillRect((float) x - 48, (float) y - 52, 96, 7);
        g.setColor(Color.red);
        g.fillRect((float) x - 48, (float) y - 52, 96 * (int) hp / maxhp, 7);
    }
}
