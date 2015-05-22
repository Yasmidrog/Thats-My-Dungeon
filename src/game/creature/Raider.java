/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.creature;

import game.main.Ability;
import game.main.shell.Game;
import game.main.sprite.Sprite;
import game.object.Bullet;
import game.object.Modificator;
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

    public Random r = new Random();             //Nah, i dunno why
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
    public void initImages() {                  //Initialization of focus Bar
        bar = Textures.animation("anim").slickAnimation;
    }

    @Override
    public void die() {                         // Raiders will die specifical
        if (getTimer("dying").is()) {
            dung.report(nick + " left the game!", 500);
            dung.delete(index);
            dung.objects.add(new Modificator((int) x + getWidth() / 2, (int) y + getHeight() / 2, 1, dung) {
                @Override
                public void aply() {
                    dung.player.gold++;
                }
            });
            dung.objects.add(new Modificator((int) x, (int) y, 2, dung) {
                @Override
                public void aply() {
                    dung.player.xp++;
                }
            });

            if (dung.player.focus == this) {
                dung.player.unfocus();
            }
        }
        if (dung.player.focus == this) {
            dung.player.unfocus();
        }

    }

    @Override
    public void init(Object... args) {
        super.init(args);
        enemy = true;
        focus = dung.player;
        speed = 3;
        nick = "cop" + index;           //Just case we're NIGGAS
        level = (int) args[4];
        setTimer("kick", 120);
        setTimer("chat", 600);
        initAbils();
    }

    @Override
    public void tick() {
        super.tick();

        if (focus !=null &&!focus.dead) {
            for (Ability ab : abils) {
                if (ab != null) {
                    ab.tick();
                }
            }
            useAbility();
            battle();
        }
    }

    @Override
    public void deadtick() {
        checkTimers();
        die();
    }

    public void useAbility() {          //Spam all of abils
        for (Ability ab : abils) {
            if (ab.ready()) {
                ab.action();
            }
        }
    }

    public void emulateChat() {             //Ya, so good 
        if (getTimer("chat").is()) {
            if (!dead) {
                dung.chat.add(dung.chat.dialog[r.nextInt(dung.chat.dialog.length)], nick);
            } else {
                dung.chat.add(dung.chat.rage[r.nextInt(dung.chat.rage.length)], nick);
            }
            getTimer("chat").start();
        }
    }

    @Override
    public void move() {                //mooving like a Creature, but with отступания
        super.move();
        double dist = distance(x, y, focus.x, focus.y);
        double angle = Math.atan2(focus.y - y, focus.x - x);
        if (dist > dmgDistance || dist < Math.max(dmgDistance / 2, focus.dmgDistance / 3 * 2)) {
            ex = focus.x - Math.cos(angle) * dmgDistance;
            ey = focus.y - Math.sin(angle) * dmgDistance;
        }

        if (focus == null) {
            for (Mob m : dung.getMobs()) {
                double d = Math.sqrt(Math.pow(m.x - x, 2) + Math.pow(m.y - y, 2));

                if (d < 500) {
                    focus = m;
                    break;
                }
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
            renderItems(g);
            renderHP(g);
            Game.font.drawString((int) x - getWidth() / 2 - 12, (int) y - getHeight() / 2 - 40, nick + "  Level " + level);
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
