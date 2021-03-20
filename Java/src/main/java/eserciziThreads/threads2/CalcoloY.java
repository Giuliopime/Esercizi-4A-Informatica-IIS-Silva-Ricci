package eserciziThreads.threads2;

public class CalcoloY extends Thread {
    private final double a, b, c;

    public CalcoloY(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public void run() {
        Main.y = 2 * (b - 2);
    }
}
