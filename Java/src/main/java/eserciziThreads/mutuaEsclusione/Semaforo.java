package eserciziThreads.mutuaEsclusione;

public class Semaforo {
    private int dispo;
    private final int dispoIniziale;

    public Semaforo(int dispo) {
        this.dispo = dispo;
        dispoIniziale = dispo;
    }

    // Synchronized mette in mutua esclusione il metodo stesso in modo che più thread non lo possano usare allo stesso momento
    synchronized public void p(boolean saltaCoda) throws InterruptedException{
        // Se la coda può essere saltata allora basta che ci sia una disponibilità (quella riservata alle autobotti) altrimenti è necessario usare la seconda (o superiore) disponibilità
        boolean passabile = saltaCoda ? dispo > 0 : dispo > 1;

        // Se non c'è disponibilità il thread viene messo in coda
        while (!passabile) {
            wait();
            passabile = saltaCoda ? dispo > 0 : dispo > 1;
        }

        dispo--;
    }

    synchronized public void v() {
        if (dispo < dispoIniziale) {
            dispo++;
            // Risveglio un altro thread che sta aspettando per una disponibilità
            notify();
        }
    }
}
