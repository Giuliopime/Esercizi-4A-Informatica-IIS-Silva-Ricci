package miniEsercizi.classiAstratte3;

public class Server extends Computer {
    private boolean raid;
    private double costoMensile;

    public Server(String SO, String CPU, int RAM, int memoriaNonVolatile, String tipoMemoriaNonVolatile, String IP, boolean raid, double costoMensile) {
        super(SO, CPU, RAM, memoriaNonVolatile, tipoMemoriaNonVolatile, IP);
        this.raid = raid;
        this.costoMensile = costoMensile;
    }


    public boolean aggiungiRAM(int ram) {
        if(ram < 1)
            return false;

        RAM += ram;
        return true;
    }

    public String toString() {
        return "Server:\nSistema Operativo: " + SO + "\nRAM: " + RAM + "\nCosto mensile: "+costoMensile;
    }

    public boolean isRaid() {
        return raid;
    }

    public void setRaid(boolean raid) {
        this.raid = raid;
    }

    public double getCostoMensile() {
        return costoMensile;
    }

    public void setCostoMensile(double costoMensile) {
        this.costoMensile = costoMensile;
    }
}
