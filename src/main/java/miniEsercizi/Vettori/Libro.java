package miniEsercizi.Vettori;

public class Libro {
    private String titolo;
    private double prezzo;
    private int numPagine;

    public Libro(String titolo, double prezzo, int numPagine) {
        this.titolo = titolo;
        this.prezzo = prezzo;
        this.numPagine = numPagine;
    }

    public String getTitolo() {
        return titolo;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public int getNumPagine() {
        return numPagine;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
}
