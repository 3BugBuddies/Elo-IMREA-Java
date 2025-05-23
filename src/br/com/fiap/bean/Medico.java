package br.com.fiap.bean;

import java.time.LocalDate;


/**
 * Classe que representa um médico, que é um tipo de profissional da saúde.
 * Herda atributos e comportamentos da classe abstrata ProfissionalSaude
 * @version 1.0
 */
public class Medico extends ProfissionalSaude{
    private String crm;

    public Medico() {}

    public Medico(String nomeCompleto, LocalDate dataNascimento, String cpf, String telefone, String emailInstitucional, String especialidade, String crm) {
        super(nomeCompleto, dataNascimento, cpf, telefone, emailInstitucional, especialidade);
        this.crm = crm;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }
}
