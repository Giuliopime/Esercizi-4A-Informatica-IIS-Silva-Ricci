package eserciziCompleti.studioDentistico.grafica;

import eserciziCompleti.studioDentistico.enums.Schermata;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.gestori.GestoreImpostazioni;
import eserciziCompleti.studioDentistico.grafica.dialogs.DialogImpostazioni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavBarUtil {
    public static void initNavBar(JButton[] bottoniNav, JLabel scrittaLogo, JLabel labelImpostazioni) {
        scrittaLogo.setText("Studio Dentistico " + GestoreImpostazioni.getInstance().getImpostazioni().getNomeStudio());

        scrittaLogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                GestoreGrafica.getInstance().changePanel(Schermata.HOME, null);
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                scrittaLogo.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                scrittaLogo.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        labelImpostazioni.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DialogImpostazioni dialogImpostazioni = new DialogImpostazioni();
                if(dialogImpostazioni.getImpostazioni() != null) {
                    GestoreImpostazioni.getInstance().modificaImpostazioni(dialogImpostazioni.getImpostazioni());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelImpostazioni.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelImpostazioni.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        for (JButton btn : bottoniNav) {
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    btn.setBackground(Colori.verdeChiaroHover);
                    btn.setForeground(Colori.bianco);
                }

                public void mouseExited(MouseEvent evt) {
                    btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    btn.setBackground(Colori.verdeChiaro);
                    btn.setForeground(Colori.bluScuro);
                }
            });
        }

        bottoniNav[0].addActionListener(e -> GestoreGrafica.getInstance().changePanel(Schermata.PAZIENTI, null));
        bottoniNav[1].addActionListener(e -> GestoreGrafica.getInstance().changePanel(Schermata.INTERVENTI, null));
        bottoniNav[2].addActionListener(e -> GestoreGrafica.getInstance().changePanel(Schermata.FATTURE, null));
    }
}
