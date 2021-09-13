import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Lotniskowiec.
 */
public class Lotniskowiec extends Statek implements MouseListener {
    private final String typUzbrojenia;
    private final ImageIcon image;
    private final ImageIcon image2;

    /**
     * Konstruktor. Patrz: {@link Statek#Statek(int[], int[][], String, Mapa)}
     *
     * @param typUzbrojenia typ uzbrojenia
     */
    Lotniskowiec(int[] xY, int[][] trasa, String typUzbrojenia, String id, Mapa mapa) {
        super(xY, trasa, id, mapa);
        image = new ImageIcon("lotniskowiec.png");
        image2  = new ImageIcon("lotniskowiec2.png");
        this.setIcon(image);
        this.typUzbrojenia = typUzbrojenia;
        this.addMouseListener(this);
    }

    /**
     * Getter typu uzbrojenia.
     *
     * @return typ uzbrojenia
     */
    public String getTypUzbrojenia() {
        return typUzbrojenia;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        new Thread(new OLotniskowiec(this, this.getMapa())).start();
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
