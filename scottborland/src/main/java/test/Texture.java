package test;

import java.awt.image.BufferedImage;

public class Texture {
    public BufferedImage [] images = new BufferedImage[2];

    private BufferedImage playerImage = null;
    
    public Texture(){
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            playerImage = loader.loadImage("/player2.png");
        }catch(Exception e){
            e.printStackTrace();
        }
        getTextures();
    }
    private void getTextures(){
        images[0] = playerImage;
    }
}
