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

    protected int verticalInput, horizontalInput;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        this.verticalInput = 0;
        this.horizontalInput = 0;
    }

    public int getVerticalInput() {
        return verticalInput;
    }

    public int getHorizontalInput() {
        return horizontalInput;
    }
    public void setVerticalInput(int vInput) {
        this.verticalInput = vInput;
    }
    public void setHorizontalInput(int hInput) {
        this.horizontalInput = hInput;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, 32, 32);
    }

    public void tick(){
        Game.angle = rotatePlayer();
        
        movementToRelative();
    }

    public void movementToRelative(){
        Vector2D velocity = new Vector2D(KeyInput.horizontalInput, KeyInput.verticalInput);
        double mag = velocity.norm();
        if(mag > 0){
            double heading = velocity.angle(); 
            double sumAngle = heading + Math.toRadians(-Game.angle);
            System.out.println(Game.angle);
            Vector2D wishdir = Vector2D.createPolar(1, sumAngle);
            //System.out.println(wishdir);
            x += (10*wishdir.x());
            y += (10*wishdir.y());
        }
    }

    public double rotatePlayer(){
        Point2D p = Game.getMousePos();
        double angle = -Game.map(p.x(), 0, Game.WIDTH, -360, 360);
        return angle;
    }

    //This function is defunct and has been replaced by the one above
    public double rotatePlayer2(){
        Point2D p = Game.getMousePos();
        //p2 is the mouse position relative to the player
        Point2D p2 = new Point2D(p.x()+ x - (Game.WIDTH/2), p.y() + y - (Game.HEIGHT/2));
        Point2D playerPos = new Point2D(x, y);
        Vector2D playerVector = new Vector2D(p2, playerPos);
        double rotationAngle = Math.toDegrees(playerVector.angle() - 1.570796);
        return rotationAngle;
    }

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(-Game.angle), x, y);
        g.setColor(Color.white);
        g2d.translate(-16, -16);
        g.fillRoundRect(x, y, 32, 32, 20, 20);
        g2d.translate(16, 16);
        Point2D p = Game.getMousePos();
        //int x2 = (int) p.x() - (-x + Game.WIDTH/2);
        //int y2 = (int) p.y() - (-y + Game.HEIGHT/2);
        //g.drawLine(x, y, x2, y2);
        g2d.rotate(Math.toRadians(Game.angle), x, y);
    }
}

