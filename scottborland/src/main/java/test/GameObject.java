package test;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

    protected int x, y;
    protected ID id;
    protected double velX, velY;

    public GameObject(int x, int y, ID id){
        this.x = x;
        this.y = y; 
        this.id = id;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    // public abstract Rectangle getBounds();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
		this.id = id;
	}

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

}

   