package miniEsercizi.file3.modelli;

import miniEsercizi.file3.tipologie.TipoArticolo;

public class Articolo {
    private String nome;
    private TipoArticolo tipo;
    private double prezzo;

    public Articolo(String nome, TipoArticolo tipo, double prezzo) {
        this.nome = nome;
        this.tipo = tipo;
        this.prezzo = prezzo > 0 ? prezzo : 0;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(TipoArticolo tipo) {
        this.tipo = tipo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getNome() {
        return nome;
    }

    public TipoArticolo getTipo() {
        return tipo;
    }

    public double getPrezzo() {
        return prezzo;
    }


    public String toString() {
        return "Nome: " + nome + ", tipologia: " + tipo.toString();
    }
}
