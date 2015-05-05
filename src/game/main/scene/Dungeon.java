/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main.scene;

import game.object.Flag;
import game.creature.*;
import game.main.gui.Advert;
import game.main.gui.Chat;
import game.object.Bullet;
import game.world.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import main.utils.Textures;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 *
 * @author Юрий
 */
public class Dungeon extends Scene {

    Floor floor = new Floor();
    public Player player;
    public Flag flag = new Flag();
    public Chat chat = new Chat();
    public Raider[] raiders = new Raider[25];
    public int camx, camy;

    public static ArrayList<Image> sprites = new ArrayList<>();
    public ArrayList<Advert> ads = new ArrayList<>();
    public ArrayList<Bullet> bullets = new ArrayList<>();

    public Raider[] getRaiders() {
        int i;
        for (i = 0; i < raiders.length; i++) {
            if (raiders[i] == null) {
                break;
            }
        }
        Raider[] u = new Raider[i];
        System.arraycopy(raiders, 0, u, 0, i);

        return u;
    }

    public Creature[] creaturesYSort() {
        Creature[] u = new Creature[getRaiders().length + 1];
        u[0] = player;
        for (int i = 1; i < u.length; i++) {
            u[i] = getRaiders()[i - 1];
        }
        for (int i = 0; i < u.length - 1; i++) {
            for (int j = i + 1; j < u.length; j++) {
                if (u[i].y > u[j].y) {
                    Creature t = u[i];
                    u[i] = u[j];
                    u[j] = t;
                }
            }
        }
        return u;
    }

    public Advert[] getAds() {
        Advert[] u = new Advert[ads.size()];

        try {
            for (int i = 0; i < ads.size(); i++) {
                u[i] = ads.get(i);
            }
        } catch (Exception e) {
        }
        return u;
    }

    public Bullet[] getBul() {
        Bullet[] u = new Bullet[bullets.size()];

        u = bullets.toArray(u);
        return u;
    }

    public int getWidth() {
        return floor.w * Block.size;
    }

    public int getHeight() {
        return floor.h * Block.size;
    }

    public void add(Raider cr) {
        for (int i = 0; i < raiders.length; i++) {
            if (raiders[i] == null) {
                raiders[i] = cr;
                cr.index = i;
                break;
            }
        }
    }

    public void report(String s, int dur) {
        ads.add(new Advert(s, this, dur));
    }

    @Override
    public void init() {
        Block.setBlocks();
        floor.init();
        try {
            chat.init(this);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dungeon.class.getName()).log(Level.SEVERE, null, ex);
        }
        initSprites();
        initCreatures();
    }

    public void initSprites() {
        sprites.add(Textures.image("particles/sparkle3.png"));
        sprites.add(Textures.image("gui/agro.png").getScaledCopy(2f));
        sprites.add(Textures.image("particles/rip.png").getScaledCopy(2f));
        sprites.add(Textures.image("particles/flag.png").getScaledCopy(2f));

        for (Image im : sprites) {
            im.setFilter(GL11.GL_NEAREST);
        }
    }

    public void initCreatures() {
        player();
        spawn(new RaiderArc(), 120.0, 240.0, 9, 1);
        spawn(new RaiderPriest(), 240.0, 240.0, 9, 1);
        spawn(new RaiderWar(), 240.0, 360.0, 20, 1);
    }

    public void player() {
        player = new Player();
        player.dung = this;
        player.init(120.0, 120.0, 90, 9);
        player.initImages();
    }

    public void spawn(Raider cr, Object... args) {
        cr.dung = this;
        add(cr);
        cr.init(args);
        cr.initImages();
    }

    Timer waveTimer = new Timer(1000, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            Random r = new Random();
            if (waveTimerSeconds > 0) {
                waveTimerSeconds--;
                if (waveTimerSeconds > 0) {
                    report(waveTimerSeconds + " seconds left!", 97);
                } else {
                    report("Let's go!", 300);
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    switch (r.nextInt(3)) {
                        case 0:
                            spawn(new RaiderArc(), 600.0, 1200.0, 20, 4);
                            break;
                        case 1:
                            spawn(new RaiderPriest(), 600.0, 1200.0, 20, 4);
                            break;
                        case 2:
                            spawn(new RaiderWar(), 600.0, 1200.0, 40, 3);
                            break;
                    }

                }
                waveTimer.stop();
            }
        }
    }
    );

    int waveTimerSeconds = 10;
    
    @Override
    public void tick() {
        if (getRaiders().length == 0) {
            if (!waveTimer.isRunning()) {
                waveTimerSeconds = 10;
                waveTimer.start();
            }
        }
        for (Raider cr : getRaiders()) {
            if (cr.dead) {
                cr.deadtick();
            } else {
                cr.tick();
            }
            cr.emulateChat();
        }
        /*
        for (Advert ad : ads) {
            if (ad != null) {
                ad.tick();
            }
        }
        */
        player.tick();
        for (Bullet b : getBul()) {
            if (b != null) {
                b.tick(this);
            }
        }
        try {
            mousing();
        }catch (IllegalStateException ignored){}
    }

    public void mousing() {
        int w = Display.getWidth(), h = Display.getHeight();
        int mx = Mouse.getX(), my = h - Mouse.getY();
        camx = w - (int) player.x - mx;
        camy = h - (int) player.y - my;
        double msx = player.x + mx * 2 - w - 8;
        double msy = player.y + my * 2 - h;

        if (player.agr == null) {
            if (Mouse.isButtonDown(0)) {
                if (player.focus != null) {
                    player.focus.focused = false;
                    player.focus = null;
                }
                for (Raider cr : getRaiders()) {
                    if (!cr.dead) {
                        if (Math.abs(cr.x - msx) < 80 && Math.abs(cr.y - msy) < 80) {
                            flag.done = true;
                            player.focussmth((Raider) cr);
                            return;
                        }
                    }
                }
                player.ex = msx;
                player.ey = msy;
                flag.set(player.ex, player.ey);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        int py = camy, px = camx;

        GL11.glTranslatef(px, py, 0);

        floor.render(g);
        for (Creature cr : creaturesYSort()) {
            if (cr.dead) {
                cr.deadrender(g);
            } else {
                cr.render(g);
            }
        }
        for (Bullet b : getBul()) {
            if (b != null) {
                b.render(g);
            }
        }
        if (flag != null) {
            flag.render(g, sprites.get(3));
        }
        GL11.glTranslatef(-px, -py, 0);

        player.healthbar.render(g, 20, 20, player.hp);
        chat.render(g);
        for (int i = 0; i < getAds().length; i++) {
            getAds()[i].render(g, Display.getWidth() / 2 - 100, 150 + i * 30);
        }
    }
}
