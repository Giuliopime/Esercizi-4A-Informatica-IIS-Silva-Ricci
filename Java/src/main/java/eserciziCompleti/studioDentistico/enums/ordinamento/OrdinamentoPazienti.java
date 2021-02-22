package eserciziCompleti.studioDentistico.enums.ordinamento;

/**
 *  Diverse modalità di ordinamento dei pazienti
 */
public enum OrdinamentoPazienti {
    DATACREAZIONE("Data creazione"), DATAULTIMAMODIFICA("Data ultima modifica"),
    NOME("Nome"), COGNOME("Cognome"),
    DATANASCITA("Data di nascita");

    private final String nome;

    OrdinamentoPazienti(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
