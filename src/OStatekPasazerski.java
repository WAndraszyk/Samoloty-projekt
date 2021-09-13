import javax.swing.*;
import java.awt.*;

/**
 * Okno specyfikacji dla {@link StatekPasazerski}.
 * Dodatkowo pokazuje pojemność, liczbę pasażerów i firmę.
 */
public class OStatekPasazerski extends OknoStatek {

    /**
     * Konstruktor. Patrz: {@link OknoStatek#OknoStatek(Statek, Mapa)}
     *
     * @param statek statek {@link StatekPasazerski}
     * @param mapa   mapa
     */
    OStatekPasazerski(StatekPasazerski statek, Mapa mapa) {
        super(statek, mapa);
        JLabel maxPasazerowie = new JLabel("Pojemność: "+ statek.getPojemnosc());
        maxPasazerowie.setSize(new Dimension(300,50));
        this.add(maxPasazerowie);
        JLabel passangers = new JLabel("Pasażerowie: "+ statek.getPasazerowie());
        passangers.setSize(new Dimension(300,50));
        this.add(passangers);
        JLabel firma = new JLabel(statek.getFirma());
        this.add(firma);
    }
}
