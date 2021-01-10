package miniEsercizi.classiAstratte;

public class Dado extends Oggetto {

    public Dado() {
        super("Dado", new String[]{"1", "2", "3", "4", "5", "6"});
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
