package eserciziCompleti.studioDentistico.oggetti;

import java.io.Serializable;
import java.util.UUID;

public class Fattura implements Serializable {
    private UUID IDPaziente;
    private UUID[] interventi;
    private long data;

    public Fattura(UUID IDPaziente, UUID[] interventi) {
        if (IDPaziente == null)
            throw new IllegalArgumentException("È necessario fornire un paziente");
        if (interventi == null || interventi.length == 0)
            throw new IllegalArgumentException("È necessario fornire almeno un intervento");

        this.IDPaziente = IDPaziente;
        this.interventi = interventi;
        data = System.currentTimeMillis();
    }

    public UUID getIDPaziente() {
        return IDPaziente;
    }

    public void setIDPaziente(UUID IDPaziente) {
        this.IDPaziente = IDPaziente;
    }

    public UUID[] getInterventi() {
        return interventi;
    }

    public void setInterventi(UUID[] interventi) {
        this.interventi = interventi;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }
}
