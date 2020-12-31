package test;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{

    private Handler handler;
    public static double verticalInput = 0;
    public static double horizontalInput = 0;

    public KeyInput(Handler handler){
        this.handler = handler;
    }
    //Version one for handling key input
    // public void keyPressed(KeyEvent e){
    //     int key = e.getKeyCode();

    //     for(int i = 0; i < handler.object.size(); i++){
    //         GameObject tempObject = handler.object.get(i);
    //         if(tempObject.getId() == ID.Player){
    //             //key events for player:
    //             //Version one with movement non-relative
    //             if(key == KeyEvent.VK_W)tempObject.setVelY(-5);
    //             if(key == KeyEvent.VK_S)tempObject.setVelY(5);
    //             if(key == KeyEvent.VK_D)tempObject.setVelX(5);
    //             if(key == KeyEvent.VK_A)tempObject.setVelX(-5);
    //         }
    //     }
    //     if(key == KeyEvent.VK_ESCAPE)System.exit(1);
    // }

    //New keyPressed function
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W)verticalInput = -1;
        if(key == KeyEvent.VK_S)verticalInput = 1;
        if(key == KeyEvent.VK_D)horizontalInput = 1;
        if(key == KeyEvent.VK_A)horizontalInput = -1;   
    }

    //New keyReleased function
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W)verticalInput = 0;
        if(key == KeyEvent.VK_S)verticalInput = 0;
        if(key == KeyEvent.VK_D)horizontalInput = 0;
        if(key == KeyEvent.VK_A)horizontalInput = 0;
    }
}
