package miniEsercizi.file;

import java.io.FileReader;
import java.io.FileWriter;

/*
Due tipi di java.file:
- File di testo composti da caratteri
- Altri tipi di java.file composti da byte

Quando leggiamo o scriviamo su java.file siamo costretti ad usare il try catch perchè possono avvenire molte eccezioni di vario genere


Scrivere un programma che scrive una frase in un java.file di testo e successivamente legge e visualizza tutti i caratteri.

 */
public class Main {
    public static void main(String[] args) {
        // Scrittura su java.file
        String testo = "Testo di prova";
        try {
            FileWriter fw = new FileWriter("Test_file.txt");
            fw.write(testo);
            fw.close();
        } catch (Exception e) {
            System.out.println("Errore:" + e);
        }

        // Lettura di un java.file
        try {
            FileReader fr = new FileReader("Test_file.txt");
            StringBuilder risultato = new StringBuilder();

            int cIndex = fr.read();
            while (cIndex > -1) {
                risultato.append((char) cIndex);
                cIndex = fr.read();
            }

            System.out.println(risultato);
        } catch (Exception e) {
            System.out.println("Errore:" + e);
        }
    }
}
