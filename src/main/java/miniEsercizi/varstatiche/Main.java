package miniEsercizi.varstatiche;

/*
Creare la classe componente con le informazioni relative ad un componente di una famiglia:
- nome
- ruolo
- portafoglio
E metodi:
- aggiungiDenaro
- spendiDenaro

Scrivere la classe che crea tre istanze della classe componente: una madre e due figli.
Aggiunge al portafoglio 1500 euro da parte della madre.
Il figlio 1 prende 300 euro dal portafolgio
 */

public class Main {
    public static void main(String[] args) {
        // Creo 3 oggetti Componente
        Componente madre = new Componente("Chiara", "madre");
        Componente figlio1 = new Componente("Andrea", "figlio");
        Componente figlio2 = new Componente("Cesare", "figlio");

        Componente.portafoglio = 0;
        System.out.println(madre.toString());

        // Uso i metodi predisposti dalla classe Componente per modificare la variabile statica portafoglio
        madre.aggiungiSoldi(1500);
        System.out.println(madre.toString());

        figlio1.prendiSoldi(300);
        System.out.println(figlio1.toString());
    }
}
