package miniEsercizi.utenza;

import javax.swing.*;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        HashSet<Utenza> utentiInseriti = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            String codice = JOptionPane.showInputDialog("Inserisci il codice dell'utente");
            int consumoAttuale = Integer.parseInt(JOptionPane.showInputDialog("Inserisci il consumo attuale"));
            int consumoPrecedente = Integer.parseInt(JOptionPane.showInputDialog("Inserisci il consumo precedente"));

            Utenza utente = new Utenza(codice, consumoAttuale, consumoPrecedente);
            utentiInseriti.add(utente);
        }

        Utenti utenti = new Utenti(utentiInseriti);

        System.out.println(utenti.consumoTot(1.37));
        System.out.println(utenti.listaConsumi(1.37));
    }
}
