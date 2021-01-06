package eserciziCompleti.studioDentistico.grafica.dialogs;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.grafica.Colori;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class ConfermaUscita extends JDialog {
    private JPanel contentPane;
    private JButton buttonConferma;
    private JButton buttonAnnulla;
    private JLabel labelDomanda;
    private boolean confermato;

    public ConfermaUscita(String titolo, String domanda) {
        setTitle(titolo);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonConferma);
        setAlwaysOnTop(true);

        labelDomanda.setText(domanda);

        pack();
        setResizable(false);
        setLocationRelativeTo(GestoreGrafica.getInstance().getFrame());

        initListeners();
        initGraficaBottoni();

        setVisible(true);
    }

    private void initListeners() {
        buttonAnnulla.addActionListener(e -> onAnnulla());

        buttonConferma.addActionListener(e -> onConferma());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onAnnulla();
            }
        });

        contentPane.registerKeyboardAction(e -> onAnnulla(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void initGraficaBottoni() {
        JButton[] bottoni = new JButton[]{
                buttonAnnulla, buttonConferma
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
                    btn.setBackground(btn.equals(buttonConferma) ? Colori.rosso : Colori.verdeChiaro);
                    btn.setForeground(btn.equals(buttonConferma) ? Colori.bianco : Colori.bluScuro);
                }
            });
        }
    }

    private void onAnnulla() {
        confermato = false;
        dispose();
    }

    private void onConferma() {
        confermato = true;
        dispose();
    }

    public boolean haConfermato() {
        return confermato;
    }

}
