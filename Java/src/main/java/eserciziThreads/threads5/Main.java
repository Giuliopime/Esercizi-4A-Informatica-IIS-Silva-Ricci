package eserciziThreads.threads5;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        SideThread thread1 = new SideThread(true);
        SideThread thread2 = new SideThread(false);

        thread1.start();
        thread2.start();

        Thread.sleep(1000);

        thread1.interrupt();
        thread2.interrupt();
    }
}
