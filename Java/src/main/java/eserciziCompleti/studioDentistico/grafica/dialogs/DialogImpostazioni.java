package eserciziCompleti.studioDentistico.grafica.dialogs;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import eserciziCompleti.studioDentistico.gestori.*;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.oggetti.Fattura;
import eserciziCompleti.studioDentistico.oggetti.Impostazioni;
import eserciziCompleti.studioDentistico.oggetti.Intervento;
import eserciziCompleti.studioDentistico.oggetti.Paziente;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
        if (tfNomeStudio.getText().isBlank()) {
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
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int scelta = fileChooser.showSaveDialog(this);

            if (scelta != JFileChooser.APPROVE_OPTION)
                return;

            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN);
            String nomeFile = "Studio Dentistico " + impostazioni.getNomeStudio() + " (" + dateFormat.format(new Date()) + ")";

            XSSFWorkbook workbook = new XSSFWorkbook();
            workbook.getProperties().getCoreProperties().setCreator("Gestionale Studio Dentistico by Giulio Pimenoff Verdolin");
            workbook.getProperties().getExtendedProperties().setApplication("Gestionale Studio Dentistico " + impostazioni.getNomeStudio());

            creaSheetPazienti(workbook);
            creaSheetInterventi(workbook);
            creaSheetFatture(workbook);

            FileOutputStream fileExcel = new FileOutputStream(fileChooser.getSelectedFile().getPath() + "/" + nomeFile + ".xlsx");
            workbook.write(fileExcel);
            fileExcel.close();
            workbook.close();

            new DialogAvviso("File Excel creato", "File Excel creato con successo nella cartella selezionata");
        } catch (IOException e) {
            new DialogAvviso("Errore file Excel", "È avvenuto un errore inaspettato nella creazione del file Excel");
            System.out.println(e.getMessage());
        }
    }

    private void creaSheetPazienti(XSSFWorkbook workbook) {
        ArrayList<Paziente> pazienti = GestorePazienti.getInstance().getPazienti();

        // PAZIENTI
        XSSFSheet sheet = workbook.createSheet("Pazienti");
        int indiceRiga = 0;

        // Headers
        String[] headers = new String[]{
                "Nome", "Cognome", "Codice Fiscale", "Luogo Di Nascita",
                "Residenza", "Provincia", "Maggiorenne", "Data di Nascita",
                "Occupazione", "Sesso", "Numero Telefonico", "ID Paziente"
        };
        // Creo row headers
        XSSFRow rigaHeaders = sheet.createRow(indiceRiga++);
        // Stile riga headers (testo bold e centrato)
        CellStyle stileHeader = workbook.createCellStyle();
        XSSFFont fontBold = workbook.createFont();
        fontBold.setBold(true);
        stileHeader.setFont(fontBold);
        stileHeader.setAlignment(HorizontalAlignment.CENTER);
        rigaHeaders.setRowStyle(stileHeader);
        // Metto testo nella riga degli headers e lo centro
        for (int i = 0; i < headers.length; i++) {
            Cell cell = rigaHeaders.createCell(i);
            CellStyle stile = cell.getCellStyle();
            stile.setAlignment(HorizontalAlignment.CENTER);
            cell.setCellValue(headers[i]);
        }

        // Contenuto
        for (int i = 0; i < pazienti.size(); i++) {
            Paziente paziente = pazienti.get(i);
            XSSFRow riga = sheet.createRow(indiceRiga++);

            for (int y = 0; y < headers.length; y++) {
                Cell cella = riga.createCell(y);
                cella.getCellStyle().setAlignment(HorizontalAlignment.LEFT);
                cella.setCellValue(getValorePazientePerTabellaDaIndice(paziente, y));
            }
        }

        // Imposto dimensioni colonne
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void creaSheetInterventi(XSSFWorkbook workbook) {
        ArrayList<Intervento> interventi = GestoreInterventi.getInstance().getInterventi();

        // PAZIENTI
        XSSFSheet sheet = workbook.createSheet("Interventi");
        int indiceRiga = 0;

        // Headers
        String[] headers = new String[]{
                "Tipo Intervento", "Paziente", "Costo (EURO)", "Tempo medio",
                "Data Intervento", "Data Ultima Modifica", "ID Paziente", "ID Intervento"
        };
        // Creo row headers
        XSSFRow rigaHeaders = sheet.createRow(indiceRiga++);
        // Stile riga headers (testo bold e centrato)
        CellStyle stileHeader = workbook.createCellStyle();
        XSSFFont fontBold = workbook.createFont();
        fontBold.setBold(true);
        stileHeader.setFont(fontBold);
        stileHeader.setAlignment(HorizontalAlignment.CENTER);
        rigaHeaders.setRowStyle(stileHeader);
        // Metto testo nella riga degli headers e lo centro
        for (int i = 0; i < headers.length; i++) {
            Cell cell = rigaHeaders.createCell(i);
            CellStyle stile = cell.getCellStyle();
            stile.setAlignment(HorizontalAlignment.CENTER);
            cell.setCellValue(headers[i]);
        }

        // Contenuto
        for (int i = 0; i < interventi.size(); i++) {
            Intervento intervento = interventi.get(i);
            XSSFRow riga = sheet.createRow(indiceRiga++);

            for (int y = 0; y < headers.length; y++) {
                Cell cella = riga.createCell(y);
                cella.getCellStyle().setAlignment(HorizontalAlignment.LEFT);
                cella.setCellValue(getValoreInterventoPerTabellaDaIndice(intervento, y));
            }
        }

        // Imposto dimensioni colonne
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void creaSheetFatture(XSSFWorkbook workbook) {
        ArrayList<Fattura> fatture = GestoreFatture.getInstance().getFatture();

        // PAZIENTI
        XSSFSheet sheet = workbook.createSheet("Fatture");
        int indiceRiga = 0;

        // Headers
        String[] headers = new String[]{
                "Paziente", "Interventi", "Costo totale (EURO)", "Data Fattura",
                "ID Paziente", "ID Interventi"
        };
        // Creo row headers
        XSSFRow rigaHeaders = sheet.createRow(indiceRiga++);
        // Stile riga headers (testo bold e centrato)
        CellStyle stileHeader = workbook.createCellStyle();
        XSSFFont fontBold = workbook.createFont();
        fontBold.setBold(true);
        stileHeader.setFont(fontBold);
        stileHeader.setAlignment(HorizontalAlignment.CENTER);
        rigaHeaders.setRowStyle(stileHeader);
        // Metto testo nella riga degli headers e lo centro
        for (int i = 0; i < headers.length; i++) {
            Cell cell = rigaHeaders.createCell(i);
            CellStyle stile = cell.getCellStyle();
            stile.setAlignment(HorizontalAlignment.CENTER);
            cell.setCellValue(headers[i]);
        }

        // Contenuto
        for (int i = 0; i < fatture.size(); i++) {
            Fattura fattura = fatture.get(i);
            XSSFRow riga = sheet.createRow(indiceRiga++);

            for (int y = 0; y < headers.length; y++) {
                Cell cella = riga.createCell(y);
                cella.getCellStyle().setAlignment(HorizontalAlignment.LEFT);
                cella.setCellValue(getValoreFatturaPerTabellaDaIndice(fattura, y));
            }
        }

        // Imposto dimensioni colonne
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private String getValorePazientePerTabellaDaIndice(Paziente paziente, int indice) {
        String valore;
        switch (indice) {
            case 0 -> valore = paziente.getNome();
            case 1 -> valore = paziente.getCognome();
            case 2 -> valore = paziente.getCodiceFiscale();
            case 3 -> valore = paziente.getLuogoNascita();
            case 4 -> valore = paziente.getResidenza();
            case 5 -> valore = paziente.getProvincia();
            case 6 -> valore = paziente.isMaggiorenne() ? "Si" : "No";
            case 7 -> valore = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN).format(paziente.getDataNascita());
            case 8 -> valore = paziente.getOccupazione();
            case 9 -> valore = paziente.getSesso();
            case 10 -> valore = paziente.getNumTelefono();
            default -> valore = paziente.getIDPaziente().toString();
        }
        return valore;
    }

    private String getValoreInterventoPerTabellaDaIndice(Intervento intervento, int indice) {
        String valore;
        switch (indice) {
            case 0 -> valore = intervento.getTipoIntervento().nome;
            case 1 -> valore = GestorePazienti.getInstance().getPaziente(intervento.getIDPaziente()).getCognome() + " " + GestorePazienti.getInstance().getPaziente(intervento.getIDPaziente()).getNome();
            case 2 -> valore = "" + intervento.getCosto();
            case 3 -> {
                long ore = TimeUnit.MILLISECONDS.toHours(intervento.getTempoMedio());
                long minuti = TimeUnit.MILLISECONDS.toMinutes(intervento.getTempoMedio() - (ore * 3600000));
                String tempo = ore != 0 ? ore + (ore > 1 ? " ore" : " ora") : "";
                tempo += ore != 0 ? " e" : "";
                tempo += minuti == 1 ? "d 1 minuto" : " " + minuti + " minuti";
                valore = tempo;
            }
            case 4 -> valore = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN).format(intervento.getDataCreazione());
            case 5 -> valore = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN).format(intervento.getUltimaModifica());
            case 6 -> valore = intervento.getIDPaziente().toString();
            default -> valore = intervento.getIDIntervento().toString();
        }
        return valore;
    }

    private String getValoreFatturaPerTabellaDaIndice(Fattura fattura, int indice) {
        String valore = "";
        String[] headers = new String[]{
                "Paziente", "Interventi", "Costo totale (EURO)", "Data Fattura",
                "ID Paziente", "ID Interventi"
        };
        switch (indice) {
            case 0 -> valore = GestorePazienti.getInstance().getPaziente(fattura.getIDPaziente()).getCognome() + " " + GestorePazienti.getInstance().getPaziente(fattura.getIDPaziente()).getNome();
            case 1 -> {
                String interventi = "";
                for (UUID idIntervento : fattura.getInterventi())
                    interventi += GestoreInterventi.getInstance().getIntervento(idIntervento).getTipoIntervento().nome + " + ";

                valore = interventi.substring(0, interventi.length() - 3);
            }
            case 2 -> {
                double prezzoComplessivo = 0;
                for (UUID idIntervento : fattura.getInterventi())
                    prezzoComplessivo += GestoreInterventi.getInstance().getIntervento(idIntervento).getCosto();

                valore = "" + prezzoComplessivo;
            }
            case 3 -> valore = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN).format(fattura.getData());
            case 4 -> valore = fattura.getIDPaziente().toString();
            default -> {
                String IDInterventi = "";
                for (UUID idIntervento : fattura.getInterventi())
                    IDInterventi += GestoreInterventi.getInstance().getIntervento(idIntervento).getIDIntervento() + " / ";

                valore = IDInterventi.substring(0, IDInterventi.length() - 3);
            }
        }

        return valore;
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
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(7, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tfNomeStudio = new JTextField();
        tfNomeStudio.setToolTipText("");
        panel1.add(tfNomeStudio, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Nome Studio");
        panel1.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel2, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel2.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setBackground(new Color(-7070673));
        buttonCancel.setBorderPainted(false);
        buttonCancel.setContentAreaFilled(true);
        buttonCancel.setEnabled(true);
        buttonCancel.setFocusPainted(false);
        buttonCancel.setFocusable(true);
        Font buttonCancelFont = this.$$$getFont$$$(null, Font.BOLD, 14, buttonCancel.getFont());
        if (buttonCancelFont != null) buttonCancel.setFont(buttonCancelFont);
        buttonCancel.setForeground(new Color(-1052173));
        buttonCancel.setHideActionText(false);
        buttonCancel.setHorizontalAlignment(0);
        buttonCancel.setInheritsPopupMenu(false);
        buttonCancel.setLabel("Annulla");
        buttonCancel.setOpaque(true);
        buttonCancel.setRequestFocusEnabled(true);
        buttonCancel.setRolloverEnabled(false);
        buttonCancel.setSelected(false);
        buttonCancel.setText("Annulla");
        buttonCancel.setVisible(true);
        panel3.add(buttonCancel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setBackground(new Color(-14503271));
        buttonOK.setBorderPainted(false);
        buttonOK.setContentAreaFilled(true);
        buttonOK.setEnabled(true);
        buttonOK.setFocusPainted(false);
        buttonOK.setFocusable(true);
        Font buttonOKFont = this.$$$getFont$$$(null, Font.BOLD, 14, buttonOK.getFont());
        if (buttonOKFont != null) buttonOK.setFont(buttonOKFont);
        buttonOK.setForeground(new Color(-15919071));
        buttonOK.setHideActionText(false);
        buttonOK.setHorizontalAlignment(0);
        buttonOK.setInheritsPopupMenu(false);
        buttonOK.setLabel("Salva");
        buttonOK.setOpaque(true);
        buttonOK.setRequestFocusEnabled(true);
        buttonOK.setRolloverEnabled(false);
        buttonOK.setSelected(false);
        buttonOK.setText("Salva");
        buttonOK.setVisible(true);
        panel3.add(buttonOK, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelErrore = new JLabel();
        Font labelErroreFont = this.$$$getFont$$$(null, Font.BOLD, 14, labelErrore.getFont());
        if (labelErroreFont != null) labelErrore.setFont(labelErroreFont);
        labelErrore.setText("");
        panel2.add(labelErrore, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Heiti SC", Font.BOLD, 22, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Impostazioni");
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        contentPane.add(spacer4, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 30), null, 0, false));
        final Spacer spacer5 = new Spacer();
        contentPane.add(spacer5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        btnEsporta = new JButton();
        btnEsporta.setBackground(new Color(-15919071));
        btnEsporta.setBorderPainted(false);
        btnEsporta.setContentAreaFilled(true);
        btnEsporta.setEnabled(true);
        btnEsporta.setFocusPainted(false);
        btnEsporta.setFocusable(true);
        Font btnEsportaFont = this.$$$getFont$$$(null, Font.BOLD, 14, btnEsporta.getFont());
        if (btnEsportaFont != null) btnEsporta.setFont(btnEsportaFont);
        btnEsporta.setForeground(new Color(-1052173));
        btnEsporta.setHideActionText(false);
        btnEsporta.setHorizontalAlignment(0);
        btnEsporta.setInheritsPopupMenu(false);
        btnEsporta.setLabel("Esporta Dati su Excel");
        btnEsporta.setOpaque(true);
        btnEsporta.setRequestFocusEnabled(true);
        btnEsporta.setRolloverEnabled(false);
        btnEsporta.setSelected(false);
        btnEsporta.setText("Esporta Dati su Excel");
        btnEsporta.setVisible(true);
        panel5.add(btnEsporta, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        final Spacer spacer6 = new Spacer();
        contentPane.add(spacer6, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 10), null, 0, false));
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
        return contentPane;
    }
}
