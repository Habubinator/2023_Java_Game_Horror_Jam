package novel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Objects;

public class Level {
    GamePanel gp;
    Graphics2D g2;
    final int roomDay = 1;
    final int outside = 2;
    public int levelMode = roomDay;
    BufferedImage SpriteRoomDay;
    BufferedImage SpriteOutside;
    ArrayList<Entity> entities = new ArrayList<>(3);

    public Level(GamePanel gp,int id){
        this.gp = gp;
        loadBackgrounds();
        switch (id){
            case 1:
                levelMode = roomDay;
                entities.add(new Entity(gp,"test",300,760,600));
                entities.add(new Entity(gp,"pc",1200,525,300));
                entities.add(new Entity(gp,"teleport",1650,320,170,true,2));
                break;
            case 2:
                levelMode = outside;
                entities.add(new Entity(gp,"teleport",75,450,200,true,1));
        }
    }

    private void loadBackgrounds() {
        try {
            this.SpriteRoomDay = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/room_background.png")));
            this.SpriteOutside = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/outside_background.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearLevel(){
        this.entities.clear();
    }

    public void drawBG(Graphics2D g2) {
        this.g2 = g2;
        switch (levelMode){
            case roomDay:
                g2.drawImage(SpriteRoomDay,0,0,1920,1080,null);
                Color c = new Color(255,255,255,5);
                g2.setColor(c);
                g2.fillRect(0,90,1980,900);
                break;
            case outside:
                g2.drawImage(SpriteOutside,0,0,1920,1080,null);
        }
    }
}
