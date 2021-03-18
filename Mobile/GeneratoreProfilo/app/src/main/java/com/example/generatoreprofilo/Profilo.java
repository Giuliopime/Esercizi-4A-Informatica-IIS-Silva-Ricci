package com.example.generatoreprofilo;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class Profilo implements Serializable {
    private String nome;
    private String cognome;
    private String sesso;
    private String dataNascita;
    private ArrayList<Interessi> interessi;
    private String altriInteressi;

    public Profilo(String nome, String cognome, String sesso, String dataNascita, ArrayList<Interessi> interessi, String altriInteressi) {
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.dataNascita = dataNascita;
        this.interessi = interessi;
        this.altriInteressi = altriInteressi;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public ArrayList<Interessi> getInteressi() {
        return interessi;
    }

    public void setInteressi(ArrayList<Interessi> interessi) {
        this.interessi = interessi;
    }

    public String getAltriInteressi() {
        return altriInteressi;
    }

    public void setAltriInteressi(String altriInteressi) {
        this.altriInteressi = altriInteressi;
    }

    public String toString() {
        return "Nome: " + nome +
                "\nCognome: " + cognome +
                "\nSesso: " + (sesso.startsWith("m") ? "Maschio" : sesso.startsWith("f") ? "Femmina" : "Altro") +
                "\nData nascita: " + dataNascita +
                "\nInteressi: " + (interessi.size() > 0 ? TextUtils.join(", ", interessi) : "nessuno") +
                "\nInteressi personalizzati: " + (altriInteressi != null ? altriInteressi : "nessuno");
    }
}
