package dungeon;

import java.util.Arrays;

public class Map {
    Tile[][] coordinates;
    public Map(Tile[][] tiles){
        Tile decoy = new Tile(false,true);
        coordinates = tiles;
        for (int i=0;i<coordinates.length;i++){
            for (int j=0;j<coordinates.length;j++){
                if (coordinates[i][j] == null){
                    coordinates[i][j] = decoy;
                }
            }
        }
    }
}
