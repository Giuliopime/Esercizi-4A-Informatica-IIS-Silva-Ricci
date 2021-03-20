package eserciziThreads.threads5;

public class SideThread extends Thread {
    private final Boolean primo;
    public SideThread(Boolean primo) {
        this.primo = primo;
    }

    @Override
    public void run() {
        try {
            if (primo)
                sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Sono " + Thread.currentThread().getName() + " e non ho potuto aspettare perchè sono stato interrotto :/");
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        System.out.println("Sono il " + (primo ? "primo" : "secondo") + " thread e sono stato interrotto :(");
    }
}
