package eserciziCompleti.studioDentistico.grafica.dialogs.pazienti;

import eserciziCompleti.studioDentistico.enums.ordinamento.OrdinamentoPazienti;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.grafica.Colori;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogOrdinaPazienti extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelErrore;
    private JRadioButton rbDataNascita;
    private JRadioButton rbCognome;
    private JRadioButton rbNome;
    private JRadioButton rbDataModifica;
    private JRadioButton rbNessuno;
    private JRadioButton rbDataCreazione;

    private OrdinamentoPazienti ordinamentoPazienti;

    public DialogOrdinaPazienti(OrdinamentoPazienti ordinamentoPazienti) {
        this.ordinamentoPazienti = ordinamentoPazienti;
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
        if (ordinamentoPazienti == null)
            rbNessuno.setSelected(true);

        else {
            switch (ordinamentoPazienti) {
                case NOME -> rbNome.setSelected(true);
                case COGNOME -> rbCognome.setSelected(true);
                case DATACREAZIONE -> rbDataCreazione.setSelected(true);
                case DATAULTIMAMODIFICA -> rbDataModifica.setSelected(true);
                case DATANASCITA -> rbDataNascita.setSelected(true);
                default -> rbNessuno.setSelected(true);
            }
        }
    }

    private void onOK() {
        ordinamentoPazienti = rbDataCreazione.isSelected() ? OrdinamentoPazienti.DATACREAZIONE :
                rbDataModifica.isSelected() ? OrdinamentoPazienti.DATAULTIMAMODIFICA :
                        rbNome.isSelected() ? OrdinamentoPazienti.NOME :
                                rbCognome.isSelected() ? OrdinamentoPazienti.COGNOME :
                                        rbDataNascita.isSelected() ? OrdinamentoPazienti.DATANASCITA :
                                                null;
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public OrdinamentoPazienti getOrdinamentoPazienti() {
        return ordinamentoPazienti;
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
