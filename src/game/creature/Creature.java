/*
 * Did by Whizzpered. 
 * All code is mine.
 */
package game.creature;

import game.main.scene.Dungeon;
import game.main.sprite.Side;
import game.main.sprite.Sprite;
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
    public int hp, maxhp, dmg, index, range;
    public boolean dead, ranged;
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
    }

    public void initImages() {

    }

    @Override
    public void tick() {
        baseTick();
        move();
    }

    public void move() {
        if (focus != null) {
            double dist = distance(x, y, focus.x, focus.y);
            int damageDistance = range + getHeight() / 2 + focus.getWidth() / 2;
            if (dist > damageDistance) {
                double angle = Math.atan2(focus.y - y, focus.x - x);
                ex = (int) (focus.x - Math.cos(angle) * damageDistance);
                ey = (int) (focus.y - Math.sin(angle) * damageDistance);
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
            vx = Math.cos(angle) * 3;
            vy = Math.sin(angle) * 3;
            if (Math.abs(ex - x) < 3 && Math.abs(ey - y) < 3) {
                vx = 0;
                vy = 0;
            }
        }
    }

    public void baseTick() {
        x += vx;
        y += vy;
        if (hp <= 0) {
            dead = true;
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

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.drawRect((int) x, (int) y, 64, 64);
    }
}
