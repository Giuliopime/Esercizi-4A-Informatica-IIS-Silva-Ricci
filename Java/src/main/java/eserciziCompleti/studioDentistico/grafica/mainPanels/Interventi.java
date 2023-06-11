package eserciziCompleti.studioDentistico.grafica.mainPanels;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import eserciziCompleti.studioDentistico.enums.Schermata;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.gestori.GestoreImpostazioni;
import eserciziCompleti.studioDentistico.gestori.GestoreInterventi;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.grafica.NavBarUtil;
import eserciziCompleti.studioDentistico.grafica.RequestFocusListener;
import eserciziCompleti.studioDentistico.grafica.WrapLayout;
import eserciziCompleti.studioDentistico.grafica.dialogs.interventi.DialogFiltriInterventi;
import eserciziCompleti.studioDentistico.grafica.dialogs.interventi.DialogOrdinaInterventi;
import eserciziCompleti.studioDentistico.grafica.panelsComponenti.PanelIntervento;
import eserciziCompleti.studioDentistico.oggetti.Impostazioni;
import eserciziCompleti.studioDentistico.oggetti.Intervento;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Locale;

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
    private JLabel labelImpostazioni;

    private String ricerca;

    private JButton[] bottoniNav;

    public Interventi(String[] ricerca) {
        this.ricerca = ricerca != null ? ricerca[0] : null;

        $$$setupUI$$$();
        bottoniNav = new JButton[]{
                btnPazienti, btnInterventi, btnFatture
        };
        NavBarUtil.initNavBar(bottoniNav, logo, labelImpostazioni);
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
                GestoreInterventi.getInstance().stringaRicercaPerFiltro = ricerca;
            }
            GestoreGrafica.getInstance().changePanel(Schermata.INTERVENTI, new String[]{ricerca});
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
            GestoreInterventi.getInstance().stringaRicercaPerFiltro = null;
            ricerca = null;
        } else {
            GestoreInterventi.getInstance().filtriIntervento = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriIntervento();
            GestoreInterventi.getInstance().stringaRicercaPerFiltro = query;
            ricerca = query;
        }

        GestoreGrafica.getInstance().changePanel(Schermata.INTERVENTI, new String[]{ricerca});
    }

    @Override
    public void focusGained(FocusEvent e) {
        inputRicerca.setCaretPosition((inputRicerca.getText().length()));
    }

    @Override
    public void focusLost(FocusEvent e) {
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        pannelloInterventi = new JPanel();
        pannelloInterventi.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        pannelloInterventi.setBackground(new Color(-13350554));
        pannelloInterventi.setEnabled(false);
        pannelloNavBar = new JPanel();
        pannelloNavBar.setLayout(new GridLayoutManager(1, 6, new Insets(10, 20, 10, 20), -1, -1));
        pannelloNavBar.setBackground(new Color(-15919071));
        pannelloInterventi.add(pannelloNavBar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        logo = new JLabel();
        logo.setEnabled(true);
        Font logoFont = this.$$$getFont$$$("Kokonor", Font.BOLD, 24, logo.getFont());
        if (logoFont != null) logo.setFont(logoFont);
        logo.setForeground(new Color(-985873));
        logo.setHorizontalAlignment(0);
        logo.setHorizontalTextPosition(11);
        logo.setIcon(new ImageIcon(getClass().getResource("/studioDentistico/logoDentista.png")));
        logo.setText("Studio Dentistico ");
        pannelloNavBar.add(logo, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnPazienti = new JButton();
        btnPazienti.setBackground(new Color(-14503271));
        btnPazienti.setBorderPainted(false);
        btnPazienti.setContentAreaFilled(true);
        btnPazienti.setEnabled(true);
        btnPazienti.setFocusPainted(false);
        btnPazienti.setFocusable(false);
        Font btnPazientiFont = this.$$$getFont$$$(null, Font.BOLD, 16, btnPazienti.getFont());
        if (btnPazientiFont != null) btnPazienti.setFont(btnPazientiFont);
        btnPazienti.setForeground(new Color(-15919071));
        btnPazienti.setHideActionText(false);
        btnPazienti.setHorizontalAlignment(0);
        btnPazienti.setInheritsPopupMenu(false);
        btnPazienti.setLabel("Pazienti");
        btnPazienti.setOpaque(true);
        btnPazienti.setRequestFocusEnabled(true);
        btnPazienti.setRolloverEnabled(false);
        btnPazienti.setSelected(false);
        btnPazienti.setText("Pazienti");
        btnPazienti.setVisible(true);
        pannelloNavBar.add(btnPazienti, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(130, -1), new Dimension(130, -1), new Dimension(130, -1), 0, false));
        final Spacer spacer1 = new Spacer();
        pannelloNavBar.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        btnInterventi = new JButton();
        btnInterventi.setBackground(new Color(-14503271));
        btnInterventi.setBorderPainted(false);
        btnInterventi.setContentAreaFilled(true);
        btnInterventi.setEnabled(true);
        btnInterventi.setFocusPainted(false);
        btnInterventi.setFocusable(false);
        Font btnInterventiFont = this.$$$getFont$$$(null, Font.BOLD, 16, btnInterventi.getFont());
        if (btnInterventiFont != null) btnInterventi.setFont(btnInterventiFont);
        btnInterventi.setForeground(new Color(-15919071));
        btnInterventi.setHideActionText(false);
        btnInterventi.setHorizontalAlignment(0);
        btnInterventi.setInheritsPopupMenu(false);
        btnInterventi.setLabel("Interventi");
        btnInterventi.setOpaque(true);
        btnInterventi.setRequestFocusEnabled(true);
        btnInterventi.setRolloverEnabled(false);
        btnInterventi.setSelected(false);
        btnInterventi.setText("Interventi");
        btnInterventi.setVisible(true);
        pannelloNavBar.add(btnInterventi, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(140, -1), new Dimension(140, -1), new Dimension(140, -1), 1, false));
        btnFatture = new JButton();
        btnFatture.setBackground(new Color(-14503271));
        btnFatture.setBorderPainted(false);
        btnFatture.setContentAreaFilled(true);
        btnFatture.setEnabled(true);
        btnFatture.setFocusPainted(false);
        btnFatture.setFocusable(false);
        Font btnFattureFont = this.$$$getFont$$$(null, Font.BOLD, 16, btnFatture.getFont());
        if (btnFattureFont != null) btnFatture.setFont(btnFattureFont);
        btnFatture.setForeground(new Color(-15919071));
        btnFatture.setHideActionText(false);
        btnFatture.setHorizontalAlignment(0);
        btnFatture.setInheritsPopupMenu(false);
        btnFatture.setLabel("Fatture");
        btnFatture.setOpaque(true);
        btnFatture.setRequestFocusEnabled(true);
        btnFatture.setRolloverEnabled(false);
        btnFatture.setSelected(false);
        btnFatture.setText("Fatture");
        btnFatture.setVisible(true);
        pannelloNavBar.add(btnFatture, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(130, -1), new Dimension(130, -1), new Dimension(130, -1), 1, false));
        labelImpostazioni = new JLabel();
        labelImpostazioni.setIcon(new ImageIcon(getClass().getResource("/studioDentistico/iconImpostazioni30.png")));
        labelImpostazioni.setText("");
        pannelloNavBar.add(labelImpostazioni, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 5, new Insets(5, 10, 5, 10), -1, -1));
        panel1.setBackground(new Color(-13350554));
        panel1.setDoubleBuffered(true);
        pannelloInterventi.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        inputRicerca = new JTextField();
        inputRicerca.setBackground(new Color(-919570));
        panel1.add(inputRicerca, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(150, -1), null, null, 1, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        filtriButton = new JButton();
        filtriButton.setBackground(new Color(-919570));
        filtriButton.setBorderPainted(false);
        filtriButton.setContentAreaFilled(true);
        filtriButton.setFocusPainted(true);
        filtriButton.setFocusable(true);
        Font filtriButtonFont = this.$$$getFont$$$(null, Font.BOLD, -1, filtriButton.getFont());
        if (filtriButtonFont != null) filtriButton.setFont(filtriButtonFont);
        filtriButton.setOpaque(true);
        filtriButton.setText("Filtri");
        panel1.add(filtriButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnOrdina = new JButton();
        btnOrdina.setBackground(new Color(-919570));
        btnOrdina.setBorderPainted(false);
        btnOrdina.setContentAreaFilled(true);
        btnOrdina.setFocusPainted(true);
        btnOrdina.setFocusable(true);
        Font btnOrdinaFont = this.$$$getFont$$$(null, Font.BOLD, -1, btnOrdina.getFont());
        if (btnOrdinaFont != null) btnOrdina.setFont(btnOrdinaFont);
        btnOrdina.setOpaque(true);
        btnOrdina.setText("Ordina per");
        panel1.add(btnOrdina, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPaneInterventi = new JScrollPane();
        scrollPaneInterventi.setBackground(new Color(-13350554));
        scrollPaneInterventi.setHorizontalScrollBarPolicy(31);
        pannelloInterventi.add(scrollPaneInterventi, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        listaInterventi.setBackground(new Color(-13350554));
        scrollPaneInterventi.setViewportView(listaInterventi);
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
        return pannelloInterventi;
    }
}
