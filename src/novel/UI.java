package novel;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font menuFont,dialogueFont,dungeonFont;
    public boolean messageOn = false;
    public String currentTalking = "";
    public String currentDialogue = "";
    public int blackScreenOpacity = 255;
    public boolean isScreenBlack = true;
    public boolean levelLoading;
    public int levelID;
    public boolean isSoundMade;


    public UI(GamePanel gp) throws IOException, FontFormatException {
        this.gp = gp;
        InputStream file = Objects.requireNonNull(getClass().getResourceAsStream("/fonts/BadComic-Regular.otf"));
        dialogueFont = Font.createFont(Font.TRUETYPE_FONT, file);
        file = Objects.requireNonNull(getClass().getResourceAsStream("/fonts/Centaur.ttf"));
        menuFont = Font.createFont(Font.TRUETYPE_FONT, file);
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        blackScreen(g2);
        if (this.isScreenBlack){
            if (this.blackScreenOpacity<255){
                blackScreenOpacity +=2;
            }
        }else if (this.blackScreenOpacity>=1){
            blackScreenOpacity -= 2;
        }
        if (this.blackScreenOpacity >=255){
            this.blackScreenOpacity = 255;
            if (this.levelLoading){
                this.isScreenBlack = false;
                gp.player2d.tpOnLevelLoaded();
                gp.novelLevel = new Level(gp,this.levelID);
                this.levelLoading = false;
            }
        }else if(this.blackScreenOpacity<0){
            this.blackScreenOpacity = 0;
        }
    }
    public void blackScreen(Graphics2D g2){
        this.g2 = g2;
        g2.setColor(new Color(0,0,0,this.blackScreenOpacity));
        g2.fillRect(0,0,1980,1200);
    }
    public void drawWindow(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(dialogueFont);
        int x = 5;
        int y = 750 ;
        int width = gp.screenWidth-70;
        int height = gp.screenHeight - 750;
        drawSubWindow(x,y,width,height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32));
        x += 25;
        y += 50;
        g2.drawString(currentTalking,x,y);
        y += 50;
        for(String line: currentDialogue.split("<br> ")){
            g2.drawString(line,x,y);
            y += 50;
        }
        if (!this.isSoundMade){
            gp.sounds.click.start();
            gp.sounds.click.setFramePosition(0);
            isSoundMade = true;
        }
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0,160);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,5,5);
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,5,5);

    }

    public void printText(Graphics2D g2,String text, int x, int y, Font font, int textStyle, int fontSize) {
        g2.setFont(font);
        g2.setFont(g2.getFont().deriveFont(textStyle,fontSize));
        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);
    }

    public void drawItemsName(Graphics2D g2, String name){
        printText(g2,name.substring(0, 1).toUpperCase() + name.substring(1),25,1040,menuFont,Font.PLAIN,48);
    }
    public void drawCredits(Graphics2D g2){

    }
}
