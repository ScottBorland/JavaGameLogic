package test;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class BasicEnemy extends GameObject{
    private Handler handler;
    public BasicEnemy(int x, int y, ID id, Handler handler){
        super(x, y, id);
        velX = 5;
        velY = 5;
        this.handler = handler;
    }
    public Rectangle getBounds(){
        return new Rectangle(x, y, 16, 16);
    }
    public void tick(){
        x += velX; 
        y += velY;

        if(y <= 0 || y >= Game.HEIGHT - 32)velY *= -1;
        if(x <= 0 || x >= Game.WIDTH - 16)velX *= -1;

        handler.addObject(new Trail(x, y, ID.Trail, Color.red, 16, 16, 0.02f, handler));

    }
    public void render(Graphics g){
        g.setColor(Color.red);
        g.fillRect(x, y, 16, 16);
    }

}
