package eserciziCompleti.studioDentistico.gestori;

import eserciziCompleti.studioDentistico.enums.OrdinamentoPazienti;
import eserciziCompleti.studioDentistico.enums.TipoQueryPaziente;
import eserciziCompleti.studioDentistico.oggetti.FiltriPaziente;
import eserciziCompleti.studioDentistico.oggetti.Paziente;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GestorePazienti {
    private static final String nomeFile = "pazienti.dat";
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static GestorePazienti instance;
    private ArrayList<Paziente> pazienti;
    public FiltriPaziente filtriPaziente;
    public String query;
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

        executor.scheduleAtFixedRate(salvaPazientiSuFile, 0, 5, TimeUnit.MINUTES);

        ordinamentoPazienti = GestoreImpostazioni.getInstance().getImpostazioni().getOrdinamentoPazienti();
    }

    public static GestorePazienti getInstance() {
        if(instance == null)
            instance = new GestorePazienti();

        return instance;
    }


    public void caricaPazienti() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(GestoreGrafica.pathFileDat + nomeFile);
        ObjectInputStream ois = new ObjectInputStream(fis);

        pazienti = new ArrayList<>();

        while(fis.available() > 0)
            pazienti.add((Paziente) ois.readObject());

        ois.close();
        fis.close();
    }

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

    public void eliminaPaziente(UUID idPaziente) {
        pazienti.removeIf(paziente -> paziente.getIDPaziente().equals(idPaziente));
        salvaPazienti();
    }

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

    public void aggiungiPaziente(Paziente paziente) {
        pazienti.add(paziente);
        salvaPazienti();
    }

    public Paziente getPaziente(UUID idPaziente) {
        for (Paziente paziente : pazienti) {
            if(paziente.getIDPaziente().equals(idPaziente))
                return paziente;
        }

        return null;
    }

    public ArrayList<Paziente> getPazienti() {
        ArrayList<Paziente> listaPazienti = pazienti;
        if(filtriPaziente != null && query != null)
            listaPazienti = getPazientiFiltrati();

        if(ordinamentoPazienti != null)
            ordinaPazienti(listaPazienti);

        return listaPazienti;
    }

    public ArrayList<Paziente> getPazientiFiltrati() {
        ArrayList<Paziente> pazientiFiltrati = new ArrayList<>();

        ArrayList<TipoQueryPaziente> tipiQuery = creaArrayQueries();

        for (TipoQueryPaziente queryPaziente : tipiQuery)
            pazientiFiltrati.addAll(filtraPazienti(queryPaziente));


        Set<Paziente> pazientiFiltratiUnivoci = new HashSet<>(pazientiFiltrati);
        pazientiFiltrati = new ArrayList<>();
        pazientiFiltrati.addAll(pazientiFiltratiUnivoci);

        filtriPaziente = null;
        query = null;

        return pazientiFiltrati;
    }

    private ArrayList<TipoQueryPaziente> creaArrayQueries() {
        ArrayList<TipoQueryPaziente> tipiQuery = new ArrayList<>();
        if(filtriPaziente.isNome())
            tipiQuery.add(TipoQueryPaziente.Nome);
        if(filtriPaziente.isCognome())
            tipiQuery.add(TipoQueryPaziente.Cognome);
        if(filtriPaziente.isCodiceFiscale())
            tipiQuery.add(TipoQueryPaziente.codiceFiscale);
        if(filtriPaziente.isDataDiNascita())
            tipiQuery.add(TipoQueryPaziente.DataDiNascita);
        if(filtriPaziente.isOccupazione())
            tipiQuery.add(TipoQueryPaziente.Occupazione);
        if(filtriPaziente.isLuogoDiNascita())
            tipiQuery.add(TipoQueryPaziente.LuogoDiNascita);
        if(filtriPaziente.isNumTelefonico())
            tipiQuery.add(TipoQueryPaziente.numTelefono);
        if(filtriPaziente.isProvincia())
            tipiQuery.add(TipoQueryPaziente.Provincia);
        if(filtriPaziente.isResidenza())
            tipiQuery.add(TipoQueryPaziente.Residenza);
        if(filtriPaziente.isSesso())
            tipiQuery.add(TipoQueryPaziente.Sesso);

        return tipiQuery;
    }

    private ArrayList<Paziente> filtraPazienti(TipoQueryPaziente tipoQueryPaziente) {
        Stream<Paziente> pazientiFiltrati;
        Stream<Paziente> streamPazienti = pazienti.stream();

        query = query.toLowerCase();

        switch (tipoQueryPaziente) {
            case Nome -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getNome().toLowerCase().contains(query));
            case Cognome -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getCognome().toLowerCase().contains(query));
            case LuogoDiNascita -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getLuogoNascita().toLowerCase().contains(query));
            case Residenza -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getResidenza().toLowerCase().contains(query));
            case Provincia -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getProvincia().toLowerCase().contains(query));
            case Occupazione -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getOccupazione().toLowerCase().contains(query));
            case Sesso -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getSesso().toLowerCase().contains(query));
            case numTelefono -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getNumTelefono().toLowerCase().contains(query));
            case codiceFiscale -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getCodiceFiscale().toLowerCase().contains(query));
            case isMaggiorenne -> pazientiFiltrati = streamPazienti.filter(paziente -> Boolean.toString(paziente.isMaggiorenne()).toLowerCase().contains(query));
            case DataDiNascita -> pazientiFiltrati = streamPazienti.filter(paziente -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN);
                return dateFormat.format(new Date(paziente.getDataNascita())).toLowerCase().contains(query);
            });
            default -> pazientiFiltrati = streamPazienti.filter(paziente -> paziente.getNome().toLowerCase().contains(query)
                    || paziente.getCognome().toLowerCase().contains(query)
                    || paziente.getLuogoNascita().toLowerCase().contains(query)
                    || paziente.getResidenza().toLowerCase().contains(query)
                    || paziente.getProvincia().toLowerCase().contains(query)
                    || paziente.getOccupazione().toLowerCase().contains(query)
                    || paziente.getSesso().toLowerCase().contains(query)
                    || paziente.getNumTelefono().toLowerCase().contains(query)
                    || paziente.getCodiceFiscale().toLowerCase().contains(query)
                    || String.valueOf(paziente.getDataNascita()).toLowerCase().contains(query)
                    || Boolean.toString(paziente.isMaggiorenne()).toLowerCase().contains(query));
        }

        return pazientiFiltrati.collect(Collectors.toCollection(ArrayList::new));
    }

    private void ordinaPazienti(ArrayList<Paziente> listaPazienti) {
        switch (ordinamentoPazienti) {
            case NOME -> listaPazienti.sort(Comparator.comparing(paziente -> paziente.getNome().toLowerCase()));
            case COGNOME -> listaPazienti.sort(Comparator.comparing(paziente -> paziente.getCognome().toLowerCase()));
            case DATANASCITA -> listaPazienti.sort(Comparator.comparing(Paziente::getDataNascita));
            case DATACREAZIONE -> listaPazienti.sort(Comparator.comparing(Paziente::getDataCreazione));
            case DATAULTIMAMODIFICA -> listaPazienti.sort(Comparator.comparing(Paziente::getUltimaModifica));
        }

        if(ordinamentoPazienti.equals(OrdinamentoPazienti.DATANASCITA) || ordinamentoPazienti.equals(OrdinamentoPazienti.DATACREAZIONE) || ordinamentoPazienti.equals(OrdinamentoPazienti.DATAULTIMAMODIFICA))
            Collections.reverse(listaPazienti);
    }
}
