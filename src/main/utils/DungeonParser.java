package main.utils;

import game.main.scene.Dungeon;
import java.io.*;
import java.util.Scanner;

/**
 * Created by yasmidrog... DICK WAS THERE
 */
public class DungeonParser {

    public static int w, h;
    private static File aim;
    private static char[][] world;
    static Dungeon dung;

    public DungeonParser(Dungeon dung, String name) throws FileNotFoundException {
        aim = new File("res/text/dungeons/" + name);
        this.dung = dung;
        Scanner sc = new Scanner(aim);
        sc.nextLine();
        world = new char[h][w];
        for (int i = 0; i < h; i++) {
            String n = sc.nextLine();
            world[i] = n.toCharArray();
        }
        aply();
    }

    public static void aply() {
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                switch (world[y][x]) {
                    case (' '):
                        break;
                    case ('*'):
                        //dung.objs.add(new game.object.Object(x*64,y*64,dung,true));
                        //dung.objs.get(dung.objs.size()-1).initImage("wall.png");
                        break;
                }
            }
        }
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                System.out.print(world[y][x]);
            }
            System.out.println();
        }
    }
}
