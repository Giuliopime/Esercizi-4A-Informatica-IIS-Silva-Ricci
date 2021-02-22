package miniEsercizi.ristorante.gestioneRistorante.util;

public class Tavolo {
    private int num;
    private Ordine ordine;

    public Tavolo(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public Ordine getOrdine() {
        return ordine;
    }
}
