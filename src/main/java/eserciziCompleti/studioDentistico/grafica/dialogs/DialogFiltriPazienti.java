package eserciziCompleti.studioDentistico.grafica.dialogs;

import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.oggetti.FiltriPaziente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogFiltriPazienti extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox cbNome;
    private JCheckBox cbLuogoNascita;
    private JCheckBox cbResidenza;
    private JCheckBox cbSesso;
    private JCheckBox cbNumTel;
    private JCheckBox cbDataNascita;
    private JCheckBox cbOccup;
    private JCheckBox cbProv;
    private JCheckBox cbCodFis;
    private JCheckBox cbCognome;
    private JLabel labelErrore;

    private FiltriPaziente filtriPaziente;

    public DialogFiltriPazienti(FiltriPaziente filtriPaziente) {
        setTitle("Filtri Pazienti");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        initListeners();
        initGrafica();

        this.filtriPaziente = filtriPaziente;
        caricaFiltri();

        setMinimumSize(new Dimension(500, 200));
        pack();
        setResizable(false);
        setLocationRelativeTo(GestoreGrafica.getInstance().getFrame());
        setVisible(true);
    }

    private void onOK() {
        filtriPaziente = new FiltriPaziente(cbNome.isSelected(), cbCognome.isSelected(), cbLuogoNascita.isSelected(), cbCodFis.isSelected(), cbResidenza.isSelected(), cbProv.isSelected(), cbSesso.isSelected(), cbOccup.isSelected(), cbNumTel.isSelected(), cbDataNascita.isSelected());
        if (filtriPaziente.tuttiFalsi()) {
            labelErrore.setIcon(new ImageIcon(getClass().getResource("/studioDentistico/errorIcon.png")));
            labelErrore.setText("Selezionare almeno un campo");
        } else
            dispose();
    }

    private void onCancel() {
        filtriPaziente = null;
        dispose();
    }

    public FiltriPaziente getFiltri() {
        return filtriPaziente;
    }

    private void caricaFiltri() {
        cbNome.setSelected(filtriPaziente.isNome());
        cbCognome.setSelected(filtriPaziente.isCognome());
        cbLuogoNascita.setSelected(filtriPaziente.isLuogoDiNascita());
        cbCodFis.setSelected(filtriPaziente.isCodiceFiscale());
        cbResidenza.setSelected(filtriPaziente.isResidenza());
        cbProv.setSelected(filtriPaziente.isProvincia());
        cbSesso.setSelected(filtriPaziente.isSesso());
        cbOccup.setSelected(filtriPaziente.isOccupazione());
        cbNumTel.setSelected(filtriPaziente.isNumTelefonico());
        cbDataNascita.setSelected(filtriPaziente.isDataDiNascita());
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
