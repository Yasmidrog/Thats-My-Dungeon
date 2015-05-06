/* Copyright (C) 2015, SHeart.  All rights reserved.
 * ______________________________________________________________________________
 * This program is proprietary software: decompiling, reverse engineering and
 * sharing of that code are denied.
 */
package game.main;

import game.creature.Player;
import main.utils.Textures;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

/**
 *
 * @author Whizzpered
 */
public abstract class Target extends Ability {

    Player player;
    int key;
    boolean pressed;

    @Override
    public void initImages(String name1, String name2) {
        icon = Textures.image("abilities/" + name1).getScaledCopy(1f);
        icon.setImageColor(99, 250, 120, 50);
        strip = Textures.image("abilities/" + name2);
    }

    public Target(int cd, boolean dur, int dura, Player player) {
        super(cd, dur, dura);
        this.player = player;
    }

    @Override
    public void tick() {
        if (key > 0) {
            key--;
        }
        super.tick();
        if (Keyboard.isKeyDown(Keyboard.getKeyIndex(number + "")) && cd.is() && key == 0) {
            action();
            key = 15;
        }
        if (Mouse.isButtonDown(1) && player.cast == this) {
            casting();
            start();
        }
    }

    @Override
    public void action() {
        if (!pressed) {
            player.cast = this;
            pressed = true;
            icon.setImageColor(255, 0, 120);
        } else {
            icon.setImageColor(255, 255, 255);
            player.cast = null;
            pressed = false;
        }

    }

    public void casting() {
        icon.setImageColor(255, 255, 255);
        pressed = false;
        player.cast = null;
        start();
    }
}
