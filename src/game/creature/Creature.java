/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.creature;

import game.main.scene.Dungeon;
import game.main.sprite.Side;
import game.main.sprite.Sprite;
import game.object.Bullet;
import java.util.ArrayList;
import main.utils.Timer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Whizzpered
 */
public class Creature extends Entity {

    public double ex, ey;
    public int hp, maxhp, dmg, index, range, dmgDistance, speed;
    public boolean dead, ranged, enemy, focused;
    public Sprite sprite;
    public Creature focus;
    public Dungeon dung;
    public Side side = Side.FRONT;

    ArrayList<Timer> timers = new ArrayList<>();
    ArrayList<String> timnames = new ArrayList<>();

    public void setTimer(String name, int tim) {
        timers.add(new Timer(tim));
        timnames.add(name);
    }

    public Timer getTimer(String name) {
        return timers.get(timnames.indexOf(name));
    }

    public int getWidth() {
        return 128;
    }

    public int getHeight() {
        return 128;
    }

    protected double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    @Override
    public void init(Object... args) {
        x = (Double) args[0];
        y = (Double) args[1];
        maxhp = (int) args[2];
        hp = maxhp;
        dmg = (int) args[3];
        setTimer("dying", 1000);
    }

    public void initImages() {

    }

    public void initAbils() {

    }

    @Override
    public void tick() {
        baseTick();
        move();
    }

    public void die() {
        if (getTimer("dying").is()) {
            //dung.report(nick + " left the game!", 500);
            //dung.objects.add(new Modifier((int) x, (int) y, 1));
            dung.raiders[index] = null;
        }
        if (dung.player.focus == this) {
            dung.player.focus = null;
        }
    }

    public void deadtick() {

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
    }

    public void battle() {
        double dist = Math.sqrt(Math.pow(x - focus.x, 2) + Math.pow(y - focus.y, 2));
        if (dist - 2 * speed <= dmgDistance && getTimer("kick").is()) {
            dung.bullets.add(new Bullet((int) x, (int) y, focus, this));
            getTimer("kick").start();
        }
    }

    public void baseTick() {
        checkTimers();
        x += vx;
        y += vy;
        if (hp <= 0) {
            dead = true;
            getTimer("dying").start();
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
        g.setColor(Color.red);
        g.drawRect((int) x, (int) y, 64, 64);
    }

    public void deadrender(Graphics g) {
        dung.sprites.get(2).draw((int) x - getWidth()/2, (int) y - 32);
    }
}
