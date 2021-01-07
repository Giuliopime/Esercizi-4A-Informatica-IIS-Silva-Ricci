package eserciziCompleti.studioDentistico.grafica.dialogs;

import eserciziCompleti.studioDentistico.enums.ordinamento.OrdinamentoPazienti;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.gestori.GestoreImpostazioni;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.oggetti.Impostazioni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogImpostazioni extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelErrore;
    private JTextField tfNomeStudio;
    private JButton btnEsporta;

    private Impostazioni impostazioni;

    public DialogImpostazioni() {
        impostazioni = GestoreImpostazioni.getInstance().getImpostazioni();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        caricaImpostazioni();
        initListeners();
        initGrafica();


        pack();
        setResizable(false);
        setLocationRelativeTo(GestoreGrafica.getInstance().getFrame());
        setVisible(true);
    }

    private void caricaImpostazioni() {
        tfNomeStudio.setText(impostazioni.getNomeStudio());
    }

    private void onOK() {
        if(tfNomeStudio.getText().isBlank()) {
            labelErrore.setIcon(new ImageIcon(getClass().getResource("/studioDentistico/errorIcon.png")));
            labelErrore.setText("Errore: fornire un nome per lo studio");
            return;
        }
        impostazioni.setNomeStudio(tfNomeStudio.getText());
        dispose();
    }

    private void onCancel() {
        impostazioni = null;
        dispose();
    }

    public Impostazioni getImpostazioni() {
        return impostazioni;
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

        btnEsporta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onEsporta();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnEsporta.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnEsporta.setBackground(Colori.verdeChiaroHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnEsporta.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnEsporta.setBackground(Colori.bluScuro);
            }
        });

        btnEsporta.addActionListener(e -> onEsporta());
    }

    private void onEsporta() {

    }
}
