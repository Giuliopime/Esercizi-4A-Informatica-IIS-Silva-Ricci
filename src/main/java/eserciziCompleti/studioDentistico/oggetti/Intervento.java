package eserciziCompleti.studioDentistico.oggetti;

import eserciziCompleti.studioDentistico.enums.TipoIntervento;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Intervento implements Serializable {
    private TipoIntervento tipoIntervento;
    private double costo;
    private long tempoMedio;
    private UUID IDIntervento;
    private UUID IDPaziente;
    private long dataCreazione, ultimaModifica;

    public Intervento(TipoIntervento tipoIntervento, Double costo, Long tempoMedio, UUID IDPaziente) {
        if (tipoIntervento == null)
            throw new IllegalArgumentException("È necessario fornire il tipo di intervento");
        if (costo == null)
            throw new IllegalArgumentException("È necessario fornire il costo dell'intervento");
        if (tempoMedio == null)
            throw new IllegalArgumentException("È necessario fornire la durata dell'intervento");

        this.tipoIntervento = tipoIntervento;

        if (costo < 0)
            throw new IllegalArgumentException("Il costo dell'intervento non può essere negativo");
        this.costo = costo;

        if (tempoMedio <= 0)
            throw new IllegalArgumentException("Il tempo medio dell'intervento deve essere maggiore di 0");
        this.tempoMedio = tempoMedio;

        IDIntervento = UUID.randomUUID();

        this.IDPaziente = IDPaziente;

        dataCreazione = new Date().getTime();
    }

    public UUID getIDPaziente() {
        return IDPaziente;
    }

    public UUID getIDIntervento() {
        return IDIntervento;
    }

    public void setIDIntervento(UUID IDIntervento) {
        this.IDIntervento = IDIntervento;
    }


    public TipoIntervento getTipoIntervento() {
        return tipoIntervento;
    }

    public void setTipoIntervento(TipoIntervento tipoIntervento) {
        this.tipoIntervento = tipoIntervento;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public long getTempoMedio() {
        return tempoMedio;
    }

    public void setTempoMedio(long tempoMedio) {
        this.tempoMedio = tempoMedio;
    }

    public long getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(long dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public long getUltimaModifica() {
        return ultimaModifica;
    }

    public void setUltimaModifica(long ultimaModifica) {
        this.ultimaModifica = ultimaModifica;
    }
}
