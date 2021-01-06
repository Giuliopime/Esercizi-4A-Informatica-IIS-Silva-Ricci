package eserciziCompleti.studioDentistico.gestori;

import eserciziCompleti.studioDentistico.enums.OrdinamentoInterventi;
import eserciziCompleti.studioDentistico.enums.TipoQueryIntervento;
import eserciziCompleti.studioDentistico.oggetti.FiltriIntervento;
import eserciziCompleti.studioDentistico.oggetti.FiltriPaziente;
import eserciziCompleti.studioDentistico.oggetti.Intervento;
import eserciziCompleti.studioDentistico.oggetti.Paziente;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GestoreInterventi {
    private static final String nomeFile = "interventi.dat";
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static GestoreInterventi instance;
    private ArrayList<Intervento> interventi;
    public FiltriIntervento filtriIntervento;
    public String query;
    public OrdinamentoInterventi ordinamentoInterventi;

    private GestoreInterventi() {
        interventi = new ArrayList<>();
        try {
            caricaInterventi();
        } catch (IOException | ClassNotFoundException e) {
            salvaInterventi();
        }

        Runnable salvaPazientiSuFile = () -> {
            salvaInterventi();
        };

        executor.scheduleAtFixedRate(salvaPazientiSuFile, 0, 5, TimeUnit.MINUTES);

        ordinamentoInterventi = GestoreImpostazioni.getInstance().getImpostazioni().getOrdinamentoInterventi();
    }

    public static GestoreInterventi getInstance() {
        if (instance == null)
            instance = new GestoreInterventi();

        return instance;
    }

    public void caricaInterventi() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(GestoreGrafica.pathFileDat + nomeFile);
        ObjectInputStream ois = new ObjectInputStream(fis);

        interventi = new ArrayList<>();

        while (fis.available() > 0)
            interventi.add((Intervento) ois.readObject());

        ois.close();
        fis.close();
    }

    public void salvaInterventi() {
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

    public void eliminaIntervento(UUID idIntervento) {
        interventi.removeIf(intervento -> intervento.getIDIntervento().equals(idIntervento));
        salvaInterventi();
    }

    public void modificaIntervento(Intervento intervento) {
        ListIterator<Intervento> iterator = interventi.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getIDIntervento().equals(intervento.getIDIntervento())) {
                iterator.set(intervento);
                salvaInterventi();
                break;
            }
        }
    }

    public void aggiungiIntervento(Intervento intervento) {
        interventi.add(intervento);
        salvaInterventi();
    }

    public Intervento getIntervento(UUID idIntervento) {
        for (Intervento intervento : interventi) {
            if (intervento.getIDIntervento().equals(idIntervento))
                return intervento;
        }

        return null;
    }

    public ArrayList<Intervento> getInterventi() {
        ArrayList<Intervento> listaInterventi = interventi;

        if (filtriIntervento != null && query != null)
            listaInterventi = getInterventiFiltrati();

        if (ordinamentoInterventi != null)
            ordinaInterventi(listaInterventi);

        return listaInterventi;
    }

    public ArrayList<Intervento> getInterventiFiltrati() {
        ArrayList<Intervento> interventiFiltrati = new ArrayList<>();

        ArrayList<TipoQueryIntervento> tipiQuery = creaArrayQueries();

        for (TipoQueryIntervento queryIntervento : tipiQuery)
            interventiFiltrati.addAll(filtraInterventi(queryIntervento));


        Set<Intervento> interventiFiltratiUnivoci = new HashSet<>(interventiFiltrati);
        interventiFiltrati = new ArrayList<>();
        interventiFiltrati.addAll(interventiFiltratiUnivoci);

        filtriIntervento = null;
        query = null;

        return interventiFiltrati;
    }

    private ArrayList<TipoQueryIntervento> creaArrayQueries() {
        ArrayList<TipoQueryIntervento> tipiQuery = new ArrayList<>();
        if (filtriIntervento.isPaziente())
            tipiQuery.add(TipoQueryIntervento.Paziente);
        if (filtriIntervento.isTipo())
            tipiQuery.add(TipoQueryIntervento.Tipo);
        if (filtriIntervento.isCosto())
            tipiQuery.add(TipoQueryIntervento.Costo);
        if (filtriIntervento.isTempo())
            tipiQuery.add(TipoQueryIntervento.Tempo);
        if (filtriIntervento.isData())
            tipiQuery.add(TipoQueryIntervento.Data);

        return tipiQuery;
    }

    private ArrayList<Intervento> filtraInterventi(TipoQueryIntervento tipoQueryIntervento) {
        Stream<Intervento> interventiFiltrati;
        Stream<Intervento> streamInterventi = interventi.stream();

        query = query.toLowerCase();

        switch (tipoQueryIntervento) {
            case Tipo -> interventiFiltrati = streamInterventi.filter(intervento -> intervento.getTipoIntervento().nome.toLowerCase().contains(query));
            case Costo -> interventiFiltrati = streamInterventi.filter(intervento -> String.valueOf(intervento.getCosto()).toLowerCase().contains(query));
            case Data -> interventiFiltrati = streamInterventi.filter(intervento -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN);
                return dateFormat.format(new Date(intervento.getDataCreazione())).toLowerCase().contains(query);
            });
            case Tempo -> interventiFiltrati = streamInterventi.filter(intervento -> {
                long ore = TimeUnit.MILLISECONDS.toHours(intervento.getTempoMedio());
                long minuti = TimeUnit.MILLISECONDS.toMinutes(intervento.getTempoMedio() - (ore * 3600000));
                String tempo = ore != 0 ? ore + (ore > 1 ? " ore" : " ora") : "";
                tempo += ore != 0 ? " e" : "";
                tempo += minuti == 1 ? "d 1 minuto" : " " + minuti + " minuti";

                return tempo.contains(query);
            });
            case Paziente -> interventiFiltrati = streamInterventi.filter(intervento -> {
                GestorePazienti gestorePazienti = GestorePazienti.getInstance();
                gestorePazienti.filtriPaziente = new FiltriPaziente();
                gestorePazienti.query = query;
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

                boolean accetta = intervento.getTipoIntervento().nome.toLowerCase().contains(query)
                        || String.valueOf(intervento.getCosto()).toLowerCase().contains(query)
                        || tempo.contains(query);

                GestorePazienti gestorePazienti = GestorePazienti.getInstance();
                gestorePazienti.filtriPaziente = new FiltriPaziente();
                gestorePazienti.query = query;
                for (Paziente paziente : gestorePazienti.getPazienti()) {
                    if (paziente.getIDPaziente().equals(intervento.getIDPaziente()))
                        accetta = true;
                }

                return accetta;
            });
        }

        return interventiFiltrati.collect(Collectors.toCollection(ArrayList::new));
    }

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
