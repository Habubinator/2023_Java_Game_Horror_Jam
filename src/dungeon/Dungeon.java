package dungeon;

import novel.GamePanel;
import novel.KeyHandler;

import java.io.*;
import java.util.ArrayList;

public class Dungeon {
    ArrayList<Tile> dungeonTiles = new ArrayList<>();
    public Player player;
    Tile[][] tiles = new Tile[10][10];
    Map map;
    String subMod; // Создавать строчную переменную и вписывать сюда значения
                   // для переключения управления между головоломками

    public Dungeon(int id, GamePanel gp, KeyHandler keyHandler){
        // Считывание файла карты данжа
        File file = new File("src/dungeon/maps/map-"+id+".txt");
        if (id == 1){
            // Инициализация игрока и считывание данных карты
            player = new Player(gp,keyHandler,this,1, 2, "North",null,null);
            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {throw new RuntimeException(e);}
            String st;
            while (true){
                try {
                    if ((st = br.readLine()) == null) break;
                } catch (IOException e) {throw new RuntimeException(e);}
                String[] data = st.split(",");
                int x = Integer.parseInt(data[0]);
                int y = Integer.parseInt(data[1]);
                boolean isTrap = Boolean.parseBoolean(data[2]);
                boolean isWall = Boolean.parseBoolean(data[3]);
                tiles[x][y] = new Tile(isTrap,isWall);
            }
            map = new Map(tiles);
        }
    }
}
