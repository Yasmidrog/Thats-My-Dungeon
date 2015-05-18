/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.main.gui;

import game.creature.Creature;
import game.creature.Player;
import static game.main.shell.Game.font;
import game.main.sprite.Side;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Whizzpered
 */
public class Stats {

    public Creature aim;
    public Player pl;

    public Stats(Creature boss) {
        if (boss.getClass() == Player.class) {
            pl = (Player) boss;
        } else {
            aim = boss;
        }
    }

    public void show(Graphics g, int x, int y) {
        try {
            aim.sprite.render(Side.FRONT, x, y);
        } catch (SlickException ex) {
            Logger.getLogger(Stats.class.getName()).log(Level.SEVERE, null, ex);
        }
        font.drawString(x, y + 96, "Attack: " + aim.dmg, Color.green);
        font.drawString(x, y + 96, "Health: " + aim.hp, Color.green);
        font.drawString(x, y + 96, "Level : " + aim.level, Color.green);
    }

    public void showForPlayer(Graphics g, int x, int y) {
        font.drawString(x, y, "Attack: " + pl.dmg, Color.green);
        font.drawString(x, y + 20, "Health: " + pl.hp, Color.green);
        font.drawString(x, y + 40, "Level : " + pl.level, Color.green);
        font.drawString(x, y + 60, "XP: " + pl.xp, Color.green);
        font.drawString(x, y + 80, "Gold : " + pl.gold, Color.green);
    }
}
