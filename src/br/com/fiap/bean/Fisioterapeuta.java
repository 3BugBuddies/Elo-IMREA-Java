package br.com.fiap.bean;

import java.time.LocalDate;

/**
 * Classe que representa um fisioterapeuta, que é um tipo de profissional da saúde.
 * Herda atributos e comportamentos da classe abstrata ProfissionalSaude
 * @version 1.0
 */
public class Fisioterapeuta extends ProfissionalSaude {
    private String crefito;

    public Fisioterapeuta() {}

    public Fisioterapeuta(String nomeCompleto, LocalDate dataNascimento, String cpf, String telefone, String emailInstitucional, String especialidade, String crefito) {
        super(nomeCompleto, dataNascimento, cpf, telefone, emailInstitucional, especialidade);
        this.crefito = crefito;
    }

    public String getCrefito() {
        return crefito;
    }

    public void setCrefito(String crefito) {
        this.crefito = crefito;
    }
}
