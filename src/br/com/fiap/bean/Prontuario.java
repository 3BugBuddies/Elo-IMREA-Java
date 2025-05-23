package br.com.fiap.bean;

/**
 * Classe que representa um prontuário de um paciente no sistema, com suas informações médicas relevantes
 * @version 1.0
 */
public class Prontuario {
    private String diagnostico;
    private String meta;
    private String evolucao;
    private String medicacao;
    private String alergia;
    private String apoioLocomocao;
    private String faseTratamento;

   public Prontuario() {}

    public Prontuario(String diagnostico, String meta, String evolucao, String medicacao, String alergia, String apoioLocomocao, String faseTratamento) {
        this.diagnostico = diagnostico;
        this.meta = meta;
        this.evolucao = evolucao;
        this.medicacao = medicacao;
        this.alergia = alergia;
        this.apoioLocomocao = apoioLocomocao;
        this.faseTratamento = faseTratamento;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getEvolucao() {
        return evolucao;
    }

    public void setEvolucao(String evolucao) {
        this.evolucao = evolucao;
    }

    public String getMedicacao() {
        return medicacao;
    }

    public void setMedicacao(String medicacao) {
        this.medicacao = medicacao;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
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
