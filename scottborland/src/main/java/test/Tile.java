package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tile extends GameObject{
    Handler handler;
    public Tile(int x, int y, ID id, Handler handler){
        super(x, y, id);
        this.handler = handler;
    }

    public void tick(){

    }
    
    public void render(Graphics g){
        g.setColor(Color.white);
        g.drawRect((int)x, (int)y, 32, 32);
    }
    public Rectangle getBounds(){
        return new Rectangle((int)x, (int)y, 32, 32);
    }
}
