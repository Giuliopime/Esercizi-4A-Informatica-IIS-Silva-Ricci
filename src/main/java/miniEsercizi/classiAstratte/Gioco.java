package miniEsercizi.classiAstratte;

import javax.swing.*;

public class Gioco {
    public static void main(String[] args) {
        String nomeG1 = JOptionPane.showInputDialog("Inserire nome primo giocatore");
        String nomeG2 = JOptionPane.showInputDialog("Inserire nome secondo giocatore");

        Dado d1 = new Dado(), d2 = new Dado();
        int p1 = 0, p2 = 0;

        do {
            p1 = Integer.parseInt(d1.lancio());
            System.out.println(nomeG1 + " ottiene " + p1);

            p2 = Integer.parseInt(d2.lancio());
            System.out.println(nomeG2 + " ottiene " + p2);

        } while(p1 == p2);

        if(p1 > p2)
            System.out.println(nomeG1 + " vince");
        else
            System.out.println(nomeG2 + " vince");
    }
}
