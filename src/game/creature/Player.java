/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.creature;

import game.main.Ability;
import game.main.Active;
import game.main.Modifier;
import game.main.Passive;
import game.main.Target;
import game.main.gui.Bar;
import game.main.shell.Game;
import game.main.sprite.Sprite;
import game.object.Magic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import main.utils.Textures;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Whizzpered
 */
public class Player extends Creature {

    public Bar healthbar;
    public RaiderWar agr;
    public Image ded;
    public ArrayList<Ability> abils = new ArrayList<>();
    public Target cast;

    @Override
    public void initImages() {
        sprite = new Sprite("warrior/");
        ded = Textures.image("warrior/dead.png");
        healthbar = new Bar(maxhp, "Bar.png", "health.png");
    }

    @Override
    public void init(Object... args) {
        super.init(args);
        speed = 2;
        ranged = false;
        range = getWidth() / 4;
        setTimer("kick", 200);
        initAbils();
    }

    public Player thisClass = this;

    @Override
    public void initAbils() {
        abils.add(new Active(1000, false, 0) {
            int d = -10;

            @Override
            public void action() {
                System.out.println("Did Ability №1");
                unfocus();
                for (Raider r : dung.getRaiders()) {
                    if (!r.dead) {
                        double dist = Math.sqrt(Math.pow(r.x - x, 2) + Math.pow(r.y - y, 2));
                        if (dist < 900) {
                            r.hp -= 4;
                        }
                    }
                }
                d = 0;
                start();
            }

            @Override
            public void render(Graphics g) {
                if (d >= 0) {
                    g.setColor(Color.red);
                    g.drawOval((int) (thisClass.x - d / 2), (int) (thisClass.y - d / 2), d, d);
                    d += 4;
                    if (d / 2 >= 150) {
                        d = -10;
                    }
                }
            }
        });
        abils.get(0).init('1', false, 0);
        abils.get(0).initImages("charge.png", "energy.png");

        abils.add(new Target(1000, false, 0, this) {
            @Override
            public void casting() {
                double angle = Math.atan2((Display.getHeight() - Mouse.getY()) - Display.getHeight() / 2, Mouse.getX() - Display.getWidth() / 2);
                dung.bullets.add(new Magic((int) thisClass.x, (int) thisClass.y, thisClass, angle));
                super.casting();
            }
        });
        abils.get(1).init('2', false, 0);
        abils.get(1).initImages("charge.png", "energy.png");

        abils.add(new Passive() {
            int n = 0;
            @Override
            public void action() {
                mods.add(new Modifier() {
                    @Override
                    public void aply(Creature unit) {
                        if (unit.hp < unit.maxhp) {
                            unit.hp += 3;
                        }
                        timer = 1;
                    }
                });
            }
            
            @Override
            public void tick() {
                if(n>0){
                    n--;
                } else {
                    action();
                    n = 50;
                }
            }
        });
        abils.get(2).init('3', false, 0);
        abils.get(2).initImages("charge.png", "energy.png");
    }

    @Override
    public void reset() {
        super.reset();
        dung.flag.done = true;
    }

    Timer deathTimer = new Timer(1000, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Random r = new Random();
            if (deathTimerSeconds > 0) {
                deathTimerSeconds--;
                if (deathTimerSeconds > 1) {
                    dung.report("Death Agony : " + (deathTimerSeconds - 1), 94);
                } else if (deathTimerSeconds > 0) {
                    dung.report("You just Die", 300);
                } else {
                    Game.dungeon = null;
                    Game.currScene = Game.menu;
                }

            } else {

            }
        }
    }
    );

    int deathTimerSeconds = 6;

    @Override
    public void tick() {
        if (!dead) {
            super.tick();
            if (focus != null) {
                battle();
            }
            for (Ability ab : abils) {
                ab.tick();
            }
        } else {
            if (!deathTimer.isRunning()) {
                dung.ads.removeAll(dung.ads);
                deathTimerSeconds = 6;
                deathTimer.start();
            }
        }
    }

    public void focussmth(Raider r) {
        if (focus != null) {
            focus.focused = false;
            focus = null;
        }
        focus = r;
        focus.focused = true;
    }

    public void unfocus() {
        if (focus != null) {
            if (agr != null) {
                agr.agro = false;
                agr = null;
            }
            focus.focused = false;
            focus = null;
        }
    }

    @Override
    public void render(Graphics g) {
        try {
            sprite.render(side, (int) x - getWidth() / 2, (int) y - getHeight() / 2);
        } catch (SlickException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deadrender(Graphics g) {
        ded.draw((int) x - getWidth() / 2, (int) y - 32);
    }

    public void abilsRender(Graphics g) {
        for (Ability ab : abils) {
            ab.render(g);
        }
    }

}
