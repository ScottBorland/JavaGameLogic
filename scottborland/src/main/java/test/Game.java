package test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    public static final int WIDTH = 1200, HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean running = false;
    private Random r;
    private Handler handler;
    private BufferedImage level = null;

    Camera cam;

    public Game() {
        cam = new Camera(0, 0);
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));

        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/tile.jpg");//loading the level
        
        new Window(WIDTH, HEIGHT, "Bunny Hopping", this);
        
        r = new Random();

        handler.addObject(new Player(WIDTH/2 -32, HEIGHT/2 -32, ID.Player, handler));
        handler.addObject(new Tile(WIDTH/2 -32, HEIGHT/2 -32, ID.Tile, handler));
    }

    private static final long serialVersionUID = 1L;

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();
        for(int i = 0; i < handler.object.size(); i++){
            if(handler.object.get(i).getId() == ID.Player){
                cam.tick(handler.object.get(i));
            }
        }     
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g2d.translate(cam.getX(), cam.getY());//begin of cam

        handler.render(g);

        g2d.translate(-cam.getX(), -cam.getY());//end of cam

        g.dispose();
        bs.show();
    }

    public static int clamp(int var, int min,  int max){
        if(var >= max){
            return var = max;
        }
        else if (var <= min){
            return var = min;
        }
        else{
            return var;
        }
    }
    public static void main( String[] args )
    {
        new Game();
    }
}
