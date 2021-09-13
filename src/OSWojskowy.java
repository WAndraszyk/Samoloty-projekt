import javax.swing.*;
import java.awt.*;

/**
 * Okno specyfikacji dla {@link SamolotWojskowy}. Patrz: {@link OknoSamolot}.
 * Dodatkowo zawiera typ uzbrojenia.
 */
public class OSWojskowy extends OknoSamolot{
    /**
     * Konstruktor. Patrz: {@link OknoSamolot#OknoSamolot(Samolot, Mapa)}
     *
     * @param samolot samolot {@link SamolotWojskowy}
     * @param mapa    mapa
     */
    OSWojskowy(SamolotWojskowy samolot, Mapa mapa) {
        super(samolot,mapa);
        JLabel uzbrojenie = new JLabel("Typ: " + samolot.getTypUzbrojenia());
        uzbrojenie.setSize((new Dimension(300,50)));
        this.add(uzbrojenie);
    }
}
