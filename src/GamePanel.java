import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48x48
    final int maxScreenCol = 16;
    final int maxScreenRow = 9;
    final int screenWidth = tileSize * maxScreenCol; // 768
    final int screenHeight = tileSize * maxScreenRow; // 432
    Thread gameThread;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

    }
}
