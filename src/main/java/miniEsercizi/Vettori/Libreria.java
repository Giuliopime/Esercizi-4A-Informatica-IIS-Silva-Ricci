package miniEsercizi.Vettori;

import java.util.Vector;

public class Libreria {
    private Vector<Libro> libri;

    public Libreria() {
        libri = new Vector<>();
    }

    public Libreria(Vector<Libro> libri) {
        this.libri = libri;
    }

    public void aggiungiLibro(Libro libro) {
        libri.add(libro);
    }

    public boolean rimuoviLibro(String titolo) {
        for (Libro libro : libri) {
            if (libro.getTitolo().equalsIgnoreCase(titolo)) {
                libri.remove(libro);
                return true;
            }
        }

        return false;
    }

    public void incrementaPrezzi(int incremento) {
        libri.forEach(libro -> {
            libro.setPrezzo(libro.getPrezzo() * ((100 + incremento) / 100.0));
        });
    }

    public int pagineUltimoLibro() {
        return libri.lastElement().getNumPagine();
    }
}
