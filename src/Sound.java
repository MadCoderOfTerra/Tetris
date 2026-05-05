import javax.sound.sampled.*;
import java.io.File;

public class Sound {
    private Clip clip;
    private long clipTimePosition = 0;
    public void loadSound(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
            } else {
                System.out.println("Audio file NOT FOUND at this exact path: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("Error loading audio: " + filePath);
            e.printStackTrace();
        }
    }
    public void playLoop() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
    }
    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
    public void pause() {
        if (clip != null && clip.isRunning()) {
            clipTimePosition = clip.getMicrosecondPosition();
            clip.stop();
        }
    }
    public void resume() {
        if (clip != null) {
            clip.setMicrosecondPosition(clipTimePosition);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
        }
    }
}