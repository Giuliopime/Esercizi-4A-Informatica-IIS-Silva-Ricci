package miniEsercizi.classiAstratte2;

public abstract class Solido extends FiguraPiana {
    protected double altezza;

    public Solido(String nome, int numLati, double area, double altezza) {
        super(nome, numLati, area);
        this.altezza = altezza;
    }

    public abstract double volume();

    public double getAltezza() {
        return altezza;
    }

    public void setAltezza(double altezza) {
        this.altezza = altezza;
    }
}
