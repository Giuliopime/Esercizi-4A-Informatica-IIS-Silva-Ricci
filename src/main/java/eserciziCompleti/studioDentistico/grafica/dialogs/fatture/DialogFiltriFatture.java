package eserciziCompleti.studioDentistico.grafica.dialogs.fatture;

import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.oggetti.filtri.FiltriFattura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogFiltriFatture extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelErrore;
    private JCheckBox cbData;
    private JCheckBox cbIntervento;
    private JCheckBox cbPaziente;

    private FiltriFattura filtriFattura;

    public DialogFiltriFatture(FiltriFattura filtriFattura) {
        setTitle("Filtri Fatture");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        initListeners();
        initGrafica();

        this.filtriFattura = filtriFattura;
        caricaFiltri();

        setMinimumSize(new Dimension(450, 120));
        pack();
        setResizable(false);
        setLocationRelativeTo(GestoreGrafica.getInstance().getFrame());
        setVisible(true);
    }

    private void onOK() {
        filtriFattura = new FiltriFattura(cbData.isSelected(), cbPaziente.isSelected(), cbIntervento.isSelected());
        if (filtriFattura.tuttiFalsi()) {
            labelErrore.setIcon(new ImageIcon(getClass().getResource("/studioDentistico/errorIcon.png")));
            labelErrore.setText("Selezionare almeno un campo");
        } else
            dispose();
    }

    private void onCancel() {
        filtriFattura = null;
        dispose();
    }

    public FiltriFattura getFiltri() {
        return filtriFattura;
    }

    private void caricaFiltri() {
        cbData.setSelected(filtriFattura.isData());
        cbPaziente.setSelected(filtriFattura.isPaziente());
        cbIntervento.setSelected(filtriFattura.isIntervento());
    }

    private void initListeners() {
        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());


        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });


        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void initGrafica() {
        JButton[] bottoni = new JButton[]{
                buttonCancel, buttonOK
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
                    btn.setBackground(btn.equals(buttonCancel) ? Colori.rosso : Colori.verdeChiaro);
                    btn.setForeground(btn.equals(buttonCancel) ? Colori.bianco : Colori.bluScuro);
                }
            });
        }
    }

}
