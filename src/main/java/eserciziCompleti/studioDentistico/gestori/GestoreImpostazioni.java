package eserciziCompleti.studioDentistico.gestori;

import eserciziCompleti.studioDentistico.oggetti.Impostazioni;

import java.io.*;

public class GestoreImpostazioni {
    private static String nomeFile = "impostazioni.dat";
    private static GestoreImpostazioni instance;
    private Impostazioni impostazioni;

    public static GestoreImpostazioni getInstance() {
        if (instance == null)
            instance = new GestoreImpostazioni();

        return instance;
    }

    private GestoreImpostazioni() {
        caricaImpostazioni();
    }

    public void caricaImpostazioni() {
        try {
            FileInputStream fis = new FileInputStream(GestoreGrafica.pathFileDat + nomeFile);
            ObjectInputStream ois = new ObjectInputStream(fis);

            impostazioni = fis.available() > 0 ? (Impostazioni) ois.readObject() : new Impostazioni();

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Non è stato possibile caricare le impostazioni dal file " + nomeFile);

            impostazioni = new Impostazioni();
            salvaImpostazioni();
        }
    }

    public void salvaImpostazioni() {
        try {
            FileOutputStream fos = new FileOutputStream(GestoreGrafica.pathFileDat + nomeFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(impostazioni);

            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("Non è stato possibile salvare le impostazioni sul file dat (" + nomeFile + ").");
            e.printStackTrace();
        }
    }

    public void modificaImpostazioni(Impostazioni impostazioni) {
        this.impostazioni = impostazioni;
        salvaImpostazioni();
    }

    public Impostazioni getImpostazioni() {
        return impostazioni;
    }
}
