package br.com.fiap.model.dto;

import java.util.List;

/**
 * Classe que representa um paciente no sistema, com dados pessoais, prontuário, acompanhante e
 * lembrete que tem dos atendimentos
 * @version 1.0
 */
public class PacienteDTO extends Pessoa {
    private ProntuarioDTO prontuario;
    private List<AcompanhanteDTO> acompanhante;
    private List<LembreteDTO> lembrete;

    public PacienteDTO() {
    }

    public ProntuarioDTO getProntuario() {
        return prontuario;
    }

    public void setProntuario(ProntuarioDTO prontuario) {
        this.prontuario = prontuario;
    }

    public List<AcompanhanteDTO> getAcompanhante() {
        return acompanhante;
    }

    public void setAcompanhante(List<AcompanhanteDTO> acompanhante) {
        this.acompanhante = acompanhante;
    }

    public List<LembreteDTO> getLembrete() {
        return lembrete;
    }

    public void setLembrete(List<LembreteDTO> lembrete) {
        this.lembrete = lembrete;
    }
}
