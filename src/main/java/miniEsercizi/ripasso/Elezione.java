package miniEsercizi.ripasso;

import javax.swing.*;

/*
In previsione delle prossime elezioni il governo sta progettando l'informatizzazione del sistema di voto al fine di diminuire le possibilità di brogli.
Ogni scheda elettorale presenterà un elenco delle coalizioni politiche e sarà possibile esprimere un unica preferenza per un candidato della lista votato.

- Classe candidato di variabili cognome e voti e con metodi costruttore, metodi get, metodo vota, toString, equals che riceve il nome di un altro candidato.

- Classe coalizione di variabili nome, array candidati e metodi costruttore, metodo trova che riceve il nome del candidato e restituisce il candidato, metodo vota che riceve il nome del candidato ed assegna il voto, toString, quantiVoti che calcola i voti totali per la coalizionel.
 */
public class Elezione {
    private int numCoalizioni = 3;
    private Coalizione[] scheda = new Coalizione[numCoalizioni];

    public static void main(String args[]){
        Elezione elezione = new Elezione();
        elezione.creaScheda();
        int scelta = -1;

        do {
            do{
                try{
                    scelta = Integer.parseInt(JOptionPane.showInputDialog("- Per visualizzare la scheda digitare 1\n- Per votare digitare 2\n- Per visualizzare i risultati digitare 3\n\n- Per uscire dal programma digitare 0"));
                    if(scelta<0 || scelta>3)JOptionPane.showMessageDialog(null, "Devi inserire un numero compreso tra zero e tre.");
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Devi inserire un numero compreso tra zero e tre.");
                }
            }while(scelta< 0 || scelta > 3);

            if(scelta==1) elezione.visualizzaScheda();
            if(scelta==2) elezione.vota();
            if(scelta==3) elezione.visualizzaRisultati();

        }while (scelta != 0);

        System.out.println("Programma arrestato con successo.");
        System.exit(0);
    }

    public void creaScheda(){
        for(int i=0; i<numCoalizioni; i++){
            String nomeCoalizione="";
            int numCandidati = 0;

            do{
                try{
                    nomeCoalizione = JOptionPane.showInputDialog("Inserire il nome della "+(i+1)+"° coalizione.");
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Devi rifare l'inserimento in modo corretto.");
                }
            }while(nomeCoalizione.isEmpty());

            do{
                try{
                    numCandidati = Integer.parseInt(JOptionPane.showInputDialog("Inserisci il numero di candidati della coalizione \""+nomeCoalizione+"\"."));
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, "Inserisci un numero maggiore di 0");
                }
            }while(numCandidati<=0);

            scheda[i] = new Coalizione(nomeCoalizione, numCandidati);
        }
    }

    public void visualizzaScheda(){
        int indiceCoalizione = -1;
        do{
            try{
                String nome = JOptionPane.showInputDialog("Inserisci il nome della coalizione da visualizzare:");
                for(int i=0; i<scheda.length; i++) {
                    if(scheda[i].getNome().equalsIgnoreCase(nome)) indiceCoalizione = i;
                }
                if(indiceCoalizione<0) JOptionPane.showMessageDialog(null, "Coalizione non trovata, reinserirne il nome.");
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Inserisci un nome di una coalizione");
            }
        }while(indiceCoalizione < 0);


        Candidato[] candidati = scheda[indiceCoalizione].getCandidati();

        StringBuilder costruzioneVisualizzazione = new StringBuilder();
        costruzioneVisualizzazione.append("I seguenti candidati fanno parte del partito: "+scheda[indiceCoalizione].getNome()+"\n");
        for(Candidato candidato: candidati) {
            costruzioneVisualizzazione.append("- "+candidato.getCognome()+"\n");
        }

        System.out.println("-------------------------------------\n"+costruzioneVisualizzazione.toString()+"-------------------------------------\n");
    }

    public void vota(){
        String cognome, nome;
        int indiceCoalizione=-1, indiceCandidato=-1;

        do {
            try {
                nome = JOptionPane.showInputDialog("Inserisci il nome della coalizione per cui votare:");
                for(int i=0; i<numCoalizioni; i++) {
                    if(scheda[i].getNome().equalsIgnoreCase(nome)) indiceCoalizione = i;
                }
                if(indiceCoalizione<0) JOptionPane.showMessageDialog(null, "Coalizione non trovata, reinserirne il nome.");
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Inserisci un nome di una coalizione");
            }
        }while(indiceCoalizione<0);
        JOptionPane.showMessageDialog(null, "Coalizione selezionata, inserire il cognome del candidato della coalizione per cui votare.");

        do {
            try {
                cognome = JOptionPane.showInputDialog("Inserisci il cognome del candidato per cui votare:");
                indiceCandidato = scheda[indiceCoalizione].vota(cognome);
                if(indiceCandidato<0) JOptionPane.showMessageDialog(null, "Candidato non trovato nella coalizione \""+scheda[indiceCoalizione].getNome()+"\", reinserirne il cognome.");
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Inserisci un cognome di un candidato");
            }
        }while(indiceCandidato<0);

        JOptionPane.showMessageDialog(null, "Votazione avvenuta con successo per il candidato: "+scheda[indiceCoalizione].getCandidati()[indiceCandidato].getCognome());
    }

    public void visualizzaRisultati() {
        for(Coalizione coalizione: scheda){
            Candidato[] candidati = coalizione.getCandidati();

            StringBuilder costruzioneVisualizzazione = new StringBuilder();
            costruzioneVisualizzazione.append("I seguenti candidati fanno parte del partito: "+coalizione.getNome()+"\n");
            for(Candidato candidato: candidati) {
                costruzioneVisualizzazione.append("- "+candidato.getCognome()+"\n");
            }
            costruzioneVisualizzazione.append("Questa coalizione ha ottenuto "+coalizione.getVotiTot()+" voto/i in totale.\n");

            System.out.println(costruzioneVisualizzazione.toString());
        }
    }
}
