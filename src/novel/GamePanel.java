package novel;

import dungeon.Dungeon;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int screenWidth = 1280;
    final int screenHeight = 720;
    int FPS = 60;
    double drawInterval = 1000000000/FPS;
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    Dungeon dungeon;
    String currentMode = "dungeon"; // novel - dungeon
    Player2D player2d;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        start();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null ){

            // UPDATE
            update();
            // DRAW
            repaint();

            double remainingTime = nextDrawTime - System.nanoTime();
            remainingTime /= 1000000;
            if (remainingTime>=0){
                try {
                    Thread.sleep((long)remainingTime); // Принимает милисекунды
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            nextDrawTime +=drawInterval;
        }
    }

    public void start(){
        dungeon = new Dungeon(1, this, keyHandler);
        player2d = new Player2D(this,keyHandler);
    }

    public void update(){
        switch (currentMode){
            case "novel":
                player2d.update();
                break;
            case "dungeon":
                dungeon.player.update();
                break;
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // draw background
        switch (currentMode){
            case "novel":
                break;
            case "dungeon":
                break;
        }

        // draw game
        switch (currentMode){
            case "novel":
                player2d.draw(g2);
                break;
            case "dungeon":
                break;
        }

        // draw front
        switch (currentMode){
            case "novel":
                break;
            case "dungeon":
                break;
        }
        //clear buffer
        g2.dispose();
    }
}