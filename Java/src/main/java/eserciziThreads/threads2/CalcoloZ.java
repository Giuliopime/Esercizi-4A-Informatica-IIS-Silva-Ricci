package eserciziThreads.threads2;

public class CalcoloZ extends Thread {
    private final double a, b, c;

    public CalcoloZ(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public void run() {
        Main.z = 3 * (c - 3);
    }
}
