package miniEsercizi.ereditarietà;

public class Lavoratore extends Persona {
    private String lavoro;
    private double stipendio;

    public Lavoratore(String nome, String cognome, int eta, String lavoro, double stipendio) {
        super(nome, cognome, eta);
        this.lavoro = lavoro;
        this.stipendio = stipendio > -1 ? stipendio : 0;
    }

    public void setLavoro(String lavoro) {
        this.lavoro = lavoro;
    }

    public void setStipendio(double stipendio) {
        this.stipendio = stipendio > -1 ? stipendio : 0;
    }

    public String getLavoro() {
        return lavoro;
    }

    public double getStipendio() {
        return stipendio;
    }
}
