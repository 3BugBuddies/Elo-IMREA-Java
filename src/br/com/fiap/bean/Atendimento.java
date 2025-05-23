package br.com.fiap.bean;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Classe que representa um atendimento, que pode ser feito por qualquer profissional da saúde a um paciente
 * @version 1.0
 *
 */
public class Atendimento {
    private Paciente paciente;
    private ProfissionalSaude profissionalSaude;
    private String tipoAtendimento;
    private LocalDate data;
    private LocalTime hora;
    private String local;
    private String status;

    public Atendimento() {
    }

    /**
     * Construtor completo.
     *
     * @param paciente           Paciente atendido
     * @param profissionalSaude  Profissional responsável pelo atendimento
     * @param tipoAtendimento    Tipo do atendimento (consulta, sessão etc.)
     * @param data               Data do atendimento
     * @param hora               Hora do atendimento
     * @param local              Local/link que o atendimento vai acontecer
     */
    public Atendimento(Paciente paciente, ProfissionalSaude profissionalSaude, String tipoAtendimento, LocalDate data, LocalTime hora, String local) {
        this.paciente = paciente;
        this.profissionalSaude = profissionalSaude;
        this.tipoAtendimento = tipoAtendimento;
        this.data = data;
        this.hora = hora;
        this.local = local;
        setStatus("Agendado");
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public ProfissionalSaude getProfissionalSaude() {
        return profissionalSaude;
    }

    public void setProfissionalSaude(ProfissionalSaude profissionalSaude) {
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
        try {
            if (status.equalsIgnoreCase("Agendado") || status.equalsIgnoreCase("Cancelado") || status.equalsIgnoreCase("Realizado")|| status.equalsIgnoreCase("Ausente")) {
                this.status = status;
            } else {
                throw new Exception("Status do atendimento inválido \nInforme se ela estiver 'Agendado' ou 'Cancelado' ou 'Realizado' ou se o paciente estiver 'Ausente'.");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
