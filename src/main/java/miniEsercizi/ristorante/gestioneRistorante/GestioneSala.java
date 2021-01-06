package miniEsercizi.ristorante.gestioneRistorante;

import miniEsercizi.ristorante.gestioneRistorante.util.Tavolo;

import java.util.ArrayList;
import java.util.List;

public class GestioneSala {
    private static GestioneSala instance;
    private List<Tavolo> tavoliOccupati;
    private List<Tavolo> tavoliLiberi;

    private GestioneSala() {
        tavoliLiberi = new ArrayList<>();
        tavoliOccupati = new ArrayList<>();
    }

    public static GestioneSala getInstance() {
        if (instance == null) instance = new GestioneSala();
        return instance;
    }

    public void inizializzaTavoli(int qt) {
        if (qt <= 0) throw new IllegalArgumentException("Il numero di tavoli dev'essere un numero positivo");
        for (int i = 0; i < qt; i++) {
            Tavolo tav = new Tavolo(i + 1);
            tavoliLiberi.add(tav);
        }
    }

    public boolean occupaTavolo(int num) {
        for (Tavolo tav : tavoliLiberi) {
            if (tav.getNum() == num) {
                tavoliOccupati.add(tav);
                tavoliLiberi.remove(tav);
                return true;
            }
        }
        return false;
    }

    public boolean liberaTavolo(int num) {
        for (Tavolo tav : tavoliOccupati) {
            if (tav.getNum() == num) {
                GestioneOrdini.getInstance().completaOrdine(num);
                tavoliLiberi.add(tav);
                tavoliOccupati.remove(tav);
                return true;
            }
        }
        return false;
    }

    // Ritorna -1 per occupato, 1 per libero, 0 per inesistente
    public int statoTavolo(int num) {
        for (Tavolo tav : tavoliOccupati) if (tav.getNum() == num) return -1;

        for (Tavolo tav : tavoliLiberi) if (tav.getNum() == num) return 1;

        return 0;
    }

    public Tavolo getTavoloLibero(int num) {
        for (Tavolo tav : tavoliLiberi)
            if (tav.getNum() == num) return tav;

        return null;
    }

    public Tavolo getTavoloOccupato(int num) {
        for (Tavolo tav : tavoliOccupati)
            if (tav.getNum() == num) return tav;

        return null;
    }

    // Getters
    public List<Tavolo> getTavoliOccupati() {
        return tavoliOccupati;
    }

    public List<Tavolo> getTavoliLiberi() {
        return tavoliLiberi;
    }
}
