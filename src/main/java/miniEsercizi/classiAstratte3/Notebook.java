package miniEsercizi.classiAstratte3;

public class Notebook extends Computer {
    private double peso;
    private String dimensioni;
    private double prezzo;
    private String produttore;
    private int batteriamAh;

    public Notebook(String SO, String CPU, int RAM, int memoriaNonVolatile, String tipoMemoriaNonVolatile, String IP, double peso, String dimensioni, double prezzo, String produttore, int batteriamAh) {
        super(SO, CPU, RAM, memoriaNonVolatile, tipoMemoriaNonVolatile, IP);
        this.peso = peso;
        this.dimensioni = dimensioni;
        this.prezzo = prezzo;
        this.produttore = produttore;
        this.batteriamAh = batteriamAh;
    }

    public boolean aggiungiRAM(int ram) {
        return false;
    }

    public String toString() {
        return "Notebook:\nSistema Operativo: " + SO + "\nRAM: " + RAM + "\nPrezzo: " + prezzo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getDimensioni() {
        return dimensioni;
    }

    public void setDimensioni(String dimensioni) {
        this.dimensioni = dimensioni;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getProduttore() {
        return produttore;
    }

    public void setProduttore(String produttore) {
        this.produttore = produttore;
    }

    public int getBatteriamAh() {
        return batteriamAh;
    }

    public void setBatteriamAh(int batteriamAh) {
        this.batteriamAh = batteriamAh;
    }
}
