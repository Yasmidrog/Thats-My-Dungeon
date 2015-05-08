/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main.scene;

import game.main.gui.Button;
import game.main.gui.SwitchButton;
import game.main.shell.Game;
import static game.main.shell.Game.font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.utils.Name;
import main.utils.Textures;
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
public class Menu extends Scene {

    ArrayList<Button> settingsButtons = new ArrayList<>();
    Name[] name = new Name[3];
    private int currentMenu = 0;

    public void reinit() {
        Game.dungeon = new Dungeon();
        Game.dungeon.init();
    }

    @Override
    public void init() throws SlickException {

        sprite = new Image[4];
        int w = Display.getWidth(), h = Display.getHeight();

        sprite[0] = Textures.image("gui/button_left.png");
        sprite[1] = Textures.image("gui/button_center.png");
        sprite[2] = Textures.image("gui/button_right.png");
        sprite[1].setFilter(GL11.GL_NEAREST);
        sprite[3] = Textures.image("gui/back.jpg");
        sprite[3].setFilter(GL11.GL_NEAREST);

        initButtons(h);

        name[0] = new Name(Display.getWidth() / 2 - 150, Display.getHeight() - 120, "Whizzpered", Color.white, "Founder, Teamleader", Color.magenta);
        name[1] = new Name(Display.getWidth() / 2 - 100, Display.getHeight() - 80, "Yew_mentzaki", Color.white, "Programmist", Color.magenta);
        name[2] = new Name(Display.getWidth() / 2 - 100, Display.getHeight() - 40, "Todo_Asano", Color.white, "Designer", Color.magenta);
    }

    public void initButtons(int h) {

        buttons.add(new Button(-Display.getWidth() / 2 + 100, h / 2 + 100, 200, "Exit", Color.red) {
            @Override
            public void click() {
                Game.exit();
            }
        });

        buttons.add(new Button(-Display.getWidth() / 2 + 100, h - 125, 200, "Reinit", Color.green) {
            @Override
            public void click() {
                reinit();
                Game.currScene = Game.dungeon;
            }
        });

        buttons.add(new Button(-Display.getWidth() / 2 + 100, h / 2 - 25, 200, "Play", Color.green) {
            @Override
            public void click() {
                if (Game.dungeon == null) {
                    Game.dungeon = new Dungeon();
                    Game.dungeon.init();
                }
                Game.currScene = Game.dungeon;
            }
        });

        buttons.add(new Button(-Display.getWidth() / 2 + 100, h / 2 + 38, 200, "Settings", Color.green) {
            @Override
            public void click() {
                currentMenu = 1;
            }
        });

        settingsButtons.add(new SwitchButton(-30, h / 2 - 25, 200, "Fullscreen", false) {
            @Override
            public void click() {
                super.click();
                System.out.println(value);
            }
        });

        settingsButtons.add(new Button(-30, h / 2 + 38, 200, "Agree", Color.green) {
            @Override
            public void click() {
                currentMenu = 0;
            }
        });
    }

    @Override
    public void render(Graphics g) {
        GL11.glDisable(GL11.GL_BLEND);
        sprite[3].draw(0, 0, Display.getWidth(), Display.getHeight());
        GL11.glEnable(GL11.GL_BLEND);
        if (currentMenu == 0) {
            font.drawString(Display.getWidth() - 300, 0, "Welcome back in " + Game.times + " time!", Color.yellow);
            for (Button but : buttons) {
                but.render(g);
            }
            for (Name n : name) {
                n.render(g);
            }
            //player.render(g);
        }
        if (currentMenu == 1) {
            for (Button but : settingsButtons) {
                but.render(g);
            }
        }
    }
}
