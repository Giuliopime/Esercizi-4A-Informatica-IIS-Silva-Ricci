package miniEsercizi.interfacce;

public class AnimaleTerrestre extends Animale{
    public AnimaleTerrestre(String verso, String nome) {
        super(verso, nome);
    }

    public String verso() {
        return verso;
    }

    public String nome() {
        return nome;
    }

    @Override
    public String siMuove() {
        return null;
    }

    @Override
    public String vive() {
        return null;
    }
}
