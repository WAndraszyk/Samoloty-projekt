import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {
    /**
     * Funkcja main.
     * @param args argumenty
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Mapa mapa = new Mapa();
        MyFrame frame = new MyFrame(mapa);
        frame.setVisible(true);
        new PanelKontrolny(mapa);
        new Muzyka("Raiders March.wav").start();
    }
}
