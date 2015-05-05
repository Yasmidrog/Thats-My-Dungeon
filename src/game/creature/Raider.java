/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.creature;

import game.main.Ability;
import game.main.sprite.Sprite;
import game.object.Bullet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.utils.Textures;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Whizzpered
 */
public class Raider extends Creature {

    public Random r = new Random();
    public Animation bar;
    public Ability[] abils;
    

    @Override
    public int getWidth() {
        return 96;
    }

    @Override
    public int getHeight() {
        return 96;
    }

    @Override
    public void initImages() {
        bar = Textures.animation("anim").slickAnimation;
    }

    @Override
    public void die() {
        if (getTimer("dying").is()) {
            dung.report(nick + " left the game!", 500);
            //dung.objects.add(new Modifier((int) x, (int) y, 1));
            dung.raiders[index] = null;
        }
        if (dung.player.focus == this) {
            dung.player.focus = null;
        }
    }
    
    @Override
    public void init(Object... args) {
        super.init(args);
        enemy = true;
        focus = dung.player;
        speed = 3;
        nick = "cop"+index;
        setTimer("kick", 120);
        setTimer("chat", 600);
        initAbils();
        dung.report(nick + " joined the game!", 500);   
        //setNick();
        //dung.report(nick + " joined the game!", 500);
    }

    @Override
    public void tick() {
        super.tick();
        for (Ability ab : abils) {
            ab.tick();
        }
        useAbility();
        battle();
    }

    @Override
    public void deadtick() {
        checkTimers();
        //chating();
        die();
    }

    public void useAbility() {
        for (Ability ab : abils) {
            if (ab.ready()) {
                ab.action();
            }
        }
    }

    public void emulateChat() {
        if (getTimer("chat").is()) {
            if (!dead) {
                dung.chat.add(dung.chat.dialog[r.nextInt(dung.chat.dialog.length)], "Cop"+index);
            } else {
                dung.chat.add(dung.chat.rage[r.nextInt(dung.chat.rage.length)], "Coppa"+index);
            }
            getTimer("chat").start();
        }
    }

    @Override
    public void move() {
        super.move();
        double dist = distance(x, y, focus.x, focus.y);
        double angle = Math.atan2(focus.y - y, focus.x - x);
        if (dist > dmgDistance || dist < Math.max(dmgDistance / 2, focus.dmgDistance / 3 * 2)) {
            ex = focus.x - Math.cos(angle) * dmgDistance;
            ey = focus.y - Math.sin(angle) * dmgDistance;
            if (ex < 0 || ex >= 1024 || ey < 0 || ey >= 1024) {
                ex = focus.x - Math.cos(angle + Math.PI) * dmgDistance;
                ey = focus.y - Math.sin(angle + Math.PI) * dmgDistance;
            }
            if (ex < 0 || ex >= 1024 || ey < 0 || ey >= 1024) {
                ex = focus.x - Math.cos(angle - Math.PI) * dmgDistance;
                ey = focus.y - Math.sin(angle - Math.PI) * dmgDistance;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        try {
            if (focused) {
                bar.draw((int) x - getWidth() / 2, (int) y - getHeight() / 3);
            }
            sprite.render(side, (int) x - getWidth() / 2, (int) y - getHeight() / 2);
            g.setColor(new Color(Color.red.getRed(), Color.red.getGreen(), Color.red.getBlue(), 100));
            g.fillRect((float)x-48, (float)y-52, 96, 7);
            g.setColor(org.newdawn.slick.Color.red);
            g.fillRect((float)x-48, (float)y-52, 96 * hp / maxhp,7);
        } catch (SlickException ex) {
            Logger.getLogger(Raider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
