package eserciziThreads.threads;

public class Numeri20 implements Runnable {

    @Override
    public void run() {
        for(int i=0; i<20; i++) {
            System.out.println(i);
            pausa(1);
        }
    }

    synchronized void pausa(int secondi) {
        try {
            wait(secondi);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
