package miniEsercizi.ripasso;

public class Candidato {
    private String cognome;
    private int voti;

    public Candidato(String cognome) {
        this.cognome = cognome;
        voti = 0;
    }

    public String getCognome() {
        return cognome;
    }

    public int getVoti() {
        return voti;
    }

    public boolean equals(String cognome) {
        return this.cognome.equalsIgnoreCase(cognome);
    }

    public void vota() {
        voti++;
    }

    @Override
    public String toString() {
        return "Cognome: " + cognome + ", voti: " + voti;
    }
}
