package novel;

import dungeon.Dungeon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int screenWidth = 1980;
    final int screenHeight = 1080;
    int FPS = 60;
    double drawInterval = 1000000000/FPS;
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    MainMenu mainMenu;
    Dungeon dungeon;
    String modeMainMenu = "mainmenu";
    String modeNovel = "novel";
    Boolean isDialogue = false;
    String modeDungeon = "dungeon";
    Level novelLevel;
    String currentMode = modeMainMenu; // mainmenu - novel - dungeon
    Player2D player2d;
    UI ui;

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
        novelLevel = new Level(this,1);
        player2d = new Player2D(this,keyHandler);
        mainMenu = new MainMenu(this);
            this.addMouseListener(mainMenu);
        try {
            ui = new UI(this);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(){
        switch (currentMode){
            case "mainmenu":
                mainMenu.update();
                break;
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
            case "mainmenu":
                break;
            case "novel":
                novelLevel.drawBG(g2);
                break;
            case "dungeon":
                break;
        }

        // draw game
        switch (currentMode){
            case "mainmenu":
                // Title name
                mainMenu.draw(g2);
                break;
            case "novel":
                for (Entity temp:
                        novelLevel.entities) {
                    temp.draw(g2);
                }
                player2d.draw(g2);
                break;
            case "dungeon":
                break;
        }

        // draw front
        switch (currentMode){
            case "mainmenu":
                break;
            case "novel":
                ui.draw(g2);
                if (this.isDialogue){
                    ui.drawWindow(g2);
                }
                break;
            case "dungeon":
                break;
        }
        //clear buffer
        g2.dispose();
    }

    public void changeGameMode(int mode){
        switch (mode){
            case 0:
                currentMode = modeMainMenu;
                break;
            case 1:
                mainMenu.clip.stop();
                currentMode = modeNovel;
                break;
            case 2:
                currentMode = modeDungeon;
        }
    }

    public void loadNovelLevel(int levelID){
        ui.isScreenBlack = true;
        ui.levelLoading = true;
        ui.levelID = levelID;
        switch (levelID){
            case 1:
                player2d.estimatedCords = new int[]{1690,450};
                break;
            case 2:
                player2d.estimatedCords = new int[]{75,450};
                break;
        }
    }

}