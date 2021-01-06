package eserciziCompleti.studioDentistico.grafica.panelsComponenti;

import eserciziCompleti.studioDentistico.enums.AzioneDialog;
import eserciziCompleti.studioDentistico.enums.TipoIntervento;
import eserciziCompleti.studioDentistico.enums.TipoPanel;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.gestori.GestoreInterventi;
import eserciziCompleti.studioDentistico.gestori.GestorePazienti;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.grafica.dialogs.DialogIntervento;
import eserciziCompleti.studioDentistico.grafica.dialogs.DialogNessunPaziente;
import eserciziCompleti.studioDentistico.oggetti.Intervento;
import eserciziCompleti.studioDentistico.oggetti.Paziente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
                if (!dialogIntervento.getAzione().equals(AzioneDialog.Niente)) {
                    if (dialogIntervento.getAzione().equals(AzioneDialog.SALVA))
                        GestoreInterventi.getInstance().modificaIntervento(dialogIntervento.getIntervento());
                    else
                        GestoreInterventi.getInstance().eliminaIntervento(dialogIntervento.getIntervento().getIDIntervento());

                    GestoreGrafica.getInstance().changePanel(TipoPanel.INTERVENTI, null);
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
                    new DialogNessunPaziente();
                    return;
                }
                DialogIntervento dialogIntervento = new DialogIntervento("Nuovo Intervento");
                if (dialogIntervento.getAzione().equals(AzioneDialog.SALVA)) {
                    GestoreInterventi.getInstance().aggiungiIntervento(dialogIntervento.getIntervento());
                    GestoreGrafica.getInstance().changePanel(TipoPanel.INTERVENTI, null);
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

}
