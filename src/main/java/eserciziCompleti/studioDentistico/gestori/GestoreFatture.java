package eserciziCompleti.studioDentistico.gestori;

import eserciziCompleti.studioDentistico.enums.ordinamento.OrdinamentoFatture;
import eserciziCompleti.studioDentistico.enums.TipiQuery.TipoQueryFattura;
import eserciziCompleti.studioDentistico.oggetti.Fattura;
import eserciziCompleti.studioDentistico.oggetti.filtri.FiltriFattura;
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

public class GestoreFatture {
    private static final String nomeFile = "fatture.dat";
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static GestoreFatture instance;
    private ArrayList<Fattura> fatture;
    public FiltriFattura filtriFattura;
    public String query;
    public OrdinamentoFatture ordinamentoFatture;

    private GestoreFatture() {
        fatture = new ArrayList<>();
        try {
            caricaFatture();
        } catch (IOException | ClassNotFoundException e) {
            salvaFatture();
        }

        Runnable salvaPazientiSuFile = () -> {
            salvaFatture();
        };

        executor.scheduleAtFixedRate(salvaPazientiSuFile, 0, 5, TimeUnit.MINUTES);

        ordinamentoFatture = GestoreImpostazioni.getInstance().getImpostazioni().getOrdinamentoFatture();
    }

    public static GestoreFatture getInstance() {
        if (instance == null)
            instance = new GestoreFatture();

        return instance;
    }

    public void caricaFatture() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(GestoreGrafica.pathFileDat + nomeFile);
        ObjectInputStream ois = new ObjectInputStream(fis);

        fatture = new ArrayList<>();

        while (fis.available() > 0)
            fatture.add((Fattura) ois.readObject());

        ois.close();
        fis.close();
    }

    public void salvaFatture() {
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

    public void aggiungiFattura(Fattura fattura) {
        fatture.add(fattura);
        salvaFatture();
    }

    public ArrayList<Fattura> getFatture() {
        ArrayList<Fattura> listaFatture = fatture;

        if (filtriFattura != null && query != null)
            listaFatture = getFattureFiltrate();

        if (ordinamentoFatture != null)
            ordinaFatture(listaFatture);

        return listaFatture;
    }

    public ArrayList<Fattura> getFattureFiltrate() {
        ArrayList<Fattura> fattureFiltrate = new ArrayList<>();

        ArrayList<TipoQueryFattura> tipiQuery = creaArrayQueries();

        for (TipoQueryFattura queryFattura : tipiQuery)
            fattureFiltrate.addAll(filtraFatture(queryFattura));


        Set<Fattura> fattureFiltratiUnivoche = new HashSet<>(fattureFiltrate);
        fattureFiltrate = new ArrayList<>();
        fattureFiltrate.addAll(fattureFiltratiUnivoche);

        filtriFattura = null;
        query = null;

        return fattureFiltrate;
    }

    private ArrayList<TipoQueryFattura> creaArrayQueries() {
        ArrayList<TipoQueryFattura> tipiQuery = new ArrayList<>();
        if (filtriFattura.isData())
            tipiQuery.add(TipoQueryFattura.DataCreazione);
        if (filtriFattura.isPaziente())
            tipiQuery.add(TipoQueryFattura.Paziente);
        if (filtriFattura.isIntervento())
            tipiQuery.add(TipoQueryFattura.Intervento);

        return tipiQuery;
    }

    private ArrayList<Fattura> filtraFatture(TipoQueryFattura tipoQueryFattura) {
        Stream<Fattura> fattureFiltrate;
        Stream<Fattura> streamFatture = fatture.stream();

        query = query.toLowerCase();

        switch (tipoQueryFattura) {
            case DataCreazione -> fattureFiltrate = streamFatture.filter(fattura -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN);
                return dateFormat.format(new Date(fattura.getData())).toLowerCase().contains(query);
            });
            case Intervento -> fattureFiltrate = streamFatture.filter(fattura -> {
                GestoreInterventi.getInstance().filtriIntervento = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriIntervento();
                GestoreInterventi.getInstance().query = query;

                ArrayList<Intervento> interventiFiltrati = GestoreInterventi.getInstance().getInterventi();
                return interventiFiltrati.stream().anyMatch(intervento -> Arrays.asList(fattura.getInterventi()).contains(intervento.getIDIntervento()));
            });
            case Paziente -> fattureFiltrate = streamFatture.filter(fattura -> {
                GestorePazienti.getInstance().filtriPaziente = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriPaziente();
                GestorePazienti.getInstance().query = query;

                ArrayList<Paziente> pazientiFiltrati = GestorePazienti.getInstance().getPazienti();
                return pazientiFiltrati.stream().anyMatch(paziente -> paziente.getIDPaziente().equals(fattura.getIDPaziente()));
            });
            default -> fattureFiltrate = streamFatture.filter(fattura -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN);

                GestoreInterventi.getInstance().filtriIntervento = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriIntervento();
                GestoreInterventi.getInstance().query = query;

                ArrayList<Intervento> interventiFiltrati = GestoreInterventi.getInstance().getInterventi();

                GestorePazienti.getInstance().filtriPaziente = GestoreImpostazioni.getInstance().getImpostazioni().getFiltriPaziente();
                GestorePazienti.getInstance().query = query;

                ArrayList<Paziente> pazientiFiltrati = GestorePazienti.getInstance().getPazienti();

                return dateFormat.format(new Date(fattura.getData())).toLowerCase().contains(query) ||
                        interventiFiltrati.stream().anyMatch(intervento -> Arrays.asList(fattura.getInterventi()).contains(intervento.getIDIntervento())) ||
                        pazientiFiltrati.stream().anyMatch(paziente -> paziente.getIDPaziente().equals(fattura.getIDPaziente()));

            });
        }

        return fattureFiltrate.collect(Collectors.toCollection(ArrayList::new));
    }

    private void ordinaFatture(ArrayList<Fattura> listaFatture) {
        switch (ordinamentoFatture) {
            case DATA -> listaFatture.sort(Comparator.comparing(Fattura::getData));
            case INTERVENTO -> listaFatture.sort(Comparator.comparing(fattura -> GestoreInterventi.getInstance().getInterventi().indexOf(GestoreInterventi.getInstance().getIntervento(fattura.getInterventi()[0]))));
            case PAZIENTE -> listaFatture.sort(Comparator.comparing(fattura -> GestorePazienti.getInstance().getPazienti().indexOf(GestorePazienti.getInstance().getPaziente(fattura.getIDPaziente()))));
        }

        if (ordinamentoFatture.equals(OrdinamentoFatture.DATA))
            Collections.reverse(listaFatture);
    }
}
