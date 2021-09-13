import javax.swing.*;
import java.awt.*;

/**
 * Okno, w którym wyświetlana jest mapa.
 */
public class MyFrame extends JFrame{

    /**
     * Konstruktor, określony zostaje tytuł okna, ikonka, umiejscowienie na ekranie i wymiary.
     *
     * @param mapa mapa, która będzie wyświetlana w oknie.
     */
    MyFrame(Mapa mapa){
        ImageIcon icon = new ImageIcon("logo.png");
        this.setTitle("Mapa");
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(400,100,1400,899);
        this.setPreferredSize(new Dimension(1400,878));
        mapa.setFrame(this);
        this.add(mapa);
        this.pack();
    }
}