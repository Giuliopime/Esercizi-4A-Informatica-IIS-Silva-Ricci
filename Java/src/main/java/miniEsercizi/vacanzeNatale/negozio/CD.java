package miniEsercizi.vacanzeNatale.negozio;

public class CD extends Articolo {
    private int numBrani;

    public CD(String codice, String titolo, double costo, int numBrani) {
        super(codice, titolo, costo);
        this.numBrani = numBrani;
    }

    public int getNumBrani() {
        return numBrani;
    }

    public void setNumBrani(int numBrani) {
        this.numBrani = numBrani;
    }

    public String toString() {
        return super.toString() + "\nTipo: CD\nNumero brani: " + numBrani;
    }
}
