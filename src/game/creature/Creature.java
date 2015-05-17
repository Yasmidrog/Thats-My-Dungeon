/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.creature;

import game.main.Modifier;
import game.main.scene.Dungeon;
import game.main.sprite.Side;
import game.main.sprite.Sprite;
import game.object.Bullet;
import game.object.Item;
import game.world.Block;
import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.utils.Timer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Whizzpered
 */
public class Creature extends Entity {

    public Random missrand = new Random();
    public double ex, ey, hp;
    public int index, range, dmgDistance, speed, level, misschance = 0;
    public int maxhp, dmg;
    public int realhp, realdmg;
    public boolean dead, ranged, enemy, focused;
    public Sprite sprite;
    public String nick;
    public Creature focus;
    public Dungeon dung;
    public Side side = Side.FRONT;

    public Item[] items = new Item[1];

    ArrayList<Timer> timers = new ArrayList<>();
    ArrayList<String> timnames = new ArrayList<>();
    public ArrayList<Modifier> mods = new ArrayList<>();

    public void setTimer(String name, int tim) {
        timers.add(new Timer(tim));
        timnames.add(name);
    }

    public Timer getTimer(String name) {
        return timers.get(timnames.indexOf(name));
    }

    public Modifier[] getMods() {           //Gettin' Array of Modifiers for executing
        //CurrentModificator Esception
        Modifier[] a = new Modifier[mods.size()];
        mods.toArray(a);
        return a;
    }

    public int getWidth() {
        return 128;                           //Returns Size
    }

    public int getHeight() {
        return 128;                            //Returns Size
    }

    protected double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    @Override
    public void init(Object... args) {
        x = (Double) args[0];
        y = (Double) args[1];
        realhp = (int) args[2];                 //For Modifiers
        realdmg = (int) args[3];
        dmg = realdmg;
        maxhp = realhp;
        hp = maxhp;
        setTimer("dying", 1000);
    }

    public void collision() {               //Two kinds of Collision :
        crCollision();                      //For other Creatures
        objCollision();                     //For blocks and objects
    }

    public void crCollision() {
        for (Raider r : dung.getRaiders()) {
            if (!r.dead) {
                double d = sqrt(Math.pow(r.x - x, 2) + pow(r.y - y, 2));
                if (d < getWidth() / 3) {
                    double a = atan2(r.y - y, r.x - x);
                    r.x += cos(a) * (getWidth() - d) / 4;
                    r.y += sin(a) * (getWidth() - d) / 4;
                    x -= cos(a) * (getWidth() - d) / 4;
                    y -= sin(a) * (getWidth() - d) / 4;
                }
            }
        }
    }

    public void objCollision() {
        for (int i = (int) ((y - getHeight() / 2) / Block.size); i < (y + getHeight() / 2) / Block.size; i++) {
            if (Block.block[dung.floor.get((int) ((x - getWidth() / 2) / Block.size), (int) (i))].solid) {
                x += 2;
            }
            if (Block.block[dung.floor.get((int) ((x + getWidth() / 2) / Block.size), (int) (i))].solid) {
                x -= 2;
            }
        }
        for (int i = (int) ((x - getWidth() / 2) / Block.size); i < (x + getWidth() / 2) / Block.size; i++) {
            if (Block.block[dung.floor.get((int) (i), (int) ((y - getHeight() / 2) / Block.size))].solid) {
                y += 2;
            }
            if (Block.block[dung.floor.get((int) (i), (int) ((y + getHeight() / 2) / Block.size))].solid) {
                y -= 2;
            }
        }
    }

    public void initImages() {

    }

    public void initAbils() {

    }

    public void setStats() {
        maxhp = realhp;             //Getting native Stats
        dmg = realdmg;
        for (Item item : items) {   //Apply items
            if (item != null) {
                item.aply(this);
            }
        }

        for (Modifier mod : getMods()) {    //Aply Buffs and Debuffs
            mod.aply(this);
        }

    }

    @Override
    public void tick() {
        setStats();
        baseTick();             //Base actions for Creature
        move();                 //Method of Movings. For focusing and for just patrool
        collision();
        for (Modifier mod : getMods()) {
            mod.tick(this);
        }
    }

    public void die() {
        //DIIIIE MTHRFCKR
    }

    public void deadtick() {
        //ARE YOU STILL ALIVE????WTF
    }

    public void move() {
        if (focus != null) {
            double dist = distance(x, y, focus.x, focus.y);
            dmgDistance = range + getHeight() / 2 + focus.getWidth() / 2;
            if (dist >= dmgDistance) {
                double angle = Math.atan2(focus.y - y, focus.x - x);
                ex = (int) (focus.x - Math.cos(angle) * dmgDistance);
                ey = (int) (focus.y - Math.sin(angle) * dmgDistance);
            }
        }
        if (ex != 0 && ey != 0) {
            double angle = Math.atan2((ey - y), (ex - x));
            if (Math.abs(angle) <= (Math.PI / 4)) {
                side = Side.RIGHT;
            } else if (Math.abs(angle) >= ((Math.PI * 3) / 4)) {
                side = Side.LEFT;
            } else if (angle > 0) {
                side = Side.FRONT;
            } else {
                side = Side.BACK;
            }
            vx = Math.cos(angle) * speed;
            vy = Math.sin(angle) * speed;
            if (Math.abs(ex - x) < speed && Math.abs(ey - y) < speed) {
                reset();
            }
        }
    }

    public void reset() {
        vx = 0;
        vy = 0;
        //IDK just for fun
    }

    public void battle() {
        double dist = Math.sqrt(Math.pow(x - focus.x, 2) + Math.pow(y - focus.y, 2));
        if (dist - 2 * speed < dmgDistance && getTimer("kick").is()) {
            shoot();
        }
        //SHOOT THIS NIGGA IF YOU SEE HIM
    }

    public void shoot() {
        dung.bullets.add(new Bullet((int) x, (int) y, focus, this, miss()));
        getTimer("kick").start();
    }

    public void baseTick() {
        checkTimers();
        x += vx;
        y += vy;
        if (hp <= 0) {
            dead = true;
            focused = false;
            getTimer("dying").start();
        }
        if (hp >= maxhp) {
            hp = maxhp;
        }

        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x > dung.getWidth()) {
            x = dung.getWidth();
        }
        if (y > dung.getHeight()) {
            y = dung.getHeight();
        }
    }

    public void checkTimers() {
        for (Timer tim : timers) {
            if (!tim.is()) {
                tim.tick();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        //Dong see there, THATS EMPTY !!!
        g.setColor(Color.red);
        g.drawRect((int) x, (int) y, 64, 64);
    }

    protected boolean miss() {

        return missrand.nextInt(100) <= misschance;
    }

    public void renderItems(Graphics g) {  //Rendering weared items
        try {
            for (Item item : items) {
                if (item != null) {
                    item.render(g, this);
                }
            }
        } catch (SlickException ex) {
            Logger.getLogger(Creature.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deadrender(Graphics g) { //GRAAAAVE
        dung.sprites.get(2).draw((int) x - getWidth() / 2, (int) y - 32);
    }
}
