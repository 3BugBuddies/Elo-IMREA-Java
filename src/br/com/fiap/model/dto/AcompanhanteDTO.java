package br.com.fiap.model.dto;

/**
 * Classe que representa o acompanhante de um paciente registrado.
 * @version 1.0
 */
public class AcompanhanteDTO extends Pessoa {
    private int idAcompanhante;
    private String parentesco;

    public AcompanhanteDTO() {}

    public int getIdAcompanhante() {
        return idAcompanhante;
    }

    public void setIdAcompanhante(int idAcompanhante) {
        this.idAcompanhante = idAcompanhante;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}
