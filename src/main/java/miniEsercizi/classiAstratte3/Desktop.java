package miniEsercizi.classiAstratte3;

public class Desktop extends Computer {
    private String monitor;
    private String orientazione;
    private String dimensioni;
    private String tastiera;
    private String mouse;
    private double prezzo;

    public Desktop(String SO, String CPU, int RAM, int memoriaNonVolatile, String tipoMemoriaNonVolatile, String IP, String monitor, String orientazione, String dimensioni, String tastiera, String mouse, double prezzo) {
        super(SO, CPU, RAM, memoriaNonVolatile, tipoMemoriaNonVolatile, IP);
        this.monitor = monitor;
        this.orientazione = orientazione;
        this.dimensioni = dimensioni;
        this.tastiera = tastiera;
        this.mouse = mouse;
        this.prezzo = prezzo;
    }

    public boolean aggiungiRAM(int ram) {
        if(ram < 1)
            return false;

        RAM += ram;
        return true;
    }

    public String toString() {
        return "Desktop:\nSistema Operativo: " + SO + "\nRAM: " + RAM + "\nPrezzo: " + prezzo;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getOrientazione() {
        return orientazione;
    }

    public void setOrientazione(String orientazione) {
        this.orientazione = orientazione;
    }

    public String getDimensioni() {
        return dimensioni;
    }

    public void setDimensioni(String dimensioni) {
        this.dimensioni = dimensioni;
    }

    public String getTastiera() {
        return tastiera;
    }

    public void setTastiera(String tastiera) {
        this.tastiera = tastiera;
    }

    public String getMouse() {
        return mouse;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
}
