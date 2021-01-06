package eserciziCompleti.studioDentistico.grafica.mainPanels;

import eserciziCompleti.studioDentistico.enums.AzioneDialog;
import eserciziCompleti.studioDentistico.enums.TipoPanel;
import eserciziCompleti.studioDentistico.gestori.*;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.grafica.NavBarUtil;
import eserciziCompleti.studioDentistico.grafica.RequestFocusListener;
import eserciziCompleti.studioDentistico.grafica.dialogs.fatture.DialogFattura;
import eserciziCompleti.studioDentistico.grafica.dialogs.fatture.DialogFiltriFatture;
import eserciziCompleti.studioDentistico.grafica.dialogs.pazienti.DialogNessunPaziente;
import eserciziCompleti.studioDentistico.grafica.dialogs.fatture.DialogOrdinaFatture;
import eserciziCompleti.studioDentistico.oggetti.Fattura;
import eserciziCompleti.studioDentistico.oggetti.Impostazioni;
import eserciziCompleti.studioDentistico.oggetti.Intervento;
import eserciziCompleti.studioDentistico.oggetti.Paziente;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class Fatture implements FocusListener {
    private JPanel pannelloFatture;
    private JPanel pannelloNavBar;
    private JLabel logo;
    private JButton btnPazienti;
    private JButton btnInterventi;
    private JButton btnFatture;
    private JTextField inputRicerca;
    private JButton filtriButton;
    private JButton btnOrdina;
    private JTable tabellaFatture;
    private JButton btnNuovaFattura;
    private JScrollPane scrollPaneTabella;
    private JButton btnStampaFattura;
    private JPanel listaFatture;

    private String ricerca;

    private JButton[] bottoniNav = new JButton[]{
            btnPazienti, btnInterventi, btnFatture
    };

    public Fatture(String[] ricerca) {
        this.ricerca = ricerca != null ? ricerca[0] : null;

        NavBarUtil.initNavBar(bottoniNav, logo);
        initGrafica();
    }

    private void initGrafica() {
        scrollPaneTabella.getVerticalScrollBar().setUnitIncrement(16);
        scrollPaneTabella.getViewport().setBackground(Colori.bluChiaro);

        ((DefaultTableCellRenderer) tabellaFatture.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        tabellaFatture.getTableHeader().setFont((new Font(null, Font.BOLD, 16)));
        tabellaFatture.getTableHeader().setPreferredSize(new Dimension(-1, 40));

        if (ricerca != null)
            inputRicerca.setText(ricerca);
        inputRicerca.addAncestorListener(new RequestFocusListener());
        inputRicerca.addFocusListener(this);


        JButton[] bottoniContenuto = new JButton[]{
                filtriButton, btnOrdina, btnNuovaFattura
        };

        for (JButton btn : bottoniContenuto) {
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    btn.setBackground(Colori.verdeChiaroHover);
                }

                public void mouseExited(MouseEvent evt) {
                    btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    btn.setBackground(btn.equals(btnNuovaFattura) ? Colori.verdeChiaro : Colori.bianco);
                }
            });
        }

        filtriButton.addActionListener(e -> {
            Impostazioni impostazioni = GestoreImpostazioni.getInstance().getImpostazioni();
            DialogFiltriFatture dialogFiltriFatture = new DialogFiltriFatture(impostazioni.getFiltriFattura());
            if (dialogFiltriFatture.getFiltri() != null) {
                impostazioni.setFiltriFattura(dialogFiltriFatture.getFiltri());
                GestoreImpostazioni.getInstance().modificaImpostazioni(impostazioni);
            }
        });

        btnNuovaFattura.addActionListener(e -> {
            if (GestorePazienti.getInstance().getPazienti().size() == 0) {
                new DialogNessunPaziente();
                return;
            }
            DialogFattura dialogFattura = new DialogFattura("Nuova Fattura");
            if (!dialogFattura.getAzione().equals(AzioneDialog.Niente)) {
                GestoreFatture.getInstance().aggiungiFattura(dialogFattura.getFattura());
                GestoreGrafica.getInstance().changePanel(TipoPanel.FATTURE, new String[]{ricerca});
            }
        });

        btnOrdina.addActionListener(e -> {
            Impostazioni impostazioni = GestoreImpostazioni.getInstance().getImpostazioni();
            DialogOrdinaFatture dialogOrdinaFatture = new DialogOrdinaFatture(impostazioni.getOrdinamentoFatture());
            impostazioni.setOrdinamentoFatture(dialogOrdinaFatture.getOrdinamentoFatture());
            GestoreImpostazioni.getInstance().modificaImpostazioni(impostazioni);

            GestoreFatture.getInstance().ordinamentoFatture = dialogOrdinaFatture.getOrdinamentoFatture();
            if (ricerca != null) {
                GestoreFatture.getInstance().filtriFattura = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriFattura();
                GestoreFatture.getInstance().query = ricerca;
            }
            GestoreGrafica.getInstance().changePanel(TipoPanel.FATTURE, new String[]{ricerca});
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

    public JPanel getPannelloFatture() {
        return pannelloFatture;
    }

    private void ricerca() {
        String query = inputRicerca.getText();
        if (query == null || query.isBlank()) {
            GestoreFatture.getInstance().filtriFattura = null;
            GestoreFatture.getInstance().query = null;
            ricerca = null;
        } else {
            GestoreFatture.getInstance().filtriFattura = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriFattura();
            GestoreFatture.getInstance().query = query;
            ricerca = query;
        }

        GestoreGrafica.getInstance().changePanel(TipoPanel.FATTURE, new String[]{ricerca});
    }

    @Override
    public void focusGained(FocusEvent e) {
        inputRicerca.setCaretPosition((inputRicerca.getText().length()));
    }

    @Override
    public void focusLost(FocusEvent e) {
    }

    private void createUIComponents() {
        ArrayList<Fattura> fatture = GestoreFatture.getInstance().getFatture();
        String[][] datiTabella = new String[fatture.size()][4];
        String[] headers = new String[]{
                "Paziente", "Interventi", "Prezzo Complessivo", "Data Fattura"
        };

        for (int i = 0; i < fatture.size(); i++) {
            Fattura fattura = fatture.get(i);
            Paziente pazienteFattura = GestorePazienti.getInstance().getPaziente(fattura.getIDPaziente());
            datiTabella[i][0] = pazienteFattura.getCognome() + " " + pazienteFattura.getNome();

            String interventi = "";
            double prezzoComplessivo = 0;
            for (UUID idIntervento : fattura.getInterventi()) {
                Intervento intervento = GestoreInterventi.getInstance().getIntervento(idIntervento);
                interventi += intervento.getTipoIntervento().nome + " + ";
                prezzoComplessivo += intervento.getCosto();
            }

            interventi = interventi.substring(0, interventi.length() - 3);
            datiTabella[i][1] = interventi;

            datiTabella[i][2] = prezzoComplessivo + " €";

            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN);
            datiTabella[i][3] = dateFormat.format(fattura.getData());
        }

        tabellaFatture = new JTable(new DefaultTableModel(datiTabella, headers) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tabellaFatture.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

}
