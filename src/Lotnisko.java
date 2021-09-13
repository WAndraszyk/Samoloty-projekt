import javax.swing.*;
import java.util.concurrent.Semaphore;

/**
 * Lotnisko.
 */
public class Lotnisko extends JLabel {
    private final int[] polozenie;
    private final String typ;
    private final String nazwa;
    private final Semaphore semaphore;

    /**
     * Getter semafora.
     *
     * @return semafor
     */
    public Semaphore getSemaphore(){
        return this.semaphore;
    }

    /**
     * Getter położenia.
     *
     * @return koordynaty położenia
     */
    public int[] getPolozenie() {
        return polozenie;
    }

    /**
     * Getter typu Lotniska.
     *
     * @return typ
     */
    public String getTyp() {
        return typ;
    }

    /**
     * Getter nazwy string.
     *
     * @return nazwa string
     */
    public String getNazwa(){
        return nazwa;
    }

    /**
     * Konstruktor.
     *
     * @param x         koordynat x
     * @param y         koordynat y
     * @param typ       typ
     * @param nazwa     nazwa
     * @param semaphore semafor
     */
    Lotnisko(int x, int y, String typ, String nazwa, Semaphore semaphore){
        this.semaphore = semaphore;
        this.typ = typ;
        this.nazwa = nazwa;
        polozenie = new int[2];
        polozenie[0] = x;
        polozenie[1] = y;
        this.setBounds(polozenie[0],polozenie[1],22,22);
        ImageIcon image = new ImageIcon("lotnisko.png");
        this.setIcon(image);
    }
}
