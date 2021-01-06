package miniEsercizi.ristorante;

import miniEsercizi.ristorante.gestioneRistorante.GestioneOrdini;
import miniEsercizi.ristorante.gestioneRistorante.GestioneSala;

public class Ristorante {
    private GestioneOrdini gestioneOrdini;
    private GestioneSala gestioneSala;
    private String nome;

    public Ristorante(String nome) {
        this.nome = nome;
        gestioneOrdini = GestioneOrdini.getInstance();
        gestioneSala = GestioneSala.getInstance();
    }
}
