package eserciziCompleti.studioDentistico.gestori;

import eserciziCompleti.studioDentistico.enums.TipoPanel;
import eserciziCompleti.studioDentistico.grafica.mainPanels.Fatture;
import eserciziCompleti.studioDentistico.grafica.mainPanels.HomePage;
import eserciziCompleti.studioDentistico.grafica.mainPanels.Interventi;
import eserciziCompleti.studioDentistico.grafica.mainPanels.Pazienti;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GestoreGrafica extends JFrame {
    private static GestoreGrafica instance;
    public static final String pathFileDat = "fileDat/studioDentistico/";

    public static GestoreGrafica getInstance() {
        if(instance == null)
            instance = new GestoreGrafica();

        return instance;
    }

    private GestoreGrafica() {
        initGrafica();
        changePanel(TipoPanel.HOME, null);

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
                GestorePazienti.getInstance().salvaPazienti();
                GestoreInterventi.getInstance().salvaInterventi();
                GestoreFatture.getInstance().salvaFatture();
                GestoreImpostazioni.getInstance().salvaImpostazioni();
                dispose();
                System.exit(0);
            }
        });
    }

    public void changePanel(TipoPanel tipoPanel, String[] args) {
        if(tipoPanel == null) {
            setContentPane(new HomePage().getMainPanel());
        } else {
            switch (tipoPanel) {
                case PAZIENTI -> setContentPane(new Pazienti(args).getPannelloPazienti());
                case INTERVENTI -> setContentPane(new Interventi(args).getPannelloInterventi());
                case FATTURE -> setContentPane(new Fatture(args).getPannelloFatture());
                default -> setContentPane(new HomePage().getMainPanel());
            }
        }
        validate();
    }

    public JFrame getFrame() {
        return this;
    }
}