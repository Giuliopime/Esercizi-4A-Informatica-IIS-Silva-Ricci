package eserciziThreads.threads4;

public class Main {
    public static void main(String[] args) {
        System.out.println("Sono il thread " + Thread.currentThread().getName());

        new StampaNomeThread("Bello").start();
        new StampaNomeThread("Bello x 2").start();
    }
}
