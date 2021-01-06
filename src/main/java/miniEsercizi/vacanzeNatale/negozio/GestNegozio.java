package miniEsercizi.vacanzeNatale.negozio;

import java.util.Comparator;
import java.util.Vector;

public class GestNegozio {
    private static final Vector<DVD> dvds = new Vector<>();
    private static final Vector<CD> cds = new Vector<>();

    public static void aggiungiDVD(DVD dvd) {
        dvds.add(dvd);
    }

    public static void aggiungiCD(CD cd) {
        cds.add(cd);
    }

    public static void rimuoviDVD(DVD dvd) {
        dvds.remove(dvd);
    }

    public static void rimuoviCD(CD cd) {
        cds.remove(cd);
    }

    public static void elencaPerCosto(double min, double max) {
        for (DVD dvd : dvds)
            if(dvd.getCosto() > min && dvd.getCosto() < max)
                System.out.println(dvd);

        for (CD cd : cds)
            if(cd.getCosto() > min && cd.getCosto() < max)
                System.out.println(cd);
    }

    public static double calcolaNumMedioBraniCD() {
        int numTotaleBrani = 0;
        for (CD cd : cds)
            numTotaleBrani += cd.getNumBrani();

        return numTotaleBrani / (cds.size() != 0 ? cds.size() : 1);
    }

    public static void visualizzaDVDOrdineCrescenteLunghezza() {
        Vector<DVD> dvdOrdinati = dvds;
        dvdOrdinati.sort(Comparator.comparing(DVD::getLunghezzaFilm));

        dvdOrdinati.forEach(System.out::println);
    }
}
