import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Ta klasa opisuje mapę, na której znajdują się pozostałe elementy.
 */
public class Mapa extends JLayeredPane{
    private final List<Samolot> samoloty;
    private final List<Statek> statki;
    private final List<Lotniskowiec> lotniskowce;
    private final List<Lotnisko> lotniska;
    private final List<Trasa> trasy;
    private final List<Skrzyzowanie> skrzyzowania;
    private MyFrame frame;

    public void setFrame(MyFrame frame){
        this.frame = frame;
    }

    public MyFrame getFrame() {
        return frame;
    }

    /**
     * Getter listy lotnisk na mapie.
     *
     * @return lista lotnisk
     */
    public List<Lotnisko> getLotniska(){
        return lotniska;
    }

    /**
     * Getter listy lotniskowców.
     *
     * @return lista lotniskowców
     */
    public List<Lotniskowiec> getLotniskowce(){return lotniskowce;}

    /**
     * Getter listy samolotów.
     *
     * @return lista samolotów.
     */
    public List<Samolot> getSamoloty(){
        return samoloty;
    }

    /**
     * Getter listy statków.
     *
     * @return lista statków.
     */
    public List<Statek> getStatki(){
        return statki;
    }

    /**
     * Getter listy tras.
     *
     * @return lista tras.
     */
    public List<Trasa> getTrasy(){
        return trasy;
    }

    /**
     * Getter listy skrzyżowań.
     *
     * @return lista skrzyżowań.
     */
    public List<Skrzyzowanie> getSkrzyzowania(){
        return skrzyzowania;
    }

    /**
     * Konstruktor mapy. Ustawiany jest rozmiar, grafika, tworzone i dodawane do odpowiednich list
     * są lotniska, trasy, skrzyzowania.
     */
    Mapa(){
        samoloty = new ArrayList<>();
        statki = new ArrayList<>();
        lotniskowce = new ArrayList<>();
        //----tło----
        JLabel map = new JLabel();
        ImageIcon image = new ImageIcon("mapa.jpg");
        map.setIcon(image);
        map.setBounds(0,0,1400,839);

        //----lotniska----
        lotniska = new ArrayList<>();
        int[][] lotniskaPolozenie = {{584, 434}, {756, 624}, {425, 617}, {709, 261}, {803, 235}, {1058, 425}, {1090, 384}, {1225, 657}, {299, 363}, {183, 267}};
        String[] lotniskaTypy = {"C",       "W",     "C",       "W",       "C",      "W",       "W",       "C",        "W",      "C"};
        String[] lotniskaNazwy = {"Mali", "RPA", "Brazylia", "Niemcy", "Moskwa", "Tajlandia", "Chiny", "Australia", "Floryda", "Kanada"};
        for(int i = 0; i < 10; i++){
            lotniska.add(new Lotnisko(lotniskaPolozenie[i][0], lotniskaPolozenie[i][1], lotniskaTypy[i], lotniskaNazwy[i],new Semaphore(1)));
        }
        //------trasy-----
        trasy = new ArrayList<>();
        final String[][] trasyCele = {{"Kanada","Niemcy"},{"Floryda","Mali"}, {"Niemcy","Brazylia"}, {"Mali","Tajlandia"},{"Moskwa","RPA"}, {"Chiny", "Moskwa"},{"Kanada","Floryda"}, {"RPA","Australia"}, {"Australia","Chiny"},{"Brazylia","RPA"}};
        final int[][] trasyPrzystanki = {{506,193},{480,295},{462,358},{435,436},{507,394},{633,523}, {886,379},{897,487},{749,440},{837,473},{937,318},{993,243},null,null,null,null};
        int p = 0;
        for(int i = 0; i < 6; i++){
            trasy.add(new Trasa(trasyCele[i][0],trasyCele[i][1],trasyPrzystanki[p],this.lotniska));
            trasy.add(new Trasa(trasyCele[i][1],trasyCele[i][0],trasyPrzystanki[p+1],this.lotniska));
            p+=2;
        }
        for(int i=6; i < 10; i++){
            Semaphore sem = new Semaphore(1);
            trasy.add(new TrasaJedno(trasyCele[i][0],trasyCele[i][1],trasyPrzystanki[p],this.lotniska,sem));
            trasy.add(new TrasaJedno(trasyCele[i][1],trasyCele[i][0],trasyPrzystanki[p],this.lotniska,sem));
            p++;
        }
        //---skrzyzowania----
        skrzyzowania = new ArrayList<>();
        int[][] skrzyzowaniaPolozenia = {{506,395},{481,441},{740,386},{737,462},{825,476},{822,395},{650,403},{206,636},{467,395},{560,679},{945,686},{918,553}};
        for(int i = 0;i < 6;i++){
            skrzyzowania.add(new Skrzyzowanie(new Semaphore(1),skrzyzowaniaPolozenia[i],40,40));
        }
        skrzyzowania.add(new Skrzyzowanie(new Semaphore(1),skrzyzowaniaPolozenia[6],40,72));
        for(int i = 7;i < 12;i++){
            skrzyzowania.add(new Skrzyzowanie(new Semaphore(1),skrzyzowaniaPolozenia[i],40,40));
        }
        //----mapa----
        int panelHeight = 839;
        int panelWidth = 1400;
        this.setBounds(0,0, panelWidth, panelHeight);
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.add(map, Integer.valueOf(0));
        for (Lotnisko i : lotniska){
            this.add(i, Integer.valueOf(1));
        }
        for(Skrzyzowanie s:skrzyzowania){
            this.add(s, Integer.valueOf(1));
        }
        this.setVisible(true);
        this.setOpaque(true);
    }

    /**
     * Ta metoda dodaje nowy samolot do mapy oraz listy samolotów,
     * a także tworzy dla nowego samolotu osobny wątek.
     *
     * @param samolot nowy samolot
     */
    public void addSamolot(Samolot samolot){
        this.add(samolot, Integer.valueOf(2));
        samoloty.add(samolot);
        new Thread(samolot).start();
    }

    /**
     * Ta metoda dodaje nowy statek pasażerski do mapy oraz listy statków,
     * a także tworzy dla nowego statku osobny wątek.
     *
     * @param statek nowy statek pasażerski
     */
    public void addStatek(Statek statek){
        this.add(statek, Integer.valueOf(1));
        statki.add(statek);
        new Thread(statek).start();
    }

    /**
     * Ta metoda dodaje nowy lotniskowiec do mapy, listy lotniskowców,
     * oraz listy statków, a także tworzy dla nowego
     * lotniskowca osobny wątek.
     *
     * @param statek nowy lotniskowiec
     */
    public void addLotniskowiec(Lotniskowiec statek){
        this.add(statek, Integer.valueOf(1));
        lotniskowce.add(statek);
        statki.add(statek);
        new Thread(statek).start();
    }
    /**
     * Ta metoda tworzy nowe okno zmiany trasy,
     * oraz rozpoczyna dla niego nowy wątek.
     *
     * @param samolot samolot, dla którego wybierana jest trasa
     */
    public void noweOknoZmiany(Samolot samolot){
        new Thread(new OknoZmianyTrasy(samolot)).start();
    }
}