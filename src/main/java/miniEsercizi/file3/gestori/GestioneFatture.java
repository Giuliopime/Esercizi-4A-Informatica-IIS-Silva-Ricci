package miniEsercizi.file3.gestori;

import miniEsercizi.file3.modelli.Fattura;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GestioneFatture {
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static ArrayList<Fattura> fatture = new ArrayList<>();

    static {
        try {
            caricaFattureDaFile();
        } catch (Exception e) {
            System.out.println("Errore nel caricare le fatture dal file:" +
                    "\n" + e);
        }

        Runnable salvaFattureSuFile = () -> {
            try {
                GestioneFatture.registraSuFile();
            } catch (IOException e) {
                System.out.println("Errore nel salvare su file le fatture:" +
                        "\n" + e);
            }
        };

        executor.scheduleAtFixedRate(salvaFattureSuFile, 0, 1, TimeUnit.MINUTES);
    }


    public static void caricaFattureDaFile() throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream("fatture.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        fatture = new ArrayList<>();

        while(fis.available() > 0){
            fatture.add((Fattura) ois.readObject());
        }
    }


    private static void registraSuFile() throws IOException{
        FileOutputStream fos = new FileOutputStream("fatture.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        for(Fattura c: fatture)
            oos.writeObject(c);

        oos.close();
        fos.close();
    }


    public static void nuovaFattura(Fattura fattura) {
        fatture.add(fattura);
    }

    public static void nuovaFattura(Fattura[] fattura) {
        GestioneFatture.fatture.addAll(Arrays.asList(fattura));
    }


    public static void visualizzaTutti() {
        fatture.forEach(System.out::println);
    }

    public static ArrayList<Fattura> getFatture() {
        return fatture;
    }
}
