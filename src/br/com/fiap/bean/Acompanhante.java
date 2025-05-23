package br.com.fiap.bean;

import java.time.LocalDate;

/**
 * Classe que representa o acompanhante de um paciente registrado.
 * @version 1.0
 */
public class Acompanhante {
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String cpf;
    private String telefone;
    private String email;
    private String parentesco;

    public Acompanhante() {}

    public Acompanhante(String nomeCompleto, LocalDate dataNascimento, String cpf, String telefone, String email, String parentesco) {
        this.nomeCompleto = nomeCompleto;
        setDataNascimento(dataNascimento);
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.parentesco = parentesco;
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
        LocalDate dataLimiteNascimento = LocalDate.now().minusYears(18);
        if (dataNascimento.isBefore(dataLimiteNascimento) || dataNascimento.isEqual(dataLimiteNascimento)) {
            this.dataNascimento = dataNascimento;
        } else {
            System.out.println("O acompanhante precisa ter pelo menos 18 anos.");
        }
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}
