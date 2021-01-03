package test;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{

    private Handler handler;
    public static double verticalInput = 0;
    public static double horizontalInput = 0;
    public static Boolean wishJump = false;

    public KeyInput(Handler handler){
        this.handler = handler;
    }

    //New keyPressed function
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W)verticalInput = -1;
        if(key == KeyEvent.VK_S)verticalInput = 1;
        if(key == KeyEvent.VK_D)horizontalInput = 1;
        if(key == KeyEvent.VK_A)horizontalInput = -1;  
        
        if(key == KeyEvent.VK_SPACE)wishJump = true;

        if(key == KeyEvent.VK_ESCAPE)System.exit(1);
    }

    //New keyReleased function
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W)verticalInput = 0;
        if(key == KeyEvent.VK_S)verticalInput = 0;
        if(key == KeyEvent.VK_D)horizontalInput = 0;
        if(key == KeyEvent.VK_A)horizontalInput = 0;
        
        if(key == KeyEvent.VK_SPACE)wishJump = false;
    }
}
