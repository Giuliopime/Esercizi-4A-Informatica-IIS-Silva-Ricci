package miniEsercizi.classiAstratte2;

public class Cilindro extends Solido {
    public Cilindro(double area, double altezza) {
        super("Cilindro", 0, area, altezza);
    }

    public double volume() {
        return area * altezza;
    }
}
