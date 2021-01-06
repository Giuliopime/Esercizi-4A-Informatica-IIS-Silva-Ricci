/*
Questa è la classe per il frame del pannello di gioco del MasterMind.

TERMINOLOGIA:
- codice: sequenza di 4 colori (con codice della soluzione si intende il codice da indovinare generato dal pc)
- buco: spazio nel gioco dove vengono inseriti i pioli per tentare di indovinare il codice
- piolo: pallino colorato che l'utente può cliccare per colorare un buco
- buchi tentativi: sono 10 e sono i buchi nella colonna destra del MasterMind, servono ad indicare, con un pallino bianco, la quantità di tentativi disponibili, e, con un pallino rosso, se il codice dell'utente è corretto
- tentativo: singola riga di 4 buchi dove l'utente inserisce i pioli
- indicatori: sono 40 e sono quei 4 pallini più piccoli affianco ad ogni riga di 4 buchi
- pioli codice: sono i 4 pioli nel pannello superiore del mastermind che rivelano il codice della soluzione
- selettore: pallino bianco che aiuta l'utente a capire quale buco è attualmente selezionato quando si muove con i bottoni "precedente" e "successivo"


CONTROLLO:
L'utente può inserire i pioli nei vari buchi utilizzando i pulsanti "precedente" e "successivo"
(o premendo freccia indietro / freccia avanti sulla tastiera) per spostarsi da un buco all'altro
e poi premendo un piolo colorato nel pannello inferiore per inserire un piolo nel buco in cui si trova al momento


FUNZIONAMENTO DELLA CLASSE:
All'avvio vengono richiamati diversi metodi nel costruttore:
- initComponents() che semplicemente fa il set up della grafica generata attraverso NetBeans
- initVariables che inizializza gli array di bottoni e JLabel
- caricaSettaggi che prende un'oggetto Settaggi dal file settaggi.dat
- initActionListeners che aggiunge gli action listeners ai bottoni ed ai pioli
- generaCodice che genera una sequenza di quattro colori, tenendo conto della difficoltà a cui è impostata la partita,
    le differenze tra le varie difficoltà sono:
        Facile - Si gioca con 4 colori e non è possibile avere duplicati
        Normale - 6 colori disponibili, senza possibilità di duplicati
        Difficile - 6 colori disponibili con possibilità di duplicati
        Hardcore - 8 colori disponibili con possibilità di duplicati
- mostraFunzionamento che semplicemente mostra un messaggio su schermo con alcune informazioni su come giocare

Fatto questo tutto il resto dipende dai listeners del programma, che sono di 2 tipi:
- navigazione: bottone precedente e successivo (oppure i tasti freccia indietro / avanti della tastiera)
- selezione: listener che ascolta i click sugli 8 pioli (o sui tasti numerici che vanno da 1 a 8 sulla tastiera)

Listeners di Selezione:
Non fanno altro che inserire un piolo nel buco attualmente selezionato
ed attribuiscono alla variabile coloreSottoSelettore il colore selezionato dall'utente (questa variabile è spiegata in seguito)

Listeners di Navigazione:
Il componente principale della navigazione è il selettore, un pallino bianco che indica all'utente in che buco si trova.
Per fare in modo che il selettore sia solamente temporaneo e che non vada a modificare i colori inseriti nei vari buchi deve fare alcune cose.
Innanzitutto c'è una variabile che contiene sempre il colore del buco selezionato prima che il selettore bianco ci si posizionasse sopra,
e a questa variabile viene assegnato un colore in due situazioni:
- quando l'utente clicca un listener di selezione (caso descritto in precedenza)
- quando viene spostato il selettore, quindi con i listeners di navigazione:
    quando viene premuto ad esempio il bottone "successivo" avvengono diverse operazioni per gestire il selettore,
    innanzitutto bisogna ripristinare il colore del buco corrente (che era stato sovrascritto dal selettore ma il cui colore originale si trova nella variabile coloreSottoSelezione)
    una volta ripristinato il colore si passa al buco successivo, si prende il colore attualmente sul buco (in caso fosse presente un piolo) e lo si attribuisce alla variabile coloreSottoSelettore,
    poi si va a sovrascrivere il buco con il pallino bianco del selettore.

Questo era quindi il funzionamento del selettore, avvengono però ovviamente altre operazioni nei listeners di navigazione:
Listener che porta al buco precedente:
- Viene controllato che l'utente non si trovi già nel primo buco di un tentativo (guarda TERMINOLOGIA)

Listener che porta al buco successivo:
Viene controllato se si deve passare al tentativo successivo
(la relativa "if" sarebbe: se l'indice del buco corrente + 1, quindi il numero del buco in cui si trova, modulo (%) 4 è uguale a zero,
quindi se il buco corrente è l'ultimo dei 4 di un tentativo)

Se si deve passare al prossimo tentativo allora avvengono diverse operazioni:
Viene creato un array con i 4 colori dei pioli inseriti dall'utente, che chiameremo ColoriIns
Viene controllato che siano 4 colori validi e che quindi l'utente non abbia lasciato buchi senza pioli
Viene poi controllato la correttezza del codice inserito dall'utente confrontandolo con quello della soluione:
    - vengono inizializzate due variabili che conterranno il numero di pioli corretti (cioè di colore giusto e nella giusta posizione)
        ed il numero di pioli solamente presenti (cioè di colore corretto ma non nella giusta posizione)
    - viene creato un ArrayList in cui verranno inseriti passo passo i colori che vengono controllati e che sono anche presenti nel codice della soluzione
    - vengono confrontati i due array ColoriIns e quello del codice della soluzione, indice per indice, e vengono cosi calcolati i pioli corretti
        (aggiungendo i colori dei pioli corretti nell'ArrayList dei colori controllati
    - viene poi fatto un altro for loop, con variabile che va da 0 a 3, che come prima cosa
        controlla che il colore dell'array ColoriIns all'indice i (la variabile del for loop) sia presente
        nel codice della soluzione, se si allora ci sono due casistiche:
            - i duplicati non sono accettati
                quindi viene solo controllato che nell'ArrayList dei colori controllati non sia già presente il colore
                in esamine al momento (per accertarsi che non sia un duplicato) e viene poi incrementata di 1 la variabile
                dei pioli presenti
            - i duplicati sono accettati:
                Chiamiamo il colore che stiamo esamindano ColoreX (che sarebbe il colore dell'array ColoriIns all'indice i (la variabile del for loop))
                Calcoliamo nel codice della soluzione quante volte appare il ColoreX e salviamo questo contatore in una variabile (la chiamiamo Y)
                Calcoliamo in ColorIns quanto volte è presente il ColoreX e salviamo questo contatore in un'altra variabile (Z)
                Poi controllo che X sia minore di Y (ad esempio: se nella soluzione ci sono 3 rossi
                e nel codice dell'utente ci sono 4 rossi, l'algoritmo quando va ad analizzare ogni colore,
                si fermerà ad incrementare il contatore dei colori presenti fino al terzo rosso inserito dell'utente, non contando quindi il quarto)
                In questo modo vengono gestiti correttamente i duplicati
   - Vengono aggiornati gli indicatori del tentativo a seconda del numero di pioli corretti e presenti
   - Se i corretti sono 4 vuol dire che l'utente ha vinto:
        - Viene colorato di rosso il buco del tentativo
        - vengono disattivati tutti i listeners di navigazione
        - Viene mostrato un messaggio di vittoria
   - se sono esauriti i tentativi
        - vengono disattivati tutti i listeners di navigazione
        - Viene mostrato un messaggio di sconfitta
   - altrimenti si passa al prossimo tentativo
 */
package eserciziCompleti.MasterMind;

import eserciziCompleti.MasterMind.util.ColoriPioli;
import eserciziCompleti.MasterMind.util.impostazioni.Settaggi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MasterMind extends JFrame {
    // <editor-fold defaultState="collapsed" desc="Codice per la grafica generato da NetBeans">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new ButtonGroup();
        jProgressBar1 = new JProgressBar();
        pannelloCodice = new JPanel();
        pioloCodice2 = new JLabel();
        pioloCodice3 = new JLabel();
        pioloCodice4 = new JLabel();
        pioloCodice1 = new JLabel();
        pannelloScrittaMM = new JPanel();
        pannelloControllo = new JPanel();
        btnPrecedente = new JButton();
        btnSuccessivo = new JButton();
        pannelloBtns = new JPanel();
        pioloRosso = new JLabel();
        pioloRosa = new JLabel();
        pioloGiallo = new JLabel();
        pioloArancio = new JLabel();
        pioloVerde = new JLabel();
        pioloBlu = new JLabel();
        pioloCiano = new JLabel();
        pioloViola = new JLabel();
        pannelloDiGioco = new JPanel();
        pannelloTentativi = new JPanel();
        bucoTentativo1 = new JLabel();
        bucoTentativo2 = new JLabel();
        bucoTentativo4 = new JLabel();
        bucoTentativo3 = new JLabel();
        bucoTentativo5 = new JLabel();
        bucoTentativo6 = new JLabel();
        bucoTentativo7 = new JLabel();
        bucoTentativo8 = new JLabel();
        bucoTentativo9 = new JLabel();
        bucoTentativo10 = new JLabel();
        panRisp1 = new JPanel();
        pannRisult1 = new JPanel();
        indicatore1 = new JLabel();
        indicatore2 = new JLabel();
        indicatore3 = new JLabel();
        indicatore4 = new JLabel();
        buco1 = new JLabel();
        buco3 = new JLabel();
        buco2 = new JLabel();
        buco4 = new JLabel();
        jSeparator1 = new JSeparator();
        panRisp2 = new JPanel();
        pannRisult2 = new JPanel();
        indicatore5 = new JLabel();
        indicatore6 = new JLabel();
        indicatore7 = new JLabel();
        indicatore8 = new JLabel();
        buco5 = new JLabel();
        buco7 = new JLabel();
        buco6 = new JLabel();
        buco8 = new JLabel();
        jSeparator2 = new JSeparator();
        panRisp3 = new JPanel();
        pannRisult3 = new JPanel();
        indicatore9 = new JLabel();
        indicatore10 = new JLabel();
        indicatore11 = new JLabel();
        indicatore12 = new JLabel();
        buco9 = new JLabel();
        buco11 = new JLabel();
        buco10 = new JLabel();
        buco12 = new JLabel();
        jSeparator3 = new JSeparator();
        panRisp4 = new JPanel();
        pannRisult4 = new JPanel();
        indicatore13 = new JLabel();
        indicatore14 = new JLabel();
        indicatore15 = new JLabel();
        indicatore16 = new JLabel();
        buco13 = new JLabel();
        buco15 = new JLabel();
        buco14 = new JLabel();
        buco16 = new JLabel();
        jSeparator4 = new JSeparator();
        panRisp5 = new JPanel();
        pannRisult5 = new JPanel();
        indicatore17 = new JLabel();
        indicatore18 = new JLabel();
        indicatore19 = new JLabel();
        indicatore20 = new JLabel();
        buco17 = new JLabel();
        buco19 = new JLabel();
        buco18 = new JLabel();
        buco20 = new JLabel();
        jSeparator5 = new JSeparator();
        panRisp6 = new JPanel();
        pannRisult6 = new JPanel();
        indicatore21 = new JLabel();
        indicatore22 = new JLabel();
        indicatore23 = new JLabel();
        indicatore24 = new JLabel();
        buco21 = new JLabel();
        buco23 = new JLabel();
        buco22 = new JLabel();
        buco24 = new JLabel();
        jSeparator6 = new JSeparator();
        panRisp7 = new JPanel();
        pannRisult7 = new JPanel();
        indicatore25 = new JLabel();
        indicatore26 = new JLabel();
        indicatore27 = new JLabel();
        indicatore28 = new JLabel();
        buco25 = new JLabel();
        buco27 = new JLabel();
        buco26 = new JLabel();
        buco28 = new JLabel();
        jSeparator7 = new JSeparator();
        panRisp8 = new JPanel();
        pannRisult8 = new JPanel();
        indicatore29 = new JLabel();
        indicatore30 = new JLabel();
        indicatore31 = new JLabel();
        indicatore32 = new JLabel();
        buco29 = new JLabel();
        buco31 = new JLabel();
        buco30 = new JLabel();
        buco32 = new JLabel();
        jSeparator8 = new JSeparator();
        panRisp9 = new JPanel();
        pannRisult9 = new JPanel();
        indicatore33 = new JLabel();
        indicatore34 = new JLabel();
        indicatore35 = new JLabel();
        indicatore36 = new JLabel();
        buco33 = new JLabel();
        buco35 = new JLabel();
        buco34 = new JLabel();
        buco36 = new JLabel();
        jSeparator9 = new JSeparator();
        panRisp10 = new JPanel();
        pannRisult10 = new JPanel();
        indicatore37 = new JLabel();
        indicatore38 = new JLabel();
        indicatore39 = new JLabel();
        indicatore40 = new JLabel();
        buco37 = new JLabel();
        buco39 = new JLabel();
        buco38 = new JLabel();
        buco40 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(206, 139, 102));

        pannelloCodice.setBackground(new Color(206, 139, 102));
        pannelloCodice.setPreferredSize(new Dimension(0, 70));

        pioloCodice2.setFont(new Font(".SF NS Text", 0, 24)); // NOI18N
        pioloCodice2.setForeground(new Color(240, 223, 219));
        pioloCodice2.setHorizontalAlignment(SwingConstants.CENTER);
        pioloCodice2.setText("⬤");
        pioloCodice2.setMaximumSize(new Dimension(22, 24));
        pioloCodice2.setMinimumSize(new Dimension(22, 20));
        pioloCodice2.setPreferredSize(new Dimension(22, 30));
        pioloCodice2.setSize(new Dimension(45, 30));

        pioloCodice3.setFont(new Font(".SF NS Text", 0, 24)); // NOI18N
        pioloCodice3.setForeground(new Color(240, 223, 219));
        pioloCodice3.setHorizontalAlignment(SwingConstants.CENTER);
        pioloCodice3.setText("⬤");
        pioloCodice3.setMaximumSize(new Dimension(22, 24));
        pioloCodice3.setMinimumSize(new Dimension(22, 20));
        pioloCodice3.setPreferredSize(new Dimension(22, 30));
        pioloCodice3.setSize(new Dimension(45, 30));

        pioloCodice4.setFont(new Font(".SF NS Text", 0, 24)); // NOI18N
        pioloCodice4.setForeground(new Color(240, 223, 219));
        pioloCodice4.setHorizontalAlignment(SwingConstants.CENTER);
        pioloCodice4.setText("⬤");
        pioloCodice4.setMaximumSize(new Dimension(22, 24));
        pioloCodice4.setMinimumSize(new Dimension(22, 20));
        pioloCodice4.setPreferredSize(new Dimension(22, 30));
        pioloCodice4.setSize(new Dimension(45, 30));

        pioloCodice1.setFont(new Font(".SF NS Text", 0, 24)); // NOI18N
        pioloCodice1.setForeground(new Color(240, 223, 219));
        pioloCodice1.setHorizontalAlignment(SwingConstants.CENTER);
        pioloCodice1.setText("⬤");
        pioloCodice1.setMaximumSize(new Dimension(22, 24));
        pioloCodice1.setMinimumSize(new Dimension(22, 20));
        pioloCodice1.setPreferredSize(new Dimension(22, 30));
        pioloCodice1.setSize(new Dimension(45, 30));

        GroupLayout pannelloCodiceLayout = new GroupLayout(pannelloCodice);
        pannelloCodice.setLayout(pannelloCodiceLayout);
        pannelloCodiceLayout.setHorizontalGroup(
            pannelloCodiceLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannelloCodiceLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(pioloCodice1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(pioloCodice2, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(pioloCodice3, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(pioloCodice4, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pannelloCodiceLayout.setVerticalGroup(
            pannelloCodiceLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, pannelloCodiceLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(pannelloCodiceLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(pioloCodice2, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
                    .addComponent(pioloCodice1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                    .addComponent(pioloCodice3, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                    .addComponent(pioloCodice4, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        pannelloScrittaMM.setBackground(new Color(106, 213, 166));

        GroupLayout pannelloScrittaMMLayout = new GroupLayout(pannelloScrittaMM);
        pannelloScrittaMM.setLayout(pannelloScrittaMMLayout);
        pannelloScrittaMMLayout.setHorizontalGroup(
            pannelloScrittaMMLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 97, Short.MAX_VALUE)
        );
        pannelloScrittaMMLayout.setVerticalGroup(
            pannelloScrittaMMLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pannelloControllo.setBackground(new Color(206, 139, 102));
        pannelloControllo.setPreferredSize(new Dimension(0, 70));

        btnPrecedente.setFont(new Font("Copperplate", 0, 14)); // NOI18N
        btnPrecedente.setText("Precedente");

        btnSuccessivo.setFont(new Font("Copperplate", 0, 14)); // NOI18N
        btnSuccessivo.setText("Successivo");

        pannelloBtns.setBackground(new Color(206, 139, 102));

        pioloRosso.setFont(new Font(".SF NS Text", 0, 22)); // NOI18N
        pioloRosso.setForeground(new Color(255, 51, 51));
        pioloRosso.setHorizontalAlignment(SwingConstants.CENTER);
        pioloRosso.setText("◉");
        pioloRosso.setMaximumSize(new Dimension(22, 24));
        pioloRosso.setMinimumSize(new Dimension(22, 20));
        pioloRosso.setPreferredSize(new Dimension(22, 30));
        pioloRosso.setSize(new Dimension(45, 30));

        pioloRosa.setFont(new Font(".SF NS Text", 0, 22)); // NOI18N
        pioloRosa.setForeground(new Color(239, 68, 182));
        pioloRosa.setHorizontalAlignment(SwingConstants.CENTER);
        pioloRosa.setText("◉");
        pioloRosa.setMaximumSize(new Dimension(22, 24));
        pioloRosa.setMinimumSize(new Dimension(22, 20));
        pioloRosa.setPreferredSize(new Dimension(22, 30));
        pioloRosa.setSize(new Dimension(45, 30));

        pioloGiallo.setFont(new Font(".SF NS Text", 0, 22)); // NOI18N
        pioloGiallo.setForeground(new Color(254, 254, 8));
        pioloGiallo.setHorizontalAlignment(SwingConstants.CENTER);
        pioloGiallo.setText("◉");
        pioloGiallo.setMaximumSize(new Dimension(22, 24));
        pioloGiallo.setMinimumSize(new Dimension(22, 20));
        pioloGiallo.setPreferredSize(new Dimension(22, 30));
        pioloGiallo.setSize(new Dimension(45, 30));

        pioloArancio.setFont(new Font(".SF NS Text", 0, 22)); // NOI18N
        pioloArancio.setForeground(new Color(252, 93, 1));
        pioloArancio.setHorizontalAlignment(SwingConstants.CENTER);
        pioloArancio.setText("◉");
        pioloArancio.setMaximumSize(new Dimension(22, 24));
        pioloArancio.setMinimumSize(new Dimension(22, 20));
        pioloArancio.setPreferredSize(new Dimension(22, 30));
        pioloArancio.setSize(new Dimension(45, 30));

        pioloVerde.setBackground(new Color(106, 213, 166));
        pioloVerde.setFont(new Font(".SF NS Text", 0, 22)); // NOI18N
        pioloVerde.setForeground(new Color(106, 213, 166));
        pioloVerde.setHorizontalAlignment(SwingConstants.CENTER);
        pioloVerde.setText("◉");
        pioloVerde.setMaximumSize(new Dimension(22, 24));
        pioloVerde.setMinimumSize(new Dimension(22, 20));
        pioloVerde.setPreferredSize(new Dimension(22, 30));
        pioloVerde.setSize(new Dimension(45, 30));

        pioloBlu.setFont(new Font(".SF NS Text", 0, 22)); // NOI18N
        pioloBlu.setForeground(new Color(51, 51, 255));
        pioloBlu.setHorizontalAlignment(SwingConstants.CENTER);
        pioloBlu.setText("◉");
        pioloBlu.setMaximumSize(new Dimension(22, 24));
        pioloBlu.setMinimumSize(new Dimension(22, 20));
        pioloBlu.setPreferredSize(new Dimension(22, 30));

        pioloCiano.setFont(new Font(".SF NS Text", 0, 22)); // NOI18N
        pioloCiano.setForeground(new Color(90, 249, 249));
        pioloCiano.setHorizontalAlignment(SwingConstants.CENTER);
        pioloCiano.setText("◉");
        pioloCiano.setMaximumSize(new Dimension(22, 24));
        pioloCiano.setMinimumSize(new Dimension(22, 20));
        pioloCiano.setPreferredSize(new Dimension(22, 30));

        pioloViola.setFont(new Font(".SF NS Text", 0, 22)); // NOI18N
        pioloViola.setForeground(new Color(153, 0, 255));
        pioloViola.setHorizontalAlignment(SwingConstants.CENTER);
        pioloViola.setText("◉");
        pioloViola.setMaximumSize(new Dimension(22, 24));
        pioloViola.setMinimumSize(new Dimension(22, 20));
        pioloViola.setPreferredSize(new Dimension(22, 30));

        GroupLayout pannelloBtnsLayout = new GroupLayout(pannelloBtns);
        pannelloBtns.setLayout(pannelloBtnsLayout);
        pannelloBtnsLayout.setHorizontalGroup(
            pannelloBtnsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannelloBtnsLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pannelloBtnsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(pioloRosso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(pioloVerde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pannelloBtnsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(pannelloBtnsLayout.createSequentialGroup()
                        .addComponent(pioloRosa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pioloArancio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pioloCiano, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(pannelloBtnsLayout.createSequentialGroup()
                        .addComponent(pioloGiallo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pioloBlu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pioloViola, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pannelloBtnsLayout.setVerticalGroup(
            pannelloBtnsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannelloBtnsLayout.createSequentialGroup()
                .addGroup(pannelloBtnsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(pioloRosso, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                    .addComponent(pioloRosa, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                    .addComponent(pioloArancio, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                    .addComponent(pioloCiano, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pannelloBtnsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(pioloVerde, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                    .addComponent(pioloGiallo, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                    .addComponent(pioloBlu, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                    .addComponent(pioloViola, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout pannelloControlloLayout = new GroupLayout(pannelloControllo);
        pannelloControllo.setLayout(pannelloControlloLayout);
        pannelloControlloLayout.setHorizontalGroup(
            pannelloControlloLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannelloControlloLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnPrecedente)
                .addGap(18, 18, 18)
                .addComponent(pannelloBtns, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnSuccessivo)
                .addGap(22, 22, 22))
        );
        pannelloControlloLayout.setVerticalGroup(
            pannelloControlloLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannelloControlloLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pannelloControlloLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSuccessivo)
                    .addComponent(btnPrecedente))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pannelloControlloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pannelloBtns, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pannelloDiGioco.setBackground(new Color(206, 139, 102));
        pannelloDiGioco.setPreferredSize(new Dimension(294, 590));
        pannelloDiGioco.setSize(new Dimension(0, 590));

        pannelloTentativi.setBackground(new Color(206, 139, 102));
        pannelloTentativi.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(149, 61, 51)));

        bucoTentativo1.setFont(new Font(".SF NS Text", 0, 14)); // NOI18N
        bucoTentativo1.setForeground(new Color(27, 3, 3));
        bucoTentativo1.setHorizontalAlignment(SwingConstants.CENTER);
        bucoTentativo1.setText("⬤");
        bucoTentativo1.setMaximumSize(new Dimension(22, 24));
        bucoTentativo1.setMinimumSize(new Dimension(22, 20));
        bucoTentativo1.setPreferredSize(new Dimension(22, 30));

        bucoTentativo2.setFont(new Font(".SF NS Text", 0, 14)); // NOI18N
        bucoTentativo2.setForeground(new Color(27, 3, 3));
        bucoTentativo2.setHorizontalAlignment(SwingConstants.CENTER);
        bucoTentativo2.setText("⬤");
        bucoTentativo2.setMaximumSize(new Dimension(22, 24));
        bucoTentativo2.setMinimumSize(new Dimension(22, 20));
        bucoTentativo2.setPreferredSize(new Dimension(22, 30));

        bucoTentativo4.setFont(new Font(".SF NS Text", 0, 14)); // NOI18N
        bucoTentativo4.setForeground(new Color(27, 3, 3));
        bucoTentativo4.setHorizontalAlignment(SwingConstants.CENTER);
        bucoTentativo4.setText("⬤");
        bucoTentativo4.setMaximumSize(new Dimension(22, 24));
        bucoTentativo4.setMinimumSize(new Dimension(22, 20));
        bucoTentativo4.setPreferredSize(new Dimension(22, 30));

        bucoTentativo3.setFont(new Font(".SF NS Text", 0, 14)); // NOI18N
        bucoTentativo3.setForeground(new Color(27, 3, 3));
        bucoTentativo3.setHorizontalAlignment(SwingConstants.CENTER);
        bucoTentativo3.setText("⬤");
        bucoTentativo3.setMaximumSize(new Dimension(22, 24));
        bucoTentativo3.setMinimumSize(new Dimension(22, 20));
        bucoTentativo3.setPreferredSize(new Dimension(22, 30));

        bucoTentativo5.setFont(new Font(".SF NS Text", 0, 14)); // NOI18N
        bucoTentativo5.setForeground(new Color(27, 3, 3));
        bucoTentativo5.setHorizontalAlignment(SwingConstants.CENTER);
        bucoTentativo5.setText("⬤");
        bucoTentativo5.setMaximumSize(new Dimension(22, 24));
        bucoTentativo5.setMinimumSize(new Dimension(22, 20));
        bucoTentativo5.setPreferredSize(new Dimension(22, 30));

        bucoTentativo6.setFont(new Font(".SF NS Text", 0, 14)); // NOI18N
        bucoTentativo6.setForeground(new Color(27, 3, 3));
        bucoTentativo6.setHorizontalAlignment(SwingConstants.CENTER);
        bucoTentativo6.setText("⬤");
        bucoTentativo6.setMaximumSize(new Dimension(22, 24));
        bucoTentativo6.setMinimumSize(new Dimension(22, 20));
        bucoTentativo6.setPreferredSize(new Dimension(22, 30));

        bucoTentativo7.setFont(new Font(".SF NS Text", 0, 14)); // NOI18N
        bucoTentativo7.setForeground(new Color(27, 3, 3));
        bucoTentativo7.setHorizontalAlignment(SwingConstants.CENTER);
        bucoTentativo7.setText("⬤");
        bucoTentativo7.setMaximumSize(new Dimension(22, 24));
        bucoTentativo7.setMinimumSize(new Dimension(22, 20));
        bucoTentativo7.setPreferredSize(new Dimension(22, 30));

        bucoTentativo8.setFont(new Font(".SF NS Text", 0, 14)); // NOI18N
        bucoTentativo8.setForeground(new Color(27, 3, 3));
        bucoTentativo8.setHorizontalAlignment(SwingConstants.CENTER);
        bucoTentativo8.setText("⬤");
        bucoTentativo8.setMaximumSize(new Dimension(22, 24));
        bucoTentativo8.setMinimumSize(new Dimension(22, 20));
        bucoTentativo8.setPreferredSize(new Dimension(22, 30));

        bucoTentativo9.setFont(new Font(".SF NS Text", 0, 14)); // NOI18N
        bucoTentativo9.setForeground(new Color(27, 3, 3));
        bucoTentativo9.setHorizontalAlignment(SwingConstants.CENTER);
        bucoTentativo9.setText("⬤");
        bucoTentativo9.setMaximumSize(new Dimension(22, 24));
        bucoTentativo9.setMinimumSize(new Dimension(22, 20));
        bucoTentativo9.setPreferredSize(new Dimension(22, 30));

        bucoTentativo10.setFont(new Font(".SF NS Text", 0, 14)); // NOI18N
        bucoTentativo10.setForeground(new Color(27, 3, 3));
        bucoTentativo10.setHorizontalAlignment(SwingConstants.CENTER);
        bucoTentativo10.setText("⬤");
        bucoTentativo10.setMaximumSize(new Dimension(22, 24));
        bucoTentativo10.setMinimumSize(new Dimension(22, 20));
        bucoTentativo10.setPreferredSize(new Dimension(22, 30));

        GroupLayout pannelloTentativiLayout = new GroupLayout(pannelloTentativi);
        pannelloTentativi.setLayout(pannelloTentativiLayout);
        pannelloTentativiLayout.setHorizontalGroup(
            pannelloTentativiLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannelloTentativiLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pannelloTentativiLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(bucoTentativo10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(bucoTentativo9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(bucoTentativo8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(bucoTentativo7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(bucoTentativo6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(bucoTentativo5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(bucoTentativo3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(bucoTentativo4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(bucoTentativo2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(bucoTentativo1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        pannelloTentativiLayout.setVerticalGroup(
            pannelloTentativiLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, pannelloTentativiLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(bucoTentativo10, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bucoTentativo9, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(bucoTentativo8, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(bucoTentativo7, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(bucoTentativo6, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(bucoTentativo5, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(bucoTentativo4, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(bucoTentativo3, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(bucoTentativo2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(bucoTentativo1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        panRisp1.setBackground(new Color(206, 139, 102));

        pannRisult1.setBackground(new Color(206, 139, 102));

        indicatore1.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore1.setText("⬤");

        indicatore2.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore2.setText("⬤");

        indicatore3.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore3.setText("⬤");

        indicatore4.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore4.setText("⬤");

        GroupLayout pannRisult1Layout = new GroupLayout(pannRisult1);
        pannRisult1.setLayout(pannRisult1Layout);
        pannRisult1Layout.setHorizontalGroup(
            pannRisult1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult1Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pannRisult1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult1Layout.createSequentialGroup()
                        .addComponent(indicatore1)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult1Layout.createSequentialGroup()
                        .addComponent(indicatore3)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore4)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pannRisult1Layout.setVerticalGroup(
            pannRisult1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult1Layout.createSequentialGroup()
                .addGroup(pannRisult1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore1)
                    .addComponent(indicatore2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pannRisult1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore4)
                    .addComponent(indicatore3))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        buco1.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco1.setForeground(new Color(65, 43, 30));
        buco1.setHorizontalAlignment(SwingConstants.CENTER);
        buco1.setText("◉");
        buco1.setMaximumSize(new Dimension(22, 24));
        buco1.setMinimumSize(new Dimension(22, 20));
        buco1.setPreferredSize(new Dimension(22, 30));
        buco1.setSize(new Dimension(45, 30));

        buco3.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco3.setForeground(new Color(65, 43, 30));
        buco3.setHorizontalAlignment(SwingConstants.CENTER);
        buco3.setText("◉");
        buco3.setMaximumSize(new Dimension(22, 24));
        buco3.setMinimumSize(new Dimension(22, 20));
        buco3.setPreferredSize(new Dimension(22, 30));

        buco2.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco2.setForeground(new Color(65, 43, 30));
        buco2.setHorizontalAlignment(SwingConstants.CENTER);
        buco2.setText("◉");
        buco2.setMaximumSize(new Dimension(22, 24));
        buco2.setMinimumSize(new Dimension(22, 20));
        buco2.setPreferredSize(new Dimension(22, 30));

        buco4.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco4.setForeground(new Color(65, 43, 30));
        buco4.setHorizontalAlignment(SwingConstants.CENTER);
        buco4.setText("◉");
        buco4.setMaximumSize(new Dimension(22, 24));
        buco4.setMinimumSize(new Dimension(22, 20));
        buco4.setPreferredSize(new Dimension(22, 30));

        GroupLayout panRisp1Layout = new GroupLayout(panRisp1);
        panRisp1.setLayout(panRisp1Layout);
        panRisp1Layout.setHorizontalGroup(
            panRisp1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pannRisult1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(buco1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco3, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco4, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panRisp1Layout.setVerticalGroup(
            panRisp1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp1Layout.createSequentialGroup()
                .addComponent(pannRisult1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panRisp1Layout.createSequentialGroup()
                .addGroup(panRisp1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, panRisp1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(buco3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(buco4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panRisp1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panRisp1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(buco2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buco1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jSeparator1.setForeground(new Color(149, 61, 51));

        panRisp2.setBackground(new Color(206, 139, 102));

        pannRisult2.setBackground(new Color(206, 139, 102));

        indicatore5.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore5.setText("⬤");

        indicatore6.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore6.setText("⬤");

        indicatore7.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore7.setText("⬤");

        indicatore8.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore8.setText("⬤");

        GroupLayout pannRisult2Layout = new GroupLayout(pannRisult2);
        pannRisult2.setLayout(pannRisult2Layout);
        pannRisult2Layout.setHorizontalGroup(
            pannRisult2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult2Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pannRisult2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult2Layout.createSequentialGroup()
                        .addComponent(indicatore5)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore6, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult2Layout.createSequentialGroup()
                        .addComponent(indicatore7)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore8)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pannRisult2Layout.setVerticalGroup(
            pannRisult2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult2Layout.createSequentialGroup()
                .addGroup(pannRisult2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore5)
                    .addComponent(indicatore6, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pannRisult2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore8)
                    .addComponent(indicatore7))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        buco5.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco5.setForeground(new Color(65, 43, 30));
        buco5.setHorizontalAlignment(SwingConstants.CENTER);
        buco5.setText("◉");
        buco5.setMaximumSize(new Dimension(22, 24));
        buco5.setMinimumSize(new Dimension(22, 20));
        buco5.setPreferredSize(new Dimension(22, 30));
        buco5.setSize(new Dimension(45, 30));

        buco7.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco7.setForeground(new Color(65, 43, 30));
        buco7.setHorizontalAlignment(SwingConstants.CENTER);
        buco7.setText("◉");
        buco7.setMaximumSize(new Dimension(22, 24));
        buco7.setMinimumSize(new Dimension(22, 20));
        buco7.setPreferredSize(new Dimension(22, 30));

        buco6.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco6.setForeground(new Color(65, 43, 30));
        buco6.setHorizontalAlignment(SwingConstants.CENTER);
        buco6.setText("◉");
        buco6.setMaximumSize(new Dimension(22, 24));
        buco6.setMinimumSize(new Dimension(22, 20));
        buco6.setPreferredSize(new Dimension(22, 30));

        buco8.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco8.setForeground(new Color(65, 43, 30));
        buco8.setHorizontalAlignment(SwingConstants.CENTER);
        buco8.setText("◉");
        buco8.setMaximumSize(new Dimension(22, 24));
        buco8.setMinimumSize(new Dimension(22, 20));
        buco8.setPreferredSize(new Dimension(22, 30));

        GroupLayout panRisp2Layout = new GroupLayout(panRisp2);
        panRisp2.setLayout(panRisp2Layout);
        panRisp2Layout.setHorizontalGroup(
            panRisp2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pannRisult2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(buco5, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco6, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco7, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco8, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panRisp2Layout.setVerticalGroup(
            panRisp2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp2Layout.createSequentialGroup()
                .addComponent(pannRisult2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panRisp2Layout.createSequentialGroup()
                .addGroup(panRisp2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, panRisp2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(buco7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(buco8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panRisp2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panRisp2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(buco6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buco5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jSeparator2.setForeground(new Color(149, 61, 51));

        panRisp3.setBackground(new Color(206, 139, 102));

        pannRisult3.setBackground(new Color(206, 139, 102));

        indicatore9.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore9.setText("⬤");

        indicatore10.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore10.setText("⬤");

        indicatore11.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore11.setText("⬤");

        indicatore12.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore12.setText("⬤");

        GroupLayout pannRisult3Layout = new GroupLayout(pannRisult3);
        pannRisult3.setLayout(pannRisult3Layout);
        pannRisult3Layout.setHorizontalGroup(
            pannRisult3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult3Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pannRisult3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult3Layout.createSequentialGroup()
                        .addComponent(indicatore9)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore10, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult3Layout.createSequentialGroup()
                        .addComponent(indicatore11)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore12)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pannRisult3Layout.setVerticalGroup(
            pannRisult3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult3Layout.createSequentialGroup()
                .addGroup(pannRisult3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore9)
                    .addComponent(indicatore10, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pannRisult3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore12)
                    .addComponent(indicatore11))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        buco9.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco9.setForeground(new Color(65, 43, 30));
        buco9.setHorizontalAlignment(SwingConstants.CENTER);
        buco9.setText("◉");
        buco9.setMaximumSize(new Dimension(22, 24));
        buco9.setMinimumSize(new Dimension(22, 20));
        buco9.setPreferredSize(new Dimension(22, 30));
        buco9.setSize(new Dimension(45, 30));

        buco11.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco11.setForeground(new Color(65, 43, 30));
        buco11.setHorizontalAlignment(SwingConstants.CENTER);
        buco11.setText("◉");
        buco11.setMaximumSize(new Dimension(22, 24));
        buco11.setMinimumSize(new Dimension(22, 20));
        buco11.setPreferredSize(new Dimension(22, 30));

        buco10.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco10.setForeground(new Color(65, 43, 30));
        buco10.setHorizontalAlignment(SwingConstants.CENTER);
        buco10.setText("◉");
        buco10.setMaximumSize(new Dimension(22, 24));
        buco10.setMinimumSize(new Dimension(22, 20));
        buco10.setPreferredSize(new Dimension(22, 30));

        buco12.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco12.setForeground(new Color(65, 43, 30));
        buco12.setHorizontalAlignment(SwingConstants.CENTER);
        buco12.setText("◉");
        buco12.setMaximumSize(new Dimension(22, 24));
        buco12.setMinimumSize(new Dimension(22, 20));
        buco12.setPreferredSize(new Dimension(22, 30));

        GroupLayout panRisp3Layout = new GroupLayout(panRisp3);
        panRisp3.setLayout(panRisp3Layout);
        panRisp3Layout.setHorizontalGroup(
            panRisp3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pannRisult3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(buco9, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco10, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco11, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco12, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panRisp3Layout.setVerticalGroup(
            panRisp3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp3Layout.createSequentialGroup()
                .addComponent(pannRisult3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panRisp3Layout.createSequentialGroup()
                .addGroup(panRisp3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, panRisp3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(buco11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(buco12, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panRisp3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panRisp3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(buco10, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buco9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jSeparator3.setForeground(new Color(149, 61, 51));

        panRisp4.setBackground(new Color(206, 139, 102));

        pannRisult4.setBackground(new Color(206, 139, 102));

        indicatore13.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore13.setText("⬤");

        indicatore14.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore14.setText("⬤");

        indicatore15.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore15.setText("⬤");

        indicatore16.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore16.setText("⬤");

        GroupLayout pannRisult4Layout = new GroupLayout(pannRisult4);
        pannRisult4.setLayout(pannRisult4Layout);
        pannRisult4Layout.setHorizontalGroup(
            pannRisult4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult4Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pannRisult4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult4Layout.createSequentialGroup()
                        .addComponent(indicatore13)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore14, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult4Layout.createSequentialGroup()
                        .addComponent(indicatore15)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore16)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pannRisult4Layout.setVerticalGroup(
            pannRisult4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult4Layout.createSequentialGroup()
                .addGroup(pannRisult4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore13)
                    .addComponent(indicatore14, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pannRisult4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore16)
                    .addComponent(indicatore15))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        buco13.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco13.setForeground(new Color(65, 43, 30));
        buco13.setHorizontalAlignment(SwingConstants.CENTER);
        buco13.setText("◉");
        buco13.setMaximumSize(new Dimension(22, 24));
        buco13.setMinimumSize(new Dimension(22, 20));
        buco13.setPreferredSize(new Dimension(22, 30));
        buco13.setSize(new Dimension(45, 30));

        buco15.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco15.setForeground(new Color(65, 43, 30));
        buco15.setHorizontalAlignment(SwingConstants.CENTER);
        buco15.setText("◉");
        buco15.setMaximumSize(new Dimension(22, 24));
        buco15.setMinimumSize(new Dimension(22, 20));
        buco15.setPreferredSize(new Dimension(22, 30));

        buco14.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco14.setForeground(new Color(65, 43, 30));
        buco14.setHorizontalAlignment(SwingConstants.CENTER);
        buco14.setText("◉");
        buco14.setMaximumSize(new Dimension(22, 24));
        buco14.setMinimumSize(new Dimension(22, 20));
        buco14.setPreferredSize(new Dimension(22, 30));

        buco16.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco16.setForeground(new Color(65, 43, 30));
        buco16.setHorizontalAlignment(SwingConstants.CENTER);
        buco16.setText("◉");
        buco16.setMaximumSize(new Dimension(22, 24));
        buco16.setMinimumSize(new Dimension(22, 20));
        buco16.setPreferredSize(new Dimension(22, 30));

        GroupLayout panRisp4Layout = new GroupLayout(panRisp4);
        panRisp4.setLayout(panRisp4Layout);
        panRisp4Layout.setHorizontalGroup(
            panRisp4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pannRisult4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(buco13, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco14, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco15, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco16, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panRisp4Layout.setVerticalGroup(
            panRisp4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp4Layout.createSequentialGroup()
                .addComponent(pannRisult4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panRisp4Layout.createSequentialGroup()
                .addGroup(panRisp4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, panRisp4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(buco15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(buco16, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panRisp4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panRisp4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(buco14, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buco13, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jSeparator4.setForeground(new Color(149, 61, 51));

        panRisp5.setBackground(new Color(206, 139, 102));

        pannRisult5.setBackground(new Color(206, 139, 102));

        indicatore17.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore17.setText("⬤");

        indicatore18.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore18.setText("⬤");

        indicatore19.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore19.setText("⬤");

        indicatore20.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore20.setText("⬤");

        GroupLayout pannRisult5Layout = new GroupLayout(pannRisult5);
        pannRisult5.setLayout(pannRisult5Layout);
        pannRisult5Layout.setHorizontalGroup(
            pannRisult5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult5Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pannRisult5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult5Layout.createSequentialGroup()
                        .addComponent(indicatore17)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore18, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult5Layout.createSequentialGroup()
                        .addComponent(indicatore19)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore20)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pannRisult5Layout.setVerticalGroup(
            pannRisult5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult5Layout.createSequentialGroup()
                .addGroup(pannRisult5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore17)
                    .addComponent(indicatore18, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pannRisult5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore20)
                    .addComponent(indicatore19))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        buco17.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco17.setForeground(new Color(65, 43, 30));
        buco17.setHorizontalAlignment(SwingConstants.CENTER);
        buco17.setText("◉");
        buco17.setMaximumSize(new Dimension(22, 24));
        buco17.setMinimumSize(new Dimension(22, 20));
        buco17.setPreferredSize(new Dimension(22, 30));
        buco17.setSize(new Dimension(45, 30));

        buco19.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco19.setForeground(new Color(65, 43, 30));
        buco19.setHorizontalAlignment(SwingConstants.CENTER);
        buco19.setText("◉");
        buco19.setMaximumSize(new Dimension(22, 24));
        buco19.setMinimumSize(new Dimension(22, 20));
        buco19.setPreferredSize(new Dimension(22, 30));

        buco18.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco18.setForeground(new Color(65, 43, 30));
        buco18.setHorizontalAlignment(SwingConstants.CENTER);
        buco18.setText("◉");
        buco18.setMaximumSize(new Dimension(22, 24));
        buco18.setMinimumSize(new Dimension(22, 20));
        buco18.setPreferredSize(new Dimension(22, 30));

        buco20.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco20.setForeground(new Color(65, 43, 30));
        buco20.setHorizontalAlignment(SwingConstants.CENTER);
        buco20.setText("◉");
        buco20.setMaximumSize(new Dimension(22, 24));
        buco20.setMinimumSize(new Dimension(22, 20));
        buco20.setPreferredSize(new Dimension(22, 30));

        GroupLayout panRisp5Layout = new GroupLayout(panRisp5);
        panRisp5.setLayout(panRisp5Layout);
        panRisp5Layout.setHorizontalGroup(
            panRisp5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pannRisult5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(buco17, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco18, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco19, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco20, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panRisp5Layout.setVerticalGroup(
            panRisp5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp5Layout.createSequentialGroup()
                .addComponent(pannRisult5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panRisp5Layout.createSequentialGroup()
                .addGroup(panRisp5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, panRisp5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(buco19, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(buco20, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panRisp5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panRisp5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(buco18, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buco17, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jSeparator5.setForeground(new Color(149, 61, 51));

        panRisp6.setBackground(new Color(206, 139, 102));

        pannRisult6.setBackground(new Color(206, 139, 102));

        indicatore21.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore21.setText("⬤");

        indicatore22.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore22.setText("⬤");

        indicatore23.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore23.setText("⬤");

        indicatore24.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore24.setText("⬤");

        GroupLayout pannRisult6Layout = new GroupLayout(pannRisult6);
        pannRisult6.setLayout(pannRisult6Layout);
        pannRisult6Layout.setHorizontalGroup(
            pannRisult6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult6Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pannRisult6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult6Layout.createSequentialGroup()
                        .addComponent(indicatore21)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore22, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult6Layout.createSequentialGroup()
                        .addComponent(indicatore23)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore24)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pannRisult6Layout.setVerticalGroup(
            pannRisult6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult6Layout.createSequentialGroup()
                .addGroup(pannRisult6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore21)
                    .addComponent(indicatore22, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pannRisult6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore24)
                    .addComponent(indicatore23))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        buco21.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco21.setForeground(new Color(65, 43, 30));
        buco21.setHorizontalAlignment(SwingConstants.CENTER);
        buco21.setText("◉");
        buco21.setMaximumSize(new Dimension(22, 24));
        buco21.setMinimumSize(new Dimension(22, 20));
        buco21.setPreferredSize(new Dimension(22, 30));
        buco21.setSize(new Dimension(45, 30));

        buco23.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco23.setForeground(new Color(65, 43, 30));
        buco23.setHorizontalAlignment(SwingConstants.CENTER);
        buco23.setText("◉");
        buco23.setMaximumSize(new Dimension(22, 24));
        buco23.setMinimumSize(new Dimension(22, 20));
        buco23.setPreferredSize(new Dimension(22, 30));

        buco22.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco22.setForeground(new Color(65, 43, 30));
        buco22.setHorizontalAlignment(SwingConstants.CENTER);
        buco22.setText("◉");
        buco22.setMaximumSize(new Dimension(22, 24));
        buco22.setMinimumSize(new Dimension(22, 20));
        buco22.setPreferredSize(new Dimension(22, 30));

        buco24.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco24.setForeground(new Color(65, 43, 30));
        buco24.setHorizontalAlignment(SwingConstants.CENTER);
        buco24.setText("◉");
        buco24.setMaximumSize(new Dimension(22, 24));
        buco24.setMinimumSize(new Dimension(22, 20));
        buco24.setPreferredSize(new Dimension(22, 30));

        GroupLayout panRisp6Layout = new GroupLayout(panRisp6);
        panRisp6.setLayout(panRisp6Layout);
        panRisp6Layout.setHorizontalGroup(
            panRisp6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pannRisult6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(buco21, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco22, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco23, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco24, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panRisp6Layout.setVerticalGroup(
            panRisp6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp6Layout.createSequentialGroup()
                .addComponent(pannRisult6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panRisp6Layout.createSequentialGroup()
                .addGroup(panRisp6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, panRisp6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(buco23, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(buco24, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panRisp6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panRisp6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(buco22, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buco21, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jSeparator6.setForeground(new Color(149, 61, 51));

        panRisp7.setBackground(new Color(206, 139, 102));

        pannRisult7.setBackground(new Color(206, 139, 102));

        indicatore25.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore25.setText("⬤");

        indicatore26.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore26.setText("⬤");

        indicatore27.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore27.setText("⬤");

        indicatore28.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore28.setText("⬤");

        GroupLayout pannRisult7Layout = new GroupLayout(pannRisult7);
        pannRisult7.setLayout(pannRisult7Layout);
        pannRisult7Layout.setHorizontalGroup(
            pannRisult7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult7Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pannRisult7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult7Layout.createSequentialGroup()
                        .addComponent(indicatore25)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore26, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult7Layout.createSequentialGroup()
                        .addComponent(indicatore27)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore28)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pannRisult7Layout.setVerticalGroup(
            pannRisult7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult7Layout.createSequentialGroup()
                .addGroup(pannRisult7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore25)
                    .addComponent(indicatore26, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pannRisult7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore28)
                    .addComponent(indicatore27))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        buco25.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco25.setForeground(new Color(65, 43, 30));
        buco25.setHorizontalAlignment(SwingConstants.CENTER);
        buco25.setText("◉");
        buco25.setMaximumSize(new Dimension(22, 24));
        buco25.setMinimumSize(new Dimension(22, 20));
        buco25.setPreferredSize(new Dimension(22, 30));
        buco25.setSize(new Dimension(45, 30));

        buco27.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco27.setForeground(new Color(65, 43, 30));
        buco27.setHorizontalAlignment(SwingConstants.CENTER);
        buco27.setText("◉");
        buco27.setMaximumSize(new Dimension(22, 24));
        buco27.setMinimumSize(new Dimension(22, 20));
        buco27.setPreferredSize(new Dimension(22, 30));

        buco26.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco26.setForeground(new Color(65, 43, 30));
        buco26.setHorizontalAlignment(SwingConstants.CENTER);
        buco26.setText("◉");
        buco26.setMaximumSize(new Dimension(22, 24));
        buco26.setMinimumSize(new Dimension(22, 20));
        buco26.setPreferredSize(new Dimension(22, 30));

        buco28.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco28.setForeground(new Color(65, 43, 30));
        buco28.setHorizontalAlignment(SwingConstants.CENTER);
        buco28.setText("◉");
        buco28.setMaximumSize(new Dimension(22, 24));
        buco28.setMinimumSize(new Dimension(22, 20));
        buco28.setPreferredSize(new Dimension(22, 30));

        GroupLayout panRisp7Layout = new GroupLayout(panRisp7);
        panRisp7.setLayout(panRisp7Layout);
        panRisp7Layout.setHorizontalGroup(
            panRisp7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pannRisult7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(buco25, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco26, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco27, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco28, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panRisp7Layout.setVerticalGroup(
            panRisp7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp7Layout.createSequentialGroup()
                .addComponent(pannRisult7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panRisp7Layout.createSequentialGroup()
                .addGroup(panRisp7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, panRisp7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(buco27, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(buco28, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panRisp7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panRisp7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(buco26, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buco25, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jSeparator7.setForeground(new Color(149, 61, 51));

        panRisp8.setBackground(new Color(206, 139, 102));

        pannRisult8.setBackground(new Color(206, 139, 102));

        indicatore29.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore29.setText("⬤");

        indicatore30.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore30.setText("⬤");

        indicatore31.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore31.setText("⬤");

        indicatore32.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore32.setText("⬤");

        GroupLayout pannRisult8Layout = new GroupLayout(pannRisult8);
        pannRisult8.setLayout(pannRisult8Layout);
        pannRisult8Layout.setHorizontalGroup(
            pannRisult8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult8Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pannRisult8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult8Layout.createSequentialGroup()
                        .addComponent(indicatore29)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore30, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult8Layout.createSequentialGroup()
                        .addComponent(indicatore31)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore32)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pannRisult8Layout.setVerticalGroup(
            pannRisult8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult8Layout.createSequentialGroup()
                .addGroup(pannRisult8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore29)
                    .addComponent(indicatore30, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pannRisult8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore32)
                    .addComponent(indicatore31))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        buco29.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco29.setForeground(new Color(65, 43, 30));
        buco29.setHorizontalAlignment(SwingConstants.CENTER);
        buco29.setText("◉");
        buco29.setMaximumSize(new Dimension(22, 24));
        buco29.setMinimumSize(new Dimension(22, 20));
        buco29.setPreferredSize(new Dimension(22, 30));
        buco29.setSize(new Dimension(45, 30));

        buco31.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco31.setForeground(new Color(65, 43, 30));
        buco31.setHorizontalAlignment(SwingConstants.CENTER);
        buco31.setText("◉");
        buco31.setMaximumSize(new Dimension(22, 24));
        buco31.setMinimumSize(new Dimension(22, 20));
        buco31.setPreferredSize(new Dimension(22, 30));

        buco30.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco30.setForeground(new Color(65, 43, 30));
        buco30.setHorizontalAlignment(SwingConstants.CENTER);
        buco30.setText("◉");
        buco30.setMaximumSize(new Dimension(22, 24));
        buco30.setMinimumSize(new Dimension(22, 20));
        buco30.setPreferredSize(new Dimension(22, 30));

        buco32.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco32.setForeground(new Color(65, 43, 30));
        buco32.setHorizontalAlignment(SwingConstants.CENTER);
        buco32.setText("◉");
        buco32.setMaximumSize(new Dimension(22, 24));
        buco32.setMinimumSize(new Dimension(22, 20));
        buco32.setPreferredSize(new Dimension(22, 30));

        GroupLayout panRisp8Layout = new GroupLayout(panRisp8);
        panRisp8.setLayout(panRisp8Layout);
        panRisp8Layout.setHorizontalGroup(
            panRisp8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pannRisult8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(buco29, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco30, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco31, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco32, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panRisp8Layout.setVerticalGroup(
            panRisp8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp8Layout.createSequentialGroup()
                .addComponent(pannRisult8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panRisp8Layout.createSequentialGroup()
                .addGroup(panRisp8Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, panRisp8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(buco31, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(buco32, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panRisp8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panRisp8Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(buco30, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buco29, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jSeparator8.setForeground(new Color(149, 61, 51));

        panRisp9.setBackground(new Color(206, 139, 102));

        pannRisult9.setBackground(new Color(206, 139, 102));

        indicatore33.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore33.setText("⬤");

        indicatore34.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore34.setText("⬤");

        indicatore35.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore35.setText("⬤");

        indicatore36.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore36.setText("⬤");

        GroupLayout pannRisult9Layout = new GroupLayout(pannRisult9);
        pannRisult9.setLayout(pannRisult9Layout);
        pannRisult9Layout.setHorizontalGroup(
            pannRisult9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult9Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pannRisult9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult9Layout.createSequentialGroup()
                        .addComponent(indicatore33)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore34, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult9Layout.createSequentialGroup()
                        .addComponent(indicatore35)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore36)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pannRisult9Layout.setVerticalGroup(
            pannRisult9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult9Layout.createSequentialGroup()
                .addGroup(pannRisult9Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore33)
                    .addComponent(indicatore34, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pannRisult9Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore36)
                    .addComponent(indicatore35))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        buco33.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco33.setForeground(new Color(65, 43, 30));
        buco33.setHorizontalAlignment(SwingConstants.CENTER);
        buco33.setText("◉");
        buco33.setMaximumSize(new Dimension(22, 24));
        buco33.setMinimumSize(new Dimension(22, 20));
        buco33.setPreferredSize(new Dimension(22, 30));
        buco33.setSize(new Dimension(45, 30));

        buco35.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco35.setForeground(new Color(65, 43, 30));
        buco35.setHorizontalAlignment(SwingConstants.CENTER);
        buco35.setText("◉");
        buco35.setMaximumSize(new Dimension(22, 24));
        buco35.setMinimumSize(new Dimension(22, 20));
        buco35.setPreferredSize(new Dimension(22, 30));

        buco34.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco34.setForeground(new Color(65, 43, 30));
        buco34.setHorizontalAlignment(SwingConstants.CENTER);
        buco34.setText("◉");
        buco34.setMaximumSize(new Dimension(22, 24));
        buco34.setMinimumSize(new Dimension(22, 20));
        buco34.setPreferredSize(new Dimension(22, 30));

        buco36.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco36.setForeground(new Color(65, 43, 30));
        buco36.setHorizontalAlignment(SwingConstants.CENTER);
        buco36.setText("◉");
        buco36.setMaximumSize(new Dimension(22, 24));
        buco36.setMinimumSize(new Dimension(22, 20));
        buco36.setPreferredSize(new Dimension(22, 30));

        GroupLayout panRisp9Layout = new GroupLayout(panRisp9);
        panRisp9.setLayout(panRisp9Layout);
        panRisp9Layout.setHorizontalGroup(
            panRisp9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pannRisult9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(buco33, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco34, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco35, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco36, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panRisp9Layout.setVerticalGroup(
            panRisp9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp9Layout.createSequentialGroup()
                .addComponent(pannRisult9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panRisp9Layout.createSequentialGroup()
                .addGroup(panRisp9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, panRisp9Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(buco35, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(buco36, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panRisp9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panRisp9Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(buco34, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buco33, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jSeparator9.setForeground(new Color(149, 61, 51));

        panRisp10.setBackground(new Color(206, 139, 102));

        pannRisult10.setBackground(new Color(206, 139, 102));

        indicatore37.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore37.setText("⬤");

        indicatore38.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore38.setText("⬤");

        indicatore39.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore39.setText("⬤");

        indicatore40.setFont(new Font(".SF NS Text", 0, 8)); // NOI18N
        indicatore40.setText("⬤");

        GroupLayout pannRisult10Layout = new GroupLayout(pannRisult10);
        pannRisult10.setLayout(pannRisult10Layout);
        pannRisult10Layout.setHorizontalGroup(
            pannRisult10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult10Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pannRisult10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult10Layout.createSequentialGroup()
                        .addComponent(indicatore37)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore38, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, pannRisult10Layout.createSequentialGroup()
                        .addComponent(indicatore39)
                        .addGap(18, 18, 18)
                        .addComponent(indicatore40)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pannRisult10Layout.setVerticalGroup(
            pannRisult10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pannRisult10Layout.createSequentialGroup()
                .addGroup(pannRisult10Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore37)
                    .addComponent(indicatore38, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pannRisult10Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(indicatore40)
                    .addComponent(indicatore39))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        buco37.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco37.setForeground(new Color(65, 43, 30));
        buco37.setHorizontalAlignment(SwingConstants.CENTER);
        buco37.setText("◉");
        buco37.setMaximumSize(new Dimension(22, 24));
        buco37.setMinimumSize(new Dimension(22, 20));
        buco37.setPreferredSize(new Dimension(22, 30));
        buco37.setSize(new Dimension(45, 30));

        buco39.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco39.setForeground(new Color(65, 43, 30));
        buco39.setHorizontalAlignment(SwingConstants.CENTER);
        buco39.setText("◉");
        buco39.setMaximumSize(new Dimension(22, 24));
        buco39.setMinimumSize(new Dimension(22, 20));
        buco39.setPreferredSize(new Dimension(22, 30));

        buco38.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco38.setForeground(new Color(65, 43, 30));
        buco38.setHorizontalAlignment(SwingConstants.CENTER);
        buco38.setText("◉");
        buco38.setMaximumSize(new Dimension(22, 24));
        buco38.setMinimumSize(new Dimension(22, 20));
        buco38.setPreferredSize(new Dimension(22, 30));

        buco40.setFont(new Font(".SF NS Text", 0, 18)); // NOI18N
        buco40.setForeground(new Color(65, 43, 30));
        buco40.setHorizontalAlignment(SwingConstants.CENTER);
        buco40.setText("◉");
        buco40.setMaximumSize(new Dimension(22, 24));
        buco40.setMinimumSize(new Dimension(22, 20));
        buco40.setPreferredSize(new Dimension(22, 30));

        GroupLayout panRisp10Layout = new GroupLayout(panRisp10);
        panRisp10.setLayout(panRisp10Layout);
        panRisp10Layout.setHorizontalGroup(
            panRisp10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pannRisult10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(buco37, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco38, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco39, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buco40, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        panRisp10Layout.setVerticalGroup(
            panRisp10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panRisp10Layout.createSequentialGroup()
                .addComponent(pannRisult10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panRisp10Layout.createSequentialGroup()
                .addGroup(panRisp10Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, panRisp10Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(buco39, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(buco40, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panRisp10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panRisp10Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(buco38, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buco37, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        GroupLayout pannelloDiGiocoLayout = new GroupLayout(pannelloDiGioco);
        pannelloDiGioco.setLayout(pannelloDiGiocoLayout);
        pannelloDiGiocoLayout.setHorizontalGroup(
            pannelloDiGiocoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, pannelloDiGiocoLayout.createSequentialGroup()
                .addGroup(pannelloDiGiocoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(panRisp1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addComponent(panRisp2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panRisp3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator3)
                    .addComponent(panRisp4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator4)
                    .addComponent(panRisp5, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator5, GroupLayout.Alignment.TRAILING)
                    .addComponent(panRisp6, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator6)
                    .addComponent(panRisp7, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator7)
                    .addComponent(panRisp8, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator8)
                    .addComponent(panRisp9, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator9, GroupLayout.Alignment.TRAILING)
                    .addComponent(panRisp10, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pannelloTentativi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        pannelloDiGiocoLayout.setVerticalGroup(
            pannelloDiGiocoLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(pannelloTentativi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pannelloDiGiocoLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(panRisp10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panRisp9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, GroupLayout.PREFERRED_SIZE, 3, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panRisp8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, GroupLayout.PREFERRED_SIZE, 3, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panRisp7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panRisp6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panRisp5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panRisp4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panRisp3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 3, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panRisp2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panRisp1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(pannelloCodice, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pannelloScrittaMM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pannelloDiGioco, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
            .addComponent(pannelloControllo, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pannelloCodice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(pannelloScrittaMM, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pannelloDiGioco, GroupLayout.PREFERRED_SIZE, 631, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pannelloControllo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold desc="Variabili della grafica">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnPrecedente;
    private JButton btnSuccessivo;
    private JLabel buco1;
    private JLabel buco10;
    private JLabel buco11;
    private JLabel buco12;
    private JLabel buco13;
    private JLabel buco14;
    private JLabel buco15;
    private JLabel buco16;
    private JLabel buco17;
    private JLabel buco18;
    private JLabel buco19;
    private JLabel buco2;
    private JLabel buco20;
    private JLabel buco21;
    private JLabel buco22;
    private JLabel buco23;
    private JLabel buco24;
    private JLabel buco25;
    private JLabel buco26;
    private JLabel buco27;
    private JLabel buco28;
    private JLabel buco29;
    private JLabel buco3;
    private JLabel buco30;
    private JLabel buco31;
    private JLabel buco32;
    private JLabel buco33;
    private JLabel buco34;
    private JLabel buco35;
    private JLabel buco36;
    private JLabel buco37;
    private JLabel buco38;
    private JLabel buco39;
    private JLabel buco4;
    private JLabel buco40;
    private JLabel buco5;
    private JLabel buco6;
    private JLabel buco7;
    private JLabel buco8;
    private JLabel buco9;
    private JLabel bucoTentativo1;
    private JLabel bucoTentativo10;
    private JLabel bucoTentativo2;
    private JLabel bucoTentativo3;
    private JLabel bucoTentativo4;
    private JLabel bucoTentativo5;
    private JLabel bucoTentativo6;
    private JLabel bucoTentativo7;
    private JLabel bucoTentativo8;
    private JLabel bucoTentativo9;
    private ButtonGroup buttonGroup1;
    private JLabel indicatore1;
    private JLabel indicatore10;
    private JLabel indicatore11;
    private JLabel indicatore12;
    private JLabel indicatore13;
    private JLabel indicatore14;
    private JLabel indicatore15;
    private JLabel indicatore16;
    private JLabel indicatore17;
    private JLabel indicatore18;
    private JLabel indicatore19;
    private JLabel indicatore2;
    private JLabel indicatore20;
    private JLabel indicatore21;
    private JLabel indicatore22;
    private JLabel indicatore23;
    private JLabel indicatore24;
    private JLabel indicatore25;
    private JLabel indicatore26;
    private JLabel indicatore27;
    private JLabel indicatore28;
    private JLabel indicatore29;
    private JLabel indicatore3;
    private JLabel indicatore30;
    private JLabel indicatore31;
    private JLabel indicatore32;
    private JLabel indicatore33;
    private JLabel indicatore34;
    private JLabel indicatore35;
    private JLabel indicatore36;
    private JLabel indicatore37;
    private JLabel indicatore38;
    private JLabel indicatore39;
    private JLabel indicatore4;
    private JLabel indicatore40;
    private JLabel indicatore5;
    private JLabel indicatore6;
    private JLabel indicatore7;
    private JLabel indicatore8;
    private JLabel indicatore9;
    private JProgressBar jProgressBar1;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private JSeparator jSeparator4;
    private JSeparator jSeparator5;
    private JSeparator jSeparator6;
    private JSeparator jSeparator7;
    private JSeparator jSeparator8;
    private JSeparator jSeparator9;
    private JPanel panRisp1;
    private JPanel panRisp10;
    private JPanel panRisp2;
    private JPanel panRisp3;
    private JPanel panRisp4;
    private JPanel panRisp5;
    private JPanel panRisp6;
    private JPanel panRisp7;
    private JPanel panRisp8;
    private JPanel panRisp9;
    private JPanel pannRisult1;
    private JPanel pannRisult10;
    private JPanel pannRisult2;
    private JPanel pannRisult3;
    private JPanel pannRisult4;
    private JPanel pannRisult5;
    private JPanel pannRisult6;
    private JPanel pannRisult7;
    private JPanel pannRisult8;
    private JPanel pannRisult9;
    private JPanel pannelloBtns;
    private JPanel pannelloCodice;
    private JPanel pannelloControllo;
    private JPanel pannelloDiGioco;
    private JPanel pannelloScrittaMM;
    private JPanel pannelloTentativi;
    private JLabel pioloArancio;
    private JLabel pioloBlu;
    private JLabel pioloCiano;
    private JLabel pioloCodice1;
    private JLabel pioloCodice2;
    private JLabel pioloCodice3;
    private JLabel pioloCodice4;
    private JLabel pioloGiallo;
    private JLabel pioloRosa;
    private JLabel pioloRosso;
    private JLabel pioloVerde;
    private JLabel pioloViola;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>

    // ArrayList contenente i colori del codice da indovinare
    private ArrayList<Color> codice;
    // Array contenenti gli 8 pioli
    private JLabel[] pioli;
    // Array contenenti i 40 indicatori dove vengono indicati i pioli corretti e quelli solamente presenti quando si effettua un tentativo per indovinare il codice
    private JLabel[] indicatori;
    // Array contenente tutti i buchi dove vengono inseriti i pioli
    private JLabel[] buchi;
    // Array dei buchi del pannello destro dove viene indicato se è stato indovinato il codice ed il numero massimo di tentativi disponibili
    private JLabel[] buchiTentativi;
    // Array dei 4 pioli della soluzione del codice
    private JLabel[] pioliCodice;

    // Questo è l'indice del buco correntemente selezionato dall'utente (l'indice dell'array "buchi")
    private int indiceBucoCorrente;
    // Colore di default dei buchi (buchi vuoti)
    private final Color coloreBuchi = new Color(65, 43, 30);
    // Variabile che contiene il colore che era presente sul buco prima che il selettore bianco lo sovrascrivesse
    private Color coloreSottoSelettore = coloreBuchi;
    private Settaggi settaggi;
    // Le seguenti due variabili sono calcolate a seconda della difficoltà selezionata
    // Booleano che indica se sono accettati colori duplicati nel codice da indovinare
    private boolean duplicati;
    // Indica il numero massimo di colori con cui si gioca la partita
    private int limiteColori;

    public MasterMind() {
        initComponents();
        // Imposto che il frame non possa essere ridimensionato
        setResizable(false);

        // Inizializza gli array
        initVariables();
        // Carico le impostazioni della partita dal file dei settaggi (stesso metodo del metodo caricaSettaggi() della Classe Impostazioni)
        caricaSettaggi();
        // Inizializzo gli ActionListeners per gli 8 bottoni dei pioli e per i due pulsanti di precedente e successivo
        initActionListeners();
        // Genero il codice da indovinare
        generaCodice();
        // Mostro un JMessageDialog con il funzionamento del gioco
        mostraFunzionamento();

        // Per testare visualizzo subito la soluzione sui 4 pioli del codice
        // for (int i=0; i<4; i++)
        //     pioliCodice[i].setForeground(codice.get(i));
    }

    // Metodi setup
    private void initVariables() {
        pioliCodice = new JLabel[] {
                pioloCodice1, pioloCodice2, pioloCodice3, pioloCodice4
        };

        pioli = new JLabel[] {
                pioloRosso, pioloRosa, pioloArancio, pioloCiano,
                pioloVerde, pioloGiallo, pioloBlu, pioloViola
        };

        indicatori = new JLabel[] {
                indicatore1, indicatore2, indicatore3, indicatore4,
                indicatore5, indicatore6, indicatore7, indicatore8,
                indicatore9, indicatore10, indicatore11, indicatore12,
                indicatore13, indicatore14, indicatore15, indicatore16,
                indicatore17, indicatore18, indicatore19, indicatore20,
                indicatore21, indicatore22, indicatore23, indicatore24,
                indicatore25, indicatore26, indicatore27, indicatore28,
                indicatore29, indicatore30, indicatore31, indicatore32,
                indicatore33, indicatore34, indicatore35, indicatore36,
                indicatore37, indicatore38, indicatore39, indicatore40,
        };

        buchi = new JLabel[] {
                buco1, buco2, buco3, buco4,
                buco5, buco6, buco7, buco8,
                buco9, buco10, buco11, buco12,
                buco13, buco14, buco15, buco16,
                buco17, buco18, buco19, buco20,
                buco21, buco22, buco23, buco24,
                buco25, buco26, buco27, buco28,
                buco29, buco30, buco31, buco32,
                buco33, buco34, buco35, buco36,
                buco37, buco38, buco39, buco40,
        };

        buchiTentativi = new JLabel[] {
                bucoTentativo1, bucoTentativo2, bucoTentativo3, bucoTentativo4,
                bucoTentativo5, bucoTentativo6, bucoTentativo7, bucoTentativo8,
                bucoTentativo9, bucoTentativo10,
        };

        // Imposto l'indicatore di selezione sul primo buco
        buchi[0].setForeground(Color.WHITE);
    }

    private void caricaSettaggi() {
        try {
            FileInputStream fis = new FileInputStream("settaggi.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            settaggi = fis.available() > 0 ? (Settaggi) ois.readObject() : new Settaggi();

            ois.close();
            fis.close();
        } catch (Exception ex) {
            settaggi = new Settaggi();
        }

        // Imposto l'indicatore bianco per il numero massimo di tentativi
        buchiTentativi[settaggi.getNumTentativi() - 1].setForeground(Color.WHITE);
    }

    private void initActionListeners() {
        // Imposto che, quando viene premuta la x per chiudere la finestra il frame viene chiuso ma il programma non termina, viene invece ricaricato il frame del menu
        // Cioè è possibile facendo l'override all'evento di chiusura del frame
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dispose();
                EventQueue.invokeLater(() -> new Menu().setVisible(true));
            }
        });

        btnPrecedente.addActionListener(e -> bucoPrecedente());
        btnSuccessivo.addActionListener(e -> bucoSuccessivo());


        // Aggiungo un MouseListener alle 8 label dei pioli per renderli cliccabili
        int cont = 0;
        for(JLabel piolo: pioli) {
            int finalCont = cont;
            piolo.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Quando un piolo viene cliccato esso richiama il metodo riempiBucoConPiolo passandogli l'indice del piolo che è stato cliccato
                    riempiBucoConPiolo(finalCont);
                }
            });
            cont++;
        }

        // Imposto il frame come focusable per poter ricevere gli eventi della tastiera
        setFocusable(true);
        setFocusableWindowState(true);
        requestFocusInWindow();

        // Aggiungo i listener per la tastiera
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Controllo se vengono premuti i tasti da 1 a 8 oppure freccia a sinistra / freccia a destra
                switch (e.getKeyCode()) {
                    // freccia indietro / avanti
                    case 37 -> bucoPrecedente();
                    case 39 -> bucoSuccessivo();
                    // tasti numerici
                    case 49 -> riempiBucoConPiolo(0);
                    case 50 -> riempiBucoConPiolo(1);
                    case 51 -> riempiBucoConPiolo(2);
                    case 52 -> riempiBucoConPiolo(3);
                    case 53 -> riempiBucoConPiolo(4);
                    case 54 -> riempiBucoConPiolo(5);
                    case 55 -> riempiBucoConPiolo(6);
                    case 56 -> riempiBucoConPiolo(7);
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    private void generaCodice() {
        codice = new ArrayList<>();

        // A seconda della difficoltà decido il limite di colori e la possibilità di generare un codice con duplicati
        switch (settaggi.getDifficolta()) {
            case Facile -> {
                limiteColori = 4;
                duplicati = false;
            }
            case Normale -> {
                limiteColori = 6;
                duplicati = false;
            }
            case Difficile -> {
                limiteColori = 6;
                duplicati = true;
            }
            case Hardcore -> {
                limiteColori = 8;
                duplicati = true;
            }
            default -> {
                limiteColori = 6;
                duplicati = false;
            }
        }

        // Genero quattro colori attraverso il metodo getRandom della classe Colori
        for(int i=0; i<4; i++) {
            Color daAggiungereAlCodice;
            // Se non vengono accettati duplicati rigenero il colore finchè è già presente nel codice
            if (!duplicati) {
                do {
                    daAggiungereAlCodice = ColoriPioli.getRandom(limiteColori);
                } while (codice.contains(daAggiungereAlCodice));
            }
            else
                daAggiungereAlCodice = ColoriPioli.getRandom(limiteColori);

            codice.add(daAggiungereAlCodice);
        }

        // Disattivo quei pioli con non servono per via del limite di colori con cui si gioca la partita
        int bottoniDisattivi = 8 - limiteColori;
        for (int i=7; i>(7 - bottoniDisattivi); i--) {
            pioli[i].setEnabled(false);
            // Rimuovo anche il MouseListenr
            pioli[i].removeMouseListener(pioli[i].getMouseListeners()[0]);
        }
    }

    private void mostraFunzionamento() {
        JOptionPane.showMessageDialog(null, """
                Per selezionare il piolo da inserire è possibile usare anche usare i tasti numerici.
                Ad esempio, per inserire il piolo rosso in un buco, puoi utilizzare il tasto "1", per inserire il piolo rosa puoi usare il tasto "2" etc...
                
                Inoltre, è possibile premere i bottoni "precedente" e "successivo" anche premendo rispettivamente i tasti "freccia indietro" e "freccia avanti".
                
                Buona fortuna! 
                """);
    }



    // Metodi di funzionamento
    private void riempiBucoConPiolo(int indicePiolo) {
        // Prende il colore corrispondente al piolo cliccato e lo imposto sul buco attualmente selezionato
        Color colorePiolo = pioli[indicePiolo].getForeground();
        buchi[indiceBucoCorrente].setForeground(colorePiolo);
        buchi[indiceBucoCorrente].setText("⬤");

        // Inoltre imposto il coloreSottoSelettore
        coloreSottoSelettore = buchi[indiceBucoCorrente].getForeground();
    }

    private void bucoPrecedente() {
        // Controllo se sotto al selettore era presente un piolo inserito e se si lo rimetto altrimenti reimposto il colore standard al buco
        if(ColoriPioli.esisteColore(coloreSottoSelettore)) {
            buchi[indiceBucoCorrente].setForeground(coloreSottoSelettore);
            buchi[indiceBucoCorrente].setText("⬤");
        }
        else
            buchi[indiceBucoCorrente].setForeground(coloreBuchi);

        // Se il selettore è correntemente al primo buco di un tentativo non permetto di tornare indietro alla riga del tentativo precedente
        if(indiceBucoCorrente == 0 || ((indiceBucoCorrente + 1) % 4) == 1)
            JOptionPane.showMessageDialog(null, "Non puoi tornare al tentativo precedente");
        else
            indiceBucoCorrente--;

        // Salvo il colore presente sul nuovo buco selezionato e poi ci sovrascrivo il selettore bianco
        coloreSottoSelettore = buchi[indiceBucoCorrente].getForeground();
        buchi[indiceBucoCorrente].setForeground(Color.WHITE);
        buchi[indiceBucoCorrente].setText("◉");
    }

    private void bucoSuccessivo() {
        // Controllo se sotto al selettore era presente un piolo inserito e se si lo rimetto altrimenti reimposto il colore standard al buco
        if(ColoriPioli.esisteColore(coloreSottoSelettore)) {
            buchi[indiceBucoCorrente].setForeground(coloreSottoSelettore);
            buchi[indiceBucoCorrente].setText("⬤");
        }
        else
            buchi[indiceBucoCorrente].setForeground(coloreBuchi);

        // Se il selettore è correntemente al quarto buco di un tentativo passo al tentativo successivo
        if(((indiceBucoCorrente + 1) % 4) == 0)
            terminaTentativo();
        else
            indiceBucoCorrente++;

        // Salvo il colore presente sul nuovo buco selezionato e poi ci sovrascrivo il selettore bianco
        coloreSottoSelettore = buchi[indiceBucoCorrente].getForeground();
        buchi[indiceBucoCorrente].setForeground(Color.WHITE);
        buchi[indiceBucoCorrente].setText("◉");
    }

    private void terminaTentativo() {
        // Creo un array con i quattro colori selezionati
        Color[] coloriBuchiTentativo = new Color[] {
                buchi[indiceBucoCorrente - 3].getForeground(),
                buchi[indiceBucoCorrente - 2].getForeground(),
                buchi[indiceBucoCorrente - 1].getForeground(),
                buchi[indiceBucoCorrente].getForeground(),
        };

        // Se sono stati lasciati buchi liberi non permetto di passare alla riga del prossimo tentativo
        if(!controllaCompletezzaTentativo(coloriBuchiTentativo)) {
            JOptionPane.showMessageDialog(null, "Non hai ancora messo un piolo su tutti e quattro i buchi.");
            return;
        }

        // Controllo se l'utente ha vinto, perso, o se può continuare a giocare
        int stato = controllaCorrettezzaTentativo(coloriBuchiTentativo);

        if(stato == -1)
            sconfitta();
        else if(stato == 1)
            vittoria();
        else
            indiceBucoCorrente++;
    }

    private boolean controllaCompletezzaTentativo(Color[] coloriBuchiTentativo) {
        // Utilizzo il metodo esistonoTuttiColori per controllare che tutti i colori dei buchi della riga del tentativo siano validi colori dei pioli
        return ColoriPioli.esistonoTuttiColori(coloriBuchiTentativo);
    }

    /* "Algoritmo" per controllare la correttezza di un codice
    Valori int che ritorna:
    1 in caso di vittoria
    -1 in caso di sconfitta
    0 se può continuare a giocare
     */
    private int controllaCorrettezzaTentativo(Color[] coloriBuchiTentativo) {
        // corretti sono quei colori (inseriti dall'utente) presenti nel codice della soluzione ed anche nella posizione corretta
        // presenti sono quei colori che sono presenti nel codice della soluzione ma non nella posizione corretta
        int corretti = 0, presenti = 0;

        // Salvo qui colori già controllati e che sono anche presenti nel codice della soluzione
        ArrayList<Color> controllati = new ArrayList<>();

        // Controllo prima se c'è qualche colore corretto confrontando pari pari il codice della soluzione con quello inserito dall'utente
        for (int i=0; i<4; i++) {
            // Se il colore inserito dall'utente è presente nel codice della soluzione allo stesso indice
            if(codice.get(i).equals(coloriBuchiTentativo[i])) {
                // Aggiungo il colore all'array di quelli controllati
                controllati.add(coloriBuchiTentativo[i]);
                corretti++;
            }
        }

        // Ora controllo i colori che sono solamente presenti
        for (int i=0; i<4; i++) {
            // Se il colore inserito dall'utente è presente nel codice della soluzione
            if(codice.contains(coloriBuchiTentativo[i])) {
                // Se c'è la possibilità che siano presenti duplicati nel codice della soluzione
                if(duplicati) {
                    int quantColoreNelCodice = 0, quantControllati = 0;

                    // Conto la quantità di pioli del colore che sto esaminando in questo momento (coloriBuchiTentativo[i]) all'interno del codice della soluzione
                    for(Color colore: codice) {
                        if (colore.equals(coloriBuchiTentativo[i]))
                            quantColoreNelCodice++;
                    }

                    // Conto la quantità di pioli del colore che sto esaminando in questo momento (coloriBuchiTentativo[i]) all'interno dei colori già controllati
                    for(Color colore: controllati) {
                        if (colore.equals(coloriBuchiTentativo[i]))
                            quantControllati++;
                    }

                    // Se la quantità dei controllati di quel colore è minore della quantità di quel colore all'interno del codice di soluzione
                    // vuol dire che posso aggiungere 1 al numero di colori presenti
                    if(quantControllati < quantColoreNelCodice) {
                        controllati.add(coloriBuchiTentativo[i]);
                        presenti++;
                    }
                }
                // Altrimenti se non sono accettati duplicati controllo solamente che all'interno dell'array
                // dei colori controllati non sia già presente il colore che sto esaminando
                else if(!controllati.contains(coloriBuchiTentativo[i])) {
                    controllati.add(coloriBuchiTentativo[i]);
                    presenti++;
                }
            }
        }

        // Aggiorno gli indicatori di corretti e presenti
        aggiornaIndicatori(corretti, presenti);

        // Se ha indovinato correttamente i 4 colori ritorna 1
        if(corretti == 4)
            return 1;
        // Se ha esaurito i tentativi disponibili ritorna -1
        else if(((indiceBucoCorrente + 1)/4) >= settaggi.getNumTentativi())
                return -1;
        else
            return 0;
    }

    private void aggiornaIndicatori(int corretti, int presenti) {
        // L'array di indicatori contiene i 4 indicatori che sono affianco delle varie righe dei tentativi
        // quindi gli indicatori da aggiornare saranno quelli dall'indice indiceBucoCorrente -3 all'indice indiceBucoCorrente perchè vanno di pari passo
        int contatoreIndicatore = indiceBucoCorrente - 3;
        for (int i=0; i<corretti; i++) {
            indicatori[contatoreIndicatore].setForeground(Color.RED);
            contatoreIndicatore++;
        }

        for (int i=0; i<presenti; i++) {
            indicatori[contatoreIndicatore].setForeground(Color.WHITE);
            contatoreIndicatore++;
        }
    }

    private void vittoria() {
        // Segnalo nel buco del tentativo, a lato del codice inserito dall'utente, che la sequenza è corretta colorando il buco del tentativo di rosso
        buchiTentativi[indiceBucoCorrente/4].setForeground(Color.RED);
        finisciPartita();
        JOptionPane.showMessageDialog(null, "Complimenti hai indovinato il codice!\n\nPuoi chiudere la finestra del gioco per tornare al menù.");
    }

    private void sconfitta() {
        finisciPartita();
        JOptionPane.showMessageDialog(null, "Game Over, hai esaurito il numero di tentativi.\n\nPuoi chiudere la finestra del gioco per tornare al menù.");
    }

    private void finisciPartita() {
        // Disabilito i bottoni di precedente e successivo
        btnSuccessivo.setEnabled(false);
        btnPrecedente.setEnabled(false);

        // Disattivo i listeners per i tasti della tastiera
        removeWindowListener(getWindowListeners()[0]);

        // Mostro il codice corretto nei pioli dedicati alla soluzione
        for (int i=0; i<4; i++)
            pioliCodice[i].setForeground(codice.get(i));
    }
}
