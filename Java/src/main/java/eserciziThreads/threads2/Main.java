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
        System.out.println(System.currentTimeMillis());
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
        System.out.println(System.currentTimeMillis());

        // Senza thread (più veloce perchè le operazioni sono molto piccole e solo aprire un nuovo thread richiede più tempo dell'effettuare i calcoli direttamente
        System.out.println("----------------------");
        System.out.println(System.currentTimeMillis());
        double a = 3 * (2 - 1);
        double b = 2 * (3 - 2);
        double c = 3 * (4 - 3);
        double d = b * c;
        double e = a + d;
        System.out.println(e);
        System.out.println(System.currentTimeMillis());
    }
}
