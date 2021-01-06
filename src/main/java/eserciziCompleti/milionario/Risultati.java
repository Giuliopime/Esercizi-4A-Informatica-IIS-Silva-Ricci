package eserciziCompleti.milionario;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

// Si crea una finestra risultati ogni volta che si inizializza la classe, attraverso quindi il costruttore
public class Risultati {
    private JFrame frame;
    private JPanel pannelloRisultati;
    private JButton giocaAncora;
    private JLabel immagineRisultati;

    public Risultati(boolean vittoria, Milionario istanzaMilionario) {
        /*
         Prima cosa si nasconde il frame del milionario
         Attraverso il metodo getFrame() dell'istanza della classe milionario che viene passata a questo costruttore
         */
        istanzaMilionario.getFrame().setVisible(false);

        // Poi creo il frame dei risultati
        frame = new JFrame("Risultati Milionario");
        frame.setContentPane(pannelloRisultati);
        frame.setSize(700, 1400);
        frame.pack();
        frame.setLocation(400, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*
        Al costruttore viene passato anche un booleano che indica se si ha vinto o perso
        In caso l'utente ha perso cambio l'immagine del frame in quella di sconfitta (gameOver.png)
         */
        if (!vittoria) {
            ImageIcon immagineSconfitta = new ImageIcon(getClass().getResource("/milionario/gameOver.png"));
            immagineRisultati.setIcon(immagineSconfitta);
            frame.pack();
        }

        /*
        Ascoltatore per il bottone "Gioca ancora"
        Quando il bottone viene premuto viene messo come invisibile il jFrame dei risultati
        e viene usato il metodo dispose() su di esso per rilasciare le risorse utilizzate dal frame
        Poi si rifà il setup alla classe milionario
         */
        giocaAncora.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            istanzaMilionario.setup();
        });

        // Una volta impostato il frame lo setto come visibile
        frame.setVisible(true);
    }

}
