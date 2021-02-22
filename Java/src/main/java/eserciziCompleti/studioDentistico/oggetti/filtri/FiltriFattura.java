package eserciziCompleti.studioDentistico.oggetti.filtri;

import java.io.Serializable;

public class FiltriFattura implements Serializable {
    private boolean data, paziente, intervento;

    public FiltriFattura(boolean data, boolean paziente, boolean intervento) {
        this.data = data;
        this.paziente = paziente;
        this.intervento = intervento;
    }

    public FiltriFattura() {
        data = true;
        paziente = true;
        intervento = true;
    }

    public boolean tuttiFalsi() {
        return !data && !paziente && !intervento;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public boolean isPaziente() {
        return paziente;
    }

    public void setPaziente(boolean paziente) {
        this.paziente = paziente;
    }

    public boolean isIntervento() {
        return intervento;
    }

    public void setIntervento(boolean intervento) {
        this.intervento = intervento;
    }
}
