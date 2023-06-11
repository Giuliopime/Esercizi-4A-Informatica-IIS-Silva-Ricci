package eserciziCompleti.studioDentistico.grafica.dialogs.fatture;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import eserciziCompleti.studioDentistico.enums.AzioneDialog;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.gestori.GestoreInterventi;
import eserciziCompleti.studioDentistico.gestori.GestorePazienti;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.grafica.dialogs.ConfermaUscita;
import eserciziCompleti.studioDentistico.oggetti.Fattura;
import eserciziCompleti.studioDentistico.oggetti.Intervento;
import eserciziCompleti.studioDentistico.oggetti.Paziente;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class DialogFattura extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancella;
    private JButton buttonSalva;
    private JLabel labelErrore;
    private JList jListInterventi;
    private JTextArea tieniPremutoCTRLPerTextArea;
    private JComboBox cbPaziente;

    private final String titolo;
    private Fattura fattura;
    private AzioneDialog azione = AzioneDialog.NIENTE;
    private ArrayList<Paziente> pazienti;
    private ArrayList<Intervento> interventi;

    public DialogFattura(String titolo) {
        this.titolo = titolo;

        $$$setupUI$$$();
        initDialog();
    }

    private void initDialog() {
        setTitle(titolo);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonSalva);

        initGraficaBottoni();
        initListeners();

        caricaInterventi();

        setMinimumSize(new Dimension(700, 250));
        pack();
        setLocationRelativeTo(GestoreGrafica.getInstance().getFrame());
        setVisible(true);
    }

    private void initGraficaBottoni() {
        JButton[] bottoni = new JButton[]{
                buttonCancella, buttonSalva
        };

        for (JButton btn : bottoni) {
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    btn.setBackground(Colori.verdeChiaroHover);
                    btn.setForeground(Colori.bianco);
                }

                public void mouseExited(MouseEvent evt) {
                    btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    btn.setBackground(btn.equals(buttonCancella) ? Colori.rosso : Colori.verdeChiaro);
                    btn.setForeground(btn.equals(buttonCancella) ? Colori.bianco : Colori.bluScuro);
                }
            });
        }
    }

    private void initListeners() {
        buttonSalva.addActionListener(e -> onSalva());

        buttonCancella.addActionListener(e -> onCancella());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancella();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancella(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        cbPaziente.addActionListener(e -> caricaInterventi());
    }

    private void onSalva() {
        if (creaFatturaFromInput())
            dispose();
    }

    private void onCancella() {
        ConfermaUscita uscitaDialog = new ConfermaUscita("Conferma uscita", "Sei sicuro di voler uscire?");
        if (uscitaDialog.haConfermato()) {
            azione = AzioneDialog.NIENTE;
            dispose();
        }
    }

    private void caricaInterventi() {
        interventi = new ArrayList<>();
        UUID IDPaziente = pazienti.get(cbPaziente.getSelectedIndex()).getIDPaziente();

        DefaultListModel listModel = (DefaultListModel) jListInterventi.getModel();
        listModel.removeAllElements();

        for (Intervento intervento : GestoreInterventi.getInstance().getInterventi()) {
            if (intervento.getIDPaziente().equals(IDPaziente)) {
                listModel.addElement(intervento.getTipoIntervento().nome);
                interventi.add(intervento);
            }
        }

        jListInterventi.setSelectedIndex(0);
    }

    private boolean creaFatturaFromInput() {
        try {
            ArrayList<UUID> IDInterventi = new ArrayList<>();
            for (int i : jListInterventi.getSelectedIndices())
                IDInterventi.add(interventi.get(i).getIDIntervento());

            fattura = new Fattura(pazienti.get(cbPaziente.getSelectedIndex()).getIDPaziente(), IDInterventi.toArray(UUID[]::new));
        } catch (IllegalArgumentException e) {
            labelErrore.setIcon(new ImageIcon(getClass().getResource("/studioDentistico/errorIcon.png")));
            labelErrore.setText("Errore: " + e.getMessage());
            return false;
        }

        azione = AzioneDialog.SALVA;
        return true;
    }

    public Fattura getFattura() {
        return fattura;
    }

    public AzioneDialog getAzione() {
        return azione;
    }

    private void createUIComponents() {
        pazienti = GestorePazienti.getInstance().getPazienti();

        interventi = new ArrayList<>();

        cbPaziente = new JComboBox();

        for (Paziente paziente : pazienti)
            cbPaziente.addItem(paziente.getNome() + " " + paziente.getCognome());

        cbPaziente.setSelectedIndex(0);
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
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.setBackground(new Color(-13350554));
        contentPane.setMaximumSize(new Dimension(800, 500));
        contentPane.setMinimumSize(new Dimension(500, 270));
        contentPane.setOpaque(true);
        contentPane.setPreferredSize(new Dimension(500, 270));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-13350554));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-13350554));
        panel1.add(panel2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonCancella = new JButton();
        buttonCancella.setBackground(new Color(-7070673));
        buttonCancella.setBorderPainted(false);
        buttonCancella.setContentAreaFilled(true);
        buttonCancella.setEnabled(true);
        buttonCancella.setFocusPainted(false);
        buttonCancella.setFocusable(true);
        Font buttonCancellaFont = this.$$$getFont$$$(null, Font.BOLD, 13, buttonCancella.getFont());
        if (buttonCancellaFont != null) buttonCancella.setFont(buttonCancellaFont);
        buttonCancella.setForeground(new Color(-985873));
        buttonCancella.setHideActionText(false);
        buttonCancella.setHorizontalAlignment(0);
        buttonCancella.setInheritsPopupMenu(false);
        buttonCancella.setLabel("Annulla");
        buttonCancella.setOpaque(true);
        buttonCancella.setRequestFocusEnabled(true);
        buttonCancella.setRolloverEnabled(false);
        buttonCancella.setSelected(false);
        buttonCancella.setText("Annulla");
        buttonCancella.setVisible(true);
        panel2.add(buttonCancella, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonSalva = new JButton();
        buttonSalva.setBackground(new Color(-14503271));
        buttonSalva.setBorderPainted(false);
        buttonSalva.setContentAreaFilled(true);
        buttonSalva.setEnabled(true);
        buttonSalva.setFocusPainted(false);
        buttonSalva.setFocusable(true);
        Font buttonSalvaFont = this.$$$getFont$$$(null, Font.BOLD, 13, buttonSalva.getFont());
        if (buttonSalvaFont != null) buttonSalva.setFont(buttonSalvaFont);
        buttonSalva.setForeground(new Color(-15919071));
        buttonSalva.setHideActionText(false);
        buttonSalva.setHorizontalAlignment(0);
        buttonSalva.setInheritsPopupMenu(false);
        buttonSalva.setLabel("Salva");
        buttonSalva.setOpaque(true);
        buttonSalva.setRequestFocusEnabled(true);
        buttonSalva.setRolloverEnabled(false);
        buttonSalva.setSelected(false);
        buttonSalva.setText("Salva");
        buttonSalva.setVisible(true);
        panel2.add(buttonSalva, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelErrore = new JLabel();
        Font labelErroreFont = this.$$$getFont$$$(null, Font.BOLD, 14, labelErrore.getFont());
        if (labelErroreFont != null) labelErrore.setFont(labelErroreFont);
        labelErrore.setForeground(new Color(-4473925));
        labelErrore.setOpaque(false);
        labelErrore.setText("");
        panel1.add(labelErrore, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-13350554));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-13350554));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.add(cbPaziente, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), null, new Dimension(400, -1), 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-3343));
        label1.setText("Paziente");
        panel4.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel4.add(spacer2, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel4.add(spacer3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-13350554));
        panel3.add(panel5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBackground(new Color(-13350554));
        panel5.add(scrollPane1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(200, 100), new Dimension(200, 100), new Dimension(400, 300), 0, false));
        jListInterventi = new JList();
        jListInterventi.setBackground(new Color(-11771000));
        Font jListInterventiFont = this.$$$getFont$$$("Heiti SC", -1, 14, jListInterventi.getFont());
        if (jListInterventiFont != null) jListInterventi.setFont(jListInterventiFont);
        jListInterventi.setForeground(new Color(-3343));
        jListInterventi.setLayoutOrientation(0);
        jListInterventi.setMaximumSize(new Dimension(400, 300));
        jListInterventi.setMinimumSize(new Dimension(200, -1));
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        jListInterventi.setModel(defaultListModel1);
        jListInterventi.setPreferredSize(new Dimension(200, -1));
        jListInterventi.setSelectionMode(2);
        scrollPane1.setViewportView(jListInterventi);
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 16, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-3343));
        label2.setText("Interventi");
        panel5.add(label2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel5.add(spacer4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel5.add(spacer5, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setBackground(new Color(-13350554));
        panel3.add(panel6, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tieniPremutoCTRLPerTextArea = new JTextArea();
        tieniPremutoCTRLPerTextArea.setBackground(new Color(-13350554));
        Font tieniPremutoCTRLPerTextAreaFont = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, tieniPremutoCTRLPerTextArea.getFont());
        if (tieniPremutoCTRLPerTextAreaFont != null)
            tieniPremutoCTRLPerTextArea.setFont(tieniPremutoCTRLPerTextAreaFont);
        tieniPremutoCTRLPerTextArea.setForeground(new Color(-3343));
        tieniPremutoCTRLPerTextArea.setText("Tieni premuto CTRL per selezionare più interventi");
        panel6.add(tieniPremutoCTRLPerTextArea, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 30), null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel3.add(spacer6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 20), new Dimension(-1, 20), new Dimension(-1, 20), 0, false));
        final Spacer spacer7 = new Spacer();
        panel3.add(spacer7, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 15), new Dimension(-1, 15), new Dimension(-1, 15), 0, false));
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
        return contentPane;
    }
}
