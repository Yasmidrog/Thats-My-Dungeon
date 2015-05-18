/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.main.gui;

import game.creature.Creature;
import game.creature.Player;
import static game.main.shell.Game.font;
import org.newdawn.slick.Graphics;

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
        font.drawString(x, y, "Attack: " + aim.dmg);
        font.drawString(x, y + 20, "Health: " + aim.hp);
        font.drawString(x, y + 40, "Level : " + aim.level);
    }

    public void showForPlayer(Graphics g, int x, int y) {
        font.drawString(x, y, "Attack: " + pl.dmg);
        font.drawString(x, y + 20, "Health: " + pl.hp);
        font.drawString(x, y + 40, "Level : " + pl.level);
        font.drawString(x, y + 60, "XP: " + pl.xp);
        font.drawString(x, y + 80, "Gold : " + pl.gold);
    }
}
