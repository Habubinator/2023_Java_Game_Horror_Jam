package dungeon;
import novel.GamePanel;
import novel.KeyHandler;

import java.util.ArrayList;

public class Player {
    int x;
    int y;
    String direction;

    boolean canMove = true;
    ArrayList <String> party ;
    ArrayList <String> inventory;
    Dungeon dungeon;
    GamePanel gp;
    KeyHandler keyHandler;

    double moveInterval = 500;
    double nextMoveTime = 0;

    Player(GamePanel gp, KeyHandler keyHandler,
           Dungeon dungeon, int x, int y, String direction,
           ArrayList<String> party, ArrayList<String> inventory){
        this.gp = gp;
        this.keyHandler = keyHandler;
        this.x = x;
        this.y = y;
        this.dungeon = dungeon;
        this.direction = direction;
        this.party = party;
        this.inventory = inventory;
    }
    public void movement(KeyHandler key)
    {
        if (key.left_Pressed ||
            key.right_Pressed||
            key.up_Pressed||
            key.down_Pressed) {
            switch (direction)
            {
                case "North":
                    if (key.up_Pressed)
                    {
                        if( dungeon.map.coordinates[y-1][x].isWall)
                        {
                            //bump sound effect
                            System.out.println("Bump");
                        }
                        else
                        {
                            y = y - 1;
                            //step sound
                        }
                        break;
                    }
                    if (key.left_Pressed)
                    {
                        direction = "West";
                        break;
                    }
                    if (key.right_Pressed)
                    {
                        direction = "East";
                        break;
                    }
                    if (key.down_Pressed)
                    {
                        direction = "South";
                        break;
                    }
                case "West":
                    if (key.up_Pressed)
                    {
                        if(dungeon.map.coordinates[y][x-1].isWall)
                        {
                            //bump sound effect
                            System.out.println("Bump");
                        }
                        else
                        {
                            x = x - 1;
                            //step sound
                        }
                        break;
                    }
                    if (key.left_Pressed)
                    {
                        direction = "South";
                        break;
                    }
                    if (key.right_Pressed)
                    {
                        direction = "North";
                        break;
                    }
                    if (key.down_Pressed)
                    {
                        direction = "East";
                        break;
                    }
                case "South":
                    if (key.up_Pressed)
                    {
                        if(dungeon.map.coordinates[y+1][x].isWall)
                        {
                            //bump sound effect
                            System.out.println("Bump");
                        }
                        else
                        {
                            y = y + 1;
                            //step sound
                        }
                        break;
                    }
                    if (key.left_Pressed)
                    {
                        direction = "East";
                        break;
                    }
                    if (key.right_Pressed)
                    {
                        direction = "West";
                        break;
                    }
                    if (key.down_Pressed)
                    {
                        direction = "North";
                        break;
                    }
                case "East":
                    if (key.up_Pressed)
                    {
                        if(dungeon.map.coordinates[y][x+1].isWall)
                        {
                            //bump sound effect
                            System.out.println("Bump");
                        }
                        else
                        {
                            x = x + 1;
                            //step sound
                        }
                        break;
                    }
                    if (key.left_Pressed)
                    {
                        direction = "North";
                        break;
                    }
                    if (key.right_Pressed)
                    {
                        direction = "South";
                        break;
                    }
                    if (key.down_Pressed)
                    {
                        direction = "West";
                        break;
                    }
            }
            nextMoveTime = System.currentTimeMillis() + moveInterval;
            drawMap(); // КОНСОЛЬНЫЙ ВЫВОД
            checkStatus();
            System.out.println("Players coord ="+x+"-"+y+", direction - "+direction);
        }
    }


    public void update() {
        if(canMove) {
            if(System.currentTimeMillis()>nextMoveTime){
                movement(keyHandler);
            }
        }
    }

    public void checkStatus(){
        if (dungeon.map.coordinates[y][x].trap){
            System.out.println("You are dead");
        }
    }

    public void drawMap(){
        StringBuilder map = new StringBuilder();
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if(dungeon.map.coordinates[i][j] != null){
                    Tile tile = dungeon.map.coordinates[i][j];
                    if (tile.isWall){
                        map.append(" W ");
                    } else if (tile.trap){
                        map.append(" T ");
                    } else if (x==j&&y==i){
                        map.append(" P ");
                    }else{
                        map.append("   ");
                    }
                }
            }
            map.append("\n");
        }
        System.out.println(map);
    }
}

