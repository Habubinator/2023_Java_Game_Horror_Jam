package novel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class Entity {
    GamePanel gp;
    String entityName;
    int x;
    int y;
    int activationWidth = 160;
    ArrayList<String> dialogues;
    int messageCounter = 0;
    boolean isTeleport;
    int locationId;
    BufferedImage sprite;
    boolean isDialogueEndTriggered = false;
    String subtext = null;
    String gameName;

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
    public Entity(GamePanel gp,String entityName, int x, int y,int activationWidth, String subtext) {
        this.gp = gp;
        this.entityName = entityName;
        this.x = x;
        this.y = y;
        this.activationWidth = activationWidth;
        this.dialogues = new ArrayList<>(3);
        this.subtext = subtext;
        addDialogues(entityName+subtext);
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
    }

    public void addDialogues(String entityName){
        InputStream file = Objects.requireNonNull(getClass().getResourceAsStream("/novel/dialogues/"+entityName+"-dialogue.txt"));
        this.gameName = entityName;
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(file));
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
                gp.ui.isSoundMade = false;
                try {
                    onDialogueStart();
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
            gp.sounds.door_open.start();
            gp.loadNovelLevel(locationId);
            gp.sounds.door_open.setFramePosition(0);
        }
    }

    private void onDialogueStart() {
        if (this.entityName.equals("Wait till the lesson start")){
            gp.ui.isScreenBlack = true;
        }
    }

    public void onDialogueEnd() {
        if (!isDialogueEndTriggered){
            isDialogueEndTriggered = true;
            if ("bedroom-1".equals(entityName)){
                gp.novelLevel.entities.remove(this);
            }
            if ("classmates".equals(entityName)) {
                gp.novelLevel.entities.removeIf(temp -> temp.entityName.equals("desk"));
                gp.novelLevel.entities.add(new Entity(gp, "Wait till the lesson start", 315, 10000, 290));
            }
            if ("Wait till the lesson start".equals(entityName)) {
                gp.loadNovelLevel(gp.novelLevel.lvlID +1);
            }
            if ("pc-1".equals(gameName)) {
                gp.loadNovelLevel(gp.novelLevel.lvlID +1);
                gp.ui.isScreenBlack = true;
            }
        }
    }

    public void incrementMsgCounter() {
        if (messageCounter< dialogues.size()){
            messageCounter++;
        }
    }
}
