package miniEsercizi.ripasso4;

import java.io.Serializable;

public class Oggetto implements Serializable {
    private String nome;

    public Oggetto(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
