package novel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player2D {
    int x = 0;
    int y = 310;
    int speed = 5;
    GamePanel gp;
    KeyHandler keyHandler;
    double dialogueInputInterval = 400;
    double nextSkipTime = 0;
    Entity SpeakingEntity;

    //TODO Спрайт - анимации
    public BufferedImage sprite;

    // TODO Спрайты реагируют на сторону движения - отзеркаливание спрайтов
    public String direction = "right"; // left\right

    public Player2D(GamePanel gp, KeyHandler keyHandler){
        this.gp = gp;
        this.keyHandler = keyHandler;
        getImage();
    }

    public void move(KeyHandler key){
        if (key.left_Pressed){
            direction = "left";
            x -= speed;
        } else
        if (key.right_Pressed) {
            direction = "right";
            x += speed;
        } else if (key.use_Pressed) {
            if(System.currentTimeMillis()>nextSkipTime){
                use();
            }
        }
    }
    public void getImage(){
        try{
            sprite = ImageIO.read(getClass().getResourceAsStream("/img/test.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    // TODO Сделать диалоговые окна
    public void use(){
        for (Entity temp:
             gp.novelLevel.entities) {
            if (this.x >=  temp.x - temp.activationWidth/2 &&
                this.x <=  temp.x + temp.activationWidth/2){
                gp.isDialogue = true;
                SpeakingEntity = temp;
                SpeakingEntity.speak();
                SpeakingEntity.incrementMsgCounter();
            }
        }

        nextSkipTime = System.currentTimeMillis() + dialogueInputInterval;
    }
    public void skipMessage(KeyHandler keyHandler){
        if (keyHandler.use_Pressed){
            //do something
            SpeakingEntity.speak();
            SpeakingEntity.incrementMsgCounter();
            if (SpeakingEntity.messageCounter == SpeakingEntity.dialogues.size()){
                gp.isDialogue = false;
            }
            nextSkipTime = System.currentTimeMillis() + dialogueInputInterval;
        }
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
                image = sprite;
                break;
            case "right":
                image = sprite;
                break;
        }
        g2.drawImage(image,x,y,150,150,null);
    }
}
