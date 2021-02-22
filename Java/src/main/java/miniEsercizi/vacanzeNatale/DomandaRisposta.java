package miniEsercizi.vacanzeNatale;

import javax.swing.*;

public class DomandaRisposta {
    public static void main(String[] args) {
        String stringa = JOptionPane.showInputDialog("Inserisci del testo");
        char ultimoChar = stringa.charAt(stringa.length() - 1);
        switch (ultimoChar) {
            case '?' -> System.out.println("Non saprei");
            case '!' -> System.out.println("Hai proprio ragione");
            default -> System.out.println("Non mi convince");
        }

        System.exit(0);
    }
}