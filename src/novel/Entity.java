package novel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Entity {
    GamePanel gp;
    String entityName;
    int x;
    int y;
    int activationWidth = 160;
    ArrayList<String> dialogues;
    Graphics2D g2;
    int messageCounter = 0;
    boolean isTeleport;
    int locationId;
    BufferedImage sprite;

    public Entity(GamePanel gp,String entityName, int x, int y,int activationWidth) {
        this.gp = gp;
        this.entityName = entityName;
        this.x = x;
        this.y = y;
        this.activationWidth = activationWidth;
        this.dialogues = new ArrayList<>(3);
        addDialogues(entityName);
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/point.png")));
        } catch (IOException e) { throw new RuntimeException(e);}
    }
    public Entity(GamePanel gp,String entityName,int x, int y) {
        this.gp = gp;
        this.entityName = entityName;
        this.x = x;
        this.y = y;
        this.dialogues = new ArrayList<>(3);
        addDialogues(entityName);
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/point.png")));
        } catch (IOException e) { throw new RuntimeException(e);}
    }

    public Entity(GamePanel gp,String entityName, int x, int y,int activationWidth, boolean isTeleport, int locationId) {
        this.gp = gp;
        this.entityName = entityName;
        this.x = x;
        this.y = y;
        this.activationWidth = activationWidth;
        this.isTeleport = isTeleport;
        this.locationId = locationId;
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/point.png")));
        } catch (IOException e) { throw new RuntimeException(e);}
    }

    public void draw(Graphics2D g2){
        if (!this.isTeleport){
            this.g2 = g2;
//        g2.setColor(Color.white);
//        g2.fillRoundRect(x,y,50,50,5,5);
            g2.drawImage(this.sprite,x,y,75,50,null);
        }
    }

    public void addDialogues(String entityName){
        File file = new File("src/novel/dialogues/"+entityName+"-dialogue.txt");
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {throw new RuntimeException(e);}
        String st;
        while (true) {
            try {
                if ((st = br.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            dialogues.add(st);
        }
    }

    public void speak() {
        if (!isTeleport){
            if (!dialogues.isEmpty()){
                try {
                    String[] text = dialogues.get(this.messageCounter).split("/");
                    gp.ui.currentTalking = text[0];
                    gp.ui.currentDialogue = text[1];
                }catch (IndexOutOfBoundsException e){
                    messageCounter--;
                    String[] text = dialogues.get(this.messageCounter).split("/");
                    gp.ui.currentTalking = text[0];
                    gp.ui.currentDialogue = text[1];
                }
            }
        }else{
            gp.loadNovelLevel(locationId);
        }
    }

    public void incrementMsgCounter() {
        if (messageCounter< dialogues.size()){
            messageCounter++;
        }
    }
}
