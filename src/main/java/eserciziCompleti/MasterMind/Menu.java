/*
Classe contenente il JFrame del menu
 */

package eserciziCompleti.MasterMind;

import javax.swing.*;

public class Menu extends JFrame {
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pannelloMenu = new JPanel();
        immagineMM = new JLabel();
        titoloMM = new JLabel();
        panelBtnMenu = new JPanel();
        nuovaPartitaBtn = new JButton();
        impostazioniBtn = new JButton();
        esciBtn = new JButton();
        footer = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Master Mind");
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(500, 450));
        setResizable(false);
        setSize(new java.awt.Dimension(500, 450));

        pannelloMenu.setBackground(new java.awt.Color(255, 255, 255));

        // Commentare la riga seguente in caso non funzioni, su IntellJ non capisco perchè non carichi le immagini
        immagineMM.setIcon(new ImageIcon(getClass().getResource("/masterMind/mmImage.png"))); // NOI18N
        immagineMM.setToolTipText("");

        titoloMM.setFont(new java.awt.Font("Copperplate", 1, 36)); // NOI18N
        titoloMM.setHorizontalAlignment(SwingConstants.CENTER);
        titoloMM.setText("MasterMind");

        panelBtnMenu.setBackground(new java.awt.Color(255, 255, 255));

        nuovaPartitaBtn.setBackground(new java.awt.Color(106, 213, 166));
        nuovaPartitaBtn.setFont(new java.awt.Font(".SF NS Text", 0, 14)); // NOI18N
        nuovaPartitaBtn.setText("Nuova Partita");
        nuovaPartitaBtn.setPreferredSize(new java.awt.Dimension(83, 30));
        nuovaPartitaBtn.setSize(new java.awt.Dimension(83, 30));

        impostazioniBtn.setBackground(new java.awt.Color(220, 93, 180));
        impostazioniBtn.setFont(new java.awt.Font(".SF NS Text", 0, 14)); // NOI18N
        impostazioniBtn.setForeground(new java.awt.Color(255, 255, 255));
        impostazioniBtn.setText("Impostazioni");
        impostazioniBtn.setPreferredSize(new java.awt.Dimension(83, 30));
        impostazioniBtn.setSize(new java.awt.Dimension(83, 30));

        esciBtn.setBackground(new java.awt.Color(255, 51, 51));
        esciBtn.setFont(new java.awt.Font(".SF NS Text", 0, 14)); // NOI18N
        esciBtn.setForeground(new java.awt.Color(255, 255, 255));
        esciBtn.setText("Esci");
        esciBtn.setBorderPainted(false);
        esciBtn.setPreferredSize(new java.awt.Dimension(83, 30));
        esciBtn.setSize(new java.awt.Dimension(83, 30));

        GroupLayout panelBtnMenuLayout = new GroupLayout(panelBtnMenu);
        panelBtnMenu.setLayout(panelBtnMenuLayout);
        panelBtnMenuLayout.setHorizontalGroup(
            panelBtnMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(nuovaPartitaBtn, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
            .addComponent(impostazioniBtn, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(esciBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelBtnMenuLayout.setVerticalGroup(
            panelBtnMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelBtnMenuLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(nuovaPartitaBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(impostazioniBtn, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(esciBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        footer.setHorizontalAlignment(SwingConstants.CENTER);
        footer.setText("Made by Giulopime with ❤");
        footer.setPreferredSize(new java.awt.Dimension(167, 20));

        GroupLayout pannelloMenuLayout = new GroupLayout(pannelloMenu);
        pannelloMenu.setLayout(pannelloMenuLayout);
        pannelloMenuLayout.setHorizontalGroup(
            pannelloMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, pannelloMenuLayout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addComponent(panelBtnMenu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(immagineMM)
                .addGap(24, 24, 24))
            .addComponent(titoloMM, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(footer, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pannelloMenuLayout.setVerticalGroup(
            pannelloMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannelloMenuLayout.createSequentialGroup()
                .addComponent(titoloMM, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pannelloMenuLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(immagineMM, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelBtnMenu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(footer, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(pannelloMenu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(pannelloMenu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton esciBtn;
    private JLabel footer;
    private JLabel immagineMM;
    private JButton impostazioniBtn;
    private JButton nuovaPartitaBtn;
    private JPanel panelBtnMenu;
    private JPanel pannelloMenu;
    private JLabel titoloMM;
    // End of variables declaration//GEN-END:variables

        public Menu() {
            initComponents();
            initActionListeners();
        }

        // Metodo per aggiungere gli ActionListener ai bottoni "Nuova Partita", "Impostazioni" ed "esci"
        private void initActionListeners() {
            nuovaPartitaBtn.addActionListener(e -> {
                // Chiudo il frame del menù e creo un nuovo frame (del MasterMind) settandolo come visibile
                dispose();
                new MasterMind().setVisible(true);
            });
            impostazioniBtn.addActionListener(e -> {
                dispose();
                new Impostazioni().setVisible(true);
            });
            esciBtn.addActionListener(e -> System.exit(0));
        }
}
