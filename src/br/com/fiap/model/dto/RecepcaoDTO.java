package br.com.fiap.model.dto;


/**
 * Classe que representa um colaborador Administrativo responsável por agendamentos e envio de lembretes.
 * @version 1.0
 */

public class RecepcaoDTO {
    ColaboradorDTO colaborador;
    private String unidade;

    public RecepcaoDTO() {
    }

    public ColaboradorDTO getColaborador() {
        return colaborador;
    }

    public void setColaborador(ColaboradorDTO colaborador) {
        this.colaborador = colaborador;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
}
