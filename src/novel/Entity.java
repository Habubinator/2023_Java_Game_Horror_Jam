package novel;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Entity {
    GamePanel gp;
    int x;
    int y;
    int activationWidth = 160;
    ArrayList<String> dialogues;
    Graphics2D g2;
    int messageCounter = 0;

    public Entity(GamePanel gp,int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        dialogues = new ArrayList<>(3);
        addDialogues();
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setColor(Color.white);
        g2.fillRoundRect(x,y,50,50,5,5);
    }

    public void addDialogues(){
        File file = new File("src/novel/dialogues/test-dialogue.txt");
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
        if (!dialogues.isEmpty()){
            try {
                String text[] = dialogues.get(this.messageCounter).split("/");
                gp.ui.currentTalking = text[0];
                gp.ui.currentDialogue = text[1];
            }catch (IndexOutOfBoundsException e){
                messageCounter--;
                String text[] = dialogues.get(this.messageCounter).split("/");
                gp.ui.currentTalking = text[0];
                gp.ui.currentDialogue = text[1];
            }
        }
    }

    public void incrementMsgCounter() {
        if (messageCounter< dialogues.size()){
            messageCounter++;
        }
    }
}
