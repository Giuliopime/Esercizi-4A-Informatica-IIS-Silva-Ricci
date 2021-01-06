package miniEsercizi.ristorante.gestioneRistorante.util;

public class Prodotto {
    private TipoProdotti tipo;
    private String nome;
    private double prezzo;

    public Prodotto(TipoProdotti tipo, String nome, double prezzo) {
        this.tipo = tipo;
        this.nome = nome;
        this.prezzo = prezzo;
    }

    public TipoProdotti getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public double getPrezzo() {
        return prezzo;
    }
}
