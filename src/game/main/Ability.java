/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main;

import game.main.gui.Button;
import static game.main.scene.Scene.sprite;
import static game.main.shell.Game.font;

import game.main.shell.Game;
import static game.main.shell.Game.font;
import main.utils.Textures;
import main.utils.Timer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
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
    public Button mouse;
    public int number;
    public String key;
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
            key = ((String) Game.conf.get(String.valueOf(but)).getValue()).toUpperCase();
        } catch (ClassCastException ex) {
            key = String.valueOf((Game.conf.get(String.valueOf(but)).getValue())).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initButton();
        number = but;
        if (trg) {
            trgt = trg;
            this.radius = radius;
        }
    }

    public void initButton() {
        mouse = new Button(0, 0, 32, null, null) {
            @Override
            public void click() {
                if (cd.is()) {
                    action();
                }
                Game.dungeon.player.reset();
            }

            @Override
            public void render(Graphics g) {
                int x = cx;
                int y = this.y;
                int mx = Mouse.getX();
                int my = Display.getHeight() - Mouse.getY();
                icon.draw(x - w / 2, y - 32);

                if (Math.abs(mx - x) < w && Math.abs(y - my) < 32) {
                    color = Color.orange;
                    if (bp && !Mouse.isButtonDown(0)) {
                        click();
                    }
                } else {
                    color = Color.white;
                }
                bp = Mouse.isButtonDown(0);
            }
        };
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
        mouse.cx = x + 16;
        mouse.y = y + 32;
        mouse.render(g);
        font.drawString(x, y + 44, key + "    " + number, Color.white);
        if (cd.is()) {
            g.setColor(Color.green);
            g.drawRect(x, y, 64, 64);
            g.drawRect(x + 1, y + 1, 62, 62);
        }
    }

    public void render(Graphics g) {

    }

}
