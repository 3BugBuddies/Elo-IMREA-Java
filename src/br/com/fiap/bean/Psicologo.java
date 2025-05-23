package br.com.fiap.bean;

import java.time.LocalDate;

/**
 * Classe que representa um psicologo, que é um tipo de profissional da saúde.
 * Herda atributos e comportamentos da classe abstrata ProfissionalSaude
 * @version 1.0
 */
public class Psicologo extends ProfissionalSaude {
    private String crp;

    public Psicologo() {}

    public Psicologo(String nomeCompleto, LocalDate dataNascimento, String cpf, String telefone, String emailInstitucional, String especialidade, String crp) {
        super(nomeCompleto, dataNascimento, cpf, telefone, emailInstitucional, especialidade);
        this.crp = crp;
    }

    public String getCrp() {
        return crp;
    }

    public void setCrp(String crp) {
        this.crp = crp;
    }
}
