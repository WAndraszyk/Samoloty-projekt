import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Okno specyfikacji statku. Pokazuje id, max prędkość, aktualne położenie
 * i pozwala usunąć statek.
 */
public class OknoStatek extends JFrame implements Runnable, ActionListener {
    private final JLabel polozenie;
    private final Mapa mapa;
    private final JButton delete;

    private Statek statek;

    /**
     * Konstruktor.
     *
     * @param statek statek
     * @param mapa   mapa
     */
    OknoStatek(Statek statek, Mapa mapa){
        ImageIcon icon = new ImageIcon("logo.png");
        this.setIconImage(icon.getImage());
        this.statek = statek;
        this.mapa = mapa;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(0,0,300,300);
        this.setLayout(new GridLayout(10,1));
        JLabel id = new JLabel(statek.getId());
        id.setSize(new Dimension(300,50));
        this.add(id);
        JLabel maxSpeed = new JLabel("Predkość: "+statek.getMaxPredkosc());
        maxSpeed.setSize(new Dimension(300,50));
        this.add(maxSpeed);
        polozenie = new JLabel();
        delete = new JButton("Usuń statek");
        delete.setFocusable(false);
        delete.addActionListener(this);
        this.add(polozenie);
        this.add(delete);
        this.setVisible(true);
    }

    /**
     * Ta metoda aktualizuje położenie statku w czasie rzeczywistym.
     */
    public void track(){
        int x = statek.getX();
        int y = statek.getY();
        polozenie.setText("X: " + x + " Y: " + y);
    }

    private boolean tracking = true;

    @Override
    public void run() {
        while (tracking) {
            track();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==delete){
            statek.setDelete(true);
            this.tracking = false;
            mapa.remove(statek);
            mapa.getStatki().remove(statek);
            mapa.getLotniskowce().remove(statek);
            statek = null;
            System.gc();
            mapa.revalidate();
            mapa.repaint();
            this.dispose();
        }
    }
}
