package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;
import java.awt.Point;

import math.geom2d.Point2D;
import math.geom2d.Vector2D;

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
        // x += velX;
        // y += velY;
        Game.angle = rotatePlayer();
        
        //movementToRelative();
        x += velX;
        y += velY;
        // velX = 0;
        // velY = 0;
        // collision();
    }

    public void movementToRelative(){
        double mag = Math.sqrt(Math.pow(velX,2) + Math.pow(velY,2));
        //System.out.println((int) mag);
        // if(mag > 0){
        //    double heading = (Math.atan2(velY, velX));
        //    //System.out.println(heading);
        //    double sumAngle = heading + Math.toRadians(Game.angle);
        //     //System.out.println(sumAngle);
        //    velX = Math.cos(sumAngle);
        //    velY = Math.sin(sumAngle);
        // }
    }

    // public double rotatePlayer(){
    //     Point p = Game.getMousePos();
    //     double x2 = p.x - (-x + Game.WIDTH/2);
    //     double y2 = p.y - (-y + Game.HEIGHT/2);
    //     double rotationAngle = (double) -1 * (Math.toDegrees(Math.atan2(y2 - y, x2 - x)) + 90);
    //     return rotationAngle;
    // }

    public double rotatePlayer(){
        Point2D p = Game.getMousePos();
        Point2D playerPos = new Point2D(x, y);
        Vector2D playerVector = new Vector2D(p, playerPos);
        double rotationAngle = playerVector.angle();
        //System.out.println(rotationAngle);
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
        g2d.translate(-16, -16);
        g.fillRoundRect(x, y, 32, 32, 20, 20);
        g2d.translate(16, 16);
        Point2D p = Game.getMousePos();
        int x2 = (int) p.x() - (-x + Game.WIDTH/2);
        int y2 = (int) p.y() - (-y + Game.HEIGHT/2);
        g.drawLine(x, y, x2, y2);
        int x3 = (int) (velX * 100) + x;
        int y3 = (int) (velY * 100) + y;
        g.drawLine(x, y, x3, y3);
        //g.drawOval(x, y, 30, 30);
        g2d.rotate(Math.toRadians(Game.angle), x, y);
    }
}

