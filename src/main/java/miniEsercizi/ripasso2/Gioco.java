package miniEsercizi.ripasso2;

/*
Scrivere la classe Carta che rappresenta una carta da poker con variabili seme e valore e con tutti i metodi opportuni.
Scrivere la classe Mazzo che inizializza un mazzo di tredici per quattro carte con metodi per:
- Prendere una carta in modo casuale
- Confrontare una carta pescata con una ricevuta (se è uguale)
- Confrontare una carte pescata con una ricevuta (se è maggiore)
Scrivere la classe Gioco che, dati due giocatori dei wuali si conosce il nome, permette di pescare una carta ciascuno e visualizza se e chi ha vinto
*/

import javax.swing.*;

public class Gioco {
    public static void main(String[] args) {
        int turni = 0, p1 = 0, p2 = 0;
        String giocatore1 = "", giocatore2 = "";
        Mazzo mazzo = new Mazzo();

        do {
            try {
                turni = Integer.parseInt(JOptionPane.showInputDialog(null, "Inserisci il numero di turni da giocare"));
                if (turni < 1) JOptionPane.showMessageDialog(null, "Inserisci un numero intero maggiore di 0");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Inserisci un numero intero maggiore di 0");
            }
        } while (turni < 1);

        do {
            try {
                giocatore1 = JOptionPane.showInputDialog("Inserisci il nome del primo giocatore");
                giocatore2 = JOptionPane.showInputDialog("Inserisci il nome del secondo giocatore");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Inserisci il nome dei due giocatori");
            }
        } while (giocatore1.isEmpty() || giocatore2.isEmpty());

        for (int i = 0; i < turni; i++) {
            Carta cartaG1 = mazzo.pesca();
            Carta cartaG2 = mazzo.pesca();

            int vincitore = cartaG1.confronta(cartaG2);

            switch (vincitore) {
                case 1:
                    p1++;
                    break;
                case -1:
                    p2++;
                    break;
                case 0: {
                    p1++;
                    p2++;
                }
            }
        }

        int vincitore = Integer.compare(p1, p2);
        switch (vincitore) {
            case 1:
                System.out.println("Il vincitore è " + giocatore1 + " con un punteggio di " + p1 + " round vinti! Mentre " + giocatore2 + " ha vinto " + p2 + " round.");
                break;
            case -1:
                System.out.println("Il vincitore è " + giocatore2 + " con un punteggio di " + p2 + " round vinti! Mentre " + giocatore1 + " ha vinto " + p1 + " round.");
                break;
            case 0:
                System.out.println("Parità: entrambi i giocatori hanno vinto esattamente " + p1 + " round!");
        }
        System.out.println("Partita terminata");
        System.exit(0);
    }
}
