package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

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
        Game.angle = rotatePlayer();
        
        movementToRelative();
        x += velX;
        y += velY;
        // velX = 0;
        // velY = 0;
        // collision();
    }

    public void movementToRelative(){
        Vector2D velocity = new Vector2D(velX, velY);
        double mag = velocity.norm();
        if(mag > 0){
            double heading = velocity.angle(); 
        //    double heading = (Math.atan2(velY, velX));
        //    //System.out.println(heading);
            double sumAngle = heading + Math.toRadians(-Game.angle);
            //System.out.println(Game.angle);
            Vector2D wishdir = Vector2D.createPolar(0, sumAngle);
            //wishdir.normalize();
            //System.out.println(wishdir);
        //     //System.out.println(sumAngle);
        //    velX = Math.cos(sumAngle);
        //    velY = Math.sin(sumAngle);
        }
    }

    public double rotatePlayer(){
        Point2D p = Game.getMousePos();
        //p2 is the mouse position relative to the player
        Point2D p2 = new Point2D(p.x()+ x - (Game.WIDTH/2), p.y() + y - (Game.HEIGHT/2));
        Point2D playerPos = new Point2D(x, y);
        Vector2D playerVector = new Vector2D(p2, playerPos);
        double rotationAngle = -Math.toDegrees(playerVector.angle() - 1.570796);
        //System.out.println("rotation angle" + rotationAngle);
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
        // int x3 = (int) (velX * 100) + x;
        // int y3 = (int) (velY * 100) + y;
        // g.drawLine(x, y, x3, y3);
        //g.drawOval(x, y, 30, 30);
        g2d.rotate(Math.toRadians(Game.angle), x, y);
    }
}

