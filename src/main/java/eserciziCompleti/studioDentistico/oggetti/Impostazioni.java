package eserciziCompleti.studioDentistico.oggetti;

import eserciziCompleti.studioDentistico.enums.OrdinamentoFatture;
import eserciziCompleti.studioDentistico.enums.OrdinamentoInterventi;
import eserciziCompleti.studioDentistico.enums.OrdinamentoPazienti;

import java.io.Serializable;

public class Impostazioni implements Serializable {
    private String nomeStudio;
    private FiltriPaziente filtriPaziente;
    private FiltriIntervento filtriIntervento;
    private FiltriFattura filtriFattura;
    private OrdinamentoPazienti ordinamentoPazienti;
    private OrdinamentoInterventi ordinamentoInterventi;
    private OrdinamentoFatture ordinamentoFatture;

    public Impostazioni(String nomeStudio, FiltriPaziente filtriPaziente, FiltriIntervento filtriIntervento, FiltriFattura filtriFattura, OrdinamentoPazienti ordinamentoPazienti, OrdinamentoInterventi ordinamentoInterventi, OrdinamentoFatture ordinamentoFatture) {
        this.nomeStudio = nomeStudio;
        this.filtriPaziente = filtriPaziente;
        this.filtriIntervento = filtriIntervento;
        this.filtriFattura = filtriFattura;
        this.ordinamentoPazienti = ordinamentoPazienti;
        this.ordinamentoInterventi = ordinamentoInterventi;
        this.ordinamentoFatture = ordinamentoFatture;
    }

    public Impostazioni() {
        nomeStudio = "Pimenoff";
        filtriPaziente = new FiltriPaziente();
        filtriIntervento = new FiltriIntervento();
        filtriFattura = new FiltriFattura();
        ordinamentoPazienti = OrdinamentoPazienti.DATAULTIMAMODIFICA;
        ordinamentoInterventi = OrdinamentoInterventi.DATAULTIMAMODIFICA;
        ordinamentoFatture = OrdinamentoFatture.DATA;
    }

    public String getNomeStudio() {
        return nomeStudio;
    }

    public FiltriPaziente getFiltriPaziente() {
        return filtriPaziente;
    }

    public FiltriIntervento getFiltriIntervento() {
        return filtriIntervento;
    }

    public void setNomeStudio(String nomeStudio) {
        this.nomeStudio = nomeStudio;
    }

    public void setFiltriPaziente(FiltriPaziente filtriPaziente) {
        this.filtriPaziente = filtriPaziente;
    }

    public void setFiltriIntervento(FiltriIntervento filtriIntervento) {
        this.filtriIntervento = filtriIntervento;
    }

    public OrdinamentoPazienti getOrdinamentoPazienti() {
        return ordinamentoPazienti;
    }

    public void setOrdinamentoPazienti(OrdinamentoPazienti ordinamentoPazienti) {
        this.ordinamentoPazienti = ordinamentoPazienti;
    }

    public OrdinamentoInterventi getOrdinamentoInterventi() {
        return ordinamentoInterventi;
    }

    public void setOrdinamentoInterventi(OrdinamentoInterventi ordinamentoInterventi) {
        this.ordinamentoInterventi = ordinamentoInterventi;
    }

    public FiltriFattura getFiltriFattura() {
        return filtriFattura;
    }

    public void setFiltriFattura(FiltriFattura filtriFattura) {
        this.filtriFattura = filtriFattura;
    }

    public OrdinamentoFatture getOrdinamentoFatture() {
        return ordinamentoFatture;
    }

    public void setOrdinamentoFatture(OrdinamentoFatture ordinamentoFatture) {
        this.ordinamentoFatture = ordinamentoFatture;
    }
}
