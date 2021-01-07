package eserciziCompleti.studioDentistico.gestori;

import eserciziCompleti.studioDentistico.enums.TipiQuery.TipoQueryIntervento;
import eserciziCompleti.studioDentistico.enums.ordinamento.OrdinamentoInterventi;
import eserciziCompleti.studioDentistico.oggetti.Intervento;
import eserciziCompleti.studioDentistico.oggetti.Paziente;
import eserciziCompleti.studioDentistico.oggetti.filtri.FiltriIntervento;
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
 *      Singleton per gestire la lista di oggetti Intervento.
 *      La lista di oggetti Intervento viene salvata su file di record (.dat)
 * </p>
 *
 * <p>
 *      La classe presenta metodi per:
 * </p>
 *      <ul>
 *      <li>Aggiungere un Intervento </li>
 *      <li>Rimuovere un Intervento </li>
 *      <li>Modificare un Intervento </li>
 *      <li>Ottenere la lista completa degli Interventi* </li>
 *      </ul>
 *
 *
 * <p>
 *     *Per ottenere una lista ordinata di interventi occorre modificare la variabile pubblica {@link GestoreInterventi#ordinamentoInterventi}
 * </p>
 * <p>
 *     *Per ottenere una lista filtrata di pazienti occorre modificare le due variabili pubbliche {@link GestoreInterventi#filtriIntervento} e {@link GestoreInterventi#stringaRicercaPerFiltro}
 * </p>
 */
public class GestoreInterventi {
    /** Istanza del Sigleton */
    private static GestoreInterventi instance;
    /** Nome del file su cui vengono salvati gli interventi*/
    private static final String nomeFile = "interventi.dat";
    /** Executor utilizzato per salvare ad intervallo di tempo gli interventi su file di record */
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    /** ArrayList contenente tutti gli oggetti Intervento*/
    private ArrayList<Intervento> interventi;

    /** Filtro utilizzato per filtrare l'ArrayList di interventi nel metodo {@link GestoreInterventi#getInterventi()} */
    public FiltriIntervento filtriIntervento;
    /** Stringa di ricerca utilizzata per filtrare l'ArrayList di interventi nel metodo {@link GestoreInterventi#getInterventi()} */
    public String stringaRicercaPerFiltro;
    /** Modalità di ordinamento degli interventi per il metodo {@link GestoreInterventi#getInterventi()} */
    public OrdinamentoInterventi ordinamentoInterventi;

    private GestoreInterventi() {
        interventi = new ArrayList<>();
        try {
            caricaDaFile();
        } catch (IOException | ClassNotFoundException e) {
            salvaSuFile();
        }

        Runnable salvaPazientiSuFile = this::salvaSuFile;

        // Imposto un executor per eseguire il metodo salvaSuFile ogni 5 minuti
        executor.scheduleAtFixedRate(salvaPazientiSuFile, 0, 5, TimeUnit.MINUTES);

        // Inizializzo la variabile ordinamentoInterventi e filtriIntervento prendendo il valore dalle Impostazioni, gestite dal Gestore apposito
        ordinamentoInterventi = GestoreImpostazioni.getInstance().getImpostazioni().getOrdinamentoInterventi();
        filtriIntervento = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriIntervento();
    }

    /**
     * @return Istanza del singleton
     */
    public static GestoreInterventi getInstance() {
        if (instance == null)
            instance = new GestoreInterventi();

        return instance;
    }

    /**
     * Carica tutti gli interventi presenti sul file di record nell'ArrayList di interventi
     * @throws IOException se avvengono errori con i file di record
     * @throws ClassNotFoundException se manca la classe Intervento nel progetto
     */
    public void caricaDaFile() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(GestoreGrafica.pathFileDat + nomeFile);
        ObjectInputStream ois = new ObjectInputStream(fis);

        interventi = new ArrayList<>();

        while (fis.available() > 0)
            interventi.add((Intervento) ois.readObject());

        ois.close();
        fis.close();
    }

    /**
     * Salva tutti gli interventi nell'ArrayList di interventi all'interno del file di record
     */
    public void salvaSuFile() {
        try {
            FileOutputStream fos = new FileOutputStream(GestoreGrafica.pathFileDat + nomeFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Intervento i : interventi)
                oos.writeObject(i);

            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("Non è stato possibile salvare gli interventi sul file dat (" + nomeFile + ").");
        }
    }

    /**
     * Rimuove un intervento dall'ArrayList di interventi
     * @param IDIntervento ID dell'Intervento da rimuovere
     */
    public void elimina(UUID IDIntervento) {
        interventi.removeIf(intervento -> intervento.getIDIntervento().equals(IDIntervento));
        salvaSuFile();
    }

    /**
     * Rimuove tutti gli interventi di un determinato Paziente
     * @param IDPaziente ID del Paziente del quale rimuovere tutti gli interventi
     */
    public void eliminaTuttiPerPaziente(UUID IDPaziente) {
        interventi.removeIf(intervento -> intervento.getIDPaziente().equals(IDPaziente));
        salvaSuFile();
    }

    /**
     * Modifica un intervento già esistente all'interno dell'ArrayList di interventi
     * @param intervento Intervento (già modificato)
     */
    public void modifica(Intervento intervento) {
        ListIterator<Intervento> iterator = interventi.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getIDIntervento().equals(intervento.getIDIntervento())) {
                iterator.set(intervento);
                salvaSuFile();
                break;
            }
        }
    }

    /**
     * Aggiungi un nuovo Intervento all'ArrayList di interventi
     * @param intervento oggetto Intervento da aggiungere
     */
    public void aggiungi(Intervento intervento) {
        interventi.add(intervento);
        salvaSuFile();
    }

    /**
     * Prendere un intervento dall'ArrayList di interventi attraverso il suo ID
     * @param IDIntervento ID dell'Intervento da ottenere
     * @return Intervento oppure null in caso nessun intervento venga trovato
     */
    public Intervento getIntervento(UUID IDIntervento) {
        for (Intervento intervento : interventi) {
            if (intervento.getIDIntervento().equals(IDIntervento))
                return intervento;
        }

        return null;
    }

    /**
     * <p>Getter per l'ArrayList di interventi</p>
     * <p> Per ottenere un ArrayList di interventi filtrato,
     * modificare le variabili pubbliche di classe {@link GestoreInterventi#filtriIntervento} ed {@link GestoreInterventi#stringaRicercaPerFiltro}</p>
     *
     * <p> Per ottenere un ArrayList di interventi con un ordinamento specifico,
     * modificare la variabile pubblica di classe {@link GestoreInterventi#ordinamentoInterventi} </p>
     * @return ArrayList di oggetti Intervento
     */
    public ArrayList<Intervento> getInterventi() {
        ArrayList<Intervento> listaInterventi = interventi;

        if (filtriIntervento != null && stringaRicercaPerFiltro != null)
            listaInterventi = getInterventiFiltrati();

        if (ordinamentoInterventi != null)
            ordinaInterventi(listaInterventi);

        return listaInterventi;
    }

    // Filtra l'ArrayList di interventi in base alle variabili pubbliche di classe filtraIntervento e stringaDiRicercaPerFiltro
    public ArrayList<Intervento> getInterventiFiltrati() {
        ArrayList<Intervento> interventiFiltrati = new ArrayList<>();

        ArrayList<TipoQueryIntervento> tipiQuery = creaArrayQueries();

        for (TipoQueryIntervento queryIntervento : tipiQuery)
            interventiFiltrati.addAll(filtraInterventi(queryIntervento));

        // Creo un Set per ottenere una lista di interventi univoci
        Set<Intervento> interventiFiltratiUnivoci = new HashSet<>(interventiFiltrati);
        interventiFiltrati = new ArrayList<>(interventiFiltratiUnivoci);

        // Resetto il filtro e la stringa di ricerca
        filtriIntervento = null;
        stringaRicercaPerFiltro = null;

        return interventiFiltrati;
    }

    // Genera un ArrayList di TipoQueryIntervento da utilizzare poi nel metodo filtraInterventi
    private ArrayList<TipoQueryIntervento> creaArrayQueries() {
        ArrayList<TipoQueryIntervento> tipiQuery = new ArrayList<>();
        if (filtriIntervento.isPaziente())
            tipiQuery.add(TipoQueryIntervento.PAZIENTE);
        if (filtriIntervento.isTipo())
            tipiQuery.add(TipoQueryIntervento.TIPO);
        if (filtriIntervento.isCosto())
            tipiQuery.add(TipoQueryIntervento.COSTO);
        if (filtriIntervento.isTempo())
            tipiQuery.add(TipoQueryIntervento.TEMPO);
        if (filtriIntervento.isData())
            tipiQuery.add(TipoQueryIntervento.DATA);

        return tipiQuery;
    }

    // Ottiene un ArrayList di interventi filtrati in base al TipoQueryIntervento e la stringaRicercaPerIntervento
    private ArrayList<Intervento> filtraInterventi(TipoQueryIntervento tipoQueryIntervento) {
        // Creo la stream di pazienti (per comodità)
        Stream<Intervento> interventiFiltrati;
        Stream<Intervento> streamInterventi = interventi.stream();

        stringaRicercaPerFiltro = stringaRicercaPerFiltro.toLowerCase();

        switch (tipoQueryIntervento) {
            case TIPO -> interventiFiltrati = streamInterventi.filter(intervento -> intervento.getTipoIntervento().nome.toLowerCase().contains(stringaRicercaPerFiltro));
            case COSTO -> interventiFiltrati = streamInterventi.filter(intervento -> String.valueOf(intervento.getCosto()).toLowerCase().contains(stringaRicercaPerFiltro));
            case DATA -> interventiFiltrati = streamInterventi.filter(intervento -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN);
                return dateFormat.format(new Date(intervento.getDataCreazione())).toLowerCase().contains(stringaRicercaPerFiltro);
            });
            case TEMPO -> interventiFiltrati = streamInterventi.filter(intervento -> {
                long ore = TimeUnit.MILLISECONDS.toHours(intervento.getTempoMedio());
                long minuti = TimeUnit.MILLISECONDS.toMinutes(intervento.getTempoMedio() - (ore * 3600000));
                String tempo = ore != 0 ? ore + (ore > 1 ? " ore" : " ora") : "";
                tempo += ore != 0 ? " e" : "";
                tempo += minuti == 1 ? "d 1 minuto" : " " + minuti + " minuti";

                return tempo.contains(stringaRicercaPerFiltro);
            });
            case PAZIENTE -> interventiFiltrati = streamInterventi.filter(intervento -> {
                GestorePazienti gestorePazienti = GestorePazienti.getInstance();
                gestorePazienti.filtriPaziente = new FiltriPaziente();
                gestorePazienti.stringaRicercaPerFiltro = stringaRicercaPerFiltro;
                for (Paziente paziente : gestorePazienti.getPazienti()) {
                    if (paziente.getIDPaziente().equals(intervento.getIDPaziente()))
                        return true;
                }

                return false;
            });
            default -> interventiFiltrati = streamInterventi.filter(intervento -> {
                long ore = TimeUnit.MILLISECONDS.toHours(intervento.getTempoMedio());
                long minuti = TimeUnit.MILLISECONDS.toMinutes(intervento.getTempoMedio() - (ore * 3600000));
                String tempo = ore != 0 ? ore + (ore > 1 ? " ore" : " ora") : "";
                tempo += ore != 0 ? " e" : "";
                tempo += minuti == 1 ? "d 1 minuto" : " " + minuti + " minuti";

                boolean accetta = intervento.getTipoIntervento().nome.toLowerCase().contains(stringaRicercaPerFiltro)
                        || String.valueOf(intervento.getCosto()).toLowerCase().contains(stringaRicercaPerFiltro)
                        || tempo.contains(stringaRicercaPerFiltro);

                GestorePazienti gestorePazienti = GestorePazienti.getInstance();
                gestorePazienti.filtriPaziente = new FiltriPaziente();
                gestorePazienti.stringaRicercaPerFiltro = stringaRicercaPerFiltro;
                for (Paziente paziente : gestorePazienti.getPazienti()) {
                    if (paziente.getIDPaziente().equals(intervento.getIDPaziente())) {
                        accetta = true;
                        break;
                    }
                }

                return accetta;
            });
        }

        // Ritorno un ArrayList creato dalla stream filtrata
        return interventiFiltrati.collect(Collectors.toCollection(ArrayList::new));
    }

    // Ordina i pazienti di un ArrayList di interventi in base alla variabile di classe pubblica ordinamentoInterventi
    private void ordinaInterventi(ArrayList<Intervento> listaInterventi) {
        switch (ordinamentoInterventi) {
            case TIPOINTERVENTO -> listaInterventi.sort(Comparator.comparing(intervento -> intervento.getTipoIntervento().nome));
            case COSTO -> listaInterventi.sort(Comparator.comparing(Intervento::getCosto));
            case TEMPO -> listaInterventi.sort(Comparator.comparing(Intervento::getTempoMedio));
            case DATACREAZIONE -> listaInterventi.sort(Comparator.comparing(Intervento::getDataCreazione));
            case DATAULTIMAMODIFICA -> listaInterventi.sort(Comparator.comparing(Intervento::getUltimaModifica));
            case PAZIENTE -> listaInterventi.sort(Comparator.comparing(intervento -> GestorePazienti.getInstance().getPazienti().indexOf(GestorePazienti.getInstance().getPaziente(intervento.getIDPaziente()))));
        }

        if (ordinamentoInterventi.equals(OrdinamentoInterventi.COSTO) || ordinamentoInterventi.equals(OrdinamentoInterventi.TEMPO) || ordinamentoInterventi.equals(OrdinamentoInterventi.DATACREAZIONE) || ordinamentoInterventi.equals(OrdinamentoInterventi.DATAULTIMAMODIFICA))
            Collections.reverse(listaInterventi);
    }
}
