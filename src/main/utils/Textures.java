/*
 * Copyright (C) 2015 yew_mentzaki
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package main.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author yew_mentzaki
 */
public class Textures {

    static class Tex {

        String name;
        Texture texture;
        Image image;

        public Tex(String name, File texture) {
            this.name = name;
            try {
                this.texture = TextureLoader.getTexture(name.split("\\.")[0].toUpperCase(), new FileInputStream(texture));
                this.image = new Image(this.texture);
                System.out.println("Texture \"" + name + "\" is loaded!");
            } catch (IOException ex) {
                Logger.getLogger(Textures.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static ArrayList<Animation> tempAnimationList = new ArrayList<Animation>();

    private static Tex[] textures;
    private static Animation[] animations;

    public static void load() {
        ArrayList<Tex> textureList = new ArrayList<Tex>();
        for (File f : new File("res/textures").listFiles()) {
            if (f.isDirectory()) {
                textureList.addAll(load(f.getName(), f));
            } else {
                textureList.add(new Tex(f.getName(), f));
            }
        }
        textures = new Tex[textureList.size()];
        for (int i = 0; i < textureList.size(); i++) {
            textures[i] = textureList.get(i);
        }
        animations = new Animation[tempAnimationList.size()];
        for (int i = 0; i < tempAnimationList.size(); i++) {
            animations[i] = tempAnimationList.get(i);
        }
        tempAnimationList.clear();
    }

    public static Image image(String name) {
        for (Tex t : textures) {
            if (t.name.equals(name)) {
                return t.image;
            }
        }
        try {
            throw new FileNotFoundException("Image \"" + name + "\" requested but not loaded!");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Textures.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Texture texture(String name) {
        for (Tex t : textures) {
            if (t.name.equals(name)) {
                return t.texture;
            }
        }
        return null;
    }

    public static Animation animation(String name) {
        for (Animation a : animations) {
            if (a.name.equals(name)) {
                return a;
            }
        }
        return null;
    }

    private static ArrayList<Tex> load(String names, File folder) {
        ArrayList<Tex> textures = new ArrayList<Tex>();
        for (File f : folder.listFiles()) {
            if (f.isDirectory()) {
                textures.addAll(load(names + "/" + f.getName(), f));
            } else {
                textures.add(new Tex(names + "/" + f.getName(), f));
            }
        }
        tempAnimationList.add(new Animation(names, textures));
        return textures;
    }
}
