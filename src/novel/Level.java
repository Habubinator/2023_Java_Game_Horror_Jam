package novel;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Level {
    long cutsceneTime;
    GamePanel gp;
    Graphics2D g2;
    int lvlID;
    final int roomDay = 1;
    final int outside = 2;
    final int classroom = 3;
    final int onPc = 4;
    final int creditThanks = 5;
    public int levelMode = roomDay;
    BufferedImage spriteRoomDay;
    BufferedImage spriteOutside;
    BufferedImage spriteClass;
    BufferedImage spriteOnPc;
    BufferedImage credits;
    ArrayList<Entity> entities = new ArrayList<>(3);

    public Level(GamePanel gp,int id){
        this.gp = gp;
        this.lvlID = id;
        loadBackgrounds();
        switch (id) {
            case 1 -> {
                levelMode = roomDay;
                entities.add(new Entity(gp, "bedroom-1", -100, -100, 1));
                gp.player2d.addTrigger("bedroom-1");
                entities.add(new Entity(gp, "bed", 300, 760, 600));
                entities.add(new Entity(gp, "pc", 1200, 525, 300));
                entities.add(new Entity(gp, "teleport", 1650, 320, 200, true, 2));
            }
            case 2 -> {
                levelMode = outside;
                gp.sounds.outdoor_bg.start();
                gp.sounds.outdoor_bg.loop(Clip.LOOP_CONTINUOUSLY);
                entities.add(new Entity(gp, "dontneedgothere", 75, 10000, 200));
                entities.add(new Entity(gp, "teleport", 1700, 320, 230, true, 3));
            }
            case 3 -> {
                gp.sounds.outdoor_bg.stop();
                levelMode = classroom;
                entities.add(new Entity(gp, "dontneedgothere", 10, 10000, 100));
                entities.add(new Entity(gp, "desk", 315, 10000, 290));
                entities.add(new Entity(gp, "classmates", 1295, 10000, 670));
            }
            case 4 -> {
                levelMode = classroom;
                entities.add(new Entity(gp, "classmates", 1295, 10000, 670,"-1"));
                entities.add(new Entity(gp, "teleport", 75, 450, 200, true, 5));
            }
            case 5 -> {
                levelMode = outside;
                gp.sounds.outdoor_bg.start();
                gp.sounds.outdoor_bg.loop(Clip.LOOP_CONTINUOUSLY);
                entities.add(new Entity(gp, "teleport", 75, 450, 200, true, 6));
            }
            case 6 -> {
                gp.sounds.outdoor_bg.stop();
                levelMode = roomDay;
                entities.add(new Entity(gp, "pc", 1200, 525, 300,"-1"));
            }
            case 7 -> {
                levelMode = onPc;
                this.cutsceneTime = System.currentTimeMillis();
            }
            case 8 ->{
                levelMode = creditThanks;
                entities.clear();
            }
        }
    }
    private void loadBackgrounds() {
        try {
            this.spriteRoomDay = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/room_background.png")));
            this.spriteOutside = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/outside_background.png")));
            this.spriteClass = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/class_dark_background.png")));
            this.spriteOnPc = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/onpcbg.png")));
            this.credits = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/credits.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawBG(Graphics2D g2) {
        this.g2 = g2;
        switch (levelMode) {
            case roomDay -> {
                g2.drawImage(spriteRoomDay, 0, 0, 1920, 1080, null);
                Color c = new Color(255, 255, 255, 5);
                g2.setColor(c);
                g2.fillRect(0, 90, 1980, 900);
            }
            case outside -> g2.drawImage(spriteOutside, 0, 0, 1920, 1080, null);
            case classroom -> {
                g2.drawImage(spriteClass, 0, 0, 1920, 1080, null);
                Color col = new Color(0, 0, 0, 20);
                g2.setColor(col);
                g2.fillRect(0, 90, 1980, 900);
            }
            case onPc -> g2.drawImage(spriteOnPc, 0, 0, 1920, 1080, null);
            case creditThanks -> g2.drawImage(credits, 0, 0, 1920, 1080, null);
            default -> {}
        }
    }
}
