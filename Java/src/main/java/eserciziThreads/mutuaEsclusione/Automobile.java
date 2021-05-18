package eserciziThreads.mutuaEsclusione;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Automobile extends Thread {
    private final Benzinaio benzinaio;
    private int name;
    private ScheduledExecutorService schedulatoreRiempimento = Executors.newSingleThreadScheduledExecutor();

    public Automobile(Benzinaio benzinaio) {
        this.benzinaio = benzinaio;
        name = 0;
        // Litri della macchina
    }

    @Override
    public void run() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                benzinaio.preleva(50, name++);
            }
        };
        schedulatoreRiempimento.scheduleAtFixedRate(task, 0, 30, TimeUnit.MILLISECONDS);

    }
}
