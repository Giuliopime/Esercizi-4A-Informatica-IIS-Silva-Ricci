/*
Classe del JFrame delle impostazioni
 */
package eserciziCompleti.MasterMind;

import eserciziCompleti.MasterMind.util.impostazioni.Difficolta;
import eserciziCompleti.MasterMind.util.impostazioni.Settaggi;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;

public class Impostazioni extends JFrame {
    // <editor-fold defaultstate="collapsed" desc="Grafica Generata con NetBeans">//GEN-BEGIN:initComponents
    private void initComponents() {

        pannello = new JPanel();
        titoloImpostazioni = new JLabel();
        salvaBtn = new JButton();
        textFieldUsername = new JTextField();
        siglaUsername = new JLabel();
        siglaTentativi = new JLabel();
        siglaDiffic = new JLabel();
        sliderTentativi = new JSlider();
        comboBoxDiffic = new JComboBox<>();
        guidaDifficBtn = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(411, 350));

        pannello.setMinimumSize(new java.awt.Dimension(400, 350));

        titoloImpostazioni.setFont(new java.awt.Font("Copperplate", 1, 36)); // NOI18N
        titoloImpostazioni.setHorizontalAlignment(SwingConstants.CENTER);
        titoloImpostazioni.setText("Impostazioni");

        salvaBtn.setBackground(new java.awt.Color(106, 213, 166));
        salvaBtn.setFont(new java.awt.Font(".SF NS Text", 0, 18)); // NOI18N
        salvaBtn.setText("Salva");
        salvaBtn.setPreferredSize(new java.awt.Dimension(83, 30));
        salvaBtn.setSize(new java.awt.Dimension(83, 30));

        textFieldUsername.setFont(new java.awt.Font(".SF NS Text", 0, 15)); // NOI18N
        textFieldUsername.setMinimumSize(new java.awt.Dimension(100, 25));
        textFieldUsername.setPreferredSize(new java.awt.Dimension(150, 25));

        siglaUsername.setFont(new java.awt.Font(".SF NS Text", 0, 15)); // NOI18N
        siglaUsername.setText("Username:");

        siglaTentativi.setFont(new java.awt.Font(".SF NS Text", 0, 15)); // NOI18N
        siglaTentativi.setText("Tentativi disponibili:");

        siglaDiffic.setFont(new java.awt.Font(".SF NS Text", 0, 15)); // NOI18N
        siglaDiffic.setText("Difficoltà:");

        sliderTentativi.setFont(new java.awt.Font(".SF NS Text", 0, 15)); // NOI18N
        sliderTentativi.setMajorTickSpacing(1);
        sliderTentativi.setMaximum(10);
        sliderTentativi.setMinimum(1);
        sliderTentativi.setPaintLabels(true);
        sliderTentativi.setSnapToTicks(true);
        sliderTentativi.setToolTipText("9");
        sliderTentativi.setValue(9);
        sliderTentativi.setMaximumSize(new java.awt.Dimension(32767, 15));
        sliderTentativi.setName(""); // NOI18N
        sliderTentativi.setPreferredSize(new java.awt.Dimension(200, 15));
        sliderTentativi.setSize(new java.awt.Dimension(200, 15));

        comboBoxDiffic.setModel(new DefaultComboBoxModel<>(new String[] { "Facile", "Normale", "Difficile", "Hardcore" }));
        comboBoxDiffic.setSelectedIndex(1);

        guidaDifficBtn.setText("?");

        GroupLayout pannelloLayout = new GroupLayout(pannello);
        pannello.setLayout(pannelloLayout);
        pannelloLayout.setHorizontalGroup(
            pannelloLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(titoloImpostazioni, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pannelloLayout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(pannelloLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(salvaBtn, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
                    .addGroup(pannelloLayout.createSequentialGroup()
                        .addComponent(siglaUsername)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(76, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, pannelloLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pannelloLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(siglaDiffic)
                    .addComponent(siglaTentativi))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pannelloLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(sliderTentativi, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addGroup(pannelloLayout.createSequentialGroup()
                        .addComponent(comboBoxDiffic, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(guidaDifficBtn)))
                .addGap(26, 26, 26))
        );
        pannelloLayout.setVerticalGroup(
            pannelloLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannelloLayout.createSequentialGroup()
                .addComponent(titoloImpostazioni, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(pannelloLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(siglaUsername)
                    .addComponent(textFieldUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(pannelloLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(siglaTentativi)
                    .addComponent(sliderTentativi, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(pannelloLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(siglaDiffic)
                    .addComponent(comboBoxDiffic, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(guidaDifficBtn))
                .addGap(28, 28, 28)
                .addComponent(salvaBtn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(pannello, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(pannello, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JComboBox<String> comboBoxDiffic;
    private JButton guidaDifficBtn;
    private JPanel pannello;
    private JButton salvaBtn;
    private JLabel siglaDiffic;
    private JLabel siglaTentativi;
    private JLabel siglaUsername;
    private JSlider sliderTentativi;
    private JTextField textFieldUsername;
    private JLabel titoloImpostazioni;
    // End of variables declaration//GEN-END:variables

    private Settaggi settaggi;

    public Impostazioni() {
        initComponents();

        try {
            caricaSettaggi();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Non è stato possibile caricare le impostazioni dal file .dat, probabilmente non era ancora stato creato");
            ex.printStackTrace();
        }

        initActionListeners();
    }

    // Carica la classe Settaggi salvata in precedenza nel file settaggi.dat (il file esiste solamente se è stato utilizzato il bottone "Salva" in precedenza)
    private void caricaSettaggi() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("settaggi.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);

        // Se disponibile il fis prendo il primo oggetto nel file e faccio il casting alla classe Settaggi altrimenti creo un nuovo oggetto settaggi
        settaggi = fis.available() > 0 ? (Settaggi) ois.readObject() : new Settaggi();

        // Prendo i valori dei settaggi e li carico nella grafica
        textFieldUsername.setText(settaggi.getUsername());
        sliderTentativi.setValue(settaggi.getNumTentativi());
        /* La ComboBoxDiffic contiene 4 elementi corrispondenti alle quattro possibili difficoltà
          Per indicare la difficoltà presa dal file dei settaggi devo utilizzare setSelectedIndex, i possibili indici sono:
          0 - Facile
          1 - Normale
          2 - Difficile
          3 - Hardcore

          Per scegliere l'indice trasformo l'Enum delle Difficoltà in una lista e trovo l'indice della difficoltà presa dal file dei settaggi nella lista
         */

        comboBoxDiffic.setSelectedIndex(Arrays.asList(Difficolta.values()).indexOf(settaggi.getDifficolta()));

        ois.close();
        fis.close();
    }

    // Inizializzo gli ActionListener
    private void initActionListeners() {
        // Imposto che, quando viene premuta la x per chiudere la finestra il frame viene chiuso ma il programma non termina, viene invece ricaricato il frame del menu
        // Cioè è possibile facendo l'override all'evento di chiusura del frame
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dispose();
                java.awt.EventQueue.invokeLater(() -> new Menu().setVisible(true));
            }
        });


        salvaBtn.addActionListener(e -> {
            try {
                salvaImpostazioni();
            } catch (IOException ex) {
                System.out.println("Errore nel salvataggio delle impostazioni");
                ex.printStackTrace();

                JOptionPane.showMessageDialog(null, "È avvenuto un errore nel salvataggio delle impostazioni");
            }
        });

        // Con il bottone "?" vengono spiegate le varie difficolta
        guidaDifficBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, """
                Spiegazione delle difficoltà:
                Facile - I colori sono solo 4 e non è possibile avere duplicati
                Normale - 6 colori disponibili, senza possibilità di duplicati
                Difficile - 6 colori disponibili con possibilità di duplicati
                Hardcore - 8 colori disponibili con possibilità di duplicati\s"""));
    }

    // Metodo per salvare le impostazioni selezionate su file
    private void salvaImpostazioni() throws IOException {
        FileOutputStream fos = new FileOutputStream("settaggi.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        // Prendo i valori di input nella grafica
        String username = textFieldUsername.getText();
        int tentativi = sliderTentativi.getValue();
        Difficolta difficolta = Difficolta.values()[comboBoxDiffic.getSelectedIndex()];

        // ricreo l'oggetto Settaggi con i nuovi valori
        settaggi = new Settaggi(username, tentativi, difficolta);

        oos.writeObject(settaggi);

        oos.close();
        fos.close();

        // Chiudo il frame e riapro il menù
        dispose();
        java.awt.EventQueue.invokeLater(() -> new Menu().setVisible(true));
    }
}
