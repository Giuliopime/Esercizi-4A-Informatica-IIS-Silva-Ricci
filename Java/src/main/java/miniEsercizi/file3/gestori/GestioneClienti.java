package miniEsercizi.file3.gestori;

import miniEsercizi.file3.modelli.Cliente;
import miniEsercizi.file3.tipologie.TipoRicercaCliente;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GestioneClienti {
    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static ArrayList<Cliente> clienti = new ArrayList<>();

    static {
        try {
            caricaClientiDaFile();
        } catch (Exception e) {
            System.out.println("Errore nel caricare i clienti dal file:" +
                    "\n" + e);
        }

        Runnable salvaClientiSuFile = () -> {
            try {
                GestioneClienti.registraSuFile();
            } catch (IOException e) {
                System.out.println("Errore nel salvare su file i clienti:" +
                        "\n" + e);
            }
        };

        executor.scheduleAtFixedRate(salvaClientiSuFile, 0, 1, TimeUnit.MINUTES);
    }


    public static void caricaClientiDaFile() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("clienti.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);
        clienti = new ArrayList<>();

        while (fis.available() > 0) {
            clienti.add((Cliente) ois.readObject());
        }
    }


    private static void registraSuFile() throws IOException {
        FileOutputStream fos = new FileOutputStream("clienti.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        for (Cliente c : clienti)
            oos.writeObject(c);

        oos.close();
        fos.close();
    }

    private static Cliente creaCliente() {
        String nome = JOptionPane.showInputDialog("Inserisci il nome del cliente");
        String cognome = JOptionPane.showInputDialog("Inserisci il cognome del cliente");
        String username = JOptionPane.showInputDialog("Inserisci l'username per il cliente");
        String password = JOptionPane.showInputDialog("Inserisci la password per l'account del cliente");

        return new Cliente(nome, cognome, username, password);
    }

    public static void nuovoCliente(Cliente cliente) {
        clienti.add(cliente);
    }

    public static void nuovoCliente(Cliente[] clienti) {
        GestioneClienti.clienti.addAll(Arrays.asList(clienti));
    }


    public static void visualizzaTutti() {
        clienti.forEach(System.out::println);
    }

    public static Cliente[] cerca(TipoRicercaCliente tipoRicercaCliente, String stringaRicerca, boolean visualizza) {
        ArrayList<Cliente> clientiFiltrati = new ArrayList<>();
        switch (tipoRicercaCliente) {
            case NOME: {
                // clientiFiltrati = clienti.stream().filter(c -> c.getNome().equalsIgnoreCase(stringaRicerca)).collect(Collectors.toCollection(ArrayList::new));
                for (Cliente c : clienti)
                    if (c.getNome().equalsIgnoreCase(stringaRicerca)) clientiFiltrati.add(c);
                break;
            }
            case COGNOME: {
                // clientiFiltrati = clienti.stream().filter(c -> c.getCognome().equalsIgnoreCase(stringaRicerca)).collect(Collectors.toCollection(ArrayList::new));
                for (Cliente c : clienti)
                    if (c.getCognome().equalsIgnoreCase(stringaRicerca)) clientiFiltrati.add(c);
                break;
            }
            case USERNAME: {
                // clientiFiltrati = clienti.stream().filter(c -> c.getUsername().equalsIgnoreCase(stringaRicerca)).collect(Collectors.toCollection(ArrayList::new));
                for (Cliente c : clienti)
                    if (c.getUsername().equalsIgnoreCase(stringaRicerca)) clientiFiltrati.add(c);
                break;
            }
            case TUTTO: {
                // clientiFiltrati = clienti.stream().filter(c -> c.getUsername().equalsIgnoreCase(stringaRicerca) || c.getNome().equalsIgnoreCase(stringaRicerca) || c.getCognome().equalsIgnoreCase(stringaRicerca)).collect(Collectors.toCollection(ArrayList::new));
                for (Cliente c : clienti)
                    if (c.getNome().equalsIgnoreCase(stringaRicerca) || c.getCognome().equalsIgnoreCase(stringaRicerca) || c.getUsername().equalsIgnoreCase(stringaRicerca))
                        clientiFiltrati.add(c);
            }
        }

        if (visualizza) clientiFiltrati.forEach(System.out::println);
        return clientiFiltrati.toArray(new Cliente[clientiFiltrati.size()]);
    }


    public static ArrayList<Cliente> getClienti() {
        return clienti;
    }
}
