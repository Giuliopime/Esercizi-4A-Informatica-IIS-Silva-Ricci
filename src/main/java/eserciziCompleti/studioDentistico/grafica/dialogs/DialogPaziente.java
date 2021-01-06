package eserciziCompleti.studioDentistico.grafica.dialogs;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import eserciziCompleti.studioDentistico.enums.AzioneDialog;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.grafica.Colori;
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
    private AzioneDialog azione = AzioneDialog.Niente;

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
            azione = AzioneDialog.Niente;
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
            azione = AzioneDialog.Niente;
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

}
