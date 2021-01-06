package eserciziCompleti.studioDentistico.grafica;

import eserciziCompleti.studioDentistico.enums.TipoPanel;
import eserciziCompleti.studioDentistico.gestori.GestoreGrafica;
import eserciziCompleti.studioDentistico.gestori.GestoreImpostazioni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavBarUtil {
    public static void initNavBar(JButton[] bottoniNav, JLabel scrittaLogo) {
        scrittaLogo.setText("Studio Dentistico " + GestoreImpostazioni.getInstance().getImpostazioni().getNomeStudio());

        scrittaLogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                GestoreGrafica.getInstance().changePanel(TipoPanel.HOME, null);
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

        bottoniNav[0].addActionListener(e -> GestoreGrafica.getInstance().changePanel(TipoPanel.PAZIENTI, null));
        bottoniNav[1].addActionListener(e -> GestoreGrafica.getInstance().changePanel(TipoPanel.INTERVENTI, null));
        bottoniNav[2].addActionListener(e -> GestoreGrafica.getInstance().changePanel(TipoPanel.FATTURE, null));
    }
}
