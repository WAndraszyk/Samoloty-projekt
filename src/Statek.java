import javax.swing.*;

/**
 * Statek.
 */
public class Statek extends JLabel implements Runnable {
    private final String id;
    private int[][] trasa;
    private double vX;
    private double vY;

    /**
     * Getter max predkości.
     *
     * @return max predkosc
     */
    public int getMaxPredkosc() {
        return 4;
    }

    /**
     * Getter prędkości x.
     *
     * @return {@link Statek#vX}
     */
    public double getvX() {
        return vX;
    }

    /**
     * Setter prędkości x.
     *
     * @param vX {@link Statek#vX}
     */
    public void setvX(double vX) {
        this.vX = vX;
    }

    /**
     * Getter prędkości y.
     *
     * @return {@link Statek#vY}
     */
    public double getvY() {
        return vY;
    }

    /**
     * Setter prędkości y.
     *
     * @param vY {@link Statek#vY}
     */
    public void setvY(double vY) {
        this.vY = vY;
    }

    /**
     * Setter trasy.
     *
     * @param trasa trasa (Nie mylić z {@link Trasa})
     */
    public void setTrasa(int[][] trasa){
        this.trasa = trasa;
    }

    /**
     * Getter id statku.
     *
     * @return {@link Statek#id}
     */
    public String getId() {
        return id;
    }

    private final Mapa mapa;

    /**
     * Getter mapy.
     *
     * @return {@link Statek#mapa}
     */
    public Mapa getMapa(){
        return mapa;
    }

    /**
     * Konstruktor.
     *
     * @param xY    koordynaty x,y położenia statku
     * @param trasa koordynaty punktów, między którymi porusza się statek
     * @param id    id statku
     * @param mapa  mapa
     */
    Statek(int[] xY, int[][] trasa, String id, Mapa mapa){
        this.mapa = mapa;
        this.id = id;
        int x = xY[0], y = xY[1];
        this.setBounds(x,y,30,17);
        this.setTrasa(trasa);
    }

    /**
     * Ta metoda odpowiada za obliczanie prędkości x i y, z jakimi musi płynąć
     * statek, aby trafić do celu. Jest używana przy zmianie celu, ale również
     * do korekty prędkości w trakcie rejsu.
     *
     * @param c koordynaty x,y celu
     */
    public void startuj(int[] c){
        int cX = c[0], cY = c[1];
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
     * Przesuwa samolot na {@link Statek#mapa}.
     *
     * @param x przesunięcie w osi x
     * @param y przesunięcie w osi y
     */
    public void przesun(int x, int y){
        this.setLocation((this.getX()) + x, (this.getY()) + y);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ta metoda odpowiada za ruch na skrzyżowaniach.
     *
     * @param x   przesuniecie w osi x
     * @param y   przesuniecie w osi y
     * @param c   skrzyżowanie
     * @param cel koordynaty celu
     * @param i   iterator pomocniczy do korekty prędkości
     * @throws InterruptedException the interrupted exception
     */
    public void crossing(int x, int y, Skrzyzowanie c, int[] cel, int i) throws InterruptedException {
        c.getSemaphore().acquire();
        while (Math.sqrt(Math.pow(c.getX() - (getX()+15), 2) + Math.pow(c.getY() - (getY()+12), 2)) < 35 && Math.sqrt(Math.pow(cel[0] - getX(), 2) + Math.pow(cel[1] - getY(), 2)) > 10){
            if (i == 10) {
                startuj(cel);
                i = 0;
            }
            przesun(x,y);
            i++;
        }
        c.getSemaphore().release();
    }

    /**
     * Ta metoda odpowiada za ruch statku. Sprawdza również, czy statek jest na skrzyżowaniu.
     *
     * @param cel koordynaty x,y celu
     * @throws InterruptedException the interrupted exception
     */
    public void kurs(int[] cel) throws InterruptedException {
        startuj(cel);
        int i = 0;
        while (Math.sqrt(Math.pow(cel[0] - getX(), 2) + Math.pow(cel[1] - getY(), 2)) > 10) {
            if (i == 10) {
                startuj(cel);
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
            for(int n = 7;n<12;n++){
                Skrzyzowanie c = mapa.getSkrzyzowania().get(n);
                if (Math.sqrt(Math.pow(c.getX() - (getX()+15), 2) + Math.pow(c.getY() - (getY()+12), 2)) < 35){
                    crossing(x,y,c, cel, i);
                }
            }
            przesun(x,y);
            i++;
        }
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
                kurs(this.trasa[1]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                kurs(this.trasa[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
