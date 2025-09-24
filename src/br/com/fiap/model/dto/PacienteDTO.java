package br.com.fiap.model.dto;

import java.util.List;

/**
 * Classe que representa um paciente no sistema, com dados pessoais, prontuário, acompanhante e
 * lembrete que tem dos atendimentos
 * @version 1.0
 */
public class PacienteDTO extends Pessoa {
    private int idPaciente;
    private String diagnostico;
    private AcompanhanteDTO acompanhante;
    private List<LembreteDTO> lembrete;
    private List<AtendimentoDTO> atendimento;

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

    public AcompanhanteDTO getAcompanhante() {
        return acompanhante;
    }

    public void setAcompanhante(AcompanhanteDTO acompanhante) {
        this.acompanhante = acompanhante;
    }

    public List<LembreteDTO> getLembrete() {
        return lembrete;
    }

    public void setLembrete(List<LembreteDTO> lembrete) {
        this.lembrete = lembrete;
    }

    public List<AtendimentoDTO> getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(List<AtendimentoDTO> atendimento) {
        this.atendimento = atendimento;
    }
}
