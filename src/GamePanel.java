import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int screenWidth = 1280;
    final int screenHeight = 720;
    int FPS = 60;
    double drawInterval = 1000000000/FPS;
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();

    String currentMode = "novel";
    Player2D player2d = new Player2D(this,keyHandler);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
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
    public void update(){
        if(Objects.equals(currentMode, "novel")){
            player2d.update();
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // draw background
        player2d.draw(g2);
        // draw front

        //clear buffer
        g2.dispose();
    }
}