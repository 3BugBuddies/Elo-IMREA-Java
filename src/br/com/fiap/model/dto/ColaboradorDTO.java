package br.com.fiap.model.dto;

public class ColaboradorDTO extends Pessoa {
    private int idColaborador;
    private String unidade;

    public ColaboradorDTO() {
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
}
