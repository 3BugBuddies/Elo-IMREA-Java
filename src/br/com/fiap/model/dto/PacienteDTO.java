package br.com.fiap.model.dto;

import java.util.ArrayList;

/**
 * Classe que representa um paciente no sistema, com dados pessoais, prontuário, acompanhante e
 * lembrete que tem dos atendimentos
 * @version 1.0
 */
public class PacienteDTO extends Pessoa {
    private int idPaciente;
    private String diagnostico;
    private ArrayList<AcompanhanteDTO> acompanhante;
    private ArrayList<LembreteDTO> lembrete;
    private ArrayList<AtendimentoDTO> atendimento;

    public PacienteDTO() {
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public ArrayList<AcompanhanteDTO> getAcompanhante() {
        return acompanhante;
    }

    public void setAcompanhante(ArrayList<AcompanhanteDTO> acompanhante) {
        this.acompanhante = acompanhante;
    }

    public ArrayList<LembreteDTO> getLembrete() {
        return lembrete;
    }

    public void setLembrete(ArrayList<LembreteDTO> lembrete) {
        this.lembrete = lembrete;
    }

    public ArrayList<AtendimentoDTO> getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(ArrayList<AtendimentoDTO> atendimento) {
        this.atendimento = atendimento;
    }
}
