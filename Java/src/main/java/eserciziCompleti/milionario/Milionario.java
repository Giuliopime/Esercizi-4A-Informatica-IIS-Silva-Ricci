package eserciziCompleti.milionario;
/*
Componenti del programma:
- La class Main server solo per far partire il programma inizializzando una classe Milionario
- package assets: contiene le immagine ed il file con le domande
- assets/domande.txt: il formato corretto per il file delle domande è il seguente:
    - domanda
    - risposta 1
    - risposta 2
    - @risposta 3
    - risposta 4
    - domanda 2
    - etc...
  Per specificare quale risposta è quella giusta, essa va preceduta da una @ (in questo esempio la risposta 3 è quella giusta)

- milionario.java e milionario.form sono la classe e la grafica del gioco in se
- risultati.java e risultati.form sono la classe e la grafica della schermata dei risultati (quindi di vittoria e sconfitta)
*/

/*
Funzionamento base della classe:
Ogni istanza della classe Milionario rappresenta una nuova finestra del gioco.
Attraverso il costruttore della classe viene create il jFrame del gioco
dopo di che viene chiamato il metodo setup, che, attraverso altri metodi, fa le seguenti operazioni:
- Legge le domande dal file domande.txt
- Controlla che le domande siano formattate correttamente
- Imposta la grafica del jFrame, organizzata come segue:
    - Immagine del milionario
    - Progress bar per indicare il progresso delle domande
    - Text Field per il testo della domanda
    - Quattro bottoni per le 4 possibili risposte
    - Bottone per la conferma della risposta

Una volta fatto ciò imposta il frame come visibile ed inserisci gli ascoltatori ai bottoni delle risposte (che servono per andare aventi con le domande)
Il resto del funzionamento è commentato all'interno del codice
*/


import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Milionario {
    // Componenti grafici
    private JFrame frame;
    private JTextPane testoDomanda;
    private JButton risposta1;
    private JButton risposta2;
    private JButton risposta3;
    private JButton risposta4;
    private JButton conferma;
    private JPanel pannelloGioco;
    private JProgressBar barraProgresso;

    // Variabili per il funzionamento
    private ArrayList<String> domande;  // Conterrà le domande + risposte
    // contatoreDomande serve per tener conto della domanda a cui ci si trovi
    // rispostaSelezionata contiene il numero della risposta selezionata
    private int contatoreDomande, rispostaSelezionata;
    // è un array che contiene semplicementi i bottoni delle risposte,
    // che viene usato in certi for loop per modificarne la grafica in modo più veloce
    private JButton[] bottoniRisposte;


    public Milionario() {
        frame = new JFrame("Milionario");
        frame.setContentPane(pannelloGioco);
        frame.setSize(800, 600);
        frame.pack();
        // Centra la finestra
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Ascoltatori dei bottoni (usando i lambda di java per essere più compatti)

        risposta1.addActionListener(e -> scegliRisposta(0));
        risposta2.addActionListener(e -> scegliRisposta(1));
        risposta3.addActionListener(e -> scegliRisposta(2));
        risposta4.addActionListener(e -> scegliRisposta(3));

        conferma.addActionListener(e -> confermaRisposta());

        setup();
    }

    /*
    Metodo che:
    - Prende le domande dal file di testo
    - le controlla
    - imposta la grafica della finestra
     */
    public void setup() {
        bottoniRisposte = new JButton[]{risposta1, risposta2, risposta3, risposta4};

        domande = new ArrayList<>();

        try {
            caricaDomande();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File delle domande non trovato");
            return;
        }

        // Se il metodo controllaDomande ritorna falso allora le domande non sono formattate correttamente e quindi il programma viene terminato
        if (!controllaDomande()) System.exit(0);

        /*
         Viene impostato a -5 perchè quando viene premuto il bottone della conferma della risposta
         il metodo che viene chiamato (prossimaDomanda()) aggiunge automaticamente 5 al contatore delle domande
         questo perchè l'array domande contiene: domanda, risposta1, risposta2, risposta3, risposta4, etc... con la prossima domanda
         quindi per passare alla domanda successiva bisogna fare +5 (cioè passare la domanda e le quattro risposte correnti)

         Poi nel metodo setupGrafica viene chiamato il metodo prossimaDomanda()= per caricare la domanda
         (quindi verrà fatto più 5 al contatore delle domande, per questo deve essere impostato a -5 all'inizio)
         è più facile da capire guardando il codice
         */
        contatoreDomande = -5;
        // Viene impostato a -1 perchè nessuna risposta è stata selezionata
        rispostaSelezionata = -1;

        setupGrafica();
    }

    private void caricaDomande() throws FileNotFoundException {
        // Uso uno scanner per prendere riga per riga il contenuto del file di test
        Scanner scanner = new Scanner(getClass().getResourceAsStream("/milionario/domande.txt"));
        while (scanner.hasNextLine()) {
            // Aggiungo all'array delle domande riga per riga il contenuto
            domande.add(scanner.nextLine());
        }
    }

    private boolean controllaDomande() {
        // Se la dimensione dell'array delle domande non è un multiplo di 5
        // vuole dire che il file non era formattato correttamente (cioè domanda + 4 risposte e così via)
        if (domande.size() % 5 != 0) {
            JOptionPane.showMessageDialog(null, "Il file con le domande non è formattato correttamente");
            return false;
        }

        // Con questo loop controllo se ci sono più risposte corrette per domanda
        // e, se si, avviso l'utente dicendogli quale domanda ha più risposte corrette
        for (int i = 0; i < domande.size(); i += 5) {
            int rispostePerDomanda = 0;
            for (int y = 1; y < 5; y++)
                if (domande.get(y + i).startsWith("@")) rispostePerDomanda++;

            if (rispostePerDomanda != 1)
                JOptionPane.showMessageDialog(null, "Una domanda contiene più di una risposta corretta:\n" + domande.get(i));
        }

        return true;
    }

    private void setupGrafica() {
        barraProgresso.setValue(0);

        // Carico la prossima domanda
        // quindi la prima visto che siamo nel setup ed è la prima volta che richiamo il metodo
        prossimaDomanda();

        // Finito il setup rendo visibile il frame
        frame.setVisible(true);
    }


    /*
     Questo metodo viene chiamato quando viene premuto un bottone di una risposta
     Sostanzialmente imposta la variabile globale "rispostaSelezionata" al valore passato al metodo
     (il bottone della prima risposta passa 0 al metodo, il bottone della risposta passa 1, e così via fino al quarto bottone)

     Poi cambia il colore dei bottoni impostando quello selezionato a giallo (con testo nero)
     e quelli non selezionati su blue (con testo bianco)
     */
    private void scegliRisposta(int indice) {
        rispostaSelezionata = indice;
        for (int i = 0; i < bottoniRisposte.length; i++) {
            if (i == rispostaSelezionata) {
                bottoniRisposte[i].setBackground(Color.decode("#DBB10C"));
                bottoniRisposte[i].setForeground(Color.decode("#090909"));
            } else {
                bottoniRisposte[i].setBackground(Color.decode("#01267C"));
                bottoniRisposte[i].setForeground(Color.decode("#FEFEFF"));
            }
        }
    }

    // Metodo che viene chiamato con il bottone per la conferma della risposta
    private void confermaRisposta() {
        // Se non è ancora stata selezionata alcuna risposta non fa niente
        if (rispostaSelezionata < 0) return;
        // Se la risposta selezionata non è quella giusta (se è giusta inizia con la @) mostra la schermata di sconfitta
        // attraverso la classe Risultati
        if (!domande.get(contatoreDomande + rispostaSelezionata + 1).startsWith("@")) {
            new Risultati(false, this);
            return;
        }

        // Se invece la risposta era quella giusta
        // Aumanta la barra di progresso in proporzione alle domande totali e domande risposte
        barraProgresso.setValue(((contatoreDomande + 5) * 100) / domande.size());

        // Se sono finite le domande mostra la schermata di vittoria
        if (domande.size() <= contatoreDomande + 5)
            new Risultati(true, this);
            // Se no passa alla prossima domanda
        else prossimaDomanda();
    }


    private void prossimaDomanda() {
        // Reimposta la variabile della risposta selezionata per indicare che non ne è stata selezionata ancora nessuna
        rispostaSelezionata = -1;
        // Aumenta il contatore delle domande (domande corrente + 4 riposte = 5)
        contatoreDomande += 5;

        // Reimposta il colore di ogni bottone
        for (JButton btn : bottoniRisposte) {
            btn.setBackground(Color.decode("#01267C"));
            btn.setForeground(Color.decode("#FEFEFF"));
        }

        // Imposta il testo dei bottoni con la domanda e le 4 riposte
        // Sostituendo la @ della risposta corretta
        testoDomanda.setText(domande.get(contatoreDomande));
        risposta1.setText("A: " + domande.get(contatoreDomande + 1).replace("@", ""));
        risposta2.setText("B: " + domande.get(contatoreDomande + 2).replace("@", ""));
        risposta3.setText("C: " + domande.get(contatoreDomande + 3).replace("@", ""));
        risposta4.setText("D: " + domande.get(contatoreDomande + 4).replace("@", ""));
    }

    /*
     Restituisce il jFrame della finestra
     Questo metodo viene usato dalla classe Risultati per nascondere la finestra del milionario e mostrare solo quella dei risultati
     */
    public JFrame getFrame() {
        return frame;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        pannelloGioco = new JPanel();
        pannelloGioco.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        pannelloGioco.setBackground(new Color(-16515027));
        pannelloGioco.setEnabled(true);
        risposta1 = new JButton();
        risposta1.setBackground(new Color(-16701828));
        Font risposta1Font = this.$$$getFont$$$(null, Font.BOLD, 12, risposta1.getFont());
        if (risposta1Font != null) risposta1.setFont(risposta1Font);
        risposta1.setForeground(new Color(-65793));
        risposta1.setHorizontalAlignment(2);
        risposta1.setHorizontalTextPosition(11);
        risposta1.setText("Risposta");
        pannelloGioco.add(risposta1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(227, 30), null, 0, false));
        risposta2 = new JButton();
        risposta2.setBackground(new Color(-16701828));
        risposta2.setForeground(new Color(-65793));
        risposta2.setHorizontalAlignment(2);
        risposta2.setText("Risposta2");
        pannelloGioco.add(risposta2, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        risposta3 = new JButton();
        risposta3.setBackground(new Color(-16701828));
        risposta3.setForeground(new Color(-65793));
        risposta3.setHideActionText(false);
        risposta3.setHorizontalAlignment(2);
        risposta3.setText("Risposta3");
        pannelloGioco.add(risposta3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(227, 30), null, 0, false));
        risposta4 = new JButton();
        risposta4.setBackground(new Color(-16701828));
        risposta4.setForeground(new Color(-65793));
        risposta4.setHorizontalAlignment(2);
        risposta4.setText("Risposta4");
        pannelloGioco.add(risposta4, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        conferma = new JButton();
        conferma.setBackground(new Color(-2379508));
        Font confermaFont = this.$$$getFont$$$(null, Font.BOLD, 16, conferma.getFont());
        if (confermaFont != null) conferma.setFont(confermaFont);
        conferma.setForeground(new Color(-16185079));
        conferma.setText("Conferma Risposta");
        pannelloGioco.add(conferma, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        barraProgresso = new JProgressBar();
        barraProgresso.setBackground(new Color(-14547116));
        barraProgresso.setForeground(new Color(-4702048));
        pannelloGioco.add(barraProgresso, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        testoDomanda = new JTextPane();
        testoDomanda.setBackground(new Color(-16701828));
        testoDomanda.setEditable(false);
        testoDomanda.setEnabled(true);
        Font testoDomandaFont = this.$$$getFont$$$(null, Font.BOLD, 16, testoDomanda.getFont());
        if (testoDomandaFont != null) testoDomanda.setFont(testoDomandaFont);
        testoDomanda.setForeground(new Color(-65793));
        testoDomanda.setText("Domanda");
        testoDomanda.putClientProperty("JEditorPane.w3cLengthUnits", Boolean.FALSE);
        pannelloGioco.add(testoDomanda, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(100, 50), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setIcon(new ImageIcon(getClass().getResource("/milionario/milionario.jpg")));
        label1.setText("");
        pannelloGioco.add(label1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return pannelloGioco;
    }
}
