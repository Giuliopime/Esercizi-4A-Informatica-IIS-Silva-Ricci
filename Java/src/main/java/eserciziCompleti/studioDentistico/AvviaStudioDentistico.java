package eserciziCompleti.studioDentistico;

import eserciziCompleti.studioDentistico.gestori.*;

import javax.swing.*;

public class AvviaStudioDentistico extends JFrame {
    public static void main(String[] args) {
        GestoreImpostazioni.getInstance();
        GestorePazienti.getInstance();
        GestoreInterventi.getInstance();
        GestoreFatture.getInstance();
        GestoreGrafica.getInstance();
    }
}
