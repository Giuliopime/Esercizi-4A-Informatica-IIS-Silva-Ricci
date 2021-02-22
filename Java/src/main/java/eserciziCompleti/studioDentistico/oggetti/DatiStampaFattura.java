package eserciziCompleti.studioDentistico.oggetti;

import eserciziCompleti.studioDentistico.gestori.GestoreInterventi;
import eserciziCompleti.studioDentistico.gestori.GestorePazienti;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class DatiStampaFattura {
    private Fattura fattura;
    private String numeroFattura;
    private boolean aggiungiIVA;
    private String descrizioneIntervento;
    private Paziente paziente;
    private ArrayList<Intervento> interventi;

    public DatiStampaFattura(Fattura fattura, String numeroFattura, boolean aggiungiIVA, String descrizioneIntervento) {
        if(numeroFattura.isBlank())
            throw new IllegalArgumentException("È necessario fornire il numero della fattura");

        this.fattura = fattura;
        this.numeroFattura = numeroFattura;
        this.aggiungiIVA = aggiungiIVA;
        this.descrizioneIntervento = descrizioneIntervento;

        paziente = GestorePazienti.getInstance().getPaziente(fattura.getIDPaziente());
        interventi = new ArrayList<>();
        for (UUID IDIntervento : fattura.getInterventi()) {
            Intervento intervento = GestoreInterventi.getInstance().getIntervento(IDIntervento);
            if(intervento != null)
                interventi.add(intervento);
        }
    }

    public Fattura getFattura() {
        return fattura;
    }

    public void setFattura(Fattura fattura) {
        this.fattura = fattura;
    }

    public String getNumeroFattura() {
        return numeroFattura;
    }

    public void setNumeroFattura(String numeroFattura) {
        this.numeroFattura = numeroFattura;
    }

    public boolean isAggiungiIVA() {
        return aggiungiIVA;
    }

    public void setAggiungiIVA(boolean aggiungiIVA) {
        this.aggiungiIVA = aggiungiIVA;
    }

    public String getDescrizioneIntervento() {
        return descrizioneIntervento;
    }

    public void setDescrizioneIntervento(String descrizioneIntervento) {
        this.descrizioneIntervento = descrizioneIntervento;

    }

    public Paziente getPaziente() {
        return paziente;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }

    public ArrayList<Intervento> getInterventi() {
        return interventi;
    }

    public void setInterventi(ArrayList<Intervento> interventi) {
        this.interventi = interventi;
    }

    public String getNomeFileFattura() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMMMMMMMMMM yyyy", Locale.ITALIAN);
        return "Fattura per " + paziente.getCognome() + " " + paziente.getNome() + "(" + dateFormat.format(fattura.getData()) + ").pdf";
    }
}
