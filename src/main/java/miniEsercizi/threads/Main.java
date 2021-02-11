package miniEsercizi.threads;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Runnable numeriRunnable = new Numeri20();
        Thread thread = new Thread(numeriRunnable);
        thread.start();

        System.out.println("Eseguendo il codice del thread");

        thread.join();
        System.out.println("Finito");
        System.exit(0);
    }
}
