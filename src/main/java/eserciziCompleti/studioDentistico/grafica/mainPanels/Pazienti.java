package eserciziCompleti.studioDentistico.grafica.mainPanels;

import eserciziCompleti.studioDentistico.enums.Schermata;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.gestori.GestoreImpostazioni;
import eserciziCompleti.studioDentistico.gestori.GestorePazienti;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.grafica.NavBarUtil;
import eserciziCompleti.studioDentistico.grafica.RequestFocusListener;
import eserciziCompleti.studioDentistico.grafica.WrapLayout;
import eserciziCompleti.studioDentistico.grafica.dialogs.pazienti.DialogFiltriPazienti;
import eserciziCompleti.studioDentistico.grafica.dialogs.pazienti.DialogOrdinaPazienti;
import eserciziCompleti.studioDentistico.grafica.panelsComponenti.PanelPaziente;
import eserciziCompleti.studioDentistico.oggetti.Impostazioni;
import eserciziCompleti.studioDentistico.oggetti.Paziente;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Pazienti implements FocusListener {
    private JTextField inputRicerca;
    private JButton filtriButton;
    private JPanel pannelloPazienti;
    private JPanel pannelloNavBar;
    private JLabel logo;
    private JButton btnPazienti;
    private JButton btnInterventi;
    private JButton btnFatture;
    private JPanel listaPazienti;
    private JButton btnOrdina;
    private JScrollPane scrollPanePazienti;
    private JPanel test;

    private String ricerca;

    private JButton[] bottoniNav = new JButton[]{
            btnPazienti, btnInterventi, btnFatture
    };

    public Pazienti(String[] ricerca) {
        this.ricerca = ricerca != null ? ricerca[0] : null;

        NavBarUtil.initNavBar(bottoniNav, logo);
        initGrafica();
    }

    public JPanel getPannelloPazienti() {
        return pannelloPazienti;
    }

    public void createUIComponents() {
        ArrayList<Paziente> pazienti = GestorePazienti.getInstance().getPazienti();
        listaPazienti = new JPanel();
        listaPazienti.setLayout(new WrapLayout(0, 18, 15));
        listaPazienti.add(new PanelPaziente().getPanelPaziente());

        for (Paziente paziente : pazienti)
            listaPazienti.add(new PanelPaziente(paziente).getPanelPaziente());
    }

    private void initGrafica() {
        scrollPanePazienti.getVerticalScrollBar().setUnitIncrement(16);

        if (ricerca != null)
            inputRicerca.setText(ricerca);
        inputRicerca.addAncestorListener(new RequestFocusListener());
        inputRicerca.addFocusListener(this);


        JButton[] bottoniContenuto = new JButton[]{
                filtriButton, btnOrdina
        };

        for (JButton btn : bottoniContenuto) {
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    btn.setBackground(Colori.verdeChiaroHover);
                }

                public void mouseExited(MouseEvent evt) {
                    btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    btn.setBackground(Colori.bianco);
                }
            });
        }

        filtriButton.addActionListener(e -> {
            Impostazioni impostazioni = GestoreImpostazioni.getInstance().getImpostazioni();
            DialogFiltriPazienti dialogFiltriPazienti = new DialogFiltriPazienti(impostazioni.getFiltriPaziente());
            if (dialogFiltriPazienti.getFiltri() != null) {
                impostazioni.setFiltriPaziente(dialogFiltriPazienti.getFiltri());
                GestoreImpostazioni.getInstance().modificaImpostazioni(impostazioni);
            }
        });

        btnOrdina.addActionListener(e -> {
            Impostazioni impostazioni = GestoreImpostazioni.getInstance().getImpostazioni();
            DialogOrdinaPazienti dialogOrdinaPazienti = new DialogOrdinaPazienti(impostazioni.getOrdinamentoPazienti());
            impostazioni.setOrdinamentoPazienti(dialogOrdinaPazienti.getOrdinamentoPazienti());
            GestoreImpostazioni.getInstance().modificaImpostazioni(impostazioni);

            GestorePazienti.getInstance().ordinamentoPazienti = dialogOrdinaPazienti.getOrdinamentoPazienti();
            if (ricerca != null) {
                GestorePazienti.getInstance().filtriPaziente = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriPaziente();
                GestorePazienti.getInstance().query = ricerca;
            }
            GestoreGrafica.getInstance().changePanel(Schermata.PAZIENTI, new String[]{ricerca});
        });

        inputRicerca.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ricerca();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ricerca();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private void ricerca() {
        String query = inputRicerca.getText();
        if (query == null || query.isBlank()) {
            GestorePazienti.getInstance().filtriPaziente = null;
            GestorePazienti.getInstance().query = null;
            ricerca = null;
        } else {
            GestorePazienti.getInstance().filtriPaziente = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriPaziente();
            GestorePazienti.getInstance().query = query;
            ricerca = query;
        }

        GestoreGrafica.getInstance().changePanel(Schermata.PAZIENTI, new String[]{query});
    }

    @Override
    public void focusGained(FocusEvent e) {
        inputRicerca.setCaretPosition((inputRicerca.getText().length()));
    }

    @Override
    public void focusLost(FocusEvent e) {
    }

}
