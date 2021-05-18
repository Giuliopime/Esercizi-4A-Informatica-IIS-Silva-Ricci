package eserciziThreads.mutuaEsclusione;

import java.util.Random;

public class Benzinaio {
    private final int maxLitri;
    private int litriDisponibili;

    private final Autobotte[] autobotti;
    private final Automobile[] automobili;

    private int macchineSospese = 0;

    private final Semaforo semaforoMacchine = new Semaforo(2);
    private final Semaforo semaforoBotti = new Semaforo(2);
    private final Semaforo semaforoDistributore = new Semaforo(2);

    public static void main(String[] args) {
        new Benzinaio();
    }

    public Benzinaio() {
        // Litri massimi del benzinaio
        maxLitri = 500;
        // Setto la quantità di litri disponibili
        litriDisponibili = maxLitri;

        // Genero randomicamente la quantità di automobili ed autobotti
        int numAutomobili = numRandom(1, 50);
        int numAutobotti = numRandom(1, 25);

        automobili = new Automobile[numAutomobili];
        autobotti = new Autobotte[numAutobotti];

        // Aggiungo gli oggetti agli array
        for (int i=0; i<numAutomobili; i++)
            automobili[i] = new Automobile(this);

        for (int i=0; i<numAutobotti; i++)
            autobotti[i] = new Autobotte(this);

        // Runno i thread delle automobili ed autobotti
        for(Automobile a: automobili)
            a.start();

        for(Autobotte a: autobotti)
            a.start();

    }

    public void preleva(int litri, String nome) throws InterruptedException {
        System.out.println("L'automobile " + nome + " vuole prelevare " + litri + " litri (disponibili: " + litriDisponibili + ").\n");

        semaforoMacchine.p(false);

        // Se non ci sono abbastanza litri disponibili continuo a rimettere la macchina in coda finchè non trova abbastanza litri
        while (litri > litriDisponibili) {
            macchineSospese++;
            semaforoMacchine.p(false);
        }

        semaforoDistributore.p(false);
        litriDisponibili -= litri;
        semaforoDistributore.v();

        semaforoMacchine.v();

        System.out.println("L'automobile " + nome + " ha prelevato " + litri + " litri (disponibili: " + litriDisponibili + ").\n");
    }

    public void riempi(String nome) throws InterruptedException {
        System.out.println("L'autobotte " + nome + " è arrivata per rifornire il ditributore (disponibili: " + litriDisponibili + ")\n");

        semaforoBotti.p(false);

        /*
         10 litri è il numero minimo di litri che una macchina può rifornire.
         Se vi sono meno di 10 litri disponibili allora nessuna macchina potrà fare rifornimento
         e quindi l'autobotte deve saltare la fila in questo caso per poter ricaricare il distributore.
         */
        semaforoDistributore.p(litriDisponibili < 10);
        // Riempe il distributore
        litriDisponibili = maxLitri;
        semaforoDistributore.v();

        // Invoco tutte le macchine sospese in precedenza per mancanza di litri
        while (macchineSospese > 0){
            macchineSospese--;
            semaforoMacchine.v();
        }

        semaforoBotti.v();

        System.out.println("L'autobotte " + nome + " ha rifornito il distributore (disponibili: " + litriDisponibili + ")\n");
    }

    public static int numRandom(int da, int a) {
        return new Random().ints(1, da, a + 1).findFirst().getAsInt();
    }
}
