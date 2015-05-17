package main.utils;

import game.main.scene.Dungeon;
import java.io.*;
import java.util.Scanner;

/**
 * Created by yasmidrog... DICK WAS THERE, GUESS WHO??? `'`'`'`'`'`WHIZZPERED
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
        w = sc.nextInt();
        h = sc.nextInt();
        dung.floor.w = w;
        dung.floor.h = h;
        dung.floor.floor = new int[w][h];
        sc.nextLine();
        world = new char[h][w];
        for (int i = 0; i < h; i++) {
            String n = sc.nextLine();
            world[i] = n.toCharArray();
        }
    }

    public static void aply() {
        System.out.println("WORKD");
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                switch (world[y][x]) {
                    case (' '):
                        break;
                    case ('*'):
                        dung.floor.set(x, y, 2);
                        System.out.println("Wooool");
                        break;
                    case ('_'):
                        dung.floor.set(x, y, 1);
                        System.out.println("FLooow");
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
