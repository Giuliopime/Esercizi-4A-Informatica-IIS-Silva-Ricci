package miniEsercizi.ereditarietà;

public class Persona {
    private String nome;
    private String cognome;
    private int eta;

    public Persona(String nome, String cognome, int eta) {
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta > -1 ? eta : 0;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setEta(int eta) {
        this.eta = eta > -1 ? eta : 0;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public int getEtà() {
        return eta;
    }
}
