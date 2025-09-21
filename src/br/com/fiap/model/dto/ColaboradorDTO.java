package br.com.fiap.model.dto;

public class ColaboradorDTO extends Pessoa {
    private String departamento;

    public ColaboradorDTO() {
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
