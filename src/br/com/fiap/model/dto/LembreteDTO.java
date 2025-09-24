package br.com.fiap.model.dto;

import java.time.LocalDate;

/**
 * Classe que representa o lembrete a ser enviado por um colaborador para um paciente.
 * @version 1.0
 */
public class LembreteDTO {
    private int idLembrete;
    private int idColaborador;
    private int idAtendimento;
    private String assunto;
    private String mensagem;
    private LocalDate dataEnvio;
    private String status;

    public LembreteDTO() {
    }

    public int getIdLembrete() {
        return idLembrete;
    }

    public void setIdLembrete(int idLembrete) {
        this.idLembrete = idLembrete;
    }

    public int getIdColaborador() {
        return idColaborador;
    }
    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public int getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(int idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
