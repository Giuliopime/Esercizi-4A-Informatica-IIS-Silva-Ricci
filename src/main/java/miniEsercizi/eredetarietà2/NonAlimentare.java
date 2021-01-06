package miniEsercizi.eredetarietà2;

public class NonAlimentare extends Prodotto {
    private String materiale;

    public NonAlimentare(int codice, String descrizione, double prezzo, String materiale) {
        super(codice, descrizione, prezzo);
        this.materiale = materiale;
    }

    public String getMateriale() {
        return materiale;
    }

    public void setMateriale(String materiale) {
        this.materiale = materiale;
    }

    public String toString() {
        return super.toString() + ", materiale: "+materiale;
    }
}
