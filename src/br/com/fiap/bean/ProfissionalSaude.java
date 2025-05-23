package br.com.fiap.bean;

import java.time.LocalDate;

/**
 * Classe Abstrata para servir de base para profissionais de saúde
 * que forem registrados no sistema
 * @version 1.0
 */
public abstract class ProfissionalSaude {
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String cpf;
    private String telefone;
    private String emailInstitucional;
    private String especialidade;

    public ProfissionalSaude() {
    }

    public ProfissionalSaude(String nomeCompleto, LocalDate dataNascimento, String cpf, String telefone, String emailInstitucional, String especialidade) {
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.telefone = telefone;
        this.emailInstitucional = emailInstitucional;
        this.especialidade = especialidade;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmailInstitucional() {
        return emailInstitucional;
    }

    public void setEmailInstitucional(String emailInstitucional) {
        this.emailInstitucional = emailInstitucional;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
