package miniEsercizi.interfacce2;

import java.util.ArrayList;
import java.util.Collections;

public class MiaCollezione implements Collection{
    private final ArrayList<Object> miaLista;

    public MiaCollezione() {
        miaLista = new ArrayList<>();
    }

    @Override
    public void aggiungi(Object o) {
        miaLista.add(o);
    }

    @Override
    public boolean contiene(Object o) {
        return miaLista.contains(o);
    }

    @Override
    public boolean rimuovi(Object o) {
        return miaLista.removeIf(obj -> obj == o);
    }

    @Override
    public int size() {
        return miaLista.size();
    }

    public void visualizzaTutti() {
        miaLista.forEach(System.out::println);
    }

    public void visualizzaTuttiDaUltimo() {
        ArrayList<Object> listaInvertita = miaLista;
        Collections.reverse(listaInvertita);
        listaInvertita.forEach(System.out::println);
    }
}
