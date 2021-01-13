package miniEsercizi.interfacce;

public class AnimaleAcquatico extends Animale implements Acqua{
    public AnimaleAcquatico(String verso, String nome) {
        super(verso, nome);
    }

    public String verso() {
        return verso;
    }

    public String nome() {
        return nome;
    }

    public String siMuove() {
        return "Nuotando";
    }

    public String vive() {
        return "In acqua";
    }

    public String tipoAcqua() {
        return nome.equalsIgnoreCase("trota") ? "dolce" : "salata";
    }
}
