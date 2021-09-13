import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Samolot pasażerski.
 */
public class SamolotPasazerski extends Samolot implements MouseListener {
    private final int maxPasazerow = 50;
    private int pasazerowie = 0;
    private final ImageIcon image;
    private final ImageIcon image2;

    /**
     * Konstruktor. Patrz: {@link Samolot#Samolot(int, int, String, Mapa)}
     *
     * @param x    koordynata x lokalizacji samolotu
     * @param y    koordynata y
     * @param id   id
     * @param mapa mapa
     * @throws InterruptedException the interrupted exception
     */
    SamolotPasazerski(int x, int y, String id, Mapa mapa) throws InterruptedException {
        super(x, y, id, mapa);
        image = new ImageIcon("samolot.png");
        this.setIcon(image);
        image2 = new ImageIcon("samolot2.png");
        this.addMouseListener(this);
    }

    /**
     * Ta metoda odpowiada za zmianę liczby pasażerów.
     */
    public void przesiadka(){
        this.pasazerowie = ThreadLocalRandom.current().nextInt(10, this.maxPasazerow);
    }

    /**
     * Getter liczby {@link SamolotPasazerski#maxPasazerow}.
     *
     * @return liczba {@link SamolotPasazerski#maxPasazerow}
     */
    public int getMaxPasazerow() {
        return maxPasazerow;
    }

    /**
     * Getter liczby {@link SamolotPasazerski#pasazerowie}.
     *
     * @return liczba {@link SamolotPasazerski#pasazerowie}
     */
    public int getPasazerowie() {
        return pasazerowie;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        new Thread(new OSPasazerski(this,this.getMapa())).start();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setIcon(image2);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setIcon(image);
    }
}
