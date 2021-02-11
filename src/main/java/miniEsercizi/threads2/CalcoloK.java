package miniEsercizi.threads2;

public class CalcoloK extends Thread {
    @Override
    public void run() {
        Main.k = Main.x + Main.t;
    }
}
