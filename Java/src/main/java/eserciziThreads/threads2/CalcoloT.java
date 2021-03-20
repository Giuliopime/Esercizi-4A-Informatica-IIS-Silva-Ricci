package eserciziThreads.threads2;

public class CalcoloT extends Thread {
    @Override
    public void run() {
        Main.t = Main.y * Main.z;
    }
}
