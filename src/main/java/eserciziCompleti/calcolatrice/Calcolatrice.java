package eserciziCompleti.calcolatrice;

/*
La classe Calcolatrice è strutturata in questo modo:
- Main: inizializza la java.calcolatrice

- Vari componenti grafici, ogni bottone ha un ascoltatore.
La java.calcolatrice ha 2 righe per il test,
la prima (riga1) per il testo in input dopo un'operazione,
la seconda (riga2) per il testo dato in input prima di un'operazione ed anche per mostrare l'intera espressione quando si preme =


- Controller: ogni ascoltatore dei bottoni richiama il metodo controller, a cui passa un carattere e la catgoria a cui il carattere fa parte.
Le possibili categorie si trovano nell'enum CategoriaBottoni
A seconda della categoria fornita al metodo controller, esso esegue diverse funzioni:
    - Se è un numero, aggiunge il numero alla riga1
    - Se è un'operazione vi sono due opzioni:
        - Operazione non = : sposta il testo della riga 1 alla riga 2 ed aggiunge il testo della riga 2 alla Stringa toEvaluate
            La stringa toEvaluate conterrà tutta l'espressione matematica da calcolare attraverso mXparser
        - Operazione = : aggiunge la riga 1 alla Stringa toEvaluate. Fatto ciò calcola, attraverso mXparser, l'espressione.
            Imposta nella riga2 l'espressione matematica completa e nella riga1 il risultato di mXparser
    - Se è un tasto di utilità:
        - tasto C: cancella la riga1;
        - tasto AC: calcella tutte le righe e la Stringa toEvaluate;

Finite queste casistiche imposta il testo nella JTextField di riga1 e riga2
*/


import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.mariuszgromada.math.mxparser.Expression;

import javax.swing.*;
import java.awt.*;

public class Calcolatrice {
    private JPanel pannelloCalcolatrice;
    private String toEvaluate = "";
    private String riga1 = "";
    private String riga2 = "";
    // Bottoni
    private JButton a4Button;
    private JButton a7Button;
    private JButton a8Button;
    private JButton a5Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a6Button;
    private JButton a9Button;
    private JButton perButton;
    private JButton menoButton;
    private JButton piuButton;
    private JButton a0Button;
    private JButton virgolaButton;
    private JButton ugualeButton;
    private JButton ACButton;
    private JButton cButton;
    private JButton moduloButton;
    private JButton divisoButton;
    private JButton a1Button;
    private JTextField testoRiga1;
    private JTextField testoRiga2;

    public static void main(String[] args) {
        new Calcolatrice();
    }

    public Calcolatrice() {
        JFrame frame = new JFrame("Calcolatrice orribile");
        frame.setContentPane(pannelloCalcolatrice);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //UTILI

        cButton.addActionListener(e -> controller('c', CategoriaBottoni.UTILI));

        ACButton.addActionListener(e -> controller('a', CategoriaBottoni.UTILI));


        // OPERAZIONI

        ugualeButton.addActionListener(e -> controller('=', CategoriaBottoni.OPERAZIONE));

        moduloButton.addActionListener(e -> controller('#', CategoriaBottoni.OPERAZIONE));

        divisoButton.addActionListener(e -> controller('/', CategoriaBottoni.OPERAZIONE));

        perButton.addActionListener(e -> controller('*', CategoriaBottoni.OPERAZIONE));

        menoButton.addActionListener(e -> controller('-', CategoriaBottoni.OPERAZIONE));

        piuButton.addActionListener(e -> controller('+', CategoriaBottoni.OPERAZIONE));


        // NUMERI


        virgolaButton.addActionListener(e -> controller('.', CategoriaBottoni.NUMERO));

        a0Button.addActionListener(e -> controller('0', CategoriaBottoni.NUMERO));

        a1Button.addActionListener(e -> controller('1', CategoriaBottoni.NUMERO));

        a2Button.addActionListener(e -> controller('2', CategoriaBottoni.NUMERO));

        a3Button.addActionListener(e -> controller('3', CategoriaBottoni.NUMERO));

        a4Button.addActionListener(e -> controller('4', CategoriaBottoni.NUMERO));

        a5Button.addActionListener(e -> controller('5', CategoriaBottoni.NUMERO));

        a6Button.addActionListener(e -> controller('6', CategoriaBottoni.NUMERO));

        a7Button.addActionListener(e -> controller('7', CategoriaBottoni.NUMERO));

        a8Button.addActionListener(e -> controller('8', CategoriaBottoni.NUMERO));

        a9Button.addActionListener(e -> controller('9', CategoriaBottoni.NUMERO));
    }

    private void controller(char carattere, CategoriaBottoni categoria) {
        if (categoria == CategoriaBottoni.NUMERO) {
            riga1 += carattere;
        } else if (categoria == CategoriaBottoni.OPERAZIONE) {
            if (carattere != '=') {
                riga2 = riga1 + carattere;
                riga1 = "";
                toEvaluate += riga2;
            } else {
                toEvaluate += riga1;
                riga2 = toEvaluate;
                try {
                    Expression e = new Expression(toEvaluate);
                    riga1 = Double.toString(e.calculate());
                    toEvaluate = "";
                } catch (Exception e) {
                    riga1 = "Errore nel calcolo dell'operazione";
                    System.out.println(e);
                }
            }
        } else if (categoria == CategoriaBottoni.UTILI) {
            switch (carattere) {
                case 'c':
                    riga1 = "";
                    break;

                case 'a':
                    toEvaluate = "";
                    riga1 = "";
                    riga2 = "";
            }
        }

        testoRiga2.setText(riga2);
        testoRiga1.setText(riga1);
    }

}