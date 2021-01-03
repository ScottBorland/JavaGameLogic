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
    
    double velZ;
    double z;

    Texture tex = Game.getInstance();

    double moveSpeed = 7;
    double jumpSpeed = 8;
    double runAcceleration = 14;
    double runDeacceleration = 10;
    public double friction = 6;
    public double gravity = 2;

    double airAcceleration = 2;          // Air accel   
    double airDecceleration = 2;         // Deacceleration experienced when opposite strafing
    double airControlVar = 0.3;               // How precise air control is
    double sideStrafeAcceleration = 50;  // How fast acceleration occurs to get up to sideStrafeSpeed when
    double sideStrafeSpeed = 1.0; 

    int size = 96;

    protected int verticalInput, horizontalInput;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        this.position = new Vector2D(0, 0);
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
        this.z = 0;
        this.velZ = 0;
        this.grounded = true;
    }

    public void update(){
        size = (int) calcSize(z, 96);
        x += velocity.x();
        y += velocity.y();
        z += velZ;
        if(z <= 0){
            grounded = true;
            z = 0;
            groundMove();
        }else{
            grounded = false;
            airMove();
        }
    }

    public void tick(){
        Game.angle = rotatePlayer();
        update();
    }

    public void groundMove(){
        Vector2D wishdir = new Vector2D(KeyInput.horizontalInput, KeyInput.verticalInput);
        double mag = wishdir.norm();
        if(mag > 0){
            double heading = wishdir.angle(); 
            double sumAngle = heading + Math.toRadians(-Game.angle);
            wishdir = Vector2D.createPolar(1, sumAngle);
            }
           
            if(!KeyInput.wishJump){
                applyFriction(1);
            }else{
                applyFriction(0);
            }
            accelerate(wishdir, moveSpeed, runAcceleration);
            velZ = -gravity;
            if(KeyInput.wishJump){
                velZ = jumpSpeed;
                //KeyInput.wishJump = false;
            }
    }

    public void airMove(){
        double wishVel = airAcceleration;
        double accel;
        Vector2D wishdir = new Vector2D(horizontalInput, verticalInput);
        if(wishdir.norm() != 0){
            double heading = wishdir.angle(); 
            double sumAngle = heading + Math.toRadians(-Game.angle);
            wishdir = Vector2D.createPolar(1, sumAngle);
        }
        double wishSpeed = wishdir.norm();
        wishSpeed *= moveSpeed;

        wishdir.normalize();

        double wishSpeed2 = wishSpeed;
        if (velocity.dot(wishdir) < 0)
            accel = airDecceleration;
        else
            accel = airAcceleration;
        // If the player is ONLY strafing left or right
        if(KeyInput.verticalInput == 0 && KeyInput.horizontalInput != 0)
        {
            if(wishSpeed > sideStrafeSpeed)
                wishSpeed = sideStrafeSpeed;
            accel = sideStrafeAcceleration;
        }

        accelerate(wishdir, wishSpeed, accel);
        if(airControlVar > 0){
            airControl(wishdir, wishSpeed2);
        }
        // !CPM: Aircontrol

        // Apply gravity
        velZ -= gravity;
    }

    public void airControl(Vector2D wishdir, double wishSpeed){
        double yspeed;
        double speed;
        double dot;
        double k;
        // Can't control movement if not moving forward or backward
        if((Math.abs(KeyInput.verticalInput) < 0.001) || (Math.abs(wishSpeed) < 0.001)){
            return;
        }
        yspeed = velZ;
        velZ = 0;
        
        speed = velocity.norm();
        velocity = velocity.normalize();

        dot = velocity.dot(wishdir);
        k = 32;
        k *= airControlVar * dot * dot;

        // Change direction while slowing down
        if (dot > 0)
        {
            Vector2D newVel = new Vector2D(velocity.x() * speed + wishdir.x() * k, velocity.y() * speed + wishdir.y() * k);
            velocity = newVel;
            
            velZ = velZ * speed;

            velocity.normalize();
            //double moveDirectionNorm = this.velocity;
        }

        velocity.times(speed);
        velZ = yspeed; // Note this line 
    }

    double calcSize(double height, double size){
        return (size + (height * 2));
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
        double drop = 0;

        Vector2D vec = velocity.clone();
        
        speed = vec.norm();
        if(grounded){
            if(speed < runDeacceleration){
            control = runDeacceleration;
            }else{
            control = speed;
         }
         drop = control * friction *  t;
        }
        
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
        double angle = -Game.map(p.x(), 0, Game.WIDTH, -180, 180);
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
        g2d.translate(-size/2, -size/2);
        //g.fillRect(x, y, 32, 32);
        g.drawImage(tex.images[0], x, y, size, size, null);
        g2d.translate(size/2, size/2);
        Point2D p = Game.getMousePos();
        g2d.rotate(Math.toRadians(Game.angle), x, y);
    }
}

