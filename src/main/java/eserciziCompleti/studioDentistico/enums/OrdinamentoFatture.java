package eserciziCompleti.studioDentistico.enums;

public enum OrdinamentoFatture {
    DATA("Data"),
    PAZIENTE("Paziente"),
    INTERVENTO("Paziente");

    private final String nome;

    OrdinamentoFatture(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
