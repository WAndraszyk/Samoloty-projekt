import javax.swing.*;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Samolot.
 */
public class Samolot extends JLabel implements Runnable {
    private final int maxPaliwo = 125000;
    private final int maxPredkosc = 5;
    private int paliwo;
    private final int personel = 5;
    private Trasa trasa;
    /**
     * Pełna trasa - zbiór {@link Trasa}, którymi będzie leciał
     * samolot.
     */
    private List<Trasa> pelnaTrasa;
    private List<List<Trasa>> mozliweTrasy;
    private int[] cel;
    private final String id;
    private double vX;
    private double vY;
    private Semaphore semaphore;

    /**
     * Setter semafora.
     *
     * @param semaphore {@link Samolot#semaphore}
     */
    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    private Semaphore semaphoreLotnisko;

    /**
     * Setter semafora lotniska.
     *
     * @param semaphoreLotnisko semafor lotniska {@link Samolot#semaphoreLotnisko}
     */
    public void setSemaphoreLotnisko(Semaphore semaphoreLotnisko) {
        this.semaphoreLotnisko = semaphoreLotnisko;
    }

    /**
     * Getter pelnej trasy.
     *
     * @return pełna trasa {@link Samolot#pelnaTrasa}
     */
    public List<Trasa> getPelnaTrasa(){
        return pelnaTrasa;
    }

    /**
     * Setter pełnej trasy.
     *
     * @param pelnaTrasa pełna trasa {@link Samolot#pelnaTrasa}
     */
    public void setPelnaTrasa(List<Trasa> pelnaTrasa) {
        this.pelnaTrasa = pelnaTrasa;
    }

    /**
     * Getter listy możliwych pełnych tras.
     *
     * @return lista możliwych pełnych tras {@link Samolot#mozliweTrasy}
     */
    public List<List<Trasa>> getMozliweTrasy(){
        return mozliweTrasy;
    }

    /**
     * Getter max ilości paliwa.
     *
     * @return max ilość paliwo {@link Samolot#maxPaliwo}
     */
    public int getMaxPaliwo() {
        return maxPaliwo;
    }

    /**
     * Getter aktualnej ilości paliwa.
     *
     * @return aktualna ilość paliwa {@link Samolot#paliwo}
     */
    public int getPaliwo() {
        return paliwo;
    }

    /**
     * Setter ilości paliwa.
     *
     * @param paliwo ilość paliwa {@link Samolot#paliwo}
     */
    public void setPaliwo(int paliwo) {
        this.paliwo = paliwo;
    }

    /**
     * Getter liczby personelu.
     *
     * @return {@link Samolot#personel}
     */
    public int getPersonel() {
        return personel;
    }

    /**
     * Getter trasy {@link Trasa}.
     *
     * @return {@link Trasa} {@link Samolot#trasa}
     */
    public Trasa getTrasa() {
        return trasa;
    }

    /**
     * Setter trasy.
     *
     * @param trasa {@link Samolot#trasa} {@link Trasa}
     * @throws InterruptedException interrupted exception
     */
    public void setTrasa(Trasa trasa) throws InterruptedException {
        if (trasa instanceof TrasaJedno){
            setTrasaJedno((TrasaJedno) trasa);
        }
        else {
            this.trasa = trasa;
        }
    }

    /**
     * Setter trasy jednopasmowej.
     *
     * @param trasa {@link Samolot#trasa} {@link TrasaJedno}
     * @throws InterruptedException interrupted exception
     */
    public synchronized void setTrasaJedno(TrasaJedno trasa) throws InterruptedException {
        setSemaphore(trasa.getSemaphore());
        this.ladowanie = true;
        semaphore.acquire();
        this.ladowanie = false;
        this.trasa = trasa;
    }

    /**
     * Getter celu.
     *
     * @return koordynaty celu {@link Samolot#cel}
     */
    public int[] getCel() {
        return cel;
    }

    /**
     * Setter celu.
     *
     * @param cel koordynaty celu {@link Samolot#cel}
     */
    public void setCel(int[] cel) {
        this.cel = cel;
    }

    /**
     * Getter id samolotu.
     *
     * @return {@link Samolot#id}
     */
    public String getId() {
        return id;
    }

    /**
     * Getter max prędkości.
     *
     * @return max prędkość {@link Samolot#maxPredkosc}
     */
    public double getMaxPredkosc() {
        return maxPredkosc;
    }

    /**
     * Getter prędkości x.
     *
     * @return prędkość x {@link Samolot#vX}
     */
    public double getvX(){
        return vX;
    }

    /**
     * Setter prędkości x.
     *
     * @param vX prędkość x {@link Samolot#vX}
     */
    public void setvX(double vX) {
        this.vX = vX;
    }

    /**
     * Getter prędkości y.
     *
     * @return prędkość y {@link Samolot#vY}
     */
    public double getvY(){
        return vY;
    }

    /**
     * Setter prędkości y.
     *
     * @param vY prędkość y {@link Samolot#vY}
     */
    public void setvY(double vY){this.vY = vY;}

    private final Mapa mapa;

    /**
     * Getter mapy.
     *
     * @return {@link Samolot#mapa}
     */
    public Mapa getMapa() {
        return mapa;
    }



    /**
     * Ta metoda przygotowuje nowy samolot do włączenia się do ruchu.
     * Przeszukuje listę tras mapy {@link Mapa} w poszukiwaniu najbliższej dogodnej trasy {@link Trasa}.
     * Kiedy takowa zostanie znaleziona, jest ona ustawiana jako obecna trasa samolotu.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void nowy() throws InterruptedException {
        double d = 1000000000;
        double d1,d2, d3;
        Trasa choice = null;
        for(Trasa t :this.mapa.getTrasy()) {
            d1 = Math.sqrt(Math.pow(t.getStartXY()[0] - getX(), 2) + Math.pow(t.getStartXY()[1] - getY(),2));
            d3 = Math.sqrt(Math.pow(t.getCelXY()[0] - getX(), 2) + Math.pow(t.getCelXY()[1] - getY(),2));
            if(d1>d3){
                d1 = d3;
            }
            if(t.getPrzystanek() != null) {
                d2 = Math.sqrt(Math.pow(t.getPrzystanek()[0] - getX(), 2) + Math.pow(t.getPrzystanek()[1] - getY(),2));
                if(d1>d2){
                    d1 = d2;
                }
            }
            if(d1 < d){
                d = d1;
                choice = t;
            }
        }
        setTrasa(choice);
    }

    /**
     * Konstruktor.
     *
     * @param x    koordynata x lokalizacji samolotu
     * @param y    koordynata y
     * @param id   {@link Samolot#id}
     * @param mapa {@link Samolot#mapa}
     * @throws InterruptedException the interrupted exception
     */
    Samolot(int x, int y, String id, Mapa mapa) throws InterruptedException {
        this.id = id;
        this.mapa = mapa;
        this.setBounds(x,y,22,21);
        this.nowy();
        this.setPaliwo(this.maxPaliwo);
    }

    /**
     * Ta metoda odpowiada za obliczanie prędkości x i y, z jakimi musi lecieć
     * samolot, aby trafić do celu. Jest używana przy starcie z lotniska, ale również
     * do korekty prędkości w trakcie lotu.
     *
     * @param cX koordynata x celu
     * @param cY koordynata y celu
     */
    public void startuj(int cX, int cY){
        int d = cX - this.getX();
        int h = cY - this.getY();
        double distance = Math.sqrt(h*h + d*d);
        double sinAlfa = h/distance;
        setvY(getMaxPredkosc()*sinAlfa);
        setvX(Math.sqrt(Math.pow(getMaxPredkosc(),2) - Math.pow(getvY(),2)));
        if(h < 0 && getvY() > 0){
            setvY(getvY()*(-1));
        }
        if(d < 0 && getvX() > 0){
            setvX(getvX()*(-1));
        }
    }

    /**
     * Ta metoda odpowiada za proces lądowania samolotu. W przypadku,
     * gdy samolot leciał po {@link TrasaJedno} zwalnia jej semafor.
     * Jeśli typ {@link Lotnisko} nie odpowiada typowi samolotu, samolot
     * nie ląduje i leci do następnego celu. W przeciwnym wypadku samolot zajmuje
     * {@link Samolot#semaphoreLotnisko}, a jeśli jest on zajęty - czeka na zwolnienie
     * nad lotniskiem. Po lądowaniu uzupełniane jest {@link Samolot#paliwo}, a dla {@link SamolotPasazerski}
     * następuje {@link SamolotPasazerski#przesiadka()}. Na końcu {@link Samolot#semaphoreLotnisko} jest zwalniany.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void laduj() throws InterruptedException {
        this.ladowanie = true;
        if(trasa instanceof TrasaJedno){
            semaphore.release();
        }
        for(Lotnisko lotnisko:mapa.getLotniska()){
            if (lotnisko.getNazwa().equals(trasa.getCel())){
                if((lotnisko.getTyp().equals("C") && this instanceof SamolotPasazerski) || (lotnisko.getTyp().equals("W") && this instanceof SamolotWojskowy)) {
                    setSemaphoreLotnisko(lotnisko.getSemaphore());
                    semaphoreLotnisko.acquire();
                    this.setLocation(trasa.getCelXY()[0], trasa.getCelXY()[1]);
                    Thread.sleep(150);
                    setPaliwo(getMaxPaliwo());
                    Thread.sleep(150);
                    if(this instanceof SamolotPasazerski){
                        ((SamolotPasazerski) this).przesiadka();
                    }
                    semaphoreLotnisko.release();
                    break;
                }
                else {
                    this.setLocation(trasa.getCelXY()[0], trasa.getCelXY()[1]);
                    break;
                }
            }
        }
        this.ladowanie = false;
    }

    /**
     * Ta metoda odpowiada za planowanie pełnej trasy. Lista {@link Samolot#pelnaTrasa} jest czyszczona.
     * Przeglądane są trasy, dla którychpunktem startu jest x,y - aktualne położenie.
     * Spośród wybranych losowana jest jedna {@link Trasa}. Trasa ta jest dodawana do {@link Samolot#pelnaTrasa}.
     * Następnie procedura ta jest powtarzana, przy czym za x,y przyjmowane jest położenie celu poprzednio wybranej trasy.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void wyborPelnejTrasy() throws InterruptedException {
        this.pelnaTrasa.clear();
        int x =this.getX(), y = this.getY();
        List<Trasa> temp = new ArrayList<>();
        for (Trasa t : this.mapa.getTrasy()) {
            if (t.getStartXY()[0] == x && t.getStartXY()[1] == y) {
                temp.add(t);
            }
        }
        int random = ThreadLocalRandom.current().nextInt(0, temp.size());
        pelnaTrasa.add(temp.get(random));
        x = temp.get(random).getCelXY()[0];;
        y = temp.get(random).getCelXY()[1];
        temp.clear();
        for (Trasa t : this.mapa.getTrasy()) {
            if (t.getStartXY()[0] == x && t.getStartXY()[1] == y) {
                temp.add(t);
            }
        }
        random = ThreadLocalRandom.current().nextInt(0, temp.size());
        pelnaTrasa.add(temp.get(random));
        setTrasa(pelnaTrasa.get(0));
    }

    /**
     * Pomocnicza zmienna, przyjmuje domyślną wartość true.
     * Oznacza, że {@link Samolot#pelnaTrasa} została stworzona po raz pierwszy.
     */
    private boolean first = true;

    /**
     * Ta metoda odpowiada za określenie {@link Samolot#trasa}.
     * Jeśli {@link Samolot#pelnaTrasa} jeszcze nie istnieje, wywoływana jest metoda
     * {@link Samolot#wyborPelnejTrasy()} po jej wcześniejszym utworzeniu. Zależnie od okoliczności
     * wybierana jest odpowiednia {@link Trasa} z listy {@link Samolot#pelnaTrasa}.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void wyborTrasy() throws InterruptedException {
        if(pelnaTrasa == null){
            this.pelnaTrasa = new ArrayList<>();
            wyborPelnejTrasy();
        }
        if(isZmiana()){
            setTrasa(pelnaTrasa.get(0));
            setZmiana(false);
        }
        else if(first){
            this.trasa = pelnaTrasa.get(0);
            this.first = false;
        }
        else {
            if (pelnaTrasa.get(0).getCelXY()[0] == this.getX() && pelnaTrasa.get(0).getCelXY()[1] == this.getY()) {
                setTrasa(pelnaTrasa.get(1));
            } else {
                wyborPelnejTrasy();
            }
        }
    }

    /**
     * Wyszukuje wszystkie {@link Samolot#pelnaTrasa} możliwe do obrania w chwili wywołania.
     */
    public void znajdzMozliwe(){
        if(mozliweTrasy == null){
            this.mozliweTrasy = new ArrayList<>();
        }
        mozliweTrasy.clear();
        for (Trasa t : this.mapa.getTrasy()) {
            if (t.getStartXY()[0] == getTrasa().getCelXY()[0] && t.getStartXY()[1] == getTrasa().getCelXY()[1]) {
                for (Trasa i : this.mapa.getTrasy()){
                    if (i.getStartXY()[0] == t.getCelXY()[0] && i.getStartXY()[1] == t.getCelXY()[1]){
                        List<Trasa> temp = new ArrayList<>();
                        temp.add(t);
                        temp.add(i);
                        mozliweTrasy.add(temp);
                    }
                }
            }
        }
    }

    /**
     * Oznacza wystąpienie awarii, domyślnie false.
     */
    private boolean awaria = false;

    /**
     * Setter {@link Samolot#awaria}.
     *
     * @param awaria {@link Samolot#awaria}
     */
    public void setAwaria(boolean awaria){
        this.awaria = awaria;
    }

    /**
     * Ta metoda odpowiada za lądowanie awaryjne. Wyszukuje najbliższe {@link Lotnisko}
     * i ustawia je jako cel lotu. Po dotarciu do celu procedura wygląda podobnie jak przy {@link Samolot#laduj()}
     * z tą różnica, że typ {@link Lotnisko} może być dowolny i nie występuje wymiana pasażerów (dla {@link SamolotPasazerski}).
     *
     * @throws InterruptedException the interrupted exception
     */
    public void awaryjne() throws InterruptedException {
        this.ladowanie = true;
        double d = 1000000;
        double d1;
        Lotnisko temp = null;
        for(Lotnisko lotnisko:mapa.getLotniska()){
            d1 = Math.sqrt(Math.pow(lotnisko.getPolozenie()[0] - getX(), 2) + Math.pow(lotnisko.getPolozenie()[1] - getY(),2));
            if(d>d1){
                d = d1;
                temp = lotnisko;
            }
        }
        assert temp != null;
        startuj(temp.getPolozenie()[0], temp.getPolozenie()[1]);
        int i = 0;
        int x, y;
        while (Math.sqrt(Math.pow(temp.getPolozenie()[0] - getX(), 2) + Math.pow(temp.getPolozenie()[1] - getY(), 2)) > 10) {
            if (i == 10) {
                startuj(temp.getPolozenie()[0], temp.getPolozenie()[1]);
                i = 0;
            }
            if (getvX() % Math.floor(getvX()) < 0.5) {
                x = (int) Math.floor(getvX());
            } else {
                x = (int) Math.ceil(getvX());
            }
            if (getvY() % Math.floor(getvY()) < 0.5) {
                y = (int) Math.floor(getvY());
            } else {
                y = (int) Math.ceil(getvY());
            }
            this.przesun(x, y);
            i++;
        }
        setSemaphoreLotnisko(temp.getSemaphore());
        semaphoreLotnisko.acquire();
        this.setLocation(temp.getPolozenie()[0], temp.getPolozenie()[1]);
        Thread.sleep(150);
        setPaliwo(getMaxPaliwo());
        Thread.sleep(150);
        semaphoreLotnisko.release();
        awaria = false;
        this.pelnaTrasa = null;
        this.ladowanie = false;
    }

    /**
     * Samolot przemieszcza się w zależności od tej wartości.
     * Domyślnie true.
     */
    private boolean go = true;

    /**
     * Zwraca wartość {@link Samolot#go}.
     *
     * @return true/false
     */
    public boolean isGo() {
        return go;
    }

    /**
     * Ustawia wartość {@link Samolot#go}. I powiadamia wątek.
     *
     * @param go {@link Samolot#go}
     */
    public synchronized void setGo(boolean go) {
        this.go = go;
        notifyAll();
    }

    /**
     * Przesuwa samolot na {@link Samolot#mapa}.
     * Jeśli {@link Samolot#go} wynosi false samolot się zatrzymuje.
     *
     * @param x przesunięcie w osi x
     * @param y przesunięcie w osi y
     */
    public synchronized void przesun(int x, int y){
        while (!isGo()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.setLocation((this.getX()) + x, (this.getY()) + y);
        this.setPaliwo(this.getPaliwo()-100);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean zmiana = false;

    /**
     * Getter {@link Samolot#zmiana}
     *
     * @return true/false
     */
    public boolean isZmiana() {
        return zmiana;
    }

    /**
     * Setter {@link Samolot#zmiana}.
     *
     * @param zmiana nowa wartość {@link Samolot#zmiana}
     */
    public void setZmiana(boolean zmiana){
        this.zmiana = zmiana;
    }

    /**
     * Ta metoda sprawdza czy samolot wleciał na skrzyżowanie.
     *
     * @param testb skrzyżowanie
     * @param testa samolot
     * @return true/false
     */
    public boolean intersects(JLabel testa, JLabel testb){
        Area areaA = new Area(testa.getBounds());
        Area areaB = new Area(testb.getBounds());

        return areaA.intersects(areaB.getBounds2D());
    }

    private boolean ladowanie = false;

    /**
     * Sprawdza czy samolot jest na lotnisku.
     *
     * @return the boolean
     */
    public boolean isLadowanie() {
        return ladowanie;
    }

    private Skrzyzowanie skrzyzowanie;

    /**
     * Ta metoda odpowiada za ruch na skrzyżowaniach.
     *
     * @param x the x
     * @param y the y
     * @param c skrzyżowanie
     * @throws InterruptedException the interrupted exception
     */
    public synchronized void crossing(int x, int y, Skrzyzowanie c) throws InterruptedException {
        setGo(false);
        c.getSemaphore().acquire();
        this.skrzyzowanie = c;
        setGo(true);
    }

    /**
     * Ta metoda odpowiada za mechanizm poruszania się samolotu. Wywołuje metody odpowiedzialne za obranie celu {@link Samolot#setCel(int[])},
     * obliczanie prędkości {@link Samolot#startuj(int, int)} (które są odpowiednio zaokrąglane do wartości całkowitych), przesuwanie na mapie {@link Samolot#przesun(int, int)},
     * lądowanie {@link Samolot#laduj()}. Sprawdza również, czy samolot nie wleciał na skrzyżowanie (wywołanie metody {@link Samolot#crossing(int, int, Skrzyzowanie)} i czy nie wystąpiła awaria ({@link Samolot#awaryjne()}).
     *
     * @throws InterruptedException the interrupted exception
     */
    public void lot() throws InterruptedException {
        if (trasa.getPrzystanek() != null) {
            setCel(trasa.getPrzystanek());
            startuj(getCel()[0], getCel()[1]);
            int i = 0;
            while (Math.sqrt(Math.pow(getCel()[0] - getX(), 2) + Math.pow(getCel()[1] - getY(), 2)) > 10 && !awaria) {
                if (i == 10) {
                    startuj(getCel()[0], getCel()[1]);
                    i = 0;
                }
                int x, y;
                if (getvX() % Math.floor(getvX()) < 0.5) {
                    x = (int) Math.floor(getvX());
                } else {
                    x = (int) Math.ceil(getvX());
                }
                if (getvY() % Math.floor(getvY()) < 0.5) {
                    y = (int) Math.floor(getvY());
                } else {
                    y = (int) Math.ceil(getvY());
                }
                if(this.skrzyzowanie == null) {
                    for (int n = 0; n < 7; n++) {
                        Skrzyzowanie c = mapa.getSkrzyzowania().get(n);
                        if(intersects(this,c)){
                            crossing(x, y, c);
                            break;
                        }
                    }
                }
                if(this.skrzyzowanie != null){
                    if(!intersects(this,this.skrzyzowanie)){
                    this.skrzyzowanie.getSemaphore().release();
                }}
                this.przesun(x,y);
                i++;
            }
        }
        setCel(trasa.getCelXY());
        startuj(getCel()[0], getCel()[1]);
        int i = 0;
        while (Math.sqrt(Math.pow(getCel()[0] - getX(), 2) + Math.pow(getCel()[1] - getY(), 2)) > 10 && !awaria) {
            if (i == 10) {
                startuj(getCel()[0], getCel()[1]);
                i = 0;
            }
            int x, y;
            if (getvX() % Math.floor(getvX()) < 0.5) {
                x = (int) Math.floor(getvX());
            } else {
                x = (int) Math.ceil(getvX());
            }
            if (getvY() % Math.floor(getvY()) < 0.5) {
                y = (int) Math.floor(getvY());
            } else {
                y = (int) Math.ceil(getvY());
            }
            if(this.skrzyzowanie == null) {
                for (int n = 0; n < 7; n++) {
                    Skrzyzowanie c = mapa.getSkrzyzowania().get(n);
                    if(intersects(this,c)){
                        crossing(x, y, c);
                    }
                }
            }
            if(this.skrzyzowanie != null){
                if(!intersects(this,this.skrzyzowanie)){
                    this.skrzyzowanie.getSemaphore().release();
                    this.skrzyzowanie = null;
                }}
            this.przesun(x,y);
            i++;
        }
        if(!awaria) {
            this.laduj();
        } else {awaryjne();}
        this.wyborTrasy();
    }

    private boolean delete = false;

    /**
     * Setter delete.
     *
     * @param delete delete
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    @Override
    public void run() {
        while (!delete) {
            try {
                lot();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//