package br.com.fiap.model.dto;

/**
 * Classe que representa um profissional da saúde com as informações básicas herdadas e seus respectivos documentos
 * @version 2.0
 */
public class ProfissionalSaudeDTO extends Pessoa {
    private int idProfissionalSaude;
    private String tipoDocumento;
    private String documento;
    private String especialidade;

    public ProfissionalSaudeDTO() {
    }


    public int getIdProfissionalSaude() {
        return idProfissionalSaude;
    }

    public void setIdProfissionalSaude(int idProfissionalSaude) {
        this.idProfissionalSaude = idProfissionalSaude;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
