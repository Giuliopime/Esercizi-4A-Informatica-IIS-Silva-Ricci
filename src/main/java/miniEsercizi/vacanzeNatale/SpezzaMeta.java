package miniEsercizi.vacanzeNatale;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

public class SpezzaMeta {
    public static void main(String[] args) throws IOException {
        String stringa = JOptionPane.showInputDialog("Inserisci del testo");

        int indiceMeta = stringa.length() % 2 == 0 ? stringa.length() / 2 : stringa.length() / 2 + 1;
        String primaMeta = stringa.substring(0, indiceMeta);
        String secondaMeta = stringa.substring(indiceMeta);

        FileWriter fileWriter = new FileWriter("Spezza_Meta.txt");
        fileWriter.write(primaMeta + "\n" + secondaMeta);
        fileWriter.close();

        System.exit(0);
    }
}
