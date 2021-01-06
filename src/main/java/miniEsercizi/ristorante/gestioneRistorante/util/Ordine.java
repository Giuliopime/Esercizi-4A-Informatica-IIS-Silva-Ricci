package miniEsercizi.ristorante.gestioneRistorante.util;

import java.util.List;

public class Ordine {
    private List<Prodotto> prodotti;
    private int numTavolo;

    public Ordine(List<Prodotto> prodotti, int numTavolo) {
        this.prodotti = prodotti;
        this.numTavolo = numTavolo;
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    public double getPrezzoTot() {
        double prezzo = 0;
        for(Prodotto prodotto: prodotti)
            prezzo += prodotto.getPrezzo();

        return prezzo;
    }

    public int getNumTavolo() {
        return numTavolo;
    }
}
