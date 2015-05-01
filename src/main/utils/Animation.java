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

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.Texture;

/**
 *
 * @author yew_mentzaki
 */
public class Animation{
    private Image[] images(ArrayList<Textures.Tex> t){
        Textures.Tex[] tex = new Textures.Tex[t.size()];
        for (int i = 0; i < t.size(); i++) {
            tex[i] = t.get(i);
        }
        Image[] i = new Image[tex.length];
        for (int j = 0; j < i.length; j++) {
            i[j] = tex[j].image.getScaledCopy(2f);
            i[j].setFilter(GL11.GL_NEAREST);
        }
        return i;
    }
    
    private Texture[] textures(ArrayList<Textures.Tex> t){
        Textures.Tex[] tex = new Textures.Tex[t.size()];
        for (int i = 0; i < t.size(); i++) {
            tex[i] = t.get(i);
        }
        Texture[] i = new Texture[tex.length];
        for (int j = 0; j < i.length; j++) {
            i[j] = tex[j].texture;
        }
        return i;
    }
    
    Animation(String name, ArrayList<Textures.Tex> tex) {
        this.name = name;
        images = images(tex);
        textures = textures(tex);
        slickAnimation = new org.newdawn.slick.Animation(images, 125);
    }
    
    public final String name;
    public final org.newdawn.slick.Animation slickAnimation;
    public final Image[] images;
    public final Texture[] textures;
    
}
