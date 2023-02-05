package dungeon;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Tile {
    boolean trap;
    boolean isWall;
    BufferedImage[] texture = new BufferedImage[4];
    Tile (boolean trap, boolean isWall) {
        this.trap = trap;
        this.isWall = isWall;
    }
    public void addTexture(int id, BufferedImage img){
        texture[id] = img;
    }
}

