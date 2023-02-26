package novel;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Objects;

public class Sounds implements LineListener{
    GamePanel gp;
    AudioInputStream ais;
    Clip menu_bg;
    Clip door_open;
    Clip outdoor_bg;
    Clip step;
    boolean isPlaybackCompleted;
    public Sounds(GamePanel gamePanel){
        gp = gamePanel;
        try {
            ais = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(getClass().getResourceAsStream("/audio/menu_sound.wav"))));
            menu_bg = AudioSystem.getClip();
            menu_bg.open(ais);
            menu_bg.setFramePosition(0);
            ais = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(getClass().getResourceAsStream("/audio/door_open.wav"))));
            door_open = AudioSystem.getClip();
            door_open.open(ais);
            door_open.setFramePosition(0);
            FloatControl gainControl1 = (FloatControl) door_open.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl1.setValue(-5.0f);
            ais = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(getClass().getResourceAsStream("/audio/outdoor.wav"))));
            outdoor_bg = AudioSystem.getClip();
            outdoor_bg.open(ais);
            outdoor_bg.setFramePosition(0);
            ais = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(getClass().getResourceAsStream("/audio/step.wav"))));
            step = AudioSystem.getClip();
            step.open(ais);
            step.setFramePosition(0);
            FloatControl gainControl2 = (FloatControl) step.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl2.setValue(-7.5f);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) { throw new RuntimeException(e);}
    }

    @Override
    public void update(LineEvent event) {
        if (LineEvent.Type.START == event.getType()) {
            System.out.println("Playback started.");
            isPlaybackCompleted = false;
        } else if (LineEvent.Type.STOP == event.getType()) {
            isPlaybackCompleted = true;
            System.out.println("Playback completed.");
        }
    }
}

