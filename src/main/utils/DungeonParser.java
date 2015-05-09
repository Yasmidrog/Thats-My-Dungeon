package main.utils;


import game.main.scene.Dungeon;
import game.object.Object;
import org.newdawn.slick.Image;

import java.io.*;

/**
 * Created by yasmidrog on 09.05.15.
 */
public class DungeonParser {

    int w,h;
    private Dungeon dung;
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

    public Object[][] parse(String lines[]){
        char[] colons;
        w=Integer.valueOf(lines[0].split(" ")[0]);
        h=Integer.valueOf(lines[0].split(" ")[1])+1;
        Object[][] objs=new Object[w][h+1];
        for(int i=1;i<h;i++) {
            colons=lines[i].toCharArray();
            for (int j = 0; j < w; j++) {
                switch (colons[j]) {
                    case '*':
                        System.out.print("*");
                        objs[j][i]=new Object(64*j,64*(i-1),dung);
                        objs[j][i].initImage(Textures.image("floor/wall.png"));
                        break;
                    default:
                        objs[j][i]=new Object(64*j,64*(i-1),dung);
                        objs[j][i].initImage(Textures.image("floor/some.png"));
                        break;
                }
            }
            System.out.println();
        }
        return objs;
    }
}
