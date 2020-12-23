package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.Point;

public class Player extends GameObject{

    Random r = new Random();
    Handler handler;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, 32, 32);
    }

    public void tick(){
        x += velX;
        y += velY;
        Game.angle = rotatePlayer();
        // x = Game.clamp(x, 0, Game.WIDTH-37);
        // y = Game.clamp(y, 0, Game.HEIGHT-67);
        // collision();
    }

    public double rotatePlayer(){
        Point p = Game.getMousePos();
        double x2 = p.x - (-x + Game.WIDTH/2);
        double y2 = p.y - (-y + Game.HEIGHT/2);
        double rotationAngle = (double) -1 * (Math.toDegrees(Math.atan2(y2 - y, x2 - x)) + 90);
        // if(rotationAngle < 0){
        //     rotationAngle += 360;
        // }
        System.out.println(rotationAngle);
        return rotationAngle;
    }

    // private void collision(){
    //     for(int i = 0; i < handler.object.size();i++){
    //         GameObject tempObject = handler.object.get(i);
    //     }
    // }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(-Game.angle), x, y);
        g.setColor(Color.white);
        g.fillRoundRect(x, y, 32, 32, 20, 20);
        g2d.rotate(Math.toRadians(Game.angle), x, y);
    }
}

