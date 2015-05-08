/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main;

import static game.main.shell.Game.font;

import game.main.shell.Game;
import main.utils.Textures;
import main.utils.Timer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Whizzpered
 */
public abstract class Ability {

    public Timer cd, duration;
    public boolean lng, trgt;
    public Image icon, strip;
    public int number;
    public char key;
    public int radius;
    public int x, y;

    public boolean ready() {
        return cd.is();
    }

    public void initImages(String name1, String name2) {
        icon = Textures.image("abilities/" + name1);
        strip = Textures.image("abilities/" + name2);
    }

    public void init(int but, boolean trg, int radius) {
        try {
            key = ((String) Game.conf.get(String.valueOf(but)).getValue()).toUpperCase().toCharArray()[0];
        }catch (ClassCastException ex){
            key = String.valueOf((Game.conf.get(String.valueOf(but)).getValue())).toUpperCase().toCharArray()[0];
        }
        catch (Exception e){
           e.printStackTrace();
        }
        number = but;
        if (trg) {
            trgt = trg;
            this.radius = radius;
        }
    }

    public Ability(int cd, boolean dur, int dura) {
        this.cd = new Timer(cd);
        if (dur) {
            lng = true;
            duration = new Timer(dura);
        }

    }

    public void tick() {
        if (!cd.is()) {
            cd.tick();
        }
        if (lng) {
            if (!duration.is()) {
                duration.tick();
            }
            if (!cd.is() && duration.is()) {
                after();
            }
        }
    }

    public abstract void action();

    public void start() {
        cd.start();
        if (duration != null) {
            duration.start();
        }
    }

    public void after() {

    }

    public void setCoors(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void renderIcon(Graphics g, int x, int y) {
        for (int i = 0; i < ((cd.period - cd.tick) * Math.pow(cd.period, -1)) * 31; i++) {
            strip.draw(x + 1, y - i * 2 + 62);
        }
        icon.draw(x, y);
        font.drawString(x+10,y+44,key+"    "+number, Color.white);
        if (cd.is()) {
            g.setColor(Color.green);
            g.drawRect(x, y, 64, 64);
            g.drawRect(x + 1, y + 1, 62, 62);
        }
    }

    public void render(Graphics g) {

    }

}
