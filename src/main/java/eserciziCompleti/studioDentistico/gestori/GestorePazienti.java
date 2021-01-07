package eserciziCompleti.studioDentistico.gestori;

import eserciziCompleti.studioDentistico.enums.TipiQuery.TipoQueryPaziente;
import eserciziCompleti.studioDentistico.enums.ordinamento.OrdinamentoPazienti;
import eserciziCompleti.studioDentistico.oggetti.Paziente;
import eserciziCompleti.studioDentistico.oggetti.filtri.FiltriPaziente;

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
 *      Singleton per gestire la lista di oggetti Paziente.
 *      La lista di oggetti Paziente viene salvata su file di record (.dat)
 * </p>
 *
 * <p>
 *      La classe presenta metodi per:
 * </p>
 *      <ul>
 *      <li>Aggiungere un Paziente </li>
 *      <li>Rimuovere un Paziente </li>
 *      <li>Modificare un Paziente </li>
 *      <li>Ottenere la lista completa dei Pazienti* </li>
 *      </ul>
 *
 *
 * <p>
 *     *Per ottenere una lista ordinata di pazienti occorre modificare la variabile pubblica {@link OrdinamentoPazienti ordinamentoPazienti}
 * </p>
 * <p>
 *     *Per ottenere una lista filtrata di pazienti occorre modificare le due variabili pubbliche {@link FiltriPaziente filtriPaziente} e {@link String query}
 * </p>
 */
public class GestorePazienti {
    /** Istanza del Sigleton */
    private static GestorePazienti instance;
    /** Nome del file su cui vengono salvati i Pazienti*/
    private static final String nomeFile = "pazienti.dat";
    /** Executor utilizzato per salvare ad intervallo di tempo i pazienti su file di record */
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    /** ArrayList contenente tutti gli oggetti Paziente*/
    private ArrayList<Paziente> pazienti;

    /** Filtro utilizzato per filtrare l'ArrayList di pazienti nel metodo getPazienti() */
    public FiltriPaziente filtriPaziente;
    /** Stringa di ricerca utilizzata per filtrare l'ArrayList di pazienti nel metodo getPazienti() */
    public String stringaRicercaPerFiltro;
    /** Modalità di ordinamento dei pazienti per il metodo getPazienti() */
    public OrdinamentoPazienti ordinamentoPazienti;

    private GestorePazienti() {
        pazienti = new ArrayList<>();
        try {
            caricaPazienti();
        } catch (IOException | ClassNotFoundException e) {
            salvaPazienti();
        }

        Runnable salvaPazientiSuFile = () -> {
            salvaPazienti();
        };

        // Imposto un executor per eseguire il metodo salvaPazientiSuFile ogni 5 minuti
        executor.scheduleAtFixedRate(salvaPazientiSuFile, 0, 5, TimeUnit.MINUTES);

        // Inizializzo la variabile ordinamentoPazienti e filtriPazienti prendendo il valore dalle Impostazioni, gestite dal Gestore apposito
        ordinamentoPazienti = GestoreImpostazioni.getInstance().getImpostazioni().getOrdinamentoPazienti();
        filtriPaziente = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriPaziente();
    }

    /**
     * @return Istanza del singleton
     */
    public static GestorePazienti getInstance() {
        if (instance == null)
            instance = new GestorePazienti();

        return instance;
    }


    /**
     * Carica tutti i pazienti presenti sul file di record nell'ArrayList di pazienti
     * @throws IOException se avvengono errori con i file di record
     * @throws ClassNotFoundException se manca la classe Paziente nel progetto
     */
    public void caricaPazienti() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(GestoreGrafica.pathFileDat + nomeFile);
        ObjectInputStream ois = new ObjectInputStream(fis);

        pazienti = new ArrayList<>();

        while (fis.available() > 0)
            pazienti.add((Paziente) ois.readObject());

        ois.close();
        fis.close();
    }

    /**
     * Salva tutti i pazienti nell'ArrayList di pazienti all'interno del file di record
     */
    public void salvaPazienti() {
        try {
            FileOutputStream fos = new FileOutputStream(GestoreGrafica.pathFileDat + nomeFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Paziente p : pazienti)
                oos.writeObject(p);

            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("Non è stato possibile salvare i pazienti sul file dat (" + nomeFile + ").");
        }
    }

    /**
     * Rimuove un paziente dall'ArrayList di pazienti
     * @param IDPaziente ID del Paziente da rimuovere
     */
    public void eliminaPaziente(UUID IDPaziente) {
        pazienti.removeIf(paziente -> {
            if(paziente.getIDPaziente().equals(IDPaziente)) {
                GestoreInterventi.getInstance().eliminaInterventiDiPaziente(paziente.getIDPaziente());
                GestoreFatture.getInstance().eliminaFattureDiPaziente(paziente.getIDPaziente());
                return true;
            }
            return false;
        });
        salvaPazienti();
    }

    /**
     * Modifica un paziente già esistente all'interno dell'ArrayList di pazienti
     * @param paziente Paziente (già modificato)
     */
    public void modificaPaziente(Paziente paziente) {
        ListIterator<Paziente> iterator = pazienti.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getIDPaziente().equals(paziente.getIDPaziente())) {
                iterator.set(paziente);
                salvaPazienti();
                break;
            }
        }
    }

    /**
     * Aggiungi un nuovo Paziente all'ArrayList di pazienti
     * @param paziente oggetto Paziente da aggiungere
     */
    public void aggiungiPaziente(Paziente paziente) {
        pazienti.add(paziente);
        salvaPazienti();
    }

    /**
     * Prendere un paziente dall'ArrayList di pazienti attraverso il suo ID
     * @param IDPaziente ID del Paziente da ottenere
     * @return Paziente oppure null in caso nessun paziente venga trovato
     */
    public Paziente getPaziente(UUID IDPaziente) {
        for (Paziente paziente : pazienti) {
            if (paziente.getIDPaziente().equals(IDPaziente))
                return paziente;
        }

        return null;
    }

    /**
     * <p>Getter per l'ArrayList di pazienti</p>
     * <p> Per ottenere un ArrayList di pazienti filtrato,
     * modificare le variabili pubbliche di classe {@link GestorePazienti#filtriPaziente} ed {@link GestorePazienti#stringaRicercaPerFiltro}</p>
     *
     * <p> Per ottenere un ArrayList di pazienti con un ordinamento specifico,
     * modificare la variabile pubblica di classe {@link GestorePazienti#ordinamentoPazienti} </p>
     * @return ArrayList di oggetti Paziente
     */
    public ArrayList<Paziente> getPazienti() {
        ArrayList<Paziente> listaPazienti = pazienti;
        if (filtriPaziente != null && stringaRicercaPerFiltro != null)
            listaPazienti = getPazientiFiltrati();

        if (ordinamentoPazienti != null)
            ordinaPazienti(listaPazienti);

        return listaPazienti;
    }

    // Filtra l'ArrayList di pazienti in base alle variabili pubbliche di classe filtriPaziente e stringaRicercaPerFiltro
    private ArrayList<Paziente> getPazientiFiltrati() {
        ArrayList<Paziente> pazientiFiltrati = new ArrayList<>();

        ArrayList<TipoQueryPaziente> tipiQuery = creaArrayQueries();

        for (TipoQueryPaziente queryPaziente : tipiQuery)
            pazientiFiltrati.addAll(filtraPazienti(queryPaziente));


        // Creo un Set per ottenere una lista di pazienti univoci
        Set<Paziente> pazientiFiltratiUnivoci = new HashSet<>(pazientiFiltrati);

        pazientiFiltrati = new ArrayList<>();
        pazientiFiltrati.addAll(pazientiFiltratiUnivoci);

        // Resetto il filtro e la stringa di ricerca
        filtriPaziente = null;
        stringaRicercaPerFiltro = null;

        return pazientiFiltrati;
    }

    // Genera un ArrayList di TipoQueryPaziente da utilizzare poi nel metodo filtraPazienti
    private ArrayList<TipoQueryPaziente> creaArrayQueries() {
        ArrayList<TipoQueryPaziente> tipiQuery = new ArrayList<>();
        if (filtriPaziente.isNome())
            tipiQuery.add(TipoQueryPaziente.NOME);
        if (filtriPaziente.isCognome())
            tipiQuery.add(TipoQueryPaziente.COGNOME);
        if (filtriPaziente.isCodiceFiscale())
            tipiQuery.add(TipoQueryPaziente.CODICE_FISCALE);
        if (filtriPaziente.isDataDiNascita())
            tipiQuery.add(TipoQueryPaziente.DATA_DI_NASCITA);
        if (filtriPaziente.isOccupazione())
            tipiQuery.add(TipoQueryPaziente.OCCUPAZIONE);
        if (filtriPaziente.isLuogoDiNascita())
            tipiQuery.add(TipoQueryPaziente.LUOGO_DI_NASCITA);
        if (filtriPaziente.isNumTelefonico())
            tipiQuery.add(TipoQueryPaziente.NUM_TELEFONO);
        if (filtriPaziente.isProvincia())
            tipiQuery.add(TipoQueryPaziente.PROVINCIA);
        if (filtriPaziente.isResidenza())
            tipiQuery.add(TipoQueryPaziente.RESIDENZA);
        if (filtriPaziente.isSesso())
            tipiQuery.add(TipoQueryPaziente.SESSO);

        return tipiQuery;
    }

    // Ottiene un ArrayList di pazienti filtrati in base al TipoQueryPaziente e la stringaRicercaPerFiltro
    private ArrayList<Paziente> filtraPazienti(TipoQueryPaziente tipoQueryPaziente) {
        // Creo la stream di pazienti (per comodità)
        Stream<Paziente> pazientiFiltrati;
        Stream<Paziente> streamPazienti = pazienti.stream();

        stringaRicercaPerFiltro = stringaRicercaPerFiltro.toLowerCase();

        switch (tipoQueryPaziente) {
            case NOME -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getNome().toLowerCase().contains(stringaRicercaPerFiltro));
            case COGNOME -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getCognome().toLowerCase().contains(stringaRicercaPerFiltro));
            case LUOGO_DI_NASCITA -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getLuogoNascita().toLowerCase().contains(stringaRicercaPerFiltro));
            case RESIDENZA -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getResidenza().toLowerCase().contains(stringaRicercaPerFiltro));
            case PROVINCIA -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getProvincia().toLowerCase().contains(stringaRicercaPerFiltro));
            case OCCUPAZIONE -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getOccupazione().toLowerCase().contains(stringaRicercaPerFiltro));
            case SESSO -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getSesso().toLowerCase().contains(stringaRicercaPerFiltro));
            case NUM_TELEFONO -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getNumTelefono().toLowerCase().contains(stringaRicercaPerFiltro));
            case CODICE_FISCALE -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getCodiceFiscale().toLowerCase().contains(stringaRicercaPerFiltro));
            case IS_MAGGIORENNE -> pazientiFiltrati = streamPazienti.filter(paziente -> Boolean.toString(paziente.isMaggiorenne()).toLowerCase().contains(stringaRicercaPerFiltro));
            case DATA_DI_NASCITA -> pazientiFiltrati = streamPazienti.filter(paziente -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN);
                return dateFormat.format(new Date(paziente.getDataNascita())).toLowerCase().contains(stringaRicercaPerFiltro);
            });
            default -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getNome().toLowerCase().contains(stringaRicercaPerFiltro)
                    || paziente.getCognome().toLowerCase().contains(stringaRicercaPerFiltro)
                    || paziente.getLuogoNascita().toLowerCase().contains(stringaRicercaPerFiltro)
                    || paziente.getResidenza().toLowerCase().contains(stringaRicercaPerFiltro)
                    || paziente.getProvincia().toLowerCase().contains(stringaRicercaPerFiltro)
                    || paziente.getOccupazione().toLowerCase().contains(stringaRicercaPerFiltro)
                    || paziente.getSesso().toLowerCase().contains(stringaRicercaPerFiltro)
                    || paziente.getNumTelefono().toLowerCase().contains(stringaRicercaPerFiltro)
                    || paziente.getCodiceFiscale().toLowerCase().contains(stringaRicercaPerFiltro)
                    || String.valueOf(paziente.getDataNascita()).toLowerCase().contains(stringaRicercaPerFiltro)
                    || Boolean.toString(paziente.isMaggiorenne()).toLowerCase().contains(stringaRicercaPerFiltro));
        }

        // Ritorno un ArrayList creato dalla stream filtrata
        return pazientiFiltrati.collect(Collectors.toCollection(ArrayList::new));
    }

    // Ordina i pazienti di un ArrayList di pazienti in base alla variabile di classe pubblica ordinamentoPazienti
    private void ordinaPazienti(ArrayList<Paziente> listaPazienti) {
        switch (ordinamentoPazienti) {
            case NOME -> listaPazienti.sort(Comparator.comparing(paziente -> paziente.getNome().toLowerCase()));
            case COGNOME -> listaPazienti.sort(Comparator.comparing(paziente -> paziente.getCognome().toLowerCase()));
            case DATANASCITA -> listaPazienti.sort(Comparator.comparing(Paziente::getDataNascita));
            case DATACREAZIONE -> listaPazienti.sort(Comparator.comparing(Paziente::getDataCreazione));
            case DATAULTIMAMODIFICA -> listaPazienti.sort(Comparator.comparing(Paziente::getUltimaModifica));
        }

        // Inverto l'ArrayList se è stato ordinato per data (cosí da ottenere le date più recenti per prime all'interno dell'array)
        if (ordinamentoPazienti.equals(OrdinamentoPazienti.DATANASCITA) || ordinamentoPazienti.equals(OrdinamentoPazienti.DATACREAZIONE) || ordinamentoPazienti.equals(OrdinamentoPazienti.DATAULTIMAMODIFICA))
            Collections.reverse(listaPazienti);
    }
}
