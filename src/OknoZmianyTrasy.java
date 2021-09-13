import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Okno zmiany trasy.
 */
public class OknoZmianyTrasy extends JFrame implements Runnable, ActionListener {
    private final Samolot samolot;
    private final JComboBox<String> comboBox;
    private final JButton wybor;
    private List<Trasa> trasa;

    /**
     * Konstruktor.
     *
     * @param samolot samolot, którego trasę chcemy zmienić
     */
    OknoZmianyTrasy(Samolot samolot){
        ImageIcon icon = new ImageIcon("logo.png");
        this.setIconImage(icon.getImage());
        this.samolot = samolot;
        samolot.znajdzMozliwe();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(0,0,300,300);
        this.setLayout(new FlowLayout());
        String[] trasy = new String[samolot.getMozliweTrasy().size()];
        int i = 0;
        for(List<Trasa> t:samolot.getMozliweTrasy()){
            String temp = t.get(0).getCel()+" -> "+t.get(1).getCel();
            trasy[i] = temp;
            i++;
        }
        comboBox = new JComboBox<>(trasy);
        comboBox.addActionListener(this);
        wybor = new JButton("Zatwierdź trasę");
        wybor.setFocusable(false);
        wybor.addActionListener(this);
        this.add(wybor);
        this.add(comboBox);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==comboBox){
            trasa = samolot.getMozliweTrasy().get(comboBox.getSelectedIndex());
        }
        if(e.getSource()==wybor){
            samolot.setPelnaTrasa(trasa);
            this.dispose();
        }
    }

    @Override
    public void run() {
        samolot.setZmiana(true);
    }
}

