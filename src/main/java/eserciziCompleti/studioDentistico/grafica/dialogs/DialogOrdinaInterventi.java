package eserciziCompleti.studioDentistico.grafica.dialogs;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import eserciziCompleti.studioDentistico.enums.OrdinamentoInterventi;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.grafica.Colori;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class DialogOrdinaInterventi extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelErrore;
    private JRadioButton rbDataCreazione;
    private JRadioButton rbPaziente;
    private JRadioButton rbUltimaModifica;
    private JRadioButton rbNessuno;
    private JRadioButton rbTempo;
    private JRadioButton rbCosto;
    private JRadioButton rbTipo;

    private OrdinamentoInterventi ordinamentoInterventi;

    public DialogOrdinaInterventi(OrdinamentoInterventi ordinamentoInterventi) {
        this.ordinamentoInterventi = ordinamentoInterventi;
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
        if (ordinamentoInterventi == null)
            rbNessuno.setSelected(true);

        else {
            switch (ordinamentoInterventi) {
                case PAZIENTE -> rbPaziente.setSelected(true);
                case TEMPO -> rbTempo.setSelected(true);
                case COSTO -> rbCosto.setSelected(true);
                case DATAULTIMAMODIFICA -> rbUltimaModifica.setSelected(true);
                case DATACREAZIONE -> rbDataCreazione.setSelected(true);
                case TIPOINTERVENTO -> rbTipo.setSelected(true);
                default -> rbNessuno.setSelected(true);
            }
        }
    }

    private void onOK() {
        ordinamentoInterventi = rbTempo.isSelected() ? OrdinamentoInterventi.TEMPO :
                rbUltimaModifica.isSelected() ? OrdinamentoInterventi.DATAULTIMAMODIFICA :
                        rbPaziente.isSelected() ? OrdinamentoInterventi.PAZIENTE :
                                rbCosto.isSelected() ? OrdinamentoInterventi.COSTO :
                                        rbDataCreazione.isSelected() ? OrdinamentoInterventi.DATACREAZIONE :
                                                rbTipo.isSelected() ? OrdinamentoInterventi.TIPOINTERVENTO :
                                                        null;
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public OrdinamentoInterventi getOrdinamentoInterventi() {
        return ordinamentoInterventi;
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
