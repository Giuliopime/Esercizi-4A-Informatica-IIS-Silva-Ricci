package miniEsercizi.file2;

public class Prodotto {
    private String nome;
    private int quant;

    public Prodotto(String nome, int quant) {
        this.nome = nome;
        if (quant < 0) throw new IllegalArgumentException("La quantità non può essere minore di 0");
        this.quant = quant;
    }

    public String getNome() {
        return nome;
    }

    public int getQuant() {
        return quant;
    }
}
