package eserciziThreads.mutuaEsclusione;

public class Benzinaio {
    private final int maxLitri = 100;
    private int litriDisponibili = maxLitri;

    private Autobotte[] autobotti = new Autobotte[1];

    private int macchineSospese = 0;
    private final Semaforo semaforoMacchine = new Semaforo(1);
    private final Semaforo semaforoBotti = new Semaforo(1);
    private final Semaforo semaforoDistributore = new Semaforo(2);

    public static void main(String[] args) {
        // Singleton
        new Benzinaio();
    }

    private Benzinaio() {
        Automobile[] automobili = new Automobile[1];
        for (int i=0; i<1; i++)
            automobili[i] = new Automobile(this);

        for (int i=0; i<1; i++)
            autobotti[i] = new Autobotte(this);

        for(Automobile a: automobili)
            a.start();

        for(Autobotte a: autobotti)
            a.start();

    }

    public void preleva(int litri, int name) {
        System.out.println("[" + name + "]" + " Tent preleva litri:" + litriDisponibili + ", ms: " + System.currentTimeMillis());
        semaforoMacchine.p(false, false);

        while (litri > litriDisponibili) {
            macchineSospese++;
            semaforoMacchine.p(false, false);
        }
        semaforoDistributore.p(false, false);
        litriDisponibili -= litri;
        semaforoDistributore.v();

        semaforoMacchine.v();
        System.out.println("[" + name + "]" + "Prelevato, litri rimasti: " + litriDisponibili);
    }

    public void riempi() {
        semaforoBotti.p(false, false);


        System.out.println(semaforoDistributore.p(true, macchineSospese > 0) ? "Sto per ricaricare" : "Ricarica in coda");
        litriDisponibili = maxLitri;
        System.out.println("Ricaricato, ms: " + System.currentTimeMillis());
        semaforoDistributore.v();

        while (macchineSospese > 0){
            macchineSospese--;
            semaforoMacchine.v();
        }

        semaforoBotti.v();
    }
}
