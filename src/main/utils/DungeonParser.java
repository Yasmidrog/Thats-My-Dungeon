package main.utils;


import game.main.scene.Dungeon;
import game.object.Object;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by yasmidrog on 09.05.15.
 */
public class DungeonParser {
    private int w,h;
    private Dungeon dung;
    private ArrayList<Object> objects;
    public DungeonParser(File file, Dungeon dng){
        dung=dng;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line =br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            parse(everything.split("\n"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void parse(String lines[]){
        char[] colons;
        w=Integer.valueOf(lines[0].split(" ")[0]);
        h=Integer.valueOf(lines[0].split(" ")[1])+1;
        ArrayList<Object> objs=new ArrayList<Object>();
        for(int i=1;i<h;i++) {
            colons=lines[i].toCharArray();
            for (int j = 0; j < w; j++) {
                switch (colons[j]) {
                    case '*':
                        System.out.print("*");
                       objs.add(new Object(64*j,64*(i-1),dung,true));
                        objs.get(objs.size()-1).initImage(Textures.image("floor/wall.png"));
                        break;
                    default:
                        objs.add(new Object(64*j,64*(i-1),dung,false));
                        objs.get(objs.size()-1).initImage(Textures.image("floor/some.png"));
                        break;
                }
            }
            System.out.println();
        }
        objects= objs;
    }
    public int getWidth() {
        return w;
    }
    public int getHeight() {
        return h;
    }
    public ArrayList<Object> getObjects(){
        return objects;
    }

}
