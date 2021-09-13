import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Statek pasażerski.
 */
public class StatekPasazerski extends Statek implements MouseListener {
    private final int pojemnosc = 50;
    private final int pasazerowie;
    private final String firma = "DB - De Best Ships in The World co.";
    private final ImageIcon image;
    private final ImageIcon image2;

    /**
     * Konstruktor. Patrz: {@link Statek#Statek(int[], int[][], String, Mapa)}
     *
     */
    StatekPasazerski(int[] xY, int[][] trasa, String id, Mapa mapa) {
        super(xY, trasa, id, mapa);
        this.pasazerowie = ThreadLocalRandom.current().nextInt(0, this.pojemnosc);
        image = new ImageIcon("wycieczkowy.png");
        this.setIcon(image);
        image2 = new ImageIcon("wycieczkowy2.png");
        this.addMouseListener(this);
    }

    /**
     * Getter pojemności statku.
     *
     * @return pojemność
     */
    public int getPojemnosc() {
        return pojemnosc;
    }

    /**
     * Getter liczby pasażerów.
     *
     * @return liczba pasażerów
     */
    public int getPasazerowie() {
        return pasazerowie;
    }

    /**
     * Getter nazwy firmy.
     *
     * @return nazwa firmy
     */
    public String getFirma() {
        return firma;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        new Thread(new OStatekPasazerski(this, this.getMapa())).start();
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
