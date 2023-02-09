package novel;

import java.awt.*;
import java.io.File;
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
    public UI(GamePanel gp) throws IOException, FontFormatException {
        this.gp = gp;
        dialogueFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/BadComic-Regular.otf"));
    }

    public void draw(Graphics2D g2){
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
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(50,50,60,200);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,5,5);
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,5,5);

    }
}
