package eserciziCompleti.studioDentistico.oggetti.filtri;

import java.io.Serializable;

public class FiltriIntervento implements Serializable {
    private boolean tipo, costo, tempo, paziente, data;

    public FiltriIntervento() {
        tipo = true;
        costo = true;
        tempo = true;
        paziente = true;
        data = true;
    }

    public FiltriIntervento(boolean tipo, boolean costo, boolean tempo, boolean paziente, boolean data) {
        this.tipo = tipo;
        this.costo = costo;
        this.tempo = tempo;
        this.paziente = paziente;
        this.data = data;
    }

    public boolean tuttiFalsi() {
        return !tipo && !costo && !tempo && !paziente && !data;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public boolean isCosto() {
        return costo;
    }

    public void setCosto(boolean costo) {
        this.costo = costo;
    }

    public boolean isTempo() {
        return tempo;
    }

    public void setTempo(boolean tempo) {
        this.tempo = tempo;
    }

    public boolean isPaziente() {
        return paziente;
    }

    public void setPaziente(boolean paziente) {
        this.paziente = paziente;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
