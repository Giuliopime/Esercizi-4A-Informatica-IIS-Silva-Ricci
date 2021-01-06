package eserciziCompleti.studioDentistico.grafica.dialogs;

import eserciziCompleti.studioDentistico.enums.OrdinamentoFatture;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.grafica.Colori;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogOrdinaFatture extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelErrore;
    private JRadioButton rbData;
    private JRadioButton rbPaziente;
    private JRadioButton rbIntervento;
    private JRadioButton rbNessuno;

    private OrdinamentoFatture ordinamentoFatture;

    public DialogOrdinaFatture(OrdinamentoFatture ordinamentoFatture) {
        this.ordinamentoFatture = ordinamentoFatture;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        caricaOrdinamento();
        initListeners();
        initGrafica();


        pack();
        setResizable(false);
        setLocationRelativeTo(GestoreGrafica.getInstance().getFrame());
        setVisible(true);
    }

    private void caricaOrdinamento() {
        if (ordinamentoFatture == null)
            rbNessuno.setSelected(true);

        else {
            switch (ordinamentoFatture) {
                case PAZIENTE -> rbPaziente.setSelected(true);
                case INTERVENTO -> rbIntervento.setSelected(true);
                case DATA -> rbData.setSelected(true);
                default -> rbNessuno.setSelected(true);
            }
        }
    }

    private void onOK() {
        ordinamentoFatture = rbData.isSelected() ? OrdinamentoFatture.DATA :
                rbPaziente.isSelected() ? OrdinamentoFatture.PAZIENTE :
                        rbIntervento.isSelected() ? OrdinamentoFatture.INTERVENTO :
                                null;
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public OrdinamentoFatture getOrdinamentoFatture() {
        return ordinamentoFatture;
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
