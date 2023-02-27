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
    Sounds sounds;
    boolean debugMode = true;
    double nextDebugSkipTime = 0;
    double DebugSkipInterval = 400;
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
        sounds = new Sounds(this);
        dungeon = new Dungeon(1, this, keyHandler);
        player2d = new Player2D(this,keyHandler);
        novelLevel = new Level(this,1);
        mainMenu = new MainMenu(this);
            this.addMouseListener(mainMenu);
        try {
            ui = new UI(this);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
        player2d.refleshTriggers();
        player2d.use();
    }

    public void update(){
        if (this.debugMode){
            if (keyHandler.exit){
                System.exit(0);
            }
        }
        switch (currentMode){
            case "mainmenu":
                mainMenu.update();
                break;
            case "novel":
                player2d.update();
                if (this.debugMode){
                    if (keyHandler.skip){
                        if (System.currentTimeMillis() > nextDebugSkipTime){
                            isDialogue = false;
                            loadNovelLevel(novelLevel.lvlID+1);
                            nextDebugSkipTime = System.currentTimeMillis() + DebugSkipInterval;
                        }
                    }
                }
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
                sounds.menu_bg.stop();
                currentMode = modeNovel;
                break;
            case 2:
                currentMode = modeDungeon;
        }
    }

    public void loadNovelLevel(int levelID){
        ui.levelID = levelID;
        switch (novelLevel.lvlID){
            case 0:
                break;
            case 1:
                player2d.estimatedCords = new int[]{75,450};
                break;
            case 2:
                player2d.estimatedCords = new int[]{75,450};
                break;
            case 3:
                player2d.estimatedCords = new int[]{player2d.x,player2d.y};
                break;
            case 4:
                player2d.estimatedCords = new int[]{1690,450};
                break;
            case 5:
                player2d.estimatedCords = new int[]{1690,450};
                break;
            case 6:
                player2d.estimatedCords = new int[]{1690,450};
                break;
        }
        ui.isScreenBlack = true;
        ui.levelLoading = true;
    }

}