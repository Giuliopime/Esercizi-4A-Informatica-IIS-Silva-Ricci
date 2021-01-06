package eserciziCompleti.studioDentistico.grafica.dialogs.fatture;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import eserciziCompleti.studioDentistico.gestori.*;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.grafica.dialogs.ConfermaUscita;
import eserciziCompleti.studioDentistico.oggetti.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.swing.*;
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
            DatiStampaFattura datiStampaFattura = new DatiStampaFattura(fatture.get(cbFatture.getSelectedIndex()), tfNumFattura.getText(), cbIVA.isSelected() ,taDescrizione.getText());
            Impostazioni impostazioni = GestoreImpostazioni.getInstance().getImpostazioni();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int scelta = fileChooser.showSaveDialog(this);

            if(scelta != JFileChooser.APPROVE_OPTION)
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

            if(!datiStampaFattura.getDescrizioneIntervento().isBlank()) {
                chunk3.addCell("Descrizione Generale");
                chunk3.addCell(datiStampaFattura.getDescrizioneIntervento());
            }

            double costoInterventi = 0;
            for(int i=0; i<datiStampaFattura.getInterventi().size(); i++) {
                chunk3.addCell((i+1) + "° intervento");
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

            if(cbIVA.isSelected()) {
                chunk4.addCell("IVA");
                chunk4.addCell("" + costoInterventi / 100 * 22);
            }

            chunk4.addCell("Totale Fattura");
            chunk4.addCell("" + (costoInterventi + (cbIVA.isSelected() ? (costoInterventi / 100 * 22) : 0)));

            document.add(chunk4);
            document.close();

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
}
