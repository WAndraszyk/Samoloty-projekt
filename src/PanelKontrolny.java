import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Okno panelu kontrolnego.
 */
public class PanelKontrolny extends JFrame implements ActionListener {
    private final JButton samolotButton;
    private final JButton statekButton;

    private final Mapa mapa;
    private final JRadioButton cywilnyButton;
    private final JRadioButton bombowiecButton;
    private final JRadioButton mysliwiecButton;

    private final JRadioButton pasazerskiButton;
    private final JRadioButton bombowiecLButton;
    private final JRadioButton mysliwiecLButton;
    private final JRadioButton cywilnyLButton;

    /**
     * Konstruktor panelu kontrolnego.
     *
     * @param mapa mapa
     */
    PanelKontrolny(Mapa mapa){
        this.mapa = mapa;
        ImageIcon icon = new ImageIcon("logo.png");

        //---tło----
        JLabel background = new JLabel();
        ImageIcon kokpit = new ImageIcon("panel.jpg");
        background.setIcon(kokpit);
        background.setOpaque(false);

        //----stworz buttons------
        samolotButton = new JButton("Stwórz samolot");
        statekButton = new JButton("Stwórz statek");
        samolotButton.setFocusable(false);
        statekButton.setFocusable(false);
        samolotButton.addActionListener(this);
        statekButton.addActionListener(this);

        //----opcje samoloty----
        cywilnyButton = new JRadioButton("Cywilny");
        cywilnyButton.addActionListener(this);
        cywilnyButton.setEnabled(false);
        cywilnyButton.setFocusable(false);
        bombowiecButton = new JRadioButton("Bombowiec");
        bombowiecButton.addActionListener(this);
        bombowiecButton.setEnabled(false);
        bombowiecButton.setFocusable(false);
        mysliwiecButton = new JRadioButton("Myśliwiec");
        mysliwiecButton.addActionListener(this);
        mysliwiecButton.setEnabled(false);
        mysliwiecButton.setFocusable(false);
        JLabel opcjeSamolot = new JLabel();
        opcjeSamolot.setPreferredSize(new Dimension(140,300));
        opcjeSamolot.setLayout(new FlowLayout());

        ButtonGroup samolotGroup = new ButtonGroup();
        samolotGroup.add(cywilnyButton);
        samolotGroup.add(bombowiecButton);
        samolotGroup.add(mysliwiecButton);

        opcjeSamolot.add(samolotButton);
        opcjeSamolot.add(cywilnyButton);
        opcjeSamolot.add(bombowiecButton);
        opcjeSamolot.add(mysliwiecButton);

        //----opcje statki----
        pasazerskiButton = new JRadioButton("Pasażerski");
        pasazerskiButton.addActionListener(this);
        pasazerskiButton.setFocusable(false);
        bombowiecLButton = new JRadioButton("L - bombowce");
        bombowiecLButton.addActionListener(this);
        bombowiecLButton.setFocusable(false);
        mysliwiecLButton = new JRadioButton("L - myśliwce");
        mysliwiecLButton.addActionListener(this);
        mysliwiecLButton.setFocusable(false);
        cywilnyLButton = new JRadioButton("L - cywilne");
        cywilnyLButton.addActionListener(this);
        cywilnyLButton.setFocusable(false);
        JLabel opcjeStatek = new JLabel();
        opcjeStatek.setPreferredSize(new Dimension(140,300));
        opcjeStatek.setLayout(new FlowLayout());

        ButtonGroup statekGroup = new ButtonGroup();
        statekGroup.add(pasazerskiButton);
        statekGroup.add(bombowiecLButton);
        statekGroup.add(mysliwiecLButton);
        statekGroup.add(cywilnyLButton);
        pasazerskiButton.setSelected(true);

        opcjeStatek.add(statekButton);
        opcjeStatek.add(pasazerskiButton);
        opcjeStatek.add(bombowiecLButton);
        opcjeStatek.add(mysliwiecLButton);
        opcjeStatek.add(cywilnyLButton);

        this.setContentPane( background );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,100);
        this.setLayout(new FlowLayout());
        this.setTitle("Panel kontrolny");
        this.setIconImage(icon.getImage());
        this.add(opcjeSamolot);
        this.add(opcjeStatek);
        this.pack();
        this.setVisible(true);
    }

    private final int[][][] trasyStatkow = {{{519,792},{654,501}},{{427,706},{693,661}},{{443,478},{542,232}},{{346,350},{542,402}},{{177,749},{182,538}},{{15,574},{336,691}},{{95,290},{169,503}},
            {{773,670},{1179,712}},{{844,519},{1083,607}},{{906,438},{981,796}},{{1223,291},{1334,615}}};
    private final String[] statkiNazwy = {"Titanic","Bismarck","Yamato","Flying Dutchman","Bebop","Genesis","Jenny","Nautilus","Argo","Morning Star","Mayflower"};
    private final String[] samolotyNazwy = {"Wraith","Raven","Banshee","Hyperion","Valkyrie","Phoenix","Corsair","Batwing","Ornitopter","TurboKat","Savoia S.21"};
    private int nr = 0, nrS = 0;
    private String samolotProdukcja = "0";
    private String statekProdukcja = "Pasażerski";
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==pasazerskiButton){
            this.statekProdukcja = "Pasażerski";
        }
        else if(e.getSource()==bombowiecLButton){
            this.statekProdukcja = "Lotniskowiec - bombowce";
        }
        else if(e.getSource()==mysliwiecLButton){
            this.statekProdukcja = "Lotniskowiec - myśliwce";
        }
        else if(e.getSource()==cywilnyLButton){
            this.statekProdukcja = "Lotniskowiec - cywilne";
        }
        if(e.getSource()==cywilnyButton){
            this.samolotProdukcja = "Pasażerski";
        }
        else if(e.getSource()==bombowiecButton){
            this.samolotProdukcja = "Bombowiec";
        }
        else if(e.getSource()==mysliwiecButton){
            this.samolotProdukcja = "Myśliwiec";
        }
        Object source = e.getSource();
        if (source == samolotButton) {
            if(nrS==10){
                samolotButton.setEnabled(false);
            }
            if(samolotProdukcja.equals("Pasażerski")){
                int x = 0, y = 0;
                boolean create = false;
                for(Lotniskowiec lotniskowiec:mapa.getLotniskowce()){
                    if(lotniskowiec.getTypUzbrojenia().equals("Lotniskowiec - cywilne")){
                        create = true;
                        x = lotniskowiec.getX();
                        y = lotniskowiec.getY();
                    }
                }
                if(create) {
                    try {
                        mapa.addSamolot(new SamolotPasazerski(x, y, samolotyNazwy[nrS], this.mapa));
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }
            else if(samolotProdukcja.equals("0")){}
            else {
                int x = 0, y = 0;
                boolean create = false;
                String odpowiednik = "Lotniskowiec - myśliwce";
                if(samolotProdukcja.equals("Bombowiec")){
                    odpowiednik = "Lotniskowiec - bombowce";
                }
                for(Lotniskowiec lotniskowiec:mapa.getLotniskowce()){
                    if(lotniskowiec.getTypUzbrojenia().equals(odpowiednik)){
                        create = true;
                        x = lotniskowiec.getX();
                        y = lotniskowiec.getY();
                    }
                }
                if(create) {
                    try {
                        mapa.addSamolot(new SamolotWojskowy(x, y, samolotyNazwy[nrS], this.mapa, samolotProdukcja));
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }
            nrS++;
        }
        else if (source == statekButton) {
            if(nr==10){
                statekButton.setEnabled(false);
            }
            if(statekProdukcja.equals("Pasażerski")){
                mapa.addStatek(new StatekPasazerski(trasyStatkow[nr][0], trasyStatkow[nr], statkiNazwy[nr], this.mapa));
            }
            else {
                mapa.addLotniskowiec(new Lotniskowiec(trasyStatkow[nr][0], trasyStatkow[nr], statekProdukcja,statkiNazwy[nr],this.mapa));
            }
            nr++;
        }

        bombowiecButton.setEnabled(false);
        mysliwiecButton.setEnabled(false);
        cywilnyButton.setEnabled(false);

        for(Lotniskowiec lotniskowiec:mapa.getLotniskowce()){
            if(lotniskowiec.getTypUzbrojenia().equals("Lotniskowiec - bombowce")){
                bombowiecButton.setEnabled(true);
            }
            if(lotniskowiec.getTypUzbrojenia().equals("Lotniskowiec - myśliwce")){
                mysliwiecButton.setEnabled(true);
            }
            if(lotniskowiec.getTypUzbrojenia().equals("Lotniskowiec - cywilne")){
                cywilnyButton.setEnabled(true);
            }
        }
    }
}
