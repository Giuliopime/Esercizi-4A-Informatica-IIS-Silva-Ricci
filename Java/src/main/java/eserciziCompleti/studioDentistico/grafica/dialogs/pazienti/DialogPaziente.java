package eserciziCompleti.studioDentistico.grafica.dialogs.pazienti;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import eserciziCompleti.studioDentistico.enums.AzioneDialog;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.grafica.dialogs.ConfermaUscita;
import eserciziCompleti.studioDentistico.oggetti.Paziente;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DialogPaziente extends JDialog {
    private JPanel contentPane;
    private JButton buttonSalva;
    private JButton buttonCancella;
    private JTextField fieldNome;
    private JTextField fieldCognome;
    private JTextField fieldLuogoNascita;
    private JTextField fieldCodiceFiscale;
    private JTextField fieldResidenza;
    private JTextField fieldProvincia;
    private JTextField fieldOccupazione;
    private JTextField fieldNumTelefono;
    private JPanel pannelloData;
    private JComboBox comboBoxSesso;
    private JLabel labelErrore;
    private JButton buttonElimina;

    private String titolo;
    private Paziente paziente;
    private JDatePickerImpl datePicker;
    private JDatePanelImpl datePanel;
    private AzioneDialog azione = AzioneDialog.NIENTE;

    public DialogPaziente(String titolo) {
        this.titolo = titolo;

        initDialog(false);
    }

    public DialogPaziente(String titolo, Paziente paziente) {
        this.titolo = titolo;
        this.paziente = paziente;

        initDialog(true);
    }

    private void initDialog(boolean caricaPaziente) {
        setTitle(titolo);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonSalva);

        initGraficaBottoni();
        initDatePicker(caricaPaziente ? true : false);
        initListeners();

        if (!caricaPaziente) buttonElimina.setVisible(false);
        if (caricaPaziente) caricaPazienteSuGrafica();

        setMinimumSize(new Dimension(600, 250));
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

    private void initDatePicker(boolean daPaziente) {
        UtilDateModel model = new UtilDateModel();
        if (daPaziente) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(paziente.getDataNascita());
            model.setValue(calendar.getTime());
        }
        Properties p = new Properties();
        p.put("text.today", "Oggi");
        p.put("text.month", "Mese");
        p.put("text.year", "Anno");
        datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        pannelloData.add(datePicker);
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
        if (creaPazienteFromInput())
            dispose();
    }

    private void onElimina() {
        ConfermaUscita uscitaDialog = new ConfermaUscita("Conferma Eliminazione", "Sei sicuro di voler rimuovere questo paziente?");
        if (uscitaDialog.haConfermato()) {
            azione = AzioneDialog.ELIMINA;
            dispose();
        }
    }

    private void onCancella() {
        ConfermaUscita uscitaDialog = new ConfermaUscita("Conferma uscita", "Sei sicuro di voler uscire?");
        if (uscitaDialog.haConfermato()) {
            azione = AzioneDialog.NIENTE;
            dispose();
        }
    }

    private void caricaPazienteSuGrafica() {
        fieldNome.setText(paziente.getNome());
        fieldCognome.setText(paziente.getCognome());
        fieldCodiceFiscale.setText(paziente.getCodiceFiscale());
        fieldLuogoNascita.setText(paziente.getLuogoNascita());
        fieldOccupazione.setText(paziente.getOccupazione());
        fieldNumTelefono.setText(paziente.getNumTelefono());
        fieldProvincia.setText(paziente.getProvincia());
        fieldResidenza.setText(paziente.getResidenza());
        comboBoxSesso.setSelectedIndex(paziente.getSesso().toLowerCase().startsWith("m") ? 0 : 1);
    }

    private boolean creaPazienteFromInput() {
        Paziente paziente2;
        UUID IDPaziente = paziente != null ? paziente.getIDPaziente() : null;

        try {
            Date dataNascita = (Date) datePicker.getModel().getValue();
            Long millisDataNascita = dataNascita != null ? dataNascita.getTime() : null;
            paziente2 = new Paziente(fieldCodiceFiscale.getText(), fieldNome.getText(), fieldCognome.getText(), fieldLuogoNascita.getText(), fieldResidenza.getText(), fieldProvincia.getText(), fieldOccupazione.getText(), comboBoxSesso.getSelectedIndex() == 0 ? "Maschio" : "Femmina", fieldNumTelefono.getText(), millisDataNascita);
            if (IDPaziente != null) {
                paziente2.setIDPaziente(IDPaziente);
                paziente2.setDataCreazione(paziente.getDataCreazione());
            }
        } catch (IllegalArgumentException e) {
            labelErrore.setIcon(new ImageIcon(getClass().getResource("/studioDentistico/errorIcon.png")));
            labelErrore.setText("Errore: " + e.getMessage());
            return false;
        }

        if (paziente != null && paziente.equals(paziente2))
            azione = AzioneDialog.NIENTE;
        else {
            azione = AzioneDialog.SALVA;
            paziente2.setUltimaModifica(new Date().getTime());
            paziente = paziente2;
        }

        return true;
    }

    public Paziente getPaziente() {
        return paziente;
    }

    public AzioneDialog getAzione() {
        return azione;
    }

    public static void main(String[] args) {
        DialogPaziente dialog = new DialogPaziente("Test Dialog Paziente");
        System.exit(0);
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
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-13350554));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
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
        panel2.add(buttonSalva, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        panel2.add(buttonElimina, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelErrore = new JLabel();
        Font labelErroreFont = this.$$$getFont$$$(null, Font.BOLD, 14, labelErrore.getFont());
        if (labelErroreFont != null) labelErrore.setFont(labelErroreFont);
        labelErrore.setForeground(new Color(-4473925));
        labelErrore.setOpaque(false);
        labelErrore.setText("");
        panel1.add(labelErrore, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-13350554));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-13350554));
        panel4.setEnabled(false);
        panel3.add(panel4, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setBackground(new Color(-13350554));
        panel4.add(panel5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-985873));
        label1.setText("Data di Nascita");
        panel5.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pannelloData = new JPanel();
        pannelloData.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        pannelloData.setBackground(new Color(-13350554));
        panel5.add(pannelloData, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel6.setBackground(new Color(-13350554));
        panel4.add(panel6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-985873));
        label2.setText("Cognome");
        panel6.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldCognome = new JTextField();
        panel6.add(fieldCognome, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel7.setBackground(new Color(-13350554));
        panel4.add(panel7, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-985873));
        label3.setText("Occupazione");
        panel7.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldOccupazione = new JTextField();
        panel7.add(fieldOccupazione, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel8.setBackground(new Color(-13350554));
        panel4.add(panel8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-985873));
        label4.setText("Provincia");
        panel8.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldProvincia = new JTextField();
        panel8.add(fieldProvincia, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel9.setBackground(new Color(-13350554));
        panel4.add(panel9, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, label5.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setForeground(new Color(-985873));
        label5.setText("Codice Fiscale");
        panel9.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldCodiceFiscale = new JTextField();
        panel9.add(fieldCodiceFiscale, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel10.setBackground(new Color(-13350554));
        panel10.setEnabled(false);
        panel3.add(panel10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel11.setBackground(new Color(-13350554));
        panel10.add(panel11, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setForeground(new Color(-985873));
        label6.setText("Numero Telefonico");
        panel11.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldNumTelefono = new JTextField();
        panel11.add(fieldNumTelefono, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel12.setBackground(new Color(-13350554));
        panel10.add(panel12, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setForeground(new Color(-985873));
        label7.setText("Nome");
        panel12.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldNome = new JTextField();
        panel12.add(fieldNome, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel13 = new JPanel();
        panel13.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel13.setBackground(new Color(-13350554));
        panel10.add(panel13, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setForeground(new Color(-985873));
        label8.setText("Residenza");
        panel13.add(label8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldResidenza = new JTextField();
        panel13.add(fieldResidenza, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel14 = new JPanel();
        panel14.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel14.setBackground(new Color(-13350554));
        panel10.add(panel14, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        Font label9Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, label9.getFont());
        if (label9Font != null) label9.setFont(label9Font);
        label9.setForeground(new Color(-985873));
        label9.setText("Sesso");
        panel14.add(label9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxSesso = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Maschio");
        defaultComboBoxModel1.addElement("Femmina");
        comboBoxSesso.setModel(defaultComboBoxModel1);
        panel14.add(comboBoxSesso, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel15 = new JPanel();
        panel15.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel15.setBackground(new Color(-13350554));
        panel10.add(panel15, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        Font label10Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, label10.getFont());
        if (label10Font != null) label10.setFont(label10Font);
        label10.setForeground(new Color(-985873));
        label10.setText("Luogo Di Nascita");
        panel15.add(label10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldLuogoNascita = new JTextField();
        panel15.add(fieldLuogoNascita, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, 1, null, new Dimension(15, -1), new Dimension(25, -1), 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 5), null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel3.add(spacer4, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 5), null, 0, false));
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
