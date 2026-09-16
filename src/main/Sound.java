package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[10];
    FloatControl fc;
    int volumeScale = 3;

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/wet_caves.wav");
    }

    public void setFile(int index) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void setVolume() {
        fc.setValue(switch (volumeScale) {
            case 1 -> -16F;
            case 2 -> -8F;
            case 3 -> 0F;
            case 4 -> 8F;
            default -> -80F;
        });
    }
}
