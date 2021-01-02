package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Vector;

import math.geom2d.Point2D;
import math.geom2d.Vector2D;

public class Player extends GameObject{

    Random r = new Random();
    Handler handler;
    Vector2D position;
    Vector2D velocity;
    Vector2D acceleration;
    Boolean grounded;

    Texture tex = Game.getInstance();

    double moveSpeed = 10;
    double runAcceleration = 40;
    double runDeacceleration = 10;
    public double friction = 0.8;
    public double gravity = 10;


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
        x += (10 * velocity.x());
        y += (10 * velocity.y());
        velocity = new Vector2D(0, 0);
    }

    public void tick(){
        Game.angle = rotatePlayer();
        groundMove();
        update();
    }

    public void groundMove(){
        Vector2D input = new Vector2D(KeyInput.horizontalInput, KeyInput.verticalInput);
        double mag = input.norm();
        if(mag > 0){
            double heading = input.angle(); 
            double sumAngle = heading + Math.toRadians(-Game.angle);
            Vector2D wishdir = Vector2D.createPolar(1, sumAngle);
            accelerate(wishdir, moveSpeed, runAcceleration);
            applyFriction(1);
        }
    }

    public void accelerate(Vector2D wishdir, double wishSpeed, double accel){
        double addspeed;
        double accelspeed;
        double currentspeed;

        currentspeed = velocity.dot(wishdir);
        addspeed = wishSpeed - currentspeed;
        if(addspeed <= 0)
            return;
        accelspeed = accel * wishSpeed;
        if(accelspeed > addspeed) 
            accelspeed = addspeed;
        Vector2D addVel = new Vector2D (accelspeed * wishdir.x(), accelspeed * wishdir.y());
        velocity = velocity.plus(addVel);
    }

    public void applyFriction(double t){
        double speed;
        double newSpeed;
        double control;
        double drop;

        Vector2D vec = velocity.clone();

        //vec.z = 0;
        speed = vec.norm();

        if(speed < runDeacceleration){
            control = runDeacceleration;
        }else{
            control = speed;
        }

        drop = control * friction *  t;
        
        newSpeed = speed - drop;
        //playerFriction = newSpeed;
        if(newSpeed < 0){
            newSpeed = 0;
        }
        if(speed > 0){
            newSpeed /= speed;
        }

        velocity = velocity.times(newSpeed);
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
        g2d.translate(-32, -32);
        //g.fillRect(x, y, 32, 32);
        g.drawImage(tex.images[0], x, y, 64, 64, null);
        g2d.translate(32, 32);
        Point2D p = Game.getMousePos();
        g2d.rotate(Math.toRadians(Game.angle), x, y);
    }
}

