package br.com.fiap.model.dto;

import br.com.fiap.model.enums.StatusAtendimento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Classe que representa um atendimento, que pode ser feito por qualquer profissional da saúde a um paciente
 * @version 1.0
 *
 */
public class AtendimentoDTO {
    private int idAtendimento;
    private String formatoAtendimento;
    private LocalDate data;
    private LocalTime hora;
    private String local;
    private StatusAtendimento status;
    private ProfissionalSaudeDTO profissionalSaude;
    private PacienteDTO paciente;
    private ArrayList<LembreteDTO> lembrete;

    public AtendimentoDTO() {
    }

    public int getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(int idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public String getFormatoAtendimento() {
        return formatoAtendimento;
    }

    public void setFormatoAtendimento(String formatoAtendimento) {
        this.formatoAtendimento = formatoAtendimento;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public StatusAtendimento getStatus() {
        return status;
    }

    public void setStatus(StatusAtendimento status) {
        this.status = status;
    }

    public ProfissionalSaudeDTO getProfissionalSaude() {
        return profissionalSaude;
    }

    public void setProfissionalSaude(ProfissionalSaudeDTO profissionalSaude) {
        this.profissionalSaude = profissionalSaude;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    public ArrayList<LembreteDTO> getLembrete() {
        return lembrete;
    }

    public void setLembrete(ArrayList<LembreteDTO> lembrete) {
        this.lembrete = lembrete;
    }
}

