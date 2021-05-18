package eserciziThreads.mutuaEsclusione;

public class Semaforo {
    private int dispo;

    public Semaforo(int dispo) {
        this.dispo = dispo;
    }

    synchronized public boolean p(boolean saltaCoda, boolean mancanoLitri){
        if (dispo < 2 && (!saltaCoda || !mancanoLitri))
            return false;

        while (dispo==0) {
            try {
                wait();
            } catch (InterruptedException e) {
                return false;
            }
        }

        dispo--;
        return true;
    }

    synchronized public void v() {
        dispo++;
        notify();
    }
}
