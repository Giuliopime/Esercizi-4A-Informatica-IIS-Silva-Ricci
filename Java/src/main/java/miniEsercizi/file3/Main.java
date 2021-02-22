package miniEsercizi.file3;

/*
scrivere la classe Cliente che fa parte della gestione di un negozio online.
La classe deve contenere nome, cognome, username e password.

Scrivere la classe InserisciCliente per inserire tutti i client in un file di record.
Scrivere la classe VisualizzaClienti che visualizza:
        1- tutti i clienti
        2- i dati di un determinato cliente del quale si conosce username
        3- tutti i dati dei clienti che hanno un determinato cognome

Scrivere la classe fattura che genera la fattura relativa alla vendita di più articoli ad un cliente.
Si devono inserire nome e cognome del cliente, nome dell'articolo e quantità e verranno stampati i seguenti dati:
- nome cliente
- cognome cliente
- username
- nome articolo
- prezzo unitario
- aquantità venduta e totale (quantità*prezzo)
(fare questo per ogni articolo)
*/

import miniEsercizi.file3.gestori.GestioneClienti;
import miniEsercizi.file3.modelli.Cliente;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Cliente c = new Cliente("Giulio", "Pimenoff", "giulio", "password");
        Cliente c2 = new Cliente("Mario", "Bottino", "mario", "password");
        Cliente c3 = new Cliente("Marco", "Galvan", "marc", "password");

        GestioneClienti.nuovoCliente(c);
        GestioneClienti.nuovoCliente(c2);
        GestioneClienti.nuovoCliente(c3);

        GestioneClienti.visualizzaTutti();
    }
}
