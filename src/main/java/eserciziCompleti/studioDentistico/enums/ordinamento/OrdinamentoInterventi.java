package eserciziCompleti.studioDentistico.enums.ordinamento;

/**
 *  Diverse modalità di ordinamento degli interventi
 */
public enum OrdinamentoInterventi {
    DATACREAZIONE("Data creazione"),
    DATAULTIMAMODIFICA("Data ultima modifica"),
    TIPOINTERVENTO("Tipo intervento"),
    PAZIENTE("Paziente"),
    COSTO("Costo"),
    TEMPO("Tempo");

    private final String nome;

    OrdinamentoInterventi(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
