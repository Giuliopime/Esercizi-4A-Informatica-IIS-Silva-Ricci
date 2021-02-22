package miniEsercizi.file3.modelli;

public class Fattura {
    private Cliente cliente;
    private Articolo[] articoli;

    public Fattura(Cliente cliente, Articolo[] articoli) {
        this.cliente = cliente;
        this.articoli = articoli;
    }

    public double prezzoTotale() {
        double prezzo = 0;
        for (Articolo a : articoli)
            prezzo += a.getPrezzo();

        return prezzo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Articolo[] getArticoli() {
        return articoli;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setArticoli(Articolo[] articoli) {
        this.articoli = articoli;
    }

    public String toString() {
        String stringa = "CLIENTE: " + cliente.toString() + "\nARTICOLI: ";
        for (Articolo a : articoli)
            stringa += "\n" + a.toString();

        return stringa;
    }
}
