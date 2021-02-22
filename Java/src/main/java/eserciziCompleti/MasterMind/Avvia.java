/*
Questa è la classe che viene utilizzata esclusivamente per avviare il programma.
I file .form non sono modificabili su IntelliJ perchè la grafica è stata creata con NetBeans.
 */

package eserciziCompleti.MasterMind;

import javax.swing.*;

public class Avvia {
    public static void main(String[] args) {
        //<editor-fold defaultState="collapsed" desc="Imposto il look and feel">
        try {
            // Codice generato da NetBeans con cui imposto il look and feel al programma per avere una grafica uguale su qualsiasi pc
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, "A causa di un errore nel settare il \"look  and feel\" del programma, qualche componente grafico potrebbe non essere visualizzato correttamente.");
            System.out.println("Errore nell'impostare il look and feel:\n" + ex);
        }
        // </editor-fold>

        // Metodo che usa NetBeans di default per avviare una classe con JFrame
        java.awt.EventQueue.invokeLater(() -> new Menu().setVisible(true));
    }
}
