package novel;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

public class MainMenu implements MouseListener {
    GamePanel gp;
    Clip clip;
    BufferedImage controls;
    boolean isControlsPressed;
    BufferedImage bgimage;
    public MainMenu(GamePanel gamePanel) {
        this.gp = gamePanel;
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(getClass().getResourceAsStream("/audio/menu_sound.wav"))));
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.setFramePosition(0);
            clip.start();
            clip.loop(9999);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
        try {
            bgimage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/main menu background.png")));
            controls = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/img/controls.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        if (gp.keyHandler.use_Pressed){
            isControlsPressed = false;
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(bgimage, 0, 0, null);
        gp.ui.printText(g2,"Pandora's Cellar",860,400,gp.ui.menuFont, Font.PLAIN,42);
        gp.ui.printText(g2,"Start",960,480, gp.ui.menuFont,Font.PLAIN,36);
        gp.ui.printText(g2,"Controls",937,580, gp.ui.menuFont,Font.PLAIN,36);
        gp.ui.printText(g2,"Quit",960,680, gp.ui.menuFont,Font.PLAIN,36);
        if (isControlsPressed){
            g2.drawImage(controls,-50,0,null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Objects.equals(gp.currentMode, gp.modeMainMenu)){
            if (e.getX() >= 960 && e.getX() <= 1025
                    &&  e.getY() >= 455 && e.getY() <= 480){
                gp.changeGameMode(1);
            }else
            if (e.getX() >= 940 && e.getX() <= 1050
                    &&  e.getY() >= 555 && e.getY() <= 580){
                this.isControlsPressed = true;

            }
            else
            if (e.getX() >= 960 && e.getX() <= 1025
                    &&  e.getY() >= 655 && e.getY() <= 680){
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
