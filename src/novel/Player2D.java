package novel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Player2D {
    int x = 150;
    int y = 450;
    int speed = 5;
    int animationPos = 0;
    GamePanel gp;
    KeyHandler keyHandler;
    double dialogueInputInterval = 400;
    double animationFrameInterval = 80;
    double nextSkipTime = 0;
    double nextFrameTime = 0;
    Entity SpeakingEntity;
    public BufferedImage[] spriteRight = new BufferedImage[6];
    public BufferedImage[] spriteLeft = new BufferedImage[6];
    public String direction = "right"; // left\right
    public int[] estimatedCords = new int[]{75,450};
    public  boolean isInScene = true;
    Map<String, Boolean> isSceneTriggered = new HashMap<>();;
    private String sceneName;

    public Player2D(GamePanel gp, KeyHandler keyHandler){
        this.gp = gp;
        this.keyHandler = keyHandler;
        getImage();
    }

    public void move(KeyHandler key){
        if (key.left_Pressed){
            direction = "left";
            if (gp.sounds.step.getFramePosition() >= gp.sounds.step.getFrameLength() - 47800){
                gp.sounds.step.stop();
                gp.sounds.step.setFramePosition(0);
            }
            if (x>=-15){
                x -= speed;
                if (System.currentTimeMillis()>nextFrameTime){
                    animationPos++;
                    nextFrameTime = System.currentTimeMillis() + animationFrameInterval;
                    if (animationPos >=6){
                        animationPos = 0;
                        gp.sounds.step.start();
                    }
                }
            }else {
                animationPos = 0;
                gp.sounds.step.stop();
                gp.sounds.step.setFramePosition(0);
            }
        } else
        if (key.right_Pressed) {
            direction = "right";
            if (gp.sounds.step.getFramePosition() >= gp.sounds.step.getFrameLength() - 47800){
                gp.sounds.step.stop();
                gp.sounds.step.setFramePosition(0);
            }
            if (x<=1800){
                x += speed;
                if (System.currentTimeMillis()>nextFrameTime){
                    animationPos++;
                    nextFrameTime = System.currentTimeMillis() + animationFrameInterval;
                    if (animationPos >=6){
                        animationPos = 0;
                        gp.sounds.step.start();
                    }
                }
            }else {
                animationPos = 0;
                gp.sounds.step.stop();
                gp.sounds.step.setFramePosition(0);
            }
        } else{
            animationPos = 0;
            gp.sounds.step.stop();
            gp.sounds.step.setFramePosition(0);
            if (key.use_Pressed) {
                if(System.currentTimeMillis()>nextSkipTime){
                    use();
                }
        }
        }
    }
    public void getImage(){
        try{
            for (int i = 0; i < spriteRight.length; i++ ) {
                spriteRight[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/mh_walking/" + i + ".png")));
                spriteLeft[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/mh_walking/" + i + "-left.png")));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void use(){
        for (Entity temp:
             gp.novelLevel.entities) {
            if (temp.entityName.equals(this.sceneName) || this.x >=  temp.x - temp.activationWidth/2 &&
                this.x <=  temp.x + temp.activationWidth/2){
                if (!temp.isTeleport){
                    gp.isDialogue = true;
                    SpeakingEntity = temp;
                    SpeakingEntity.speak();
                    SpeakingEntity.incrementMsgCounter();
                }else{
                    temp.speak();
                }
                break;
            }
        }

        nextSkipTime = System.currentTimeMillis() + dialogueInputInterval;
    }

    public void skipMessage(KeyHandler keyHandler){
        if (keyHandler.use_Pressed){
            SpeakingEntity.speak();
            SpeakingEntity.incrementMsgCounter();
            if (SpeakingEntity.messageCounter == SpeakingEntity.dialogues.size()){
                if (isInScene){
                    isInScene = false;
                    gp.ui.isScreenBlack = false;
                    isSceneTriggered.put(SpeakingEntity.entityName,true);
                    gp.novelLevel.entities.remove(SpeakingEntity);
                    SpeakingEntity = null;
                }
                gp.isDialogue = false;
                if (SpeakingEntity != null){
                    SpeakingEntity.onDialogueEnd();
                }
            }
            nextSkipTime = System.currentTimeMillis() + dialogueInputInterval;
        }
    }
    
    public void refleshTriggers(){
        for (HashMap.Entry<String,Boolean> temp:
                isSceneTriggered.entrySet()) {
            if (!temp.getValue()){
                this.sceneName = temp.getKey();
            }
        }
    }

    public void addTrigger(String name){
        this.isSceneTriggered.put(name,false);
    }

    public void update(){
        if (!gp.isDialogue && !gp.ui.isScreenBlack){
            move(keyHandler);
        }else{
            if(System.currentTimeMillis()>nextSkipTime){
                skipMessage(keyHandler);
            }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        switch (direction){
            case "left":
                image = spriteLeft[animationPos];
                break;
            case "right":
                image = spriteRight[animationPos];
                break;
        }
        g2.drawImage(image,x,y,200,550,null);
    }

    public void tpOnLevelLoaded() {
        this.x = estimatedCords[0];
        this.y = estimatedCords[1];
    }
}
