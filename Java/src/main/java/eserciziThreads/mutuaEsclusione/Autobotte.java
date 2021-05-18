package eserciziThreads.mutuaEsclusione;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Autobotte extends Thread {
    private final Benzinaio benzinaio;
    private ScheduledExecutorService schedulatoreRiempimento = Executors.newSingleThreadScheduledExecutor();
    private String name;

    public Autobotte(Benzinaio benzinaio) {
        this.benzinaio = benzinaio;
        name = getName();
        // Litri della macchina
    }

    @Override
    public void run() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                benzinaio.riempi();
            }
        };
        schedulatoreRiempimento.scheduleAtFixedRate(task, 50, 103, TimeUnit.MILLISECONDS);
    }
}
