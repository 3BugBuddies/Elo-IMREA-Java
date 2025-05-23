package br.com.fiap.bean;

import java.time.LocalDate;

/**
 * Classe que representa o lembrete a ser enviado por um colaborador para um paciente.
 * @version 1.0
 */
public class Lembrete {
    private String assunto;
    private String mensagem;
    private LocalDate dataEnvio;
    private String status;
    private Atendimento atendimento;

    public Lembrete() {
    }

    public Lembrete(String assunto, String mensagem, LocalDate dataEnvio, String status, Atendimento atendimento) {
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
        this.status = status;
        this.atendimento = atendimento;
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
        try {
            if (status.equalsIgnoreCase("Enviado") || status.equalsIgnoreCase("Falhou")) {
                this.status = status;
            } else {
                throw new Exception("Status do lembrete inválido \nInforme se o status é 'Enviado' ou 'Falhou'");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }
}
