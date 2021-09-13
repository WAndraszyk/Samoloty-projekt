import java.util.List;

/**
 * Aktualna trasa samolotu, rozumiana jako odcinek między
 * dwoma lotniskami z przystankiem.
 */
public class Trasa {
    private final String start;
    private final int[] przystanek;
    private final String cel;
    private Lotnisko doceloweLotnisko;
    private int[] startXY;
    private int[] celXY;

    /**
     * Getter docelowego lotniska.
     *
     * @return docelowe lotnisko
     */
    public Lotnisko getDoceloweLotnisko() {
        return doceloweLotnisko;
    }

    /**
     * Getter przystanku na trasie.
     *
     * @return koordynaty przystanku.
     */
    public int[] getPrzystanek() {
        return przystanek;
    }

    /**
     * Getter celu.
     *
     * @return cel
     */
    public String getCel() {
        return cel;
    }

    /**
     * Getter koordynatów startu.
     *
     * @return koordynaty x,y startu
     */
    public int[] getStartXY(){
        return startXY;
    }

    /**
     * Getter koordynatów celu.
     *
     * @return koordynaty x,y celu
     */
    public int[] getCelXY(){
        return celXY;
    }

    /**
     * Konstruktor Trasy.
     *
     * @param start    miejsce startu
     * @param cel      cel
     * @param pXY      koordynaty przystanku
     * @param lotniska lista lotnisk
     */
    Trasa(String start, String cel, int[] pXY, List<Lotnisko> lotniska){
        this.start = start;
        przystanek = pXY;
        this.cel = cel;
        for (Lotnisko i: lotniska) {
            if (i.getNazwa().equals(this.start)){
                this.startXY = i.getPolozenie();
            }
            if (i.getNazwa().equals(this.cel)){
                this.celXY = i.getPolozenie();
                this.doceloweLotnisko = i;
            }
        }
    }
}
