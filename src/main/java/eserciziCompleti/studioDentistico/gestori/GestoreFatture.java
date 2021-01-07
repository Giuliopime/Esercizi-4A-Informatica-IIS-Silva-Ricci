package eserciziCompleti.studioDentistico.gestori;

import eserciziCompleti.studioDentistico.enums.TipiQuery.TipoQueryFattura;
import eserciziCompleti.studioDentistico.enums.ordinamento.OrdinamentoFatture;
import eserciziCompleti.studioDentistico.oggetti.Fattura;
import eserciziCompleti.studioDentistico.oggetti.Intervento;
import eserciziCompleti.studioDentistico.oggetti.Paziente;
import eserciziCompleti.studioDentistico.oggetti.filtri.FiltriFattura;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *      Singleton per gestire la lista di oggetti Fattura.
 *      La lista di oggetti Fattura viene salvata su file di record (.dat)
 * </p>
 *
 * <p>
 *      La classe presenta metodi per:
 * </p>
 *      <ul>
 *      <li>Aggiungere una Fattura </li>
 *      <li>Ottenere la lista completa delle fatture* </li>
 *      </ul>
 *
 *
 * <p>
 *     *Per ottenere una lista ordinata di fatture occorre modificare la variabile pubblica {@link GestoreFatture#ordinamentoFatture}
 * </p>
 * <p>
 *     *Per ottenere una lista filtrata di fatture occorre modificare le due variabili pubbliche {@link GestoreFatture#filtriFattura} e {@link GestoreFatture#stringaRicercaPerFiltro}
 * </p>
 */
public class GestoreFatture {
    /** Istanza del Sigleton */
    private static GestoreFatture instance;
    /** Nome del file su cui vengono salvati le fatture*/
    private static final String nomeFile = "fatture.dat";
    /** Executor utilizzato per salvare ad intervallo di tempo le fatture su file di record */
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    /** ArrayList contenente tutti gli oggetti Intervento*/
    private ArrayList<Fattura> fatture;

    /** Filtro utilizzato per filtrare l'ArrayList di fatture nel metodo {@link GestoreFatture#getFatture()} */
    public FiltriFattura filtriFattura;
    /** Stringa di ricerca utilizzata per filtrare l'ArrayList di fatture nel metodo {@link GestoreFatture#getFatture() } */
    public String stringaRicercaPerFiltro;
    /** Modalità di ordinamento delle fatture per il metodo {@link GestoreFatture#getFatture() } */
    public OrdinamentoFatture ordinamentoFatture;

    private GestoreFatture() {
        fatture = new ArrayList<>();
        try {
            caricaDaFile();
        } catch (IOException | ClassNotFoundException e) {
            salvaSuFile();
        }

        Runnable salvaPazientiSuFile = this::salvaSuFile;

        // Imposto un executor per eseguire il metodo salvaSuFile ogni 5 minuti
        executor.scheduleAtFixedRate(salvaPazientiSuFile, 0, 5, TimeUnit.MINUTES);

        // Inizializzo la variabile ordinamentoFatture e filtriFattura prendendo il valore dalle Impostazioni, gestite dal Gestore apposito
        ordinamentoFatture = GestoreImpostazioni.getInstance().getImpostazioni().getOrdinamentoFatture();
        filtriFattura = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriFattura();
    }

    /**
     * @return Istanza del singleton
     */
    public static GestoreFatture getInstance() {
        if (instance == null)
            instance = new GestoreFatture();

        return instance;
    }

    /**
     * Carica tutti le fatture presenti sul file di record nell'ArrayList di fatture
     * @throws IOException se avvengono errori con i file di record
     * @throws ClassNotFoundException se manca la classe Fattura nel progetto
     */
    public void caricaDaFile() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(GestoreGrafica.pathFileDat + nomeFile);
        ObjectInputStream ois = new ObjectInputStream(fis);

        fatture = new ArrayList<>();

        while (fis.available() > 0)
            fatture.add((Fattura) ois.readObject());

        ois.close();
        fis.close();
    }

    /**
     * Salva tutti le fatture nell'ArrayList di fatture all'interno del file di record
     */
    public void salvaSuFile() {
        try {
            FileOutputStream fos = new FileOutputStream(GestoreGrafica.pathFileDat + nomeFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Fattura i : fatture)
                oos.writeObject(i);

            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("Non è stato possibile salvare le fatture sul file dat (" + nomeFile + ").");
        }
    }

    /**
     * Aggiungi una nuova Fattura all'ArrayList di fatture
     * @param fattura oggetto Fattura da aggiungere
     */
    public void aggiungi(Fattura fattura) {
        fatture.add(fattura);
        salvaSuFile();
    }

    /**
     * Elimina tutte le fatture di un certo Paziente
     * @param IDPaziente ID del Paziente del quale rimuovere tutte le fatture
     */
    public void eliminaFattureDiPaziente(UUID IDPaziente) {
        fatture.removeIf(fattura -> fattura.getIDPaziente().equals(IDPaziente));
        salvaSuFile();
    }

    /**
     * <p>Getter per l'ArrayList di fatture</p>
     * <p> Per ottenere un ArrayList di fatture filtrato,
     * modificare le variabili pubbliche di classe {@link GestoreFatture#filtriFattura} ed {@link GestoreFatture#stringaRicercaPerFiltro}</p>
     *
     * <p> Per ottenere un ArrayList di fatture con un ordinamento specifico,
     * modificare la variabile pubblica di classe {@link GestoreFatture#ordinamentoFatture} </p>
     * @return ArrayList di oggetti Fattura
     */
    public ArrayList<Fattura> getFatture() {
        ArrayList<Fattura> listaFatture = fatture;

        if (filtriFattura != null && stringaRicercaPerFiltro != null)
            listaFatture = getFattureFiltrate();

        if (ordinamentoFatture != null)
            ordinaFatture(listaFatture);

        return listaFatture;
    }

    // Filtra l'ArrayList di fatture in base alle variabili pubbliche di classe filtriFattura e stringaRicercaPerFiltro
    public ArrayList<Fattura> getFattureFiltrate() {
        ArrayList<Fattura> fattureFiltrate = new ArrayList<>();

        ArrayList<TipoQueryFattura> tipiQuery = creaArrayQueries();

        for (TipoQueryFattura queryFattura : tipiQuery)
            fattureFiltrate.addAll(filtraFatture(queryFattura));

        // Creo un Set per ottenere una lista di fatture univoche
        Set<Fattura> fattureFiltratiUnivoche = new HashSet<>(fattureFiltrate);
        fattureFiltrate = new ArrayList<>(fattureFiltratiUnivoche);

        // Resetto il filtro e la stringa di ricerca
        filtriFattura = null;
        stringaRicercaPerFiltro = null;

        return fattureFiltrate;
    }

    // Genera un ArrayList di TipoQueryFattura da utilizzare poi nel metodo filtraFatture
    private ArrayList<TipoQueryFattura> creaArrayQueries() {
        ArrayList<TipoQueryFattura> tipiQuery = new ArrayList<>();
        if (filtriFattura.isData())
            tipiQuery.add(TipoQueryFattura.DATA_CREAZIONE);
        if (filtriFattura.isPaziente())
            tipiQuery.add(TipoQueryFattura.PAZIENTE);
        if (filtriFattura.isIntervento())
            tipiQuery.add(TipoQueryFattura.INTERVENTO);

        return tipiQuery;
    }

    // Ottiene un ArrayList di fatture filtrate in base al TipoQueryFattura e la stringaRicercaPerFiltro
    private ArrayList<Fattura> filtraFatture(TipoQueryFattura tipoQueryFattura) {
        // Creo la stream di fatture (per comodità)
        Stream<Fattura> fattureFiltrate;
        Stream<Fattura> streamFatture = fatture.stream();

        stringaRicercaPerFiltro = stringaRicercaPerFiltro.toLowerCase();

        switch (tipoQueryFattura) {
            case DATA_CREAZIONE -> fattureFiltrate = streamFatture.filter(fattura -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN);
                return dateFormat.format(new Date(fattura.getData())).toLowerCase().contains(stringaRicercaPerFiltro);
            });
            case INTERVENTO -> fattureFiltrate = streamFatture.filter(fattura -> {
                GestoreInterventi.getInstance().filtriIntervento = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriIntervento();
                GestoreInterventi.getInstance().stringaRicercaPerFiltro = stringaRicercaPerFiltro;

                ArrayList<Intervento> interventiFiltrati = GestoreInterventi.getInstance().getInterventi();
                return interventiFiltrati.stream().anyMatch(intervento -> Arrays.asList(fattura.getInterventi()).contains(intervento.getIDIntervento()));
            });
            case PAZIENTE -> fattureFiltrate = streamFatture.filter(fattura -> {
                GestorePazienti.getInstance().filtriPaziente = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriPaziente();
                GestorePazienti.getInstance().stringaRicercaPerFiltro = stringaRicercaPerFiltro;

                ArrayList<Paziente> pazientiFiltrati = GestorePazienti.getInstance().getPazienti();
                return pazientiFiltrati.stream().anyMatch(paziente -> paziente.getIDPaziente().equals(fattura.getIDPaziente()));
            });
            default -> fattureFiltrate = streamFatture.filter(fattura -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN);

                GestoreInterventi.getInstance().filtriIntervento = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriIntervento();
                GestoreInterventi.getInstance().stringaRicercaPerFiltro = stringaRicercaPerFiltro;

                ArrayList<Intervento> interventiFiltrati = GestoreInterventi.getInstance().getInterventi();

                GestorePazienti.getInstance().filtriPaziente = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriPaziente();
                GestorePazienti.getInstance().stringaRicercaPerFiltro = stringaRicercaPerFiltro;

                ArrayList<Paziente> pazientiFiltrati = GestorePazienti.getInstance().getPazienti();

                return dateFormat.format(new Date(fattura.getData())).toLowerCase().contains(stringaRicercaPerFiltro) ||
                        interventiFiltrati.stream().anyMatch(intervento -> Arrays.asList(fattura.getInterventi()).contains(intervento.getIDIntervento())) ||
                        pazientiFiltrati.stream().anyMatch(paziente -> paziente.getIDPaziente().equals(fattura.getIDPaziente()));

            });
        }

        // Ritorno un ArrayList creato dalla stream filtrata
        return fattureFiltrate.collect(Collectors.toCollection(ArrayList::new));
    }

    // Ordina le fatture di un ArrayList di fatture in base alla variabile di classe pubblica ordinamentoFatture
    private void ordinaFatture(ArrayList<Fattura> listaFatture) {
        switch (ordinamentoFatture) {
            case DATA -> listaFatture.sort(Comparator.comparing(Fattura::getData));
            case INTERVENTO -> listaFatture.sort(Comparator.comparing(fattura -> GestoreInterventi.getInstance().getInterventi().indexOf(GestoreInterventi.getInstance().getIntervento(fattura.getInterventi()[0]))));
            case PAZIENTE -> listaFatture.sort(Comparator.comparing(fattura -> GestorePazienti.getInstance().getPazienti().indexOf(GestorePazienti.getInstance().getPaziente(fattura.getIDPaziente()))));
        }

        // Inverto l'ArrayList se è stato ordinato per data (cosí da ottenere le date più recenti per prime all'interno dell'array)
        if (ordinamentoFatture.equals(OrdinamentoFatture.DATA))
            Collections.reverse(listaFatture);
    }
}
