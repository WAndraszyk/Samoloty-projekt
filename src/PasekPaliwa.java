import javax.swing.*;
import java.awt.*;

/**
 * Pasek paliwa wyświetlany w oknie samolotu.
 */
public class PasekPaliwa extends JProgressBar implements Runnable {
    private final Samolot samolot;

    /**
     * Konstruktor.
     *
     * @param samolot {@link PasekPaliwa#samolot}, którego ilość paliwa jest pokazywana
     */
    PasekPaliwa(Samolot samolot) {
        this.samolot = samolot;
        this.setMaximum(samolot.getMaxPaliwo());
        this.setStringPainted(true);
        this.setBounds(0, 0, 300, 50);
        this.setStringPainted(true);
        this.setForeground(Color.red);
    }

    @Override
    public void run() {
        while (true) {
            this.setValue(this.samolot.getPaliwo());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
