package eserciziThreads.mutuaEsclusione;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Automobile extends Thread {
    private final Benzinaio benzinaio;
    private final ScheduledExecutorService schedulatoreRiempimento = Executors.newSingleThreadScheduledExecutor();

    public Automobile(Benzinaio benzinaio) {
        this.benzinaio = benzinaio;
    }

    @Override
    public void run()  {
        // La macchina preleva una quantità random di litri (minimo 10) ogni x secondi (x numero random tra 3 e 10)
        // Con un delay iniziale randomico tra 100 e 2000 millisecondi
        Runnable task = () -> {
            try {
                benzinaio.preleva(Benzinaio.numRandom(10, 150), getName());
            } catch (InterruptedException e) {
                System.out.println("L'auto " + getName() + " è esplosa :/");
            }
        };
        schedulatoreRiempimento.scheduleAtFixedRate(task, Benzinaio.numRandom(100, 2000), Benzinaio.numRandom(3000, 10000), TimeUnit.MILLISECONDS);
    }
}
