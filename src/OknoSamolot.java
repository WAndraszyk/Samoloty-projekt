import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Okno specyfikacji samolotu. Pokazuje paliwo, id, nastepne lądowanie, aktualną trasę
 * aktualne położenie. Pozwala również lądować awaryjnie, zmienić trasę lub usunąć samolot.
 */
public class OknoSamolot extends JFrame implements Runnable, ActionListener {
    private Samolot samolot;
    private final JLabel polozenie;
    private final JLabel nastepneLadowanie;
    private final JLabel trasa;
    private final JButton zmianaTrasy;
    private final JButton delete;
    private final Mapa mapa;
    private final JButton awaryjne;

    /**
     * Konstruktor.
     *
     * @param samolot {@link Samolot}, którego specyfikacja będzie wyświetlana
     * @param mapa    {@link OknoSamolot#mapa}
     */
    OknoSamolot(Samolot samolot, Mapa mapa){
        ImageIcon icon = new ImageIcon("logo.png");
        this.setIconImage(icon.getImage());
        this.mapa = mapa;
        this.samolot = samolot;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(0,0,300,400);
        this.setLayout(new GridLayout(11,1));
        JLabel id = new JLabel(samolot.getId());
        id.setSize(new Dimension(300,50));
        JLabel personel = new JLabel("Personel: "+samolot.getPersonel());
        personel.setSize(new Dimension(300,50));
        nastepneLadowanie = new JLabel(samolot.getTrasa().getCel());
        nastepneLadowanie.setSize(new Dimension(300,50));
        trasa = new JLabel();
        trasa.setSize(new Dimension(300,50));
        polozenie = new JLabel();
        polozenie.setSize(new Dimension(300,50));
        PasekPaliwa pasekPaliwa = new PasekPaliwa(samolot);
        zmianaTrasy = new JButton("Zmień trasę");
        zmianaTrasy.setFocusable(false);
        zmianaTrasy.addActionListener(this);
        awaryjne = new JButton("Lądowanie awaryjne");
        awaryjne.setFocusable(false);
        awaryjne.addActionListener(this);
        delete = new JButton("Usuń samolot");
        delete.setFocusable(false);
        delete.addActionListener(this);

        this.add(pasekPaliwa);
        new Thread(pasekPaliwa).start();
        this.add(id);
        this.add(personel);
        this.add(nastepneLadowanie);
        this.add(trasa);
        this.add(polozenie);
        this.add(zmianaTrasy);
        this.add(awaryjne);
        this.add(delete);
        this.setVisible(true);
    }

    /**
     * Ta metoda odpowiada za aktualizację informacji wyświetlanych w oknie {@link OknoSamolot} w czasie rzeczywistym.
     */
    public void track(){
        int x = samolot.getX();
        int y = samolot.getY();
        polozenie.setText("X: " + x + " Y: " + y);
        this.zmianaTrasy.setEnabled(!samolot.isLadowanie());
        if(samolot.getPelnaTrasa() != null && samolot.getPelnaTrasa().size() == 2){
            List<Trasa> pelnaTrasa = samolot.getPelnaTrasa();
            try {
                trasa.setText("Trasa: " + pelnaTrasa.get(0).getCel() + " -> " + pelnaTrasa.get(1).getCel());
            }catch (IndexOutOfBoundsException e){
                trasa.setText("Ładowanie trasy...");
            }
            try {
                if ((pelnaTrasa.get(0).getDoceloweLotnisko().getTyp().equals("C") && samolot instanceof SamolotPasazerski) || (pelnaTrasa.get(0).getDoceloweLotnisko().getTyp().equals("W") && samolot instanceof SamolotWojskowy)) {
                    nastepneLadowanie.setText("Następne lądowanie: " + pelnaTrasa.get(0).getCel());
                } else {
                    nastepneLadowanie.setText("Następne lądowanie: " + pelnaTrasa.get(1).getCel());
                }
            }catch (IndexOutOfBoundsException e){
                nastepneLadowanie.setText("Ładowanie następnego lądowania...");
            }
        }
    }

    private boolean tracking = true;

    /**
     * Aktualizacja liczby pasażerów, metoda dla klasy abstrakcyjnej jest pusta.
     */
    public void trackPasazerowie(){}

    @Override
    public void run() {
        while (tracking) {
            track();
            trackPasazerowie();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void actionPerformed(ActionEvent e) {
        if(e.getSource()==zmianaTrasy){
            mapa.noweOknoZmiany(samolot);
        }
        else if(e.getSource()==awaryjne){
            samolot.setAwaria(true);
        }
        else if(e.getSource()==delete){
            samolot.setDelete(true);
            this.tracking = false;
            mapa.remove(samolot);
            mapa.getSamoloty().remove(samolot);
            samolot = null;
            mapa.revalidate();
            mapa.repaint();
            this.dispose();
        }
    }
}
