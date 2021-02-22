package miniEsercizi.ripasso4;

import java.io.*;
import java.util.ArrayList;

public class esempioCollezione {
    public static void main(String[] args) {
        ArrayList<Oggetto> lista = new ArrayList<>();

        Oggetto oggetto = new Oggetto("Oggetto di test");
        Oggetto oggetto2 = new Oggetto("Oggetto di test 2");
        Oggetto oggetto3 = new Oggetto("Oggetto di test 3");

        lista.add(oggetto);
        lista.add(oggetto2);
        lista.add(oggetto3);

        try {
            FileOutputStream fos = new FileOutputStream("oggetti.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for(int i=0; i<lista.size(); i++) {
                oos.writeObject(lista.get(i));
            }

            oos.close();
            fos.close();
        }
        catch (Exception e) {
            System.out.println(e);
            System.out.println("File non esiste");
        }

        try {
            FileInputStream fis = new FileInputStream("oggetti.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            ArrayList<Oggetto> lista2 = new ArrayList<>();

            while(fis.available() > 0) {
                Oggetto letto = (Oggetto) ois.readObject();
                lista2.add(letto);
            }

            System.out.println(lista2);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
