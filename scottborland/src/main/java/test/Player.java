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
    Vector2D position;
    Vector2D velocity;
    Vector2D acceleration;
    Boolean grounded;

    protected int verticalInput, horizontalInput;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        this.position = new Vector2D(0, 0);
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
        this.grounded = true;
    }

    public void update(){
        x += velocity.x();
        y += velocity.y();
        position = position.plus(velocity);
        // x = (int) position.x();
        // y = (int) position.y();
    }

    public void tick(){
        Game.angle = rotatePlayer();
        update();
        groundMove();
    }

    public void groundMove(){
        Vector2D velocity = new Vector2D(KeyInput.horizontalInput, KeyInput.verticalInput);
        double mag = velocity.norm();
        if(mag > 0){
            double heading = velocity.angle(); 
            double sumAngle = heading + Math.toRadians(-Game.angle);
            Vector2D wishdir = Vector2D.createPolar(1, sumAngle);
            //position = new Vector2D(10*wishdir.x(), 10*wishdir.y());
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
        g.fillRect(x, y, 32, 32);
        g2d.translate(16, 16);
        Point2D p = Game.getMousePos();
        g2d.rotate(Math.toRadians(Game.angle), x, y);
    }
}

