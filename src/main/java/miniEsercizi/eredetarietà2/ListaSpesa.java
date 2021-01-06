package miniEsercizi.eredetarietà2;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListaSpesa {
    private ArrayList<Alimentare> alimenti = new ArrayList<Alimentare>();
    private ArrayList<NonAlimentare> nonAlimenti = new ArrayList<NonAlimentare>();;

    public ListaSpesa() {
        int scelta = 0;
        do {
            try {
                scelta = Integer.parseInt(JOptionPane.showInputDialog("- Digitare 1 per aggiungere un prodotto alla lista della spesa\n" +
                        "- Digitare 0 per terminare la lista della spesa"));
            } catch (Exception e) {
                scelta = 2;
                JOptionPane.showMessageDialog(null, "Inserire un numero corretto");
            }

            if(scelta == 1) {
                String tipo = JOptionPane.showInputDialog("Il tuo prodotto è un alimento?\nDigitare si o no.");
                int codice = 0;
                String descrizione;
                double prezzo = 0;

                do {
                    try {
                        codice = Integer.parseInt(JOptionPane.showInputDialog("Inserire il codice del prodotto"));
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Il codice dev'essere un numero intero");
                    }
                } while(codice == 0);

                descrizione = JOptionPane.showInputDialog("Inserire la descrizione del prodotto");

                do {
                    try {
                        prezzo = Double.parseDouble(JOptionPane.showInputDialog("Inserire il prezzod del prodotto"));
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Il prezzo deve essere un numero");
                    }
                } while(prezzo == 0);

                if(tipo.startsWith("s")) {
                    Date scadenza = null;

                    do {
                        try {
                            String data = JOptionPane.showInputDialog("Inserisci la data nel formato: giorno/mese/anno.\n" +
                                    "Esempio: 1/1/2020");
                            scadenza =new SimpleDateFormat("dd/MM/yyyy").parse(data);
                        }catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Data invalida");
                        }
                    }while(scadenza == null);

                    Alimentare prodotto = new Alimentare(codice, descrizione, prezzo, scadenza);
                    alimenti.add(prodotto);
                }
                else {
                    String materiale = JOptionPane.showInputDialog("Inserisci il materiale principale del prodotto");
                    NonAlimentare prodotto = new NonAlimentare(codice, descrizione, prezzo, materiale);
                    nonAlimenti.add(prodotto);
                }
            }
        } while(scelta != 0);

        int prezzoTot = 0;
        for(Alimentare alimento: alimenti) {
            prezzoTot += alimento.getPrezzo();
        }
    }
}
