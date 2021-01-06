package miniEsercizi.eredetarietà2;

import java.util.Date;

public class Alimentare extends Prodotto {
    private Date scadenza;

    public Alimentare(int codice, String descrizione, double prezzo, Date scadenza) {
        super(codice, descrizione, prezzo);
        this.scadenza = scadenza;
    }

    public Date getScadenza() {
        return scadenza;
    }

    public void setScadenza(Date scadenza) {
        this.scadenza = scadenza;
    }

    public String toString() {
        return super.toString() + ", scadenza: " + scadenza;
    }
}
