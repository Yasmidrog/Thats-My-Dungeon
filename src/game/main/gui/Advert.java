/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main.gui;

import game.main.scene.Dungeon;
import static game.main.shell.Game.font;
import main.utils.Timer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 *
 * @author Whizzpered
 */
public class Advert {
    public Timer vsbl;
    public String mess;
    Dungeon dung;

    public Advert(String mess, Dungeon dung, int period) {
        vsbl = new Timer(period);
        this.mess = mess;
        this.dung = dung;
        vsbl.start();
    }

    public void tick() {
        if (!vsbl.is()) {
            vsbl.tick();
        } else {
            dung.ads.remove(this);
        }

    }

    public void render(Graphics g, int x, int y) {
        font.drawString(x, y, mess, Color.yellow);
    }
}
