package br.com.fiap.model.dto;

import java.util.HashMap;

/**
 * Classe Abstrata para servir de base para profissionais de saúde
 * que forem registrados no sistema
 * @version 1.0
 */
public class ProfissionalSaudeDTO extends Pessoa {
    private int idProfissionalSaude;
    private HashMap<String, String> documento;
    private String especialidade;

    public ProfissionalSaudeDTO() {
    }

    public int getIdProfissionalSaude() {
        return idProfissionalSaude;
    }

    public void setIdProfissionalSaude(int idProfissionalSaude) {
        this.idProfissionalSaude = idProfissionalSaude;
    }

    public HashMap<String, String> getDocumento() {
        return documento;
    }

    public void setDocumento(HashMap<String, String> documento) {
        this.documento = documento;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

}
