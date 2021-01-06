package miniEsercizi.ripasso2;

public class Carta {
    private String seme;
    private int valore;

    public Carta(String seme, int valore) {
        this.seme = seme;
        this.valore = valore;
    }

    public String getSeme() {
        return seme;
    }

    public int getValore() {
        return valore;
    }

    public int confronta(Carta carta) {
        if(valore > carta.getValore()) return 1;
        if(valore < carta.getValore()) return -1;

        String[] semi = { "cuori", "quadri", "fiori", "picche" };
        int pSeme1=0, pSeme2=0;
        for(int i=0; i<semi.length; i++) {
            if(semi[i].equalsIgnoreCase(seme)) pSeme1 = i+1;
            if(semi[i].equalsIgnoreCase(carta.getSeme())) pSeme2 = i+1;
        }

        return Integer.compare(pSeme1, pSeme2);
    }
}
