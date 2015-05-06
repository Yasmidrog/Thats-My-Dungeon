/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main.gui;

import game.main.scene.Dungeon;
import static game.main.shell.Game.chatfont;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Whizzpered
 */
public class Chat {

    public Dungeon dung;
    public String[] dialog, rage;
    public String[] alrdy = new String[7];
    public String[] alrdynick = new String[7];
    String curr;
    int n = 0;
    Random r = new Random();

    public void init(Dungeon dung) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("res/text/replicks.txt"));
        dialog = scan.nextLine().split("``");
        scan = new Scanner(new File("res/text/rage.txt"));
        rage = scan.nextLine().split("``");
        this.dung = dung;
    }

    public void add(String s, String nick) {
        if (n < alrdy.length - 1) {
            alrdy[n] = s;
            alrdynick[n] = nick + ": ";
            n++;
        } else {
            for (int i = 1; i < alrdy.length; i++) {
                alrdynick[i - 1] = alrdynick[i];
                alrdy[i - 1] = alrdy[i];
            }
            alrdy[alrdynick.length - 2] = s;
            alrdynick[alrdynick.length - 2] = nick + ": ";
        }
    }

    public void render(Graphics g) {
        int x = Display.getWidth() - 450;
        int y = Display.getHeight() - 137;
        g.setColor(new Color(0, 0, 0, 128));
        g.fillRect(x, y, 450, 137);
        for (int i = 0; i < alrdy.length; i++) {
            if (alrdy[i] != null && alrdynick[i] != null) {
                chatfont.drawString(x + 5, y + 8 + i * 20 - 7, alrdynick[i], Color.orange);
                chatfont.drawString(x + 5 + (alrdynick[i].length() * 10) + 4, y + 8 + i * 20 - 7,
                        alrdy[i].replaceAll("\n", ""), Color.yellow);
            }
        }
    }
}
