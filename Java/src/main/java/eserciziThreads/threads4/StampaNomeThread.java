package eserciziThreads.threads4;

public class StampaNomeThread extends Thread {
    private final String nome;

    public StampaNomeThread(String nome) {
        this.nome = nome;
    }

    @Override
    public void run() {
        try {
            sleep(1000);
            System.out.println("Sono il thread " + nome);
        } catch (InterruptedException e) {
            System.out.println("Un thread non ha saputo aspettare :/\n" + Thread.currentThread().getName());
        }
    }
}
