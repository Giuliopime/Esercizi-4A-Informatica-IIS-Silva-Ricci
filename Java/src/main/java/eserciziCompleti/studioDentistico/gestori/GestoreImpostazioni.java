package eserciziCompleti.studioDentistico.gestori;

import eserciziCompleti.studioDentistico.oggetti.Impostazioni;

import java.io.*;

/**
 * <p>
 *      Singleton per gestire le Impostazioni.
 *      L'oggetto Impostazioni viene salvato su file di record (.dat)
 * </p>
 *
 * <p>
 *      La classe presenta metodi per:
 * </p>
 *      <ul>
 *      <li>Ottenere le Impostazioni </li>
 *      <li>Modificare le Impostazioni </li>
 *      </ul>
 */
public class GestoreImpostazioni {
    /** Istanza del Sigleton */
    private static GestoreImpostazioni instance;
    /** Nome del file su cui vengono salvati le impostazioni*/
    private static final String nomeFile = "impostazioni.dat";
    /** Oggetto Impostazioni */
    private Impostazioni impostazioni;

    private GestoreImpostazioni() {
        caricaDaFile();
    }

    /**
     * @return Istanza del singleton
     */
    public static GestoreImpostazioni getInstance() {
        if (instance == null)
            instance = new GestoreImpostazioni();

        return instance;
    }

    /**
     * Carica le impostazioni del file di record sulla variabile di classe {@link GestoreImpostazioni#impostazioni}
     */
    public void caricaDaFile() {
        try {
            FileInputStream fis = new FileInputStream(GestoreGrafica.pathFileDat + nomeFile);
            ObjectInputStream ois = new ObjectInputStream(fis);

            impostazioni = fis.available() > 0 ? (Impostazioni) ois.readObject() : new Impostazioni();

            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Non è stato possibile caricare le impostazioni dal file " + nomeFile);

            impostazioni = new Impostazioni();
            salvaSuFile();
        }
    }

    /**
     * Salva le impostazioni sul file di record
     */
    public void salvaSuFile() {
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

    /**
     * Modifica le impostazioni
     * @param impostazioni nuove impostazioni (già modificate)
     */
    public void modificaImpostazioni(Impostazioni impostazioni) {
        this.impostazioni = impostazioni;
        salvaSuFile();
    }

    /**
     * Getter per le impostazioni
     * @return oggetto Impostazioni
     */
    public Impostazioni getImpostazioni() {
        return impostazioni;
    }
}
