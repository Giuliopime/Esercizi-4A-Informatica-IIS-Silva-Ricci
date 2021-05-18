package eserciziThreads.mutuaEsclusione;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Autobotte extends Thread {
    private final Benzinaio benzinaio;
    private final ScheduledExecutorService schedulatoreRiempimento = Executors.newSingleThreadScheduledExecutor();

    public Autobotte(Benzinaio benzinaio) {
        this.benzinaio = benzinaio;
    }

    @Override
    public void run() {
        // Riempe il distributore ogni x secondi (x numero random tra 3 e 15)
        // Con un delay iniziale randomico tra 100 e 2000 millisecondi
        Runnable task = () -> {
            try {
                benzinaio.riempi(getName());
            } catch (InterruptedException e) {
                System.out.println("L'autobotte " + getName() + " è esplosa :/");
            }
        };
        schedulatoreRiempimento.scheduleAtFixedRate(task, Benzinaio.numRandom(100, 2000), Benzinaio.numRandom(10000, 30000), TimeUnit.MILLISECONDS);
    }
}
