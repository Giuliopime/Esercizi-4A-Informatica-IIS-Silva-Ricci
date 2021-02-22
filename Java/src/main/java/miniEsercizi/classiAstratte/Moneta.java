package miniEsercizi.classiAstratte;

public class Moneta extends Oggetto{
    public Moneta() {
        super("Moneta", new String[]{"Testa", "Croce"});
    }

    @Override
    public String lancio() {
        int scelta = (int) (Math.random() * facce.length);

        for (int i = 0; i < facce.length; i++) {
            if(i==scelta)
                return facce[i];
        }

        return null;
    }
}
