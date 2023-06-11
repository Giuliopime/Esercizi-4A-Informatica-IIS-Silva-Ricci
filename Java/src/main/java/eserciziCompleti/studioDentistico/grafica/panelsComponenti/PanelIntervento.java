package eserciziCompleti.studioDentistico.grafica.panelsComponenti;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import eserciziCompleti.studioDentistico.enums.AzioneDialog;
import eserciziCompleti.studioDentistico.enums.Schermata;
import eserciziCompleti.studioDentistico.enums.TipoIntervento;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.gestori.GestoreInterventi;
import eserciziCompleti.studioDentistico.gestori.GestorePazienti;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.grafica.dialogs.DialogAvviso;
import eserciziCompleti.studioDentistico.grafica.dialogs.interventi.DialogIntervento;
import eserciziCompleti.studioDentistico.oggetti.Intervento;
import eserciziCompleti.studioDentistico.oggetti.Paziente;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PanelIntervento {
    private JPanel pannelloTesto;
    private JLabel fieldTipoIntervento;
    private JPanel panelIntervento;
    private JPanel panelTestoPaziente;
    private JPanel panelTestoIntervento;
    private JPanel panelTestoTempo;
    private JLabel fieldNomePaziente;
    private JLabel fieldCosto;
    private JLabel fieldTempo;
    private JLabel labelImmagine;
    private JPanel panelInformazioni;
    private JPanel panelImmagine;

    private Intervento intervento;
    private TipoIntervento tipoIntervento;
    private Color colore;

    public PanelIntervento(Intervento intervento) {
        this.intervento = intervento;
        initGrafica();
    }

    public PanelIntervento() {
        initGraficaNewIntervento();
    }

    private void initGrafica() {
        panelImmagine.setVisible(false);
        tipoIntervento = intervento.getTipoIntervento();

        fieldTipoIntervento.setText(intervento.getTipoIntervento().nome);
        Paziente pazienteIntervento = GestorePazienti.getInstance().getPaziente(intervento.getIDPaziente());
        String nomePaziente = pazienteIntervento.getCognome() + " " + pazienteIntervento.getNome();
        if (nomePaziente.length() >= 19)
            nomePaziente = nomePaziente.replace(nomePaziente.substring(16, 19), "...");
        fieldNomePaziente.setText(nomePaziente);
        fieldCosto.setText("€ " + intervento.getCosto());

        long ore = TimeUnit.MILLISECONDS.toHours(intervento.getTempoMedio());
        long minuti = TimeUnit.MILLISECONDS.toMinutes(intervento.getTempoMedio() - (ore * 3600000));
        String tempo = ore != 0 ? ore + (ore > 1 ? " ore" : " ora") : "";
        tempo += ore != 0 ? " e" : "";
        tempo += minuti == 1 ? "d 1 minuto" : " " + minuti + " minuti";
        fieldTempo.setText(tempo);

        switch (tipoIntervento) {
            case CARIE -> colore = Colori.intervento1;
            case ESTRAZIONE -> colore = Colori.intervento2;
            case APPARECCHIO -> colore = Colori.intervento3;
            case CONTROLLO -> colore = Colori.intervento4;
            default -> colore = Colori.intervento5;
        }

        pannelloTesto.setBackground(colore);

        panelIntervento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DialogIntervento dialogIntervento = new DialogIntervento("Modifica Intervento", intervento);
                if (!dialogIntervento.getAzione().equals(AzioneDialog.NIENTE)) {
                    if (dialogIntervento.getAzione().equals(AzioneDialog.SALVA))
                        GestoreInterventi.getInstance().modifica(dialogIntervento.getIntervento());
                    else
                        GestoreInterventi.getInstance().elimina(dialogIntervento.getIntervento().getIDIntervento());

                    GestoreGrafica.getInstance().changePanel(Schermata.INTERVENTI, null);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                panelIntervento.setCursor(new Cursor(Cursor.HAND_CURSOR));
                pannelloTesto.setBackground(Colori.verdeChiaroHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseEntered(e);
                panelIntervento.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                pannelloTesto.setBackground(colore);
            }
        });
    }

    private void initGraficaNewIntervento() {
        fieldTipoIntervento.setText("Crea Intervento");
        pannelloTesto.setBackground(Colori.verdeChiaro);
        panelInformazioni.setVisible(false);

        panelImmagine.setVisible(true);
        labelImmagine.setIcon(new ImageIcon(getClass().getResource("/studioDentistico/new.png")));

        panelIntervento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (GestorePazienti.getInstance().getPazienti().size() == 0) {
                    new DialogAvviso("Nessun paziente", "Devi prima creare un paziente");
                    return;
                }
                DialogIntervento dialogIntervento = new DialogIntervento("Nuovo Intervento");
                if (dialogIntervento.getAzione().equals(AzioneDialog.SALVA)) {
                    GestoreInterventi.getInstance().aggiungi(dialogIntervento.getIntervento());
                    GestoreGrafica.getInstance().changePanel(Schermata.INTERVENTI, null);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                panelIntervento.setCursor(new Cursor(Cursor.HAND_CURSOR));
                pannelloTesto.setBackground(Colori.verdeChiaroHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseEntered(e);
                panelIntervento.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                pannelloTesto.setBackground(Colori.verdeChiaro);
            }
        });
    }

    public JPanel getPanelIntervento() {
        return panelIntervento;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelIntervento = new JPanel();
        panelIntervento.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelIntervento.setBackground(new Color(-1052173));
        panelIntervento.setEnabled(true);
        panelIntervento.setMaximumSize(new Dimension(400, 300));
        panelIntervento.setMinimumSize(new Dimension(150, 100));
        panelIntervento.setPreferredSize(new Dimension(150, 200));
        pannelloTesto = new JPanel();
        pannelloTesto.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        pannelloTesto.setBackground(new Color(-1052173));
        pannelloTesto.setEnabled(true);
        pannelloTesto.setForeground(new Color(-16777216));
        panelIntervento.add(pannelloTesto, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 40), new Dimension(-1, 40), new Dimension(-1, 40), 0, false));
        fieldTipoIntervento = new JLabel();
        Font fieldTipoInterventoFont = this.$$$getFont$$$("Heiti SC", Font.BOLD, 17, fieldTipoIntervento.getFont());
        if (fieldTipoInterventoFont != null) fieldTipoIntervento.setFont(fieldTipoInterventoFont);
        fieldTipoIntervento.setForeground(new Color(-15919071));
        fieldTipoIntervento.setText("Intervento");
        pannelloTesto.add(fieldTipoIntervento, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelInformazioni = new JPanel();
        panelInformazioni.setLayout(new GridLayoutManager(3, 1, new Insets(5, 5, 5, 5), -1, -1));
        panelInformazioni.setBackground(new Color(-1052173));
        panelIntervento.add(panelInformazioni, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelTestoPaziente = new JPanel();
        panelTestoPaziente.setLayout(new GridLayoutManager(4, 1, new Insets(5, 0, 5, 0), -1, -1));
        panelTestoPaziente.setBackground(new Color(-1052173));
        panelInformazioni.add(panelTestoPaziente, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-15919071));
        label1.setText("Paziente:");
        panelTestoPaziente.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldNomePaziente = new JLabel();
        Font fieldNomePazienteFont = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, fieldNomePaziente.getFont());
        if (fieldNomePazienteFont != null) fieldNomePaziente.setFont(fieldNomePazienteFont);
        fieldNomePaziente.setForeground(new Color(-15919071));
        fieldNomePaziente.setText("Nome Paziente");
        panelTestoPaziente.add(fieldNomePaziente, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final Spacer spacer1 = new Spacer();
        panelTestoPaziente.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panelTestoPaziente.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelTestoIntervento = new JPanel();
        panelTestoIntervento.setLayout(new GridLayoutManager(4, 1, new Insets(5, 0, 5, 0), -1, -1));
        panelTestoIntervento.setBackground(new Color(-1052173));
        panelInformazioni.add(panelTestoIntervento, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 16, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-15919071));
        label2.setText("Costo Intervento:");
        panelTestoIntervento.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldCosto = new JLabel();
        Font fieldCostoFont = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, fieldCosto.getFont());
        if (fieldCostoFont != null) fieldCosto.setFont(fieldCostoFont);
        fieldCosto.setForeground(new Color(-15919071));
        fieldCosto.setText("Costo Intervento");
        panelTestoIntervento.add(fieldCosto, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final Spacer spacer3 = new Spacer();
        panelTestoIntervento.add(spacer3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panelTestoIntervento.add(spacer4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelTestoTempo = new JPanel();
        panelTestoTempo.setLayout(new GridLayoutManager(4, 1, new Insets(5, 0, 5, 0), -1, -1));
        panelTestoTempo.setBackground(new Color(-1052173));
        panelInformazioni.add(panelTestoTempo, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 16, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-15919071));
        label3.setText("Tempo Intervento:");
        panelTestoTempo.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldTempo = new JLabel();
        Font fieldTempoFont = this.$$$getFont$$$("Heiti SC", Font.BOLD, 14, fieldTempo.getFont());
        if (fieldTempoFont != null) fieldTempo.setFont(fieldTempoFont);
        fieldTempo.setForeground(new Color(-15919071));
        fieldTempo.setText("Tempo Intervento");
        panelTestoTempo.add(fieldTempo, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final Spacer spacer5 = new Spacer();
        panelTestoTempo.add(spacer5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panelTestoTempo.add(spacer6, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelImmagine = new JPanel();
        panelImmagine.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelImmagine.setBackground(new Color(-1052173));
        panelIntervento.add(panelImmagine, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelImmagine = new JLabel();
        labelImmagine.setText("");
        panelImmagine.add(labelImmagine, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelIntervento;
    }
}
