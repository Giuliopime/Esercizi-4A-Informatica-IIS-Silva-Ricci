package miniEsercizi.interfacce;

public class Uccello extends AnimaleTerrestre implements IsVertebrato{
    public Uccello(String nome, String verso) {
        super(nome, verso);
    }

    @Override
    public String isVertebrato() {
        return "Vertebrato";
    }
}
