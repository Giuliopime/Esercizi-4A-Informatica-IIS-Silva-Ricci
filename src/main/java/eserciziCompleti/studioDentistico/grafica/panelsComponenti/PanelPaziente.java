package eserciziCompleti.studioDentistico.grafica.panelsComponenti;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import eserciziCompleti.studioDentistico.enums.AzioneDialog;
import eserciziCompleti.studioDentistico.enums.TipoPanel;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.gestori.GestorePazienti;
import eserciziCompleti.studioDentistico.grafica.Colori;
import eserciziCompleti.studioDentistico.grafica.dialogs.DialogPaziente;
import eserciziCompleti.studioDentistico.oggetti.Paziente;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

public class PanelPaziente {
    private JLabel labelNome;
    private JLabel immagine;
    private JPanel panelPaziente;
    private JPanel pannelloTesto;

    private Paziente paziente;
    private boolean maschio;

    public PanelPaziente(Paziente paziente) {
        this.paziente = paziente;
        initGrafica();
    }

    public PanelPaziente() {
        initGraficaNewPaziente();
    }

    private void initGrafica() {
        maschio = paziente.getSesso().toLowerCase().startsWith("m");

        labelNome.setText(paziente.getCognome());
        if (maschio) {
            ImageIcon immagineUomo = new ImageIcon(getClass().getResource("/studioDentistico/manIcon.png"));
            immagine.setIcon(immagineUomo);
            pannelloTesto.setBackground(Colori.azzurro);
        }

        panelPaziente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DialogPaziente dialogPaziente = new DialogPaziente("Modifica Paziente", paziente);
                if (!dialogPaziente.getAzione().equals(AzioneDialog.Niente)) {
                    if (dialogPaziente.getAzione().equals(AzioneDialog.SALVA))
                        GestorePazienti.getInstance().modificaPaziente(dialogPaziente.getPaziente());
                    else
                        GestorePazienti.getInstance().eliminaPaziente(dialogPaziente.getPaziente().getIDPaziente());

                    GestoreGrafica.getInstance().changePanel(TipoPanel.PAZIENTI, null);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                panelPaziente.setCursor(new Cursor(Cursor.HAND_CURSOR));
                pannelloTesto.setBackground(Colori.verdeChiaroHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseEntered(e);
                panelPaziente.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                pannelloTesto.setBackground(maschio ? Colori.azzurro : Colori.rosa);
            }
        });
    }

    private void initGraficaNewPaziente() {
        labelNome.setText("Crea Paziente");
        pannelloTesto.setBackground(Colori.verdeChiaro);
        ImageIcon immagineUomo = new ImageIcon(getClass().getResource("/studioDentistico/new.png"));
        immagine.setIcon(immagineUomo);

        panelPaziente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DialogPaziente dialogPaziente = new DialogPaziente("Nuovo paziente");
                if (dialogPaziente.getAzione().equals(AzioneDialog.SALVA)) {
                    GestorePazienti.getInstance().aggiungiPaziente(dialogPaziente.getPaziente());
                    GestoreGrafica.getInstance().changePanel(TipoPanel.PAZIENTI, null);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                panelPaziente.setCursor(new Cursor(Cursor.HAND_CURSOR));
                pannelloTesto.setBackground(Colori.verdeChiaroHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseEntered(e);
                panelPaziente.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                pannelloTesto.setBackground(Colori.verdeChiaro);
            }
        });
    }

    public JPanel getPanelPaziente() {
        return panelPaziente;
    }

}
