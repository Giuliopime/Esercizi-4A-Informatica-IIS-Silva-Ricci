package eserciziCompleti.studioDentistico.grafica.dialogs;

import eserciziCompleti.studioDentistico.enums.AzioneDialog;
import eserciziCompleti.studioDentistico.enums.TipoIntervento;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.gestori.GestorePazienti;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.oggetti.Intervento;
import eserciziCompleti.studioDentistico.oggetti.Paziente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
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
    private AzioneDialog azione = AzioneDialog.Niente;
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
            azione = AzioneDialog.Niente;
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
            azione = AzioneDialog.Niente;
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

}
