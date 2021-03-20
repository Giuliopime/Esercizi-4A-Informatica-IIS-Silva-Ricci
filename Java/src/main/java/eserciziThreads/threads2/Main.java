package eserciziThreads.threads2;

public class Main {
    public static double x, y, z, t, k;
    /*
    Espressione:
        k = 3*(a - 1) + 2*(b - 2)*3*(c - 3)

        x = 3*(a - 1)
        y = 2*(b - 2)
        z = 3*(c - 3)
        t = y * z
        k = x + t
    */

    public static void main(String[] args) throws InterruptedException {
        CalcoloX t1 = new CalcoloX(2, 3, 4);
        CalcoloY t2 = new CalcoloY(2, 3, 4);
        CalcoloZ t3 = new CalcoloZ(2, 3, 4);

        t1.start();
        t2.start();
        t3.start();

        t2.join();
        t3.join();

        CalcoloT t4 = new CalcoloT();
        CalcoloK t5 = new CalcoloK();

        t4.start();

        t4.join();
        t1.join();

        t5.start();
        t5.join();

        System.out.println(k);
    }
}
