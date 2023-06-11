package eserciziCompleti.studioDentistico.grafica.dialogs.fatture;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import eserciziCompleti.studioDentistico.gestori.*;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.grafica.dialogs.ConfermaUscita;
import eserciziCompleti.studioDentistico.grafica.dialogs.DialogAvviso;
import eserciziCompleti.studioDentistico.oggetti.*;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;

public class DialogStampaFattura extends JDialog {
    private JPanel contentPane;
    private JButton buttonStampa;
    private JButton buttonCancella;
    private JLabel labelErrore;
    private JComboBox cbFatture;
    private JTextField tfNumFattura;
    private JTextArea taDescrizione;
    private JCheckBox cbIVA;

    private ArrayList<Fattura> fatture;

    public DialogStampaFattura() {
        $$$setupUI$$$();
        initDialog();
    }

    private void initDialog() {
        setTitle("Stampa Fattura");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonStampa);

        initGraficaBottoni();
        initListeners();

        setMinimumSize(new Dimension(600, 200));
        pack();
        setLocationRelativeTo(GestoreGrafica.getInstance().getFrame());
        setVisible(true);
    }

    private void initGraficaBottoni() {
        JButton[] bottoni = new JButton[]{
                buttonCancella, buttonStampa
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
                    btn.setBackground(btn.equals(buttonCancella) ? Colori.rosso : Colori.verdeChiaro);
                    btn.setForeground(btn.equals(buttonCancella) ? Colori.bianco : Colori.bluScuro);
                }
            });
        }
    }

    private void initListeners() {
        buttonStampa.addActionListener(e -> onStampa());

        buttonCancella.addActionListener(e -> onCancella());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancella();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancella(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onStampa() {
        try {
            DatiStampaFattura datiStampaFattura = new DatiStampaFattura(fatture.get(cbFatture.getSelectedIndex()), tfNumFattura.getText(), cbIVA.isSelected(), taDescrizione.getText());
            Impostazioni impostazioni = GestoreImpostazioni.getInstance().getImpostazioni();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int scelta = fileChooser.showSaveDialog(this);

            if (scelta != JFileChooser.APPROVE_OPTION)
                return;

            File fileFattura = new File(fileChooser.getSelectedFile().getPath() + "/" + datiStampaFattura.getNomeFileFattura());

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileFattura));

            document.open();
            Font fontTitoloGrande = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
            Font fontTitolo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
            Font fontTesto = FontFactory.getFont(FontFactory.COURIER, 13, BaseColor.BLACK);

            Chunk chunk = new Chunk("Studio Dentistico " + impostazioni.getNomeStudio(), fontTitoloGrande);
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);
            chapter.add(new Paragraph("Fattura N° " + datiStampaFattura.getNumeroFattura(), fontTesto));

            chapter.add(Chunk.NEWLINE);
            document.add(chapter);
            document.add(Chunk.NEWLINE);


            String destinatario = datiStampaFattura.getPaziente().getCognome() + " " + datiStampaFattura.getPaziente().getNome()
                    + "\n" + datiStampaFattura.getPaziente().getResidenza() + " (" + datiStampaFattura.getPaziente().getProvincia() + ")"
                    + "\n" + datiStampaFattura.getPaziente().getCodiceFiscale();

            Paragraph paragraph = new Paragraph();
            paragraph.add(new Chunk("Destinatario: ", fontTitolo));
            paragraph.add(Chunk.NEWLINE);
            paragraph.add(new Chunk(destinatario, fontTesto));
            document.add(paragraph);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);


            // Tabella Interventi
            PdfPTable chunk3 = new PdfPTable(2);
            // Header
            Stream.of("Intervento", "Descrizione").forEach(stringa -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(stringa));
                chunk3.addCell(header);
            });

            if (!datiStampaFattura.getDescrizioneIntervento().isBlank()) {
                chunk3.addCell("Descrizione Generale");
                chunk3.addCell(datiStampaFattura.getDescrizioneIntervento());
            }

            double costoInterventi = 0;
            for (int i = 0; i < datiStampaFattura.getInterventi().size(); i++) {
                chunk3.addCell((i + 1) + "° intervento");
                chunk3.addCell(datiStampaFattura.getInterventi().get(i).getTipoIntervento().nome);
                costoInterventi += datiStampaFattura.getInterventi().get(i).getCosto();
            }

            document.add(chunk3);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);


            PdfPTable chunk4 = new PdfPTable(2);
            Stream.of("", "Euro").forEach(stringa -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(stringa));
                chunk4.addCell(header);
            });

            chunk4.addCell("Imponibile");
            chunk4.addCell("" + costoInterventi);

            if (cbIVA.isSelected()) {
                chunk4.addCell("IVA");
                chunk4.addCell("" + costoInterventi / 100 * 22);
            }

            chunk4.addCell("Totale Fattura");
            chunk4.addCell("" + (costoInterventi + (cbIVA.isSelected() ? (costoInterventi / 100 * 22) : 0)));

            document.add(chunk4);
            document.close();
            new DialogAvviso("Fattura Stampata", "Fattura creata con successo come file pdf nella cartella selezionata");

            dispose();
        } catch (IllegalArgumentException e) {
            labelErrore.setIcon(new ImageIcon(getClass().getResource("/studioDentistico/errorIcon.png")));
            labelErrore.setText(e.getMessage());
        } catch (DocumentException | IOException e) {
            labelErrore.setIcon(new ImageIcon(getClass().getResource("/studioDentistico/errorIcon.png")));
            labelErrore.setText("È avvenuto un errore inaspettato");
            e.printStackTrace();
        }
    }

    private void onCancella() {
        ConfermaUscita uscitaDialog = new ConfermaUscita("Conferma uscita", "Sei sicuro di voler uscire?");
        if (uscitaDialog.haConfermato())
            dispose();

    }

    private void createUIComponents() {
        fatture = GestoreFatture.getInstance().getFatture();

        cbFatture = new JComboBox();

        for (Fattura fattura : fatture) {
            String testoFattura = "";
            Paziente pazienteFattura = GestorePazienti.getInstance().getPaziente(fattura.getIDPaziente());
            testoFattura += pazienteFattura.getCognome() + " " + pazienteFattura.getNome() + "     ";

            double prezzoComplessivo = 0;
            for (UUID idIntervento : fattura.getInterventi()) {
                Intervento intervento = GestoreInterventi.getInstance().getIntervento(idIntervento);
                testoFattura += intervento.getTipoIntervento().nome + " + ";
                prezzoComplessivo += intervento.getCosto();
            }

            testoFattura = testoFattura.substring(0, testoFattura.length() - 3) + "     ";

            testoFattura += prezzoComplessivo + " €     ";

            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN);
            testoFattura += dateFormat.format(fattura.getData());
            cbFatture.addItem(testoFattura);
        }

        cbFatture.setSelectedIndex(0);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(10, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.add(cbFatture, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(300, -1), new Dimension(400, -1), new Dimension(800, -1), 0, false));
        final JLabel label1 = new JLabel();
        java.awt.Font label1Font = this.$$$getFont$$$("Heiti SC", java.awt.Font.BOLD, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Fattura:");
        panel1.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel2, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel2.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonCancella = new JButton();
        buttonCancella.setBackground(new Color(-7070673));
        buttonCancella.setBorderPainted(false);
        buttonCancella.setContentAreaFilled(true);
        buttonCancella.setEnabled(true);
        buttonCancella.setFocusPainted(false);
        buttonCancella.setFocusable(true);
        java.awt.Font buttonCancellaFont = this.$$$getFont$$$(null, java.awt.Font.BOLD, 14, buttonCancella.getFont());
        if (buttonCancellaFont != null) buttonCancella.setFont(buttonCancellaFont);
        buttonCancella.setForeground(new Color(-1052173));
        buttonCancella.setHideActionText(false);
        buttonCancella.setHorizontalAlignment(0);
        buttonCancella.setInheritsPopupMenu(false);
        buttonCancella.setLabel("Annulla");
        buttonCancella.setOpaque(true);
        buttonCancella.setRequestFocusEnabled(true);
        buttonCancella.setRolloverEnabled(false);
        buttonCancella.setSelected(false);
        buttonCancella.setText("Annulla");
        buttonCancella.setVisible(true);
        panel3.add(buttonCancella, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonStampa = new JButton();
        buttonStampa.setBackground(new Color(-14503271));
        buttonStampa.setBorderPainted(false);
        buttonStampa.setContentAreaFilled(true);
        buttonStampa.setEnabled(true);
        buttonStampa.setFocusPainted(false);
        buttonStampa.setFocusable(true);
        java.awt.Font buttonStampaFont = this.$$$getFont$$$(null, java.awt.Font.BOLD, 14, buttonStampa.getFont());
        if (buttonStampaFont != null) buttonStampa.setFont(buttonStampaFont);
        buttonStampa.setForeground(new Color(-15919071));
        buttonStampa.setHideActionText(false);
        buttonStampa.setHorizontalAlignment(0);
        buttonStampa.setInheritsPopupMenu(false);
        buttonStampa.setLabel("Stampa");
        buttonStampa.setOpaque(true);
        buttonStampa.setRequestFocusEnabled(true);
        buttonStampa.setRolloverEnabled(false);
        buttonStampa.setSelected(false);
        buttonStampa.setText("Stampa");
        buttonStampa.setVisible(true);
        panel3.add(buttonStampa, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelErrore = new JLabel();
        java.awt.Font labelErroreFont = this.$$$getFont$$$(null, java.awt.Font.BOLD, 14, labelErrore.getFont());
        if (labelErroreFont != null) labelErrore.setFont(labelErroreFont);
        labelErrore.setText("");
        panel2.add(labelErrore, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        java.awt.Font label2Font = this.$$$getFont$$$("Heiti SC", java.awt.Font.BOLD, 22, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Stampa Fattura");
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tfNumFattura = new JTextField();
        tfNumFattura.setText("1");
        panel5.add(tfNumFattura, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        java.awt.Font label3Font = this.$$$getFont$$$("Heiti SC", java.awt.Font.BOLD, 16, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Numero Fattura");
        panel5.add(label3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel5.add(spacer4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel5.add(spacer5, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel6, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        java.awt.Font label4Font = this.$$$getFont$$$("Heiti SC", java.awt.Font.BOLD, 16, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Descrizione Intervento");
        panel6.add(label4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        panel6.add(spacer6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        panel6.add(spacer7, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        taDescrizione = new JTextArea();
        panel6.add(taDescrizione, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, 50), null, 0, false));
        final Spacer spacer8 = new Spacer();
        contentPane.add(spacer8, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final Spacer spacer9 = new Spacer();
        contentPane.add(spacer9, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final Spacer spacer10 = new Spacer();
        contentPane.add(spacer10, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final Spacer spacer11 = new Spacer();
        contentPane.add(spacer11, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel7, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        panel7.add(spacer12, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        panel7.add(spacer13, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        cbIVA = new JCheckBox();
        java.awt.Font cbIVAFont = this.$$$getFont$$$("Heiti SC", java.awt.Font.BOLD, 16, cbIVA.getFont());
        if (cbIVAFont != null) cbIVA.setFont(cbIVAFont);
        cbIVA.setText("Aggiunta IVA automatica");
        panel7.add(cbIVA, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private java.awt.Font $$$getFont$$$(String fontName, int style, int size, java.awt.Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            java.awt.Font testFont = new java.awt.Font(fontName, java.awt.Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        java.awt.Font font = new java.awt.Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        java.awt.Font fontWithFallback = isMac ? new java.awt.Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
