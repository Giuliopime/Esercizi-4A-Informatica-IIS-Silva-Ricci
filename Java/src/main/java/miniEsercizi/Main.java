package miniEsercizi;

public class Main {
    public static void main(String[] args) {
        String valori = "34 4 2 34 565 34";
        String[] valoriInStringa = valori.split(" ");
        int[] valoriInIntero = new int[valoriInStringa.length];

        for (int i=0; i<valoriInStringa.length; i++)
            valoriInIntero[i] = Integer.parseInt(valoriInStringa[i]);

        for (int i=0; i<valoriInIntero.length; i++)
            System.out.println(valoriInIntero[i]);
    }
}
