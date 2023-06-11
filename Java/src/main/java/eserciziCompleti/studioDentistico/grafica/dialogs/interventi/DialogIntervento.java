package eserciziCompleti.studioDentistico.grafica.dialogs.interventi;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import eserciziCompleti.studioDentistico.enums.AzioneDialog;
import eserciziCompleti.studioDentistico.enums.TipoIntervento;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.gestori.GestorePazienti;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.grafica.dialogs.ConfermaUscita;
import eserciziCompleti.studioDentistico.oggetti.Intervento;
import eserciziCompleti.studioDentistico.oggetti.Paziente;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DialogIntervento extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton buttonCancella;
    private JButton buttonSalva;
    private JButton buttonElimina;
    private JLabel labelErrore;
    private JComboBox cbPaziente;
    private JTextField fieldTempo;
    private JComboBox cbTipoIntervento;
    private JTextField fieldCosto;

    private String titolo;
    private Intervento intervento;
    private AzioneDialog azione = AzioneDialog.NIENTE;
    private ArrayList<Paziente> pazienti;

    public DialogIntervento(String titolo) {
        this.titolo = titolo;

        initDialog(false);
    }

    public DialogIntervento(String titolo, Intervento intervento) {
        this.titolo = titolo;
        this.intervento = intervento;

        initDialog(true);
    }

    private void initDialog(boolean caricaIntervento) {
        setTitle(titolo);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonSalva);

        initGraficaBottoni();
        initListeners();

        caricaComboBox();
        if (!caricaIntervento) buttonElimina.setVisible(false);
        if (caricaIntervento) caricaInterventoSuGrafica();

        setMinimumSize(new Dimension(700, 250));
        pack();
        setLocationRelativeTo(GestoreGrafica.getInstance().getFrame());
        setVisible(true);
    }

    private void initGraficaBottoni() {
        JButton[] bottoni = new JButton[]{
                buttonCancella, buttonSalva, buttonElimina
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
                    btn.setBackground(btn.equals(buttonCancella) || btn.equals(buttonElimina) ? Colori.rosso : Colori.verdeChiaro);
                    btn.setForeground(btn.equals(buttonCancella) || btn.equals(buttonElimina) ? Colori.bianco : Colori.bluScuro);
                }
            });
        }
    }

    private void initListeners() {
        buttonSalva.addActionListener(e -> onSalva());

        buttonCancella.addActionListener(e -> onCancella());

        buttonElimina.addActionListener(e -> onElimina());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancella();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancella(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void onSalva() {
        if (creaInterventoFromInput())
            dispose();
    }

    private void onCancella() {
        ConfermaUscita uscitaDialog = new ConfermaUscita("Conferma uscita", "Sei sicuro di voler uscire?");
        if (uscitaDialog.haConfermato()) {
            azione = AzioneDialog.NIENTE;
            dispose();
        }
    }

    private void onElimina() {
        ConfermaUscita uscitaDialog = new ConfermaUscita("Conferma Eliminazione", "Sei sicuro di voler rimuovere questo intervento?");
        if (uscitaDialog.haConfermato()) {
            azione = AzioneDialog.ELIMINA;
            dispose();
        }
    }

    private void caricaComboBox() {
        pazienti = GestorePazienti.getInstance().getPazienti();
        for (Paziente paziente : pazienti)
            cbPaziente.addItem(paziente.getNome() + " " + paziente.getCognome());

        for (TipoIntervento tipoIntervento : TipoIntervento.getTipiIntervento())
            cbTipoIntervento.addItem(tipoIntervento.nome);
    }

    private void caricaInterventoSuGrafica() {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        fieldCosto.setText(decimalFormat.format(intervento.getCosto()));
        fieldTempo.setText(String.valueOf(TimeUnit.MILLISECONDS.toMinutes(intervento.getTempoMedio())));

        cbPaziente.setSelectedIndex(pazienti.indexOf(GestorePazienti.getInstance().getPaziente(intervento.getIDPaziente())));
        cbTipoIntervento.setSelectedIndex(Arrays.asList(TipoIntervento.getTipiIntervento()).indexOf(intervento.getTipoIntervento()));
    }

    private boolean creaInterventoFromInput() {
        Intervento intervento2;
        UUID IDIntervento = intervento != null ? intervento.getIDIntervento() : null;

        try {
            UUID idPaziente = pazienti.get(cbPaziente.getSelectedIndex()).getIDPaziente();
            TipoIntervento tipoIntervento = TipoIntervento.getTipiIntervento()[cbTipoIntervento.getSelectedIndex()];
            intervento2 = new Intervento(tipoIntervento, Double.parseDouble(fieldCosto.getText()), (Long.parseLong(fieldTempo.getText()) * 60000), idPaziente);
            if (IDIntervento != null) {
                intervento2.setIDIntervento(IDIntervento);
                intervento2.setDataCreazione(intervento.getDataCreazione());
            }
        } catch (NumberFormatException e) {
            labelErrore.setIcon(new ImageIcon(getClass().getResource("/studioDentistico/errorIcon.png")));
            labelErrore.setText("Errore: il costo o il tempo non è un numero valido");
            return false;
        } catch (IllegalArgumentException e) {
            labelErrore.setIcon(new ImageIcon(getClass().getResource("/studioDentistico/errorIcon.png")));
            labelErrore.setText("Errore: " + e.getMessage());
            return false;
        }

        if (intervento != null && intervento.equals(intervento2))
            azione = AzioneDialog.NIENTE;
        else {
            azione = AzioneDialog.SALVA;
            intervento2.setUltimaModifica(new Date().getTime());
            intervento = intervento2;
        }

        return true;
    }

    public Intervento getIntervento() {
        return intervento;
    }

    public AzioneDialog getAzione() {
        return azione;
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
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.setBackground(new Color(-13350554));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-13350554));
        contentPane.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-13350554));
        panel2.setEnabled(false);
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-13350554));
        panel2.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-985873));
        label1.setText("Tipo Intervento");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbTipoIntervento = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        cbTipoIntervento.setModel(defaultComboBoxModel1);
        panel3.add(cbTipoIntervento, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-13350554));
        panel2.add(panel4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-985873));
        label2.setText("Costo (in euro)");
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldCosto = new JTextField();
        panel4.add(fieldCosto, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-13350554));
        panel5.setEnabled(false);
        panel1.add(panel5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setBackground(new Color(-13350554));
        panel5.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-985873));
        label3.setText("Paziente");
        panel6.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbPaziente = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        cbPaziente.setModel(defaultComboBoxModel2);
        panel6.add(cbPaziente, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel7.setBackground(new Color(-13350554));
        panel5.add(panel7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-985873));
        label4.setText("Tempo (in minuti)");
        panel7.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldTempo = new JTextField();
        panel7.add(fieldTempo, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(15, -1), new Dimension(25, -1), 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 5), null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 5), null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel8.setBackground(new Color(-13350554));
        contentPane.add(panel8, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel8.add(spacer4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel9.setBackground(new Color(-13350554));
        panel8.add(panel9, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
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
        panel9.add(buttonCancella, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        panel9.add(buttonSalva, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonElimina = new JButton();
        buttonElimina.setBackground(new Color(-7070673));
        buttonElimina.setBorderPainted(false);
        buttonElimina.setContentAreaFilled(true);
        buttonElimina.setEnabled(true);
        buttonElimina.setFocusPainted(false);
        buttonElimina.setFocusable(true);
        Font buttonEliminaFont = this.$$$getFont$$$(null, Font.BOLD, 13, buttonElimina.getFont());
        if (buttonEliminaFont != null) buttonElimina.setFont(buttonEliminaFont);
        buttonElimina.setForeground(new Color(-985873));
        buttonElimina.setHideActionText(false);
        buttonElimina.setHorizontalAlignment(0);
        buttonElimina.setInheritsPopupMenu(false);
        buttonElimina.setLabel("Elimina");
        buttonElimina.setOpaque(true);
        buttonElimina.setRequestFocusEnabled(true);
        buttonElimina.setRolloverEnabled(false);
        buttonElimina.setSelected(false);
        buttonElimina.setText("Elimina");
        buttonElimina.setVisible(true);
        panel9.add(buttonElimina, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelErrore = new JLabel();
        Font labelErroreFont = this.$$$getFont$$$(null, Font.BOLD, 14, labelErrore.getFont());
        if (labelErroreFont != null) labelErrore.setFont(labelErroreFont);
        labelErrore.setForeground(new Color(-4473925));
        labelErrore.setOpaque(false);
        labelErrore.setText("");
        panel8.add(labelErrore, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
