/*
Classe per gestire le impostazioni del gioco scelte dall'utente, che viene poi salvata su file .dat per rendere persistenti le impostazioni.
Le impostazioni al momento disponibili sono:
- username
- numero di tentativi disponibili per indovinare il codice
- difficoltà del gioco
 */
package eserciziCompleti.MasterMind.util.impostazioni;

import java.io.Serializable;

public class Settaggi implements Serializable {
    private String username;
    private int numTentativi;
    private Difficolta difficolta;

    // Due costruttori, uno per i valori di default ed uno con valori custom
    public Settaggi() {
        username = "Giocatore 1";
        numTentativi = 9;
        difficolta = Difficolta.Normale;
    }

    public Settaggi(String username, int numTentativi, Difficolta difficolta) {
        this.username = username;
        this.numTentativi = numTentativi > 0 && numTentativi < 11 ? numTentativi : 10;
        this.difficolta = difficolta;
    }

    // Metodi get
    public String getUsername() {
        return username;
    }

    public int getNumTentativi() {
        return numTentativi;
    }

    public Difficolta getDifficolta() {
        return difficolta;
    }
}
