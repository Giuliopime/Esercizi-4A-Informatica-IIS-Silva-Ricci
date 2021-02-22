package miniEsercizi.vacanzeNatale.negozio;

public class Articolo {
    private String codice, titolo;
    private double costo;

    public Articolo(String codice, String titolo, double costo) {
        this.codice = codice;
        this.titolo = titolo;
        this.costo = costo;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String toString() {
        return "Codice articolo: " + codice +
                "\nTitolo: " + titolo +
                "\nCosto: " + costo + " €";
    }
}
