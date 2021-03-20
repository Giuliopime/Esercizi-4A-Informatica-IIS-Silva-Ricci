package eserciziThreads.threads3;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Dormiente> dormienti = new ArrayList<>() {{
            add(new Dormiente("T 1"));
            add(new Dormiente("T 2"));
            add(new Dormiente("T 3"));
            add(new Dormiente("T 4"));
            add(new Dormiente("T 5"));
        }};

        for (Dormiente dormiente : dormienti) {
            dormiente.start();
        }

        dormienti.get(2).stop();

        System.out.println(dormienti.get(2).isInterrupted() ? "T 3 è stato interrotto" : "T 3 non è stato interrotto");
    }
}
