package game.main.gui;

import game.main.shell.Game;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import static game.main.scene.Menu.sprite;




/**
 * Created by yasmidrog on 27.04.15.
 */
public abstract class ValueButton extends Button{
    public char value;
    private boolean reading=false;
    public ValueButton(int x, int y, int w, String text, Color c) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.text = text;
        this.color = c;
        initValue();
    }
    public  void click(){
        if(!reading) reading=true;
    }
    public void setValue(char c){value=c;}
    public abstract void changeSet();//when value set
    public void initValue(){value=' ';}
    @Override
    public void render(Graphics g){
        int x = this.x + Display.getWidth() / 2;
        int y = this.y;
        int w = this.w + wp;
        int mx = Mouse.getX();
        int my = Display.getHeight() - Mouse.getY();

        sprite[0].setImageColor(color.r, color.g, color.b);
        sprite[1].setImageColor(color.r, color.g, color.b);
        sprite[2].setImageColor(color.r, color.g, color.b);
        sprite[0].draw(x - w / 2 - 16, y);
        sprite[1].draw(x - w / 2, y, w, 50);
        sprite[2].draw(x + w / 2, y);
        g.setColor(Color.white);
        if(reading) {
            Game.font.drawString(x - (text.length() * 8), y + 13, text, Color.white);
            if(Keyboard.getEventKeyState()) {
                value = Keyboard.getEventCharacter();
                reading = false;
                bp = false;
                changeSet();
            }
        }else{
            Game.font.drawString(x - (text.length() * 8), y + 13, text + ": " + value, Color.white);
            if (Math.abs(mx - x) < (w + wp) / 2 && Math.abs(y + 25 - my) < 25) {
                if (wp < 16) {
                    wp *= 2;
                }
                if (bp && !Mouse.isButtonDown(0)) {
                    click();
                }
            } else {
                if (wp > 1) {
                    wp /= 2;
                }
            }
            bp = Mouse.isButtonDown(0);
        }
    }

}