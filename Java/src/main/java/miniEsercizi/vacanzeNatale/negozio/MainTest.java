package miniEsercizi.vacanzeNatale.negozio;

public class MainTest {
    public static void main(String[] args) {
        GestNegozio.aggiungiCD(new CD("ASDFGHJKL", "CD 1", 12.50, 8));
        GestNegozio.aggiungiCD(new CD("QWERTYUIO", "CD 2", 15, 2));
        GestNegozio.aggiungiCD(new CD("ZXCVBNMAS", "CD 3", 14, 10));
        GestNegozio.aggiungiCD(new CD("MNASIOOPD", "CD 4", 18.50, 6));
        GestNegozio.aggiungiCD(new CD("ACSACNOUN", "CD 5", 10, 15));

        GestNegozio.aggiungiDVD(new DVD("CBKISAOA", "DVD 1", 12, 120));
        GestNegozio.aggiungiDVD(new DVD("ACJSNASS", "DVD 2", 20, 80));
        GestNegozio.aggiungiDVD(new DVD("IAUSHHCQ", "DVD 3", 6, 100));
        GestNegozio.aggiungiDVD(new DVD("ABNIUSDA", "DVD 4", 13.60, 85));
        GestNegozio.aggiungiDVD(new DVD("ICHIVUHB", "DVD 5", 11.90, 102));

        System.out.println(GestNegozio.calcolaNumMedioBraniCD());
        System.out.println("\n<----------------------->\n");
        GestNegozio.elencaPerCosto(2, 15);
        System.out.println("\n<----------------------->\n");
        GestNegozio.visualizzaDVDOrdineCrescenteLunghezza();
    }
}
