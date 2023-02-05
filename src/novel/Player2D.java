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

    //TODO Спрайт - анимации
    public BufferedImage sprite;
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
                use();
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
        // TODO Когда игрок возле точки интереса то при использовании сделать вывод диалогового окна
    }
    public void update(){
        move(keyHandler);
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
