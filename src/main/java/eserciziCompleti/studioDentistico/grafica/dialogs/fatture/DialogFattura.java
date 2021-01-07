package eserciziCompleti.studioDentistico.grafica.dialogs.fatture;

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
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.UUID;

public class DialogFattura extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancella;
    private JButton buttonSalva;
    private JLabel labelErrore;
    private JList jListInterventi;
    private JTextArea tieniPremutoCTRLPerTextArea;
    private JComboBox cbPaziente;

    private String titolo;
    private Fattura fattura;
    private AzioneDialog azione = AzioneDialog.NIENTE;
    private ArrayList<Paziente> pazienti;
    private ArrayList<Intervento> interventi;

    public DialogFattura(String titolo) {
        this.titolo = titolo;

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
}
