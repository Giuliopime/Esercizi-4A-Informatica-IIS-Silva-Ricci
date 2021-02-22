package miniEsercizi.file2;

/*
Un'azienda memorizza su java.file di testo i prodotti:
- nome del prodotto
- quantità venduta
-

Scrivere una classe che registra le vendite effettuate in un java.file di testo
ed un'altra classe che legge il contenuto del java.file di testo, lo copia in una stringa e somma tutte le quantità dei prodotti
 */

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class GestoreProdotto {
    private final ArrayList<Prodotto> prodotti = new ArrayList<>();

    public static void main(String[] args) {
        new GestoreProdotto();
    }

    public GestoreProdotto() {
        try {
            caricaProdotti();
            scriviProdotti();
            JOptionPane.showMessageDialog(null, visualizzaProdotti());
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Il java.file dei prodotti non esiste");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "è stato inserito un prodotto con una quantità non intera o minore a 0");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Uno dei prodotti ha una quantità negativa");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Il java.file contenente i prodotti è stato cancellato durante l'esecuzione del programma");
        }
    }

    private void caricaProdotti() throws FileNotFoundException, NumberFormatException, IllegalArgumentException {
        URL pathFile = getClass().getResource("prodotti.txt");
        if (pathFile == null) throw new FileNotFoundException();

        File fileProdotti = new File(pathFile.getPath());

        Scanner scanner = new Scanner(fileProdotti);
        while (scanner.hasNextLine()) {
            String[] stringProdotto = scanner.nextLine().split("@");
            prodotti.add(new Prodotto(stringProdotto[0], Integer.parseInt(stringProdotto[1])));
        }
    }

    private void scriviProdotti() throws IOException, NumberFormatException {
        URL pathFile = getClass().getResource("prodotti.txt");
        FileWriter fileProdotti = new FileWriter(pathFile.getPath());

        for (int i = 0; i < 3; i++) {
            String nomeProdotto = JOptionPane.showInputDialog("Inserisci il nome del " + (i + 1) + " prodotto");
            int quant = Integer.parseInt(JOptionPane.showInputDialog("Inserisci la quantità del prodotto"));

            prodotti.add(new Prodotto(nomeProdotto, quant));
            fileProdotti.write(nomeProdotto + "@" + quant + "\n");
        }

        fileProdotti.close();
    }

    private String visualizzaProdotti() {
        StringBuilder creaStringa = new StringBuilder().append("Lista dei prodotti:\n");
        for (Prodotto p : prodotti)
            creaStringa.append(p.getNome()).append(": ").append(p.getQuant()).append("\n");

        return creaStringa.toString();
    }
}
