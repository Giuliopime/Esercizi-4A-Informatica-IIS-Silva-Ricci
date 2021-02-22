package miniEsercizi.ripasso2;

import java.util.Random;

public class Mazzo {
    private Carta[] carte = new Carta[52];

    public Mazzo() {
        String[] semi = {"cuori", "quadri", "fiori", "picche"};
        int count = 0;

        for (int i = 0; i < 4; i++) {
            for (int y = 0; y < 13; y++) {
                carte[count] = new Carta(semi[i], y);
                count++;
            }
        }
        mescola();
    }

    public void mescola() {
        Random rand = new Random();
        for (int i = 0; i < carte.length; i++) {
            int randomIndice = rand.nextInt(carte.length);
            Carta temp = carte[randomIndice];
            carte[randomIndice] = carte[i];
            carte[i] = temp;
        }
    }

    public Carta pesca() {
        Carta temp = carte[carte.length - 1];
        for (int i = 0; i < carte.length; i++) {
            Carta temp2 = carte[i];
            carte[i] = temp;
            temp = temp2;
        }

        return carte[0];
    }
}
