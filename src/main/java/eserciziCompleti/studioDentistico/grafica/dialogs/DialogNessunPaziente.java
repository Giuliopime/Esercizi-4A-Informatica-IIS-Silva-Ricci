package eserciziCompleti.studioDentistico.grafica.dialogs;

import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.grafica.Colori;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogNessunPaziente extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public DialogNessunPaziente() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        initListeners();
        initGrafica();


        pack();
        setResizable(false);
        setLocationRelativeTo(GestoreGrafica.getInstance().getFrame());
        setVisible(true);
    }

    private void onOK() {
        dispose();
    }

    private void initListeners() {
        buttonOK.addActionListener(e -> onOK());


        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });


        contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void initGrafica() {
        buttonOK.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                buttonOK.setCursor(new Cursor(Cursor.HAND_CURSOR));
                buttonOK.setBackground(Colori.verdeChiaroHover);
                buttonOK.setForeground(Colori.bianco);
            }

            public void mouseExited(MouseEvent evt) {
                buttonOK.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                buttonOK.setBackground(Colori.verdeChiaro);
                buttonOK.setForeground(Colori.bluScuro);
            }
        });
    }
}
