package miniEsercizi.ristorante.gestioneRistorante.util;

import java.util.List;

public class Menu {
    private List<Prodotto> prodotti;
    private Menu instance;

    public Menu() {
    }

    public Menu getInstance() {
        if (instance == null) instance = new Menu();
        return instance;
    }

    private void inizializzaProdotti(List<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public List<Prodotto> getProdotti() {
        return prodotti;
    }
}
