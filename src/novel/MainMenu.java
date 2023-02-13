package novel;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

public class MainMenu implements MouseListener {
    GamePanel gp;
    Clip clip;
    public MainMenu(GamePanel gamePanel) {
        this.gp = gamePanel;
        try {
            URL file = new URL("file:src/audio/menu_sound.wav");
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.setFramePosition(0);
            clip.start();
            clip.loop(9999);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }

    }

    public void update() {
        if (gp.currentMode != gp.modeMainMenu){
            clip.stop();
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage bgimage;
        try {
            bgimage = ImageIO.read(new File("src/img/main menu background.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(bgimage, 0, 0, null);
        gp.ui.printText(g2,"Puzzle Cellar",876,375,gp.ui.menuFont, Font.PLAIN,48);
        gp.ui.printText(g2,"Start",960,470, gp.ui.menuFont,Font.PLAIN,36);
        gp.ui.printText(g2,"Controls",937,570, gp.ui.menuFont,Font.PLAIN,36);
        gp.ui.printText(g2,"Quit",960,670, gp.ui.menuFont,Font.PLAIN,36);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Objects.equals(gp.currentMode, gp.modeMainMenu)){
            if (e.getX() >= 960 && e.getX() <= 1025
                    &&  e.getY() >= 450 && e.getY() <= 470){
                gp.changeGameMode(1);
            }else
            if (e.getX() >= 940 && e.getX() <= 1050
                    &&  e.getY() >= 545 && e.getY() <= 570){
                System.out.println("Controls");
                //TODO Add controls image
            }
            else
            if (e.getX() >= 960 && e.getX() <= 1025
                    &&  e.getY() >= 645 && e.getY() <= 670){
                System.exit(0);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
