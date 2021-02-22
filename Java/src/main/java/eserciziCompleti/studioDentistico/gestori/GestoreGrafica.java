package eserciziCompleti.studioDentistico.gestori;

import eserciziCompleti.studioDentistico.enums.Schermata;
import eserciziCompleti.studioDentistico.grafica.mainPanels.Fatture;
import eserciziCompleti.studioDentistico.grafica.mainPanels.HomePage;
import eserciziCompleti.studioDentistico.grafica.mainPanels.Interventi;
import eserciziCompleti.studioDentistico.grafica.mainPanels.Pazienti;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * <p>
 *      Singleton per gestire il JFrame del programma.
 * </p>
 *
 * <p>
 *      La classe presenta metodi per:
 * </p>
 *      <ul>
 *      <li>Cambiare il contentPane del JFrame </li>
 *      </ul>
 */
public class GestoreGrafica extends JFrame {
    /** Istanza della classe*/
    private static GestoreGrafica instance;
    /** Path per la directory dove verranno salvati i file dat*/
    public static final String pathFileDat = "fileDat/studioDentistico/";

    /**
     * Getter per l'istanza del singleton
     * @return Istanza del singleton GestoreGrafica
     */
    public static GestoreGrafica getInstance() {
        if (instance == null)
            instance = new GestoreGrafica();

        return instance;
    }

    private GestoreGrafica() {
        initGrafica();
        changePanel(Schermata.HOME, null);

        setName("Studio Dentistico");
        setSize(new Dimension(1000, 600));
        setMinimumSize(new Dimension(1000, 400));
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void initGrafica() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Salvo tutto sui vari file .dat
                GestorePazienti.getInstance().salvaSuFile();
                GestoreInterventi.getInstance().salvaSuFile();
                GestoreFatture.getInstance().salvaSuFile();
                GestoreImpostazioni.getInstance().salvaSuFile();
                dispose();
                System.exit(0);
            }
        });
    }

    /**
     * Naviga ad un'altra {@link Schermata}
     * @param schermata Il tipo di {@link Schermata} a cui navigare
     * @param args Argomenti per il costruttore della nuova Schermata
     */
    public void changePanel(Schermata schermata, String[] args) {
        if (schermata == null) {
            setContentPane(new HomePage().getMainPanel());
        } else {
            switch (schermata) {
                case PAZIENTI -> setContentPane(new Pazienti(args).getPannelloPazienti());
                case INTERVENTI -> setContentPane(new Interventi(args).getPannelloInterventi());
                case FATTURE -> setContentPane(new Fatture(args).getPannelloFatture());
                default -> setContentPane(new HomePage().getMainPanel());
            }
        }
        // Ricarica il JFrame
        validate();
    }

    /**
     * Getter per il JFrame
     * @return JFrame del programma
     */
    public JFrame getFrame() {
        return this;
    }
}
