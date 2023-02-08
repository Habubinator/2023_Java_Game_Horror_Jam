package novel;

import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Level {
    GamePanel gp;
    ArrayList<Entity> entities = new ArrayList<>(3);

    public Level(GamePanel gp,int id){
        this.gp = gp;
        switch (id){
            case 1:
                entities.add(new Entity(gp,200,350));
                break;
        }
    }

    public void clearLevel(){
        this.entities.clear();
    }
}
