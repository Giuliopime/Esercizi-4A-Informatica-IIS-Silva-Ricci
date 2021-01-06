package miniEsercizi.utenza;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Utenti {
    private DecimalFormat df = new DecimalFormat("#.##");
    private HashSet<Utenza> utenti;

    public Utenti() {
        utenti = new HashSet<>();
    }

    public Utenti(HashSet<Utenza> utenti) {
        this.utenti = utenti;
    }


    public double consumoTot(double metroCubo) {
        double consumoTot = 0.0;

        Iterator<Utenza> iter = utenti.iterator();

        while(iter.hasNext()) {
            consumoTot += iter.next().getConsumo(metroCubo);
        }

        return consumoTot;
    }

    public HashMap<String, Double> listaConsumi(double metroCubo) {
        Iterator<Utenza> iter = utenti.iterator();

        HashMap<String, Double> listaConsumi = new HashMap<>();

        while(iter.hasNext()) {
            Utenza utente = iter.next();

            listaConsumi.put(utente.getCodiceUtente(), utente.getConsumo(metroCubo));
        }

        return listaConsumi;
    }

    public void addUtente(Utenza utente) {
        utenti.add(utente);
    }

    public void removeUtente(Utenza utente) {
        utenti.remove(utente);
    }

    public boolean esisteUtente(Utenza utente) {
        return utenti.contains(utente);
    }

    public HashSet<Utenza> getUtenti() {
        return utenti;
    }
}
