import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Ta klasa jest odpowiedzialna za muzykę w tle.
 */
public class Muzyka extends Thread {
    private final AudioInputStream audioInputStream;

    /**
     * Konstruktor. You can create an {@link AudioInputStream} with: {@link
     *
     * @link AudioSystem#getAudioInputStream(java.io.File)} ,
     *       {@link AudioSystem#getAudioInputStream(java.io.InputStream)},
     *       {@link AudioSystem#getAudioInputStream(java.net.URL)}
     *
     * @param name {@link AudioInputStream} połączony z dźwiękiem do odtworzenia.
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     *
     */
    public Muzyka(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super();
        this.audioInputStream =AudioSystem.getAudioInputStream(new File(name));
        Clip clip = AudioSystem.getClip();
        clip.open(this.audioInputStream);
        clip.loop(-1);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(1));

    }

    /**
     * Ta metoda pozwala odtwarzać muzykę z
     * {@link #audioInputStream}
     *
     * @throws LineUnavailableException
     *             jeśli obiekt {@link Clip} nie może zostać utworzony
     * @throws IOException
     *             jeśli nie można znależć pliku docelowego
     */
    protected void play() throws LineUnavailableException, IOException {
        Clip clip = AudioSystem.getClip();
        try (clip) {
            clip.open(audioInputStream);
            clip.start();
        }
        audioInputStream.close();
    }

    @Override
    public void run() {
        try {
            this.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


