package miniEsercizi.utenza;

/*

Considerare già creata la classe java.utenza che specifica il consumo dell'acqua da parte dell'utente di un acquedotto con tracciato:
 - il codice utente (Stringa)
 - consumo attuale (int)
 - consumo precedente (int)
 - con i vari metodi

Scrivere la classe utente che permette di creare un elenco di utenti con metodi per
 - aggiornare tale elenco
 - inserito il costo di un m3 di acqua, calcolare la spesa di bolletta per ogni utente
 */
public class Utenza {
    private String codiceUtente;
    private int consumoAttuale, consumoPrecedente;

    public Utenza(String codiceUtente, int consumoAttuale, int consumoPrecedente) {
        this.codiceUtente = codiceUtente;
        this.consumoAttuale = consumoAttuale >= 0 ? consumoAttuale : 0;
        this.consumoPrecedente = consumoPrecedente >= 0 ? consumoPrecedente : 0;

        if (this.consumoAttuale < this.consumoPrecedente) this.consumoAttuale = this.consumoPrecedente;
    }

    public double getConsumo(double metroCubo) {
        return (consumoAttuale - consumoPrecedente) * metroCubo * 1.22;
    }

    public String getCodiceUtente() {
        return codiceUtente;
    }

    public void setCodiceUtente(String codiceUtente) {
        this.codiceUtente = codiceUtente;
    }

    public int getConsumoAttuale() {
        return consumoAttuale;
    }

    public void setConsumoAttuale(int consumoAttuale) {
        this.consumoAttuale = consumoAttuale;
    }

    public int getConsumoPrecedente() {
        return consumoPrecedente;
    }

    public void setConsumoPrecedente(int consumoPrecedente) {
        this.consumoPrecedente = consumoPrecedente;
    }
}
