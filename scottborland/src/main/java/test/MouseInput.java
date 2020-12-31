package test;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import math.geom2d.Point2D;
import math.geom2d.Vector2D;

public class MouseInput implements MouseListener, MouseMotionListener {

    public static Point point;
    public static Point2D point2d;

    @Override
    public void mouseDragged(MouseEvent e) {
        point = e.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        point = e.getPoint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }
    public static Point2D getPoint() {
        //point = e.getPoint();
        Point2D point2d = new Point2D(point.x, point.y); 
        return point2d;
    }  
}
