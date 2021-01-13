package miniEsercizi.interfacce;

/*
Scrivere una classe astratta di nome "animale" con variabili:
Nome e verso e metodi astratti "verso", "si muove", "vive".
Questi metodi ritornano una stringa.
Aggiungere il metodo concreto toString.
Scrivere le due sottoclassi "animale terrestre" e "animale
acquatico" che implementano la classe animale.
 */

public abstract class Animale {
    protected String verso, nome;

    public Animale(String verso, String nome) {
        this.verso = verso;
        this.nome = nome;
    }

    public abstract String verso();

    public abstract String nome();

    public abstract String siMuove();

    public abstract String vive();

    @Override
    public String toString() {
        return "Nome animale: " + nome + ". Verso: " + verso;
    }
}
