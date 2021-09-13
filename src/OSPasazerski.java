import javax.swing.*;
import java.awt.*;

/**
 * Okno specyfikacji dla {@link SamolotPasazerski}. Patrz: {@link OknoSamolot}.
 * Dodatkowo zawiera pojemność i liczbę pasażerów.
 */
public class OSPasazerski extends OknoSamolot{
    private final JLabel passangers;
    private final SamolotPasazerski samolot;

    /**
     * Konstruktor. Patrz: {@link OknoSamolot#OknoSamolot(Samolot, Mapa)}.
     *
     * @param samolot samolot {@link SamolotPasazerski}
     * @param mapa    mapa
     */
    OSPasazerski(SamolotPasazerski samolot, Mapa mapa) {
        super(samolot,mapa);
        this.samolot = samolot;
        JLabel maxPasazerowie = new JLabel("Pojemność: "+ samolot.getMaxPasazerow());
        maxPasazerowie.setSize(new Dimension(300,50));
        this.add(maxPasazerowie);
        passangers = new JLabel("Pasażerowie: "+ samolot.getPasazerowie());
        passangers.setSize(new Dimension(300,50));
        this.add(passangers);
    }

    /**
     * Ta metoda aktualizuje liczbę pasażerów.
     */
    @Override
    public void trackPasazerowie(){
        passangers.setText("Pasażerowie: "+ samolot.getPasazerowie());
    }
}
