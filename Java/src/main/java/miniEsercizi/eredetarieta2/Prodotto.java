package miniEsercizi.eredetarieta2;

public class Prodotto {
    protected int codice;
    protected String descrizione;
    protected double prezzo;

    public Prodotto(int codice, String descrizione, double prezzo) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.prezzo = prezzo > 0 ? prezzo : 0;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo > -1 ? prezzo : 0;
    }

    public int getCodice() {
        return codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public double getPrezzo() {
        return prezzo;
    }


    public String toString() {
        return "Codice prodotto: " + codice + ", descrizione: " + descrizione + ", prezzo: " + prezzo;
    }


}
