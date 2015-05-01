/*
 * Did by Whizzpered. 
 * All code is mine.
 */
package main.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Юрий Whizzpered
 */
public class Nick {

    public ArrayList<String> nicks = new ArrayList<String>();
    Random r = new Random();
    
    
    public void init() throws FileNotFoundException {
        Scanner scan = new Scanner(new File("res/text/nicknames.txt"));
        String [] nick = scan.nextLine().split(" ");
        for(String s : nick){
            nicks.add(s);
        }
    }

    public String getNick() {
        String n = nicks.get(r.nextInt(nicks.size()));
        //nicks.remove(n);
        return (n);
    }

}
