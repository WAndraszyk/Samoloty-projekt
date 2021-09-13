import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Trasa jednopasmowa.
 */
public class TrasaJedno extends Trasa{
    private final Semaphore semaphore;

    /**
     * Getter semafora trasy.
     *
     * @return semafor
     */
    public Semaphore getSemaphore(){
        return this.semaphore;
    }

    /**
     * Konstruktor.
     *
     * @param start     start
     * @param cel       cel
     * @param pXY       koordynaty przystanku
     * @param lotniska  lista Lotnisk
     * @param semaphore semafor
     */
    TrasaJedno(String start, String cel, int[] pXY, List<Lotnisko> lotniska, Semaphore semaphore) {
        super(start, cel, pXY, lotniska);
        this.semaphore = semaphore;
    }
}
