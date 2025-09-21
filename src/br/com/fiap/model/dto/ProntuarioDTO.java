package br.com.fiap.model.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * Classe que representa um prontuário de um paciente no sistema, com suas informações médicas relevantes
 * @version 1.0
 */
public class ProntuarioDTO {
    private HashMap<LocalDate, String> diagnostico;
    private String meta;
    private HashMap<LocalDate, String> evolucao;
    private HashMap<String, String> medicacao;
    private List<String> alergia;
    private String apoioLocomocao;
    private String faseTratamento;

   public ProntuarioDTO() {}

    public HashMap<LocalDate, String> getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(HashMap<LocalDate, String> diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public HashMap<LocalDate, String> getEvolucao() {
        return evolucao;
    }

    public void setEvolucao(HashMap<LocalDate, String> evolucao) {
        this.evolucao = evolucao;
    }

    public HashMap<String, String> getMedicacao() {
        return medicacao;
    }

    public void setMedicacao(HashMap<String, String> medicacao) {
        this.medicacao = medicacao;
    }

    public List<String> getAlergia() {
        return alergia;
    }

    public void setAlergia(List<String> alergia) {
        this.alergia = alergia;
    }

    public String getApoioLocomocao() {
        return apoioLocomocao;
    }

    public void setApoioLocomocao(String apoioLocomocao) {
        this.apoioLocomocao = apoioLocomocao;
    }

    public String getFaseTratamento() {
        return faseTratamento;
    }

    public void setFaseTratamento(String faseTratamento) {
        this.faseTratamento = faseTratamento;
    }
}
