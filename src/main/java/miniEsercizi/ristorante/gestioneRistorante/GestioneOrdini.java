package miniEsercizi.ristorante.gestioneRistorante;

import miniEsercizi.ristorante.gestioneRistorante.util.Ordine;
import miniEsercizi.ristorante.gestioneRistorante.util.Prodotto;

import java.util.List;

public class GestioneOrdini {
    private static GestioneOrdini instance;
    private List<Ordine> ordinazioni;
    private List<Ordine> ordiniCompletati;

    private GestioneOrdini() {}

    public static GestioneOrdini getInstance() {
        if(instance == null) instance = new GestioneOrdini();
        return instance;
    }

    public Ordine newOrdine(List<Prodotto> prodotti, int numTavolo) {
        Ordine ordine = new Ordine(prodotti, numTavolo);
        ordinazioni.add(ordine);
        return ordine;
    }

    public void completaOrdine(int numTav) {
        for(Ordine ord: ordinazioni) {
            if(ord.getNumTavolo() == numTav) {
                ordiniCompletati.add(ord);
                ordinazioni.remove(ord);
                break;
            }
        }

    }

    public List<Ordine> getOrdinazioni() {
        return ordinazioni;
    }

    public List<Ordine> getOrdiniCompletati() {
        return ordiniCompletati;
    }
}
