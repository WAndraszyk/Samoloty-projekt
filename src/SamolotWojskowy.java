import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Samolot wojskowy.
 */
public class SamolotWojskowy extends Samolot implements MouseListener {
    private final String typUzbrojenia;
    private final ImageIcon image;
    private final ImageIcon image2;

    /**
     * Konstruktor. Patrz: {@link Samolot#Samolot(int, int, String, Mapa)}
     *
     * @param x             x
     * @param y             y
     * @param id            id
     * @param mapa          mapa
     * @param typUzbrojenia {@link SamolotWojskowy#typUzbrojenia}
     * @throws InterruptedException the interrupted exception
     */
    SamolotWojskowy(int x, int y, String id, Mapa mapa, String typUzbrojenia) throws InterruptedException {
        super(x, y, id, mapa);
        this.typUzbrojenia = typUzbrojenia;
        image = new ImageIcon("wojskowy.png");
        this.setIcon(image);
        image2 = new ImageIcon("wojskowy2.png");
        this.addMouseListener(this);
    }

    /**
     * Getter typu uzbrojenia.
     *
     * @return {@link SamolotWojskowy#typUzbrojenia}
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
        new Thread(new OSWojskowy(this,this.getMapa())).start();
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
