package eserciziCompleti.studioDentistico.enums;

public enum TipoIntervento {
    ESTRAZIONE("Estrazione"), CARIE("Carie"), PULIZIA("Pulizia"), CONTROLLO("Controllo"), APPARECCHIO("Apparecchio");


    public final String nome;

    TipoIntervento(String nome) {
        this.nome = nome;
    }

    public static TipoIntervento[] getTipiIntervento() {
        return new TipoIntervento[]{
                ESTRAZIONE, CARIE, PULIZIA, CONTROLLO, APPARECCHIO
        };
    }
}
