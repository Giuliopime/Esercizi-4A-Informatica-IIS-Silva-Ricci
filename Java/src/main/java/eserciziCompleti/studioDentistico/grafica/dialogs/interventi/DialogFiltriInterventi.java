package eserciziCompleti.studioDentistico.grafica.dialogs.interventi;

import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.oggetti.filtri.FiltriIntervento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DialogFiltriInterventi extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelErrore;
    private JCheckBox cbTipoIntervento;
    private JCheckBox cbCosto;
    private JCheckBox cbPaziente;
    private JCheckBox cbTempo;
    private JCheckBox cbDataIntervento;

    private FiltriIntervento filtriIntervento;

    public DialogFiltriInterventi(FiltriIntervento filtriIntervento) {
        setTitle("Filtri Interventi");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        initListeners();
        initGrafica();

        this.filtriIntervento = filtriIntervento;
        caricaFiltri();

        setMinimumSize(new Dimension(450, 120));
        pack();
        setResizable(false);
        setLocationRelativeTo(GestoreGrafica.getInstance().getFrame());
        setVisible(true);
    }

    private void onOK() {
        filtriIntervento = new FiltriIntervento(cbTipoIntervento.isSelected(), cbCosto.isSelected(), cbTempo.isSelected(), cbPaziente.isSelected(), cbDataIntervento.isSelected());
        if (filtriIntervento.tuttiFalsi()) {
            labelErrore.setIcon(new ImageIcon(getClass().getResource("/studioDentistico/errorIcon.png")));
            labelErrore.setText("Selezionare almeno un campo");
        } else
            dispose();
    }

    private void onCancel() {
        filtriIntervento = null;
        dispose();
    }

    public FiltriIntervento getFiltri() {
        return filtriIntervento;
    }

    private void caricaFiltri() {
        cbTipoIntervento.setSelected(filtriIntervento.isTipo());
        cbPaziente.setSelected(filtriIntervento.isPaziente());
        cbCosto.setSelected(filtriIntervento.isCosto());
        cbTempo.setSelected(filtriIntervento.isTempo());
        cbDataIntervento.setSelected(filtriIntervento.isData());
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
