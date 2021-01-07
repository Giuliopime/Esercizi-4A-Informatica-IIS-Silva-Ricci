package eserciziCompleti.studioDentistico.grafica.dialogs;

import eserciziCompleti.studioDentistico.enums.ordinamento.OrdinamentoPazienti;
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
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
        try {
            ArrayList<Paziente> pazienti = GestorePazienti.getInstance().getPazienti();
            ArrayList<Intervento> interventi = GestoreInterventi.getInstance().getInterventi();
            ArrayList<Fattura> fatture = GestoreFatture.getInstance().getFatture();

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


            // PAZIENTI
            XSSFSheet sheet = workbook.createSheet("Pazienti");
            int indiceRiga = 0;

            // Headers
            String[] headers = new String[]{
                    "Nome", "Cognome", "Codice Fiscale", "Luogo Di Nascita",
                    "Residenza", "Provincia", "Maggiorenne", "Data di Nascita",
                    "Occupazione", "Sesso", "Numero Telefonico",
            };
            // Creo row headers
            XSSFRow rigaHeaders = sheet.createRow(indiceRiga++);
            // Stile riga headers (testo bold e centrato)
            CellStyle stileHeader = workbook.createCellStyle();
            XSSFFont fontBold = workbook.createFont();
            fontBold.setBold(true);
            stileHeader.setFont(fontBold);
            rigaHeaders.setRowStyle(stileHeader);
            // Metto testo nella riga degli headers e lo centro
            for (int i = 0; i < headers.length; i++) {
                Cell cell = rigaHeaders.createCell(i);
                CellStyle stile = cell.getCellStyle();
                stile.setAlignment(HorizontalAlignment.CENTER);
                cell.setCellValue(headers[i]);
            }

            // Contenuto
            for (int i=0; i<pazienti.size(); i++) {
                Paziente paziente = pazienti.get(i);
                XSSFRow riga = sheet.createRow(indiceRiga++);

                for (int y=0; y<headers.length; y++) {
                    Cell cella = riga.createCell(y);
                    cella.setCellValue(getValorePazientePerTabellaDaIndice(paziente, y));
                }
            }

            // Imposto dimensioni colonne
            for (int i=0; i<headers.length; i++) {
                sheet.autoSizeColumn(i);
            }


            FileOutputStream fileExcel = new FileOutputStream(fileChooser.getSelectedFile().getPath() + "/" + nomeFile + ".xlsx");
            workbook.write(fileExcel);
            fileExcel.close();
            workbook.close();

            new DialogAvviso("File Excel creato", "File Excel creato con successo nella cartella selezionata");
        } catch (IOException e) {
            new DialogAvviso("Errore file Excel", "È avvenuto un errore inaspettato nella creazione del file Excel");
            System.out.println(e);
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
            default -> valore = paziente.getNumTelefono();
        }
        return valore;
    }
}
