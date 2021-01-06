package miniEsercizi.file3.gestori;

import miniEsercizi.file3.modelli.Articolo;
import miniEsercizi.file3.tipologie.TipoArticolo;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GestioneArticoli {
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static ArrayList<Articolo> articoli = new ArrayList<>();

    static {
        try {
            caricaArticoliDaFile();
        } catch (Exception e) {
            System.out.println("Errore nel caricare gli articoli dal file:" +
                    "\n" + e);
        }

        Runnable salvaArticoliSuFile = () -> {
            try {
                GestioneArticoli.registraSuFile();
            } catch (IOException e) {
                System.out.println("Errore nel salvare su file gli articoli:" +
                        "\n" + e);
            }
        };

        executor.scheduleAtFixedRate(salvaArticoliSuFile, 0, 1, TimeUnit.MINUTES);
    }


    public static void caricaArticoliDaFile() throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream("articoli.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        articoli = new ArrayList<>();

        while(fis.available() > 0){
            articoli.add((Articolo) ois.readObject());
        }
    }


    private static void registraSuFile() throws IOException{
        FileOutputStream fos = new FileOutputStream("articoli.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        for(Articolo a: articoli)
            oos.writeObject(a);

        oos.close();
        fos.close();
    }


    private static Articolo creaArticolo() {
        String nome = JOptionPane.showInputDialog("Inserisci il nome dell'articolo");


        int sceltaTipo = 0;
        do {
            try {
                String scelte = "Dare in input il numero corrispondente al tipo di articolo, tra quelli elencati:";
                int count = 1;
                for(TipoArticolo ta: TipoArticolo.values()) {
                    scelte += "\n" + count + " - " + ta.toString();
                    count++;
                }

                sceltaTipo = Integer.parseInt(JOptionPane.showInputDialog(scelte));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Inserire un numero intero per scegliere il tipo di articolo");
            }
        } while(sceltaTipo == 0);

        TipoArticolo tipoArticolo = TipoArticolo.values()[sceltaTipo-1];

        double prezzo = -1;
        do {
            try {
                prezzo = Double.parseDouble(JOptionPane.showInputDialog("Inserire il prezzo dell'articolo"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Inserire un prezzo maggiore di -1");
            }
        } while (prezzo == -1);

        return new Articolo(nome, tipoArticolo, prezzo);
    }

    public static void nuovoArticolo(Articolo articolo) {
        articoli.add(articolo);
    }

    public static void nuovoArticolo(Articolo[] articoli) {
        GestioneArticoli.articoli.addAll(Arrays.asList(articoli));
    }


    public static void visualizzaTutti() {
        articoli.forEach(System.out::println);
    }

    public static ArrayList<Articolo> getClienti() {
        return articoli;
    }
}
