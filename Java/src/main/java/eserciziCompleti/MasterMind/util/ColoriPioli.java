/*
Questa classe è utilizzata per gestire i colori dei pioli ed è statica perchè i valori non cambiano mai
 */

package eserciziCompleti.MasterMind.util;

import java.awt.*;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class ColoriPioli {
    // Variabili ed array degli 8 colori default degli 8 pioli
    private static final Color rosso = Color.decode("#FF3333"),
            rosa = Color.decode("#EF44B6"),
            arancio = Color.decode("#FC5D01"),
            ciano = Color.decode("#5AF9F9"),
            verde = Color.decode("#6AD5A6"),
            giallo = Color.decode("#FEFE08"),
            blu = Color.decode("#3333FF"),
            viola = Color.decode("#9900FF");

    private static final Color[] colori = new Color[]{
            rosso,
            rosa,
            arancio,
            ciano,
            verde,
            giallo,
            blu,
            viola
    };

    /* Prende un colore a random dall'array di colori
    Il limitatore server per limitare la quantità di colori da cui viene scelto il colore
    visto che nel gioco si possono selezionare diverse difficoltà in cui aumenta o diminuisce il possibile numero di colori del codice da indovinare
    Ad esempio in difficoltà facile si gioca solamente con i primi quattro colori
    */
    public static Color getRandom(int limitatore) {
        int indice = ThreadLocalRandom.current().nextInt(0, limitatore);
        return colori[indice];
    }

    // Controlla se esiste un piolo di un certo colore
    public static boolean esisteColore(Color colore) {
        return Arrays.asList(colori).contains(colore);
    }

    // Controlla se tutti i colori passati al metodo corrispondono ad almeno un colore di un piolo
    // Se quindi sono tutti validi colori dei pioli
    public static boolean esistonoTuttiColori(Color[] colori) {
        return Arrays.asList(ColoriPioli.colori).containsAll(Arrays.asList(colori));
    }
}
