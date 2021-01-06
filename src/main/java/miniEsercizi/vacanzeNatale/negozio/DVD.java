package miniEsercizi.vacanzeNatale.negozio;

public class DVD extends Articolo{
    private int lunghezzaFilm;

    public DVD(String codice, String titolo, double costo, int lunghezzaFilm) {
        super(codice, titolo, costo);
        this.lunghezzaFilm = lunghezzaFilm;
    }

    public int getLunghezzaFilm() {
        return lunghezzaFilm;
    }

    public void setLunghezzaFilm(int lunghezzaFilm) {
        this.lunghezzaFilm = lunghezzaFilm;
    }

    public String toString() {
        return super.toString() + "\nTipo: DVD\nLunghezza film: " + lunghezzaFilm + " minuti";
    }
}
