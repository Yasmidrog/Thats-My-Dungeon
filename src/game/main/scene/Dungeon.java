/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main.scene;

import game.creature.*;
import game.object.Bullet;
import game.world.*;
import java.util.ArrayList;
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
    public Creature[] creatures = new Creature[50];
    public int camx, camy;

    public static ArrayList<Image> sprite = new ArrayList<>();
    public ArrayList<Bullet> bullets = new ArrayList<>();

    public Creature[] getCreatures() {
        int i;
        for (i = 0; i < creatures.length; i++) {
            if (creatures[i] == null) {
                break;
            }
        }
        Creature[] u = new Creature[i];
        System.arraycopy(creatures, 0, u, 0, i);

        return u;
    }

    public Creature[] creaturesYSort() {
        Creature[] u;
        u = getCreatures();
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

    public void add(Creature cr) {
        for (int i = 0; i < creatures.length; i++) {
            if (creatures[i] == null) {
                creatures[i] = cr;
                cr.index = i;
                break;
            }
        }
    }

    @Override
    public void init() {
        Block.setBlocks();
        floor.init();
        initSprites();
        initCreatures();
    }

    public void initSprites() {
        sprite.add(Textures.image("particles/sparkle3.png"));
        sprite.add(Textures.image("gui/agro.png").getScaledCopy(2f));
        sprite.get(1).setFilter(GL11.GL_NEAREST);
        sprite.add(Textures.image("particles/rip.png"));
        sprite.add(Textures.image("particles/flag.png").getScaledCopy(2f));
        sprite.get(3).setFilter(GL11.GL_NEAREST);
    }

    public void initCreatures() {
        spawn(new Player(), 120.0, 120.0, 60, 9);
        player = (Player) creatures[0];
        spawn(new RaiderArc(), 120.0, 240.0, 90, 1);
    }

    public void spawn(Creature cr, Object... args) {
        cr.dung = this;
        cr.init(args);
        cr.initImages();
        add(cr);
    }

    @Override
    public void tick() {
        for (Creature cr : getCreatures()) {
            cr.tick();
        }

        for (Bullet b : getBul()) {
            if (b != null) {
                b.tick(this);
            }
        }
        mousing();
    }

    public void mousing() {
        int w = Display.getWidth(), h = Display.getHeight();
        int mx = Mouse.getX(), my = h - Mouse.getY();
        camx = w - (int) player.x - mx;
        camy = h - (int) player.y - my;
        double msx = player.x + mx * 2 - w - 8;
        double msy = player.y + my * 2 - h;

        if (Mouse.isButtonDown(0)) {
            if (player.focus != null) {
                player.focus.focused = false;
                player.focus = null;
            }
            for (Creature cr : getCreatures()) {
                if (!cr.dead && cr != player) {
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

    @Override
    public void render(Graphics g) {
        int py = camy, px = camx;

        GL11.glTranslatef(px, py, 0);

        floor.render(g);
        for (Creature cr : creaturesYSort()) {
            cr.render(g);
        }
        for (Bullet b : getBul()) {
            if (b != null) {
                b.render(g);
            }
        }
        if (flag != null) {
            flag.render(g, sprite.get(3));
        }
        GL11.glTranslatef(-px, -py, 0);
        
        player.healthbar.render(g, 20, 20, player.hp);
    }
}
