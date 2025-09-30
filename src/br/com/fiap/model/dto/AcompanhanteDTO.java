package br.com.fiap.model.dto;

import java.time.LocalDate;

/**
 * Classe que representa o acompanhante de um paciente registrado.
 * @version 1.0
 */
public class AcompanhanteDTO extends Pessoa {
    private int idAcompanhante;
    private int idPaciente;
    private String parentesco;

    public AcompanhanteDTO() {}

    public int getIdAcompanhante() {
        return idAcompanhante;
    }

    public void setIdAcompanhante(int idAcompanhante) {
        this.idAcompanhante = idAcompanhante;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

}
