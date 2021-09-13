import javax.swing.*;
import java.util.concurrent.Semaphore;

/**
 * Skrzyżowanie.
 */
public class Skrzyzowanie extends JLabel {
    private final Semaphore semaphore;

    /**
     * Getter semafora.
     *
     * @return {@link Skrzyzowanie#semaphore}
     */
    public Semaphore getSemaphore() {
        return semaphore;
    }

    /**
     * Konstruktor.
     *
     * @param semaphore {@link Skrzyzowanie#semaphore}
     * @param xy        koordynaty skrzyżowania
     */
    Skrzyzowanie(Semaphore semaphore, int[] xy, int w, int h){
        int x = xy[0];
        int y = xy[1];
        this.semaphore = semaphore;
        this.setBounds(x, y, w, h);
    }
}
