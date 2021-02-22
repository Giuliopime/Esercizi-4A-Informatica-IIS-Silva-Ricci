package miniEsercizi.classiAstratte2;

public abstract class FiguraPiana {
    protected String nome;
    protected int numLati;
    protected double area;

    public FiguraPiana(String nome, int numLati, double area) {
        this.nome = nome;
        this.numLati = numLati;
        this.area = area;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumLati() {
        return numLati;
    }

    public void setNumLati(int numLati) {
        this.numLati = numLati;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
