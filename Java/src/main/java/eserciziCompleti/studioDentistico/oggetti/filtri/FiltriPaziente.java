package eserciziCompleti.studioDentistico.oggetti.filtri;

import java.io.Serializable;

public class FiltriPaziente implements Serializable {
    private boolean nome, cognome, luogoDiNascita, codiceFiscale, residenza, provincia, sesso, occupazione, numTelefonico, dataDiNascita;

    public FiltriPaziente() {
        nome = true;
        cognome = true;
        luogoDiNascita = true;
        codiceFiscale = true;
        residenza = true;
        provincia = true;
        sesso = true;
        occupazione = true;
        numTelefonico = true;
        dataDiNascita = true;
    }

    public FiltriPaziente(boolean nome, boolean cognome, boolean luogoDiNascita, boolean codiceFiscale, boolean residenza, boolean provincia, boolean sesso, boolean occupazione, boolean numTelefonico, boolean dataDiNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.luogoDiNascita = luogoDiNascita;
        this.codiceFiscale = codiceFiscale;
        this.residenza = residenza;
        this.provincia = provincia;
        this.sesso = sesso;
        this.occupazione = occupazione;
        this.numTelefonico = numTelefonico;
        this.dataDiNascita = dataDiNascita;
    }

    public boolean tuttiFalsi() {
        return !nome && !cognome && !codiceFiscale && !luogoDiNascita && !residenza && !provincia && !sesso && !occupazione && !numTelefonico && !dataDiNascita;
    }

    public boolean isNome() {
        return nome;
    }

    public void setNome(boolean nome) {
        this.nome = nome;
    }

    public boolean isCognome() {
        return cognome;
    }

    public void setCognome(boolean cognome) {
        this.cognome = cognome;
    }

    public boolean isLuogoDiNascita() {
        return luogoDiNascita;
    }

    public void setLuogoDiNascita(boolean luogoDiNascita) {
        this.luogoDiNascita = luogoDiNascita;
    }

    public boolean isCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(boolean codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public boolean isResidenza() {
        return residenza;
    }

    public void setResidenza(boolean residenza) {
        this.residenza = residenza;
    }

    public boolean isProvincia() {
        return provincia;
    }

    public void setProvincia(boolean provincia) {
        this.provincia = provincia;
    }

    public boolean isSesso() {
        return sesso;
    }

    public void setSesso(boolean sesso) {
        this.sesso = sesso;
    }

    public boolean isOccupazione() {
        return occupazione;
    }

    public void setOccupazione(boolean occupazione) {
        this.occupazione = occupazione;
    }

    public boolean isNumTelefonico() {
        return numTelefonico;
    }

    public void setNumTelefonico(boolean numTelefonico) {
        this.numTelefonico = numTelefonico;
    }

    public boolean isDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(boolean dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }
}
