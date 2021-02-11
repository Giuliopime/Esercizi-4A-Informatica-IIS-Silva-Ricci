package miniEsercizi.classiAstratte3;

public abstract class Computer {
    protected String SO;
    protected String CPU;
    protected int RAM;
    protected int memoriaNonVolatile;
    protected String tipoMemoriaNonVolatile;
    protected String IP;

    public Computer(String SO, String CPU, int RAM, int memoriaNonVolatile, String tipoMemoriaNonVolatile, String IP) {
        this.SO = SO;
        this.CPU = CPU;
        this.RAM = RAM;
        this.memoriaNonVolatile = memoriaNonVolatile;
        this.tipoMemoriaNonVolatile = tipoMemoriaNonVolatile;
        this.IP = IP;
    }

    public abstract boolean aggiungiRAM(int ram);

    public abstract String toString();

    public String getSO() {
        return SO;
    }

    public void setSO(String SO) {
        this.SO = SO;
    }

    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    public int getRAM() {
        return RAM;
    }

    public String getIP() {
        return IP;
    }

    public void setRAM(int RAM) {
        this.RAM = RAM;
    }

    public int getMemoriaNonVolatile() {
        return memoriaNonVolatile;
    }

    public void setMemoriaNonVolatile(int memoriaNonVolatile) {
        this.memoriaNonVolatile = memoriaNonVolatile;
    }

    public String getTipoMemoriaNonVolatile() {
        return tipoMemoriaNonVolatile;
    }

    public void setTipoMemoriaNonVolatile(String tipoMemoriaNonVolatile) {
        this.tipoMemoriaNonVolatile = tipoMemoriaNonVolatile;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
}
