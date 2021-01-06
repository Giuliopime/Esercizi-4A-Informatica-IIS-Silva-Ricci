package eserciziCompleti.studioDentistico.grafica.mainPanels;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import eserciziCompleti.studioDentistico.gestori.GestoreImpostazioni;
import eserciziCompleti.studioDentistico.grafica.NavBarUtil;
import eserciziCompleti.studioDentistico.oggetti.Impostazioni;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class HomePage {
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private JPanel pannelloHomePage;
    private JPanel pannelloNavBar;
    private JPanel pannelloContenuto;
    private JLabel logo;
    private JButton btnPazienti;
    private JButton btnInterventi;
    private JButton btnFatture;
    private JLabel logoContenuto;
    private JLabel testoBenvenuto;
    private JLabel descrizioneBenvenuto;

    private JButton[] bottoniNav = new JButton[]{
            btnPazienti, btnInterventi, btnFatture
    };

    public HomePage() {
        caricaImpostazioni();
        initGrafica();
    }

    private void caricaImpostazioni() {
        Impostazioni impostazioni;
        impostazioni = GestoreImpostazioni.getInstance().getImpostazioni();

        logo.setText("Studio Dentistico " + impostazioni.getNomeStudio());
        descrizioneBenvenuto.setText("Gestionale dello Studio Dentistico " + impostazioni.getNomeStudio());
    }

    private void initGrafica() {
        NavBarUtil.initNavBar(bottoniNav, logo);
    }


    public JPanel getMainPanel() {
        return pannelloHomePage;
    }

}
