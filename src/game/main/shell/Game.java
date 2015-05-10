/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main.shell;

/**
 *
 * @author Whizzpered
 */
import game.main.scene.*;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.sparkle.jcfg.JCFG;
import org.sparkle.jcfg.Parser;
import main.utils.FPScounter;
import main.utils.Textures;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.sparkle.jcfg.Writer;

public class Game extends BasicGame {

    Graphics g = new Graphics();
    public static JCFG conf = new JCFG();
    public static int times = 0;
    public static boolean paused;
    public static Menu menu = new Menu();
    public static Scene currScene = menu;
    public static Dungeon dungeon;
    public static TrueTypeFont font, chatfont;

    public static AppGameContainer app;

    public Game() {
        super("");
    }

    public static void exit() {
        times++;
        File cfg = new File("conf.cfg");
        conf.set("w", Display.getWidth());
        conf.set("h", Display.getHeight());
        conf.set("x", Display.getX());
        conf.set("y", Display.getY());
        conf.set("times", times);
        try {
            Writer.writeToFile(conf, cfg);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        System.exit(0);
    }
    
    @Override
    public boolean closeRequested(){
        exit();
        return false;
    }

    public static void main(String[] arguments) throws SlickException, LWJGLException {
        setUpNatives();
        app = new AppGameContainer(new Game());
        Display.setResizable(true);
        setSources();
        app.setDefaultMouseCursor();
        app.setAlwaysRender(true);
        app.setShowFPS(false);
        app.start();
        Keyboard.create();
        Mouse.create();
    }

    public static void setSources() throws SlickException {
        try {
            int w = 1024, h = 700, x = 0, y = 0;
            File cfg = new File("conf.cfg");
            if (cfg.exists()) {
                conf = Parser.parse(cfg);
                w = conf.get("w").getValueAsInteger();
                h = conf.get("h").getValueAsInteger();
                x = conf.get("x").getValueAsInteger();
                y = conf.get("y").getValueAsInteger();
                Game.times = conf.get("times").getValueAsInteger();
            } else {
                conf.set("music", true);
            }
            app.setDisplayMode(w, h, false);
            Display.setResizable(true);
            Display.setLocation(x, y);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        Image logo = new Image("res/textures/gui/logo.png");
        Image wait = new Image("res/textures/gui/wait.png");
        logo.draw((Display.getWidth() - logo.getWidth()) / 2, (Display.getHeight() - logo.getHeight()) / 2);
        wait.draw(0, (Display.getHeight() - wait.getHeight()));
        Display.update();
        Font awtFont = new Font("ITALIC", Font.TRUETYPE_FONT, 20);
        font = new TrueTypeFont(awtFont, false);
        awtFont = new Font("ITALIC", Font.TRUETYPE_FONT, 18);
        chatfont = new TrueTypeFont(awtFont, false);
        Textures.load();
        currScene.init();
        sceneTimer();
    }

    public static void sceneTimer() {
        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (!paused) {
                    currScene.tick();
                }
            }
        }, 0, 10);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (Display.isCloseRequested()) {
            exit();
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        currScene.render(g);
        FPScounter.render(g);
    }

    public static void setUpNatives() {
        if (!new File("natives").exists()) {
            JOptionPane.showMessageDialog(null, "Error!\nNative libraries not found!");
            System.exit(1);
        }
        try {
            System.setProperty("java.library.path", new File("natives").getAbsolutePath());

            Field fieldSysPath = ClassLoader.class
                    .getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            try {
                fieldSysPath.set(null, null);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Error!\n" + ex.toString());
                System.exit(1);
            } catch (IllegalAccessException ex) {
                JOptionPane.showMessageDialog(null, "Error!\n" + ex.toString());
                System.exit(1);
            }
        } catch (NoSuchFieldException ex) {
            JOptionPane.showMessageDialog(null, "Error!\n" + ex.toString());
            System.exit(1);
        } catch (SecurityException ex) {
            JOptionPane.showMessageDialog(null, "Error!\n" + ex.toString());
            System.exit(1);
        }
    }
}
