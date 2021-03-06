package game.sound;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundRepository {
    public void playSound() {
        String bombClicked = "resource/bip.wav";
        try {
            InputStream inputStream = new FileInputStream(bombClicked);
            AudioStream audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
        } catch (IOException e) {
            System.out.println("Error opening file " + bombClicked);
        }
    }
}
