package miniEsercizi.classiAstratte;

import java.util.HashSet;

public abstract class Oggetto {
    protected String nome;
    protected String[] facce;

    public Oggetto(String nome, String[] facce) {
        this.nome = nome;
        this.facce = facce;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String[] getFacce() {
        return facce;
    }

    public void setFacce(String[] facce) {
        this.facce = facce;
    }

    public String toString() {
        return "Nome oggetto: " + nome + "\nFacce: " + facce.toString();
    }

    public abstract String lancio();

    public String defaultLancio() {
        return facce[(int) (Math.random() * facce.length)];
    }
}
