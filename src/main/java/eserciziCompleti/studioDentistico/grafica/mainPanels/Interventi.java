package eserciziCompleti.studioDentistico.grafica.mainPanels;

import eserciziCompleti.studioDentistico.enums.TipoPanel;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.gestori.GestoreImpostazioni;
import eserciziCompleti.studioDentistico.gestori.GestoreInterventi;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.grafica.NavBarUtil;
import eserciziCompleti.studioDentistico.grafica.RequestFocusListener;
import eserciziCompleti.studioDentistico.grafica.WrapLayout;
import eserciziCompleti.studioDentistico.grafica.dialogs.DialogFiltriInterventi;
import eserciziCompleti.studioDentistico.grafica.dialogs.DialogOrdinaInterventi;
import eserciziCompleti.studioDentistico.grafica.panelsComponenti.PanelIntervento;
import eserciziCompleti.studioDentistico.oggetti.Impostazioni;
import eserciziCompleti.studioDentistico.oggetti.Intervento;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Interventi implements FocusListener {
    private JPanel pannelloInterventi;
    private JPanel pannelloNavBar;
    private JLabel logo;
    private JButton btnPazienti;
    private JButton btnInterventi;
    private JButton btnFatture;
    private JTextField inputRicerca;
    private JButton filtriButton;
    private JButton btnOrdina;
    private JPanel listaInterventi;
    private JScrollPane scrollPaneInterventi;

    private String ricerca;

    private JButton[] bottoniNav = new JButton[]{
            btnPazienti, btnInterventi, btnFatture
    };

    public Interventi(String[] ricerca) {
        this.ricerca = ricerca != null ? ricerca[0] : null;

        NavBarUtil.initNavBar(bottoniNav, logo);
        initGrafica();
    }

    private void initGrafica() {
        scrollPaneInterventi.getVerticalScrollBar().setUnitIncrement(16);
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
            DialogFiltriInterventi dialogFiltriInterventi = new DialogFiltriInterventi(impostazioni.getFiltriIntervento());
            if (dialogFiltriInterventi.getFiltri() != null) {
                impostazioni.setFiltriIntervento(dialogFiltriInterventi.getFiltri());
                GestoreImpostazioni.getInstance().modificaImpostazioni(impostazioni);
            }
        });

        btnOrdina.addActionListener(e -> {
            Impostazioni impostazioni = GestoreImpostazioni.getInstance().getImpostazioni();
            DialogOrdinaInterventi dialogOrdinaInterventi = new DialogOrdinaInterventi(impostazioni.getOrdinamentoInterventi());
            impostazioni.setOrdinamentoInterventi(dialogOrdinaInterventi.getOrdinamentoInterventi());
            GestoreImpostazioni.getInstance().modificaImpostazioni(impostazioni);

            GestoreInterventi.getInstance().ordinamentoInterventi = dialogOrdinaInterventi.getOrdinamentoInterventi();
            if (ricerca != null) {
                GestoreInterventi.getInstance().filtriIntervento = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriIntervento();
                GestoreInterventi.getInstance().query = ricerca;
            }
            GestoreGrafica.getInstance().changePanel(TipoPanel.INTERVENTI, new String[]{ricerca});
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

    private void createUIComponents() {
        ArrayList<Intervento> interventi = GestoreInterventi.getInstance().getInterventi();
        listaInterventi = new JPanel();
        listaInterventi.setLayout(new WrapLayout(0, 18, 15));
        listaInterventi.add(new PanelIntervento().getPanelIntervento());

        for (Intervento intervento : interventi)
            listaInterventi.add(new PanelIntervento(intervento).getPanelIntervento());
    }

    public JPanel getPannelloInterventi() {
        return pannelloInterventi;
    }

    private void ricerca() {
        String query = inputRicerca.getText();
        if (query == null || query.isBlank()) {
            GestoreInterventi.getInstance().filtriIntervento = null;
            GestoreInterventi.getInstance().query = null;
            ricerca = null;
        } else {
            GestoreInterventi.getInstance().filtriIntervento = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriIntervento();
            GestoreInterventi.getInstance().query = query;
            ricerca = query;
        }

        GestoreGrafica.getInstance().changePanel(TipoPanel.INTERVENTI, new String[]{ricerca});
    }

    @Override
    public void focusGained(FocusEvent e) {
        inputRicerca.setCaretPosition((inputRicerca.getText().length()));
    }

    @Override
    public void focusLost(FocusEvent e) {
    }

}
