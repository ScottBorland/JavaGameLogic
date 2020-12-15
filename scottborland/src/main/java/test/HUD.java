package test;

import java.awt.Graphics;
import java.awt.Color;

public class HUD {

    public static int HEALTH = 100;

    public void tick(){
        HEALTH = Game.clamp(HEALTH, 0, 100);
    }

    public void render(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(15, 15, 200, 32);
        g.setColor(Color.GREEN);
        g.fillRect(15, 15, HEALTH * 2, 32);
        g.setColor(Color.WHITE);
        g.drawRect(15, 15, 200, 32);
    }  
}
