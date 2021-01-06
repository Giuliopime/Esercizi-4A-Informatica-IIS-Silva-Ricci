package miniEsercizi.ripasso;

import javax.swing.*;

public class Coalizione {
    private String nome;
    private Candidato[] candidati;

    public Coalizione(String nome, int numCandidati) {
        this.nome = nome;
        candidati = new Candidato[numCandidati];
        for(int i=0; i<candidati.length; i++) {
            String cognome = JOptionPane.showInputDialog("Inserisci il cognome del "+(i+1)+"° candidato");
            candidati[i] = new Candidato(cognome);
        }
    }

    public boolean trova(String cognome) {
        for(Candidato candidato: candidati) if(candidato.equals(cognome)) return true;
        return false;
    }

    public int vota(String cognome) {
        for(int i=0; i<candidati.length; i++) {
            Candidato candidato = candidati[i];
            if(candidato.equals(cognome)) {
                candidato.vota();
                return i;
            }
        }
        return -1;
    }

    public Candidato[] getCandidati() {
        return candidati;
    }

    public String getNome() {
        return nome;
    }

    public int getVotiTot() {
        int votiTot = 0;
        for(Candidato candidato: candidati) votiTot += candidato.getVoti();
        return votiTot;
    }
}
