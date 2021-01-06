package eserciziCompleti.studioDentistico.oggetti;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Paziente implements Serializable {
    private String codiceFiscale, nome, cognome, luogoNascita, residenza, provincia, occupazione, sesso, numTelefono;
    private long dataNascita, dataCreazione, ultimaModifica;
    private boolean isMaggiorenne;
    private UUID IDPaziente;

    public Paziente(String codiceFiscale, String nome, String cognome, String luogoNascita, String residenza, String provincia, String occupazione, String sesso, String numTelefono, Long dataNascita) {
        if (codiceFiscale == null || codiceFiscale.isBlank())
            throw new IllegalArgumentException("È necessario fornire il codice fiscale del paziente");
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("È necessario fornire il nome del paziente");
        if (cognome == null || cognome.isBlank())
            throw new IllegalArgumentException("È necessario fornire il cognome del paziente");
        if (luogoNascita == null || luogoNascita.isBlank())
            throw new IllegalArgumentException("È necessario fornire il luogoNascita del paziente");
        if (residenza == null || residenza.isBlank())
            throw new IllegalArgumentException("È necessario fornire la residenza del paziente");
        if (provincia == null || provincia.isBlank())
            throw new IllegalArgumentException("È necessario fornire la provincia del paziente");
        if (occupazione == null || occupazione.isBlank())
            throw new IllegalArgumentException("È necessario fornire l' occupazione del paziente");
        if (sesso == null || sesso.isBlank())
            throw new IllegalArgumentException("È necessario fornire il sesso del paziente");
        if (numTelefono == null || numTelefono.isBlank())
            throw new IllegalArgumentException("È necessario fornire il numero di telefono del paziente");
        if (!numTelefono.matches("\\+?\\d+"))
            throw new IllegalArgumentException("Il numero di telefono deve contenere solo numeri");
        if (dataNascita == null)
            throw new IllegalArgumentException("È necessario fornire la data di nascita del paziente");

        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.luogoNascita = luogoNascita;
        this.residenza = residenza;
        this.provincia = provincia;
        this.occupazione = occupazione;
        this.sesso = sesso;
        if (System.currentTimeMillis() < dataNascita)
            throw new IllegalArgumentException("La data di nascita non può essere nel futuro");
        this.dataNascita = dataNascita;
        this.numTelefono = numTelefono;
        isMaggiorenne = System.currentTimeMillis() - dataNascita >= Long.parseLong("568025136000");
        IDPaziente = UUID.randomUUID();

        dataCreazione = new Date().getTime();
    }

    public UUID getIDPaziente() {
        return IDPaziente;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public String getResidenza() {
        return residenza;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getOccupazione() {
        return occupazione;
    }

    public String getSesso() {
        return sesso;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public long getDataNascita() {
        return dataNascita;
    }

    public boolean isMaggiorenne() {
        return isMaggiorenne;
    }

    public void setIDPaziente(UUID IDPaziente) {
        this.IDPaziente = IDPaziente;
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
