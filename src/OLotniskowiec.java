import javax.swing.*;
import java.awt.*;

/**
 * Okno specyfikacji dla {@link Lotniskowiec}.
 * Dodatkowo pokazuje typ uzbrojenia.
 */
public class OLotniskowiec extends OknoStatek{
    /**
     * Konstruktor. Patrz: {@link OknoStatek#OknoStatek(Statek, Mapa)}
     *
     * @param statek {@link Lotniskowiec}
     * @param mapa   mapa
     */
    OLotniskowiec(Lotniskowiec statek, Mapa mapa) {
        super(statek, mapa);
        JLabel typ = new JLabel("Typ: "+statek.getTypUzbrojenia());
        typ.setSize(new Dimension(300,50));
        this.add(typ);
    }
}
