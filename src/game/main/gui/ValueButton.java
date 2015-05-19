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
public abstract class ValueButton extends Button {

    public String value;
    private boolean reading = false;

    public ValueButton(int x, int y, int width, String text, Color c) {
        super(x, y, width, text, c);
        initValue();
    }

    public ValueButton(buttonState st, int y, int width, String text, Color c) {
        super(st, y, width, text, c);
        initValue();
    }

    public void click() {
        if (!reading) {
            reading = true;
        }
    }

    public void setValue(String c) {
        value = c;
    }

    public abstract void changeSet();//when value set

    public void initValue() {
        value = " ";
    }

    @Override
    public void render(Graphics g) {
        int x;
        if (state == buttonState.LEFT) {
            x = w / 2;
        } else if (state == buttonState.RIGHT) {
            x = Display.getWidth() - w / 2;
        } else if (state == buttonState.CENTER) {
            x = cx + Display.getWidth() / 2;
        } else {
            x = 0;
        }
        int y = this.y;
        int w = this.w + wp;
        int mx = Mouse.getX();
        int my = Display.getHeight() - Mouse.getY();
        sprite[0].setImageColor(color.r, color.g, color.b);
        sprite[1].setImageColor(color.r, color.g, color.b);
        sprite[2].setImageColor(color.r, color.g, color.b);
        if (state == buttonState.CENTER) {
            sprite[0].draw(x - w / 2 - 16, y);
            sprite[1].draw(x - w / 2, y, w, 50);
            sprite[2].draw(x + w / 2, y);
        } else if (state == buttonState.LEFT) {
            sprite[1].draw(x - w / 2, y, w, 50);
            sprite[2].draw(x + w / 2, y);
        } else if (state == buttonState.RIGHT) {
            sprite[0].draw(x - w / 2, y, sprite[0].getWidth(), 50);
            sprite[1].draw(x - w / 2 + sprite[0].getWidth(), y, w, 50);
        }
        g.setColor(Color.white);
        if (reading) {
            Game.font.drawString(x - (text.length() * 10), y + 13, text, Color.white);
            if (Keyboard.getEventKeyState()) {
                value = Keyboard.getKeyName(Keyboard.getEventKey());
                reading = false;
                bp = false;
                changeSet();
            }
        } else {
            Game.font.drawString(x - (text.length() * 10), y + 13, text + ": " + value, Color.white);
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
