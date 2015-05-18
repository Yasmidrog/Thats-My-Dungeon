/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main.scene;

import game.object.Flag;
import game.creature.*;
import game.main.gui.Advert;
import game.main.gui.Button;
import game.main.gui.Chat;
import game.main.gui.FloatText;
import game.main.shell.Game;
import game.object.Bullet;
import game.object.Item;
import game.world.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import main.utils.DungeonParser;
import main.utils.Textures;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Whizzpered
 */
public class Dungeon extends Scene {

    public Floor floor = new Floor();
    public Player player;
    public Flag flag = new Flag();              //Moving flag, for Player
    public Chat chat = new Chat();
    public Raider[] raiders = new Raider[25];
    public int camx, camy, flx, fly, level = 1, end = 5, kk;      //flx, fly - cam for floor, dont touch kk!!!

    public static ArrayList<Image> sprites = new ArrayList<>();
    public ArrayList<Advert> ads = new ArrayList<>();
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public ArrayList<FloatText> text = new ArrayList<>();

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

    public FloatText[] getText() {
        FloatText[] u = new FloatText[text.size()];

        try {
            for (int i = 0; i < text.size(); i++) {
                u[i] = text.get(i);
            }
        } catch (Exception e) {
        }
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

    public void report(String s, int dur) {
        ads.add(new Advert(s, this, dur));
    }

    @Override
    public void init() {
        Block.setBlocks();      //Initializating blocks for building
        try {
            DungeonParser dp = new DungeonParser(this, "1");    //Getting plan of level from file
            dp.aply();                                          //aplying this plan
            chat.init(this);                                    //Getting цитаты from file
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dungeon.class.getName()).log(Level.SEVERE, null, ex);
        }
        initSprites();
        initButtons();
        initCreatures();
    }

    public void initButtons() {
        buttons.add(new Button(Display.getWidth() / 2 - 110, 20, 100, "Pause", Color.green) {
            @Override
            public void click() {
                Game.currScene = Game.menu;
            }
        });

    }

    public void initSprites() {
        sprites.add(Textures.image("particles/sparkle3.png"));
        sprites.add(Textures.image("gui/agro.png").getScaledCopy(2f));
        sprites.add(Textures.image("particles/rip.png").getScaledCopy(2f));
        sprites.add(Textures.image("particles/flag.png").getScaledCopy(2f));
        sprites.add(Textures.image("particles/sparkle2.png").getScaledCopy(2f));
        for (Image im : sprites) {
            im.setFilter(GL11.GL_NEAREST);
        }

        Block.initSprites();
    }

    public void initCreatures() {
        player();
        //spawn(new RaiderArc(), 120.0, 240.0, 9, 4, 1);
        //spawn(new RaiderPriest(), 240.0, 240.0, 9, 3, 1);
        //spawn(new RaiderWar(), 240.0, 360.0, 20, 2, 1);
    }

    public void player() {
        player = new Player();
        player.dung = this;
        player.init(120.0, 120.0, 90, 9, 1);
        player.initImages();
    }

    public void spawn(Raider cr, Object... args) {          //Use this for spawn
        cr.dung = this;
        cr.initImages();
        cr.init(args);
        add(cr);
        report(cr.nick + " joined the game!", 500);

    }

    public void add(Raider cr) {                            //Dont touch
        for (int i = 0; i < raiders.length; i++) {
            if (raiders[i] == null) {
                raiders[i] = cr;
                cr.index = i;
                break;
            }
        }
    }

    public void delete(int index) {                         //Yo, nigga, so good crap
        int s = 0;
        for (int i = 0; i < raiders.length; i++) {
            if (raiders[i].index == index) {
                raiders[i] = null;
                s = i + 1;
                break;
            }
        }

        for (int i = s; i < raiders.length; i++) {
            raiders[i - 1] = raiders[i];
        }
    }

    Timer waveTimer = new Timer(1000, new ActionListener() {          //Your timer but with third dick and thousand of tities

        @Override
        public void actionPerformed(ActionEvent ae) {

            Random r = new Random();
            if (waveTimerSeconds > 0) {
                waveTimerSeconds--;
                if (waveTimerSeconds > 0) {
                    if (level > end) {
                        report("YOU WON!!", 60);
                    } else {
                        report(waveTimerSeconds + " seconds left!", 94);
                    }
                } else {
                    if (level > end) {
                        report("No Go to hell, Loser :3", 60);
                    } else {
                        report("Lets Go", 60);
                    }
                }
            } else {
                if (level == end) {
                    spawn(new RaiderWar() {
                        @Override
                        public void init(Object... args) {
                            super.init(args);
                            nick = "FUGKING CHEATER";
                        }

                        @Override
                        public void render(Graphics g) {
                            try {
                                if (focused) {
                                    bar.draw((int) x - getWidth() / 2, (int) y - getHeight() / 3);
                                }
                                sprite.render(side, (int) x - getWidth() / 2, (int) y - getHeight() / 2);
                                renderHP(g);
                                Game.font.drawString((int) x - getWidth() / 2 - 12, (int) y - getHeight() / 2 - 40, nick);
                            } catch (SlickException ex) {
                                Logger.getLogger(Raider.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }, (double) (getWidth() / 2 + 48), (double) (getHeight() - 64), 200 + level * 3, 6 + level, level
                    );
                } else if (level > end) {
                    Game.dungeon = null;
                    Game.currScene = Game.menu;
                } else {
                    for (int i = 0; i < 4; i++) {
                        switch (r.nextInt(3)) {
                            case 0:
                                spawn(new RaiderArc(), (double) (getWidth() / 2 - i * 48 + 48), (double) (getHeight() - 64), 20 + level * 2, +(int) (level / 3), level);
                                break;
                            case 1:
                                spawn(new RaiderPriest(), (double) (getWidth() / 2 - i * 48 + 48), (double) (getHeight() - 64), 20 + level * 2, 4 + (int) (level / 5), level);
                                break;
                            case 2:
                                spawn(new RaiderWar(), (double) (getWidth() / 2 - i * 48 + 48), (double) (getHeight() - 64), 40 + level * 4, 2 + (int) (level / 5), level);
                                break;
                        }

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
        if (getRaiders().length == 0) {             //Mechanism of waves
            if (!waveTimer.isRunning()) {
                waveTimerSeconds = 10;
                level++;
                player.dmg++;
                player.maxhp += 10;
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

        for (Advert ad : getAds()) {
            if (ad != null) {
                ad.tick();
            }
        }

        player.tick();                          //Player is a HERO

        for (Bullet b : getBul()) {
            if (b != null) {
                b.tick(this);
            }
        }

        try {
            mousing();                      //Methods of control
            button();
        } catch (IllegalStateException ignored) {
        }
    }

    @Override
    public void subtick() {

    }

    public void mousing() {
        int w = Display.getWidth(), h = Display.getHeight();
        int mx = Mouse.getX(), my = h - Mouse.getY();
        camx = w - (int) player.x - mx;
        camy = h - (int) player.y - my;
        flx = (int) player.x + mx - w;
        fly = (int) player.y + my - h;
        double msx = player.x + mx * 2 - w;
        double msy = player.y + my * 2 - h;             //Woooah, so big shit

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

    public void button() {
        if (kk > 0) {
            kk--;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {              //Getting to Menu
            Game.currScene = Game.menu;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && kk == 0) {        //actualy doing nothing, but must delete all items from boss
            if (player.items[0] != null) {
                for (Item it : player.items) {
                    it = null;
                }
            } else {
                player.items[0] = new Item("pants") {
                    @Override
                    public void aply(Creature cr) {
                        cr.maxhp += 20;
                    }
                };
                player.items[1] = new Item("arms") {
                    @Override
                    public void aply(Creature cr) {
                        cr.maxhp += 10;
                    }
                };
                player.items[2] = new Item("braces") {
                    @Override
                    public void aply(Creature cr) {
                        cr.dmg += 5;
                    }
                };
            }
            kk = 30;
        }
    }

    @Override
    public void render(Graphics g) {
        int py = camy, px = camx;                       //Idk, but i'm too harriered to delete

        GL11.glTranslatef(px, py, 0);

        floor.render(g, flx, fly);
        for (Creature cr : creaturesYSort()) {
            if (cr.dead) {
                cr.deadrender(g);
            } else {
                cr.render(g);
            }

            if (cr == player) {
                player.abilsRender(g);
            }
        }
        for (Bullet b : getBul()) {
            if (b != null) {
                b.render(g);

            }
        }
        for (FloatText ft : getText()) {
            ft.render(g);
        }
        if (flag != null) {
            flag.render(g, sprites.get(3));
        }
        GL11.glTranslatef(-px, -py, 0);

        player.healthbar.render(g, 20, 20, (int) player.hp);
        chat.render(g);
        for (int i = 0; i < getAds().length; i++) {
            getAds()[i].render(g, Display.getWidth() / 2 - 100, 150 + i * 30);
        }
        for (int i = 0; i < player.abils.size(); i++) {
            player.abils.get(i).renderIcon(g, Display.getWidth() / 2 - 70 + i * 66, Display.getHeight() - 70);
        }
    }
}
