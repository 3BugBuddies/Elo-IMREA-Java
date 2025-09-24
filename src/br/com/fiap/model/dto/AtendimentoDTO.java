package br.com.fiap.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Classe que representa um atendimento, que pode ser feito por qualquer profissional da saúde a um paciente
 * @version 1.0
 *
 */
public class AtendimentoDTO {
    private int idAtendimento;
    private PacienteDTO paciente;
    private ProfissionalSaudeDTO profissionalSaude;
    private String tipoAtendimento;
    private LocalDate data;
    private LocalTime hora;
    private String local;
    private String status;

    public AtendimentoDTO() {
    }


    public int getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(int idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    public ProfissionalSaudeDTO getProfissionalSaude() {
        return profissionalSaude;
    }

    public void setProfissionalSaude(ProfissionalSaudeDTO profissionalSaude) {
        this.profissionalSaude = profissionalSaude;
    }

    public String getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
