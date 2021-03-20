package eserciziThreads.threads2;

public class CalcoloX extends Thread {
    private final double a, b, c;

    public CalcoloX(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public void run() {
        Main.x = 3 * (a - 1);
    }
}
