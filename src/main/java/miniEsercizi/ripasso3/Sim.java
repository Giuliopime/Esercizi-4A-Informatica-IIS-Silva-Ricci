package miniEsercizi.ripasso3;

public class Sim {
    private int numero;
    private double credito;
    private Telefonata[] telefonate;
    private int count = 0;

    public Sim(int numero, double credito) {
        this.numero = numero;
        this.credito = credito;
        telefonate = new Telefonata[50];
    }

    public void telefona(int numero, int durata) {
        if(count == 50) count = 0;
        telefonate[count] = new Telefonata(numero, durata);
        credito -= 1;
    }

    public int durataTotale() {
        int durataTot = 0;
        for(Telefonata telefonata: telefonate) durataTot += telefonata.getDurata();
        return durataTot;
    }

    public int telefonateA(int numero) {
        int totTelefonateA = 0;
        for(Telefonata telefonata: telefonate) totTelefonateA += telefonata.getNumero() == numero ? 1:0;
        return totTelefonateA;
    }
}
