package miniEsercizi.varstatiche;

public class Componente {
    private String nome, ruolo;
    public static double portafoglio;

    // Non ricevo un double per il portafoglio perchè
    public Componente(String nome, String ruolo) {
        this.nome = nome;
        this.ruolo = ruolo;
    }

    public void aggiungiSoldi(double soldi) {
        Componente.portafoglio += soldi;
    }

    public void prendiSoldi(double soldi) {
        if(Componente.portafoglio - soldi > 0)
            Componente.portafoglio -= soldi;
        else
            Componente.portafoglio = 0;
    }

    public String toString() {
        return "Nome: " + nome +
                "\nRuolo: " + ruolo +
                "\nPortafoglio: " + portafoglio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public static double getPortafoglio() {
        return portafoglio;
    }

    public static void setPortafoglio(double portafoglio) {
        Componente.portafoglio = portafoglio;
    }
}
