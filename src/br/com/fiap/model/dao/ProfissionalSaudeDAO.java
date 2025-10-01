package br.com.fiap.model.dao;

import br.com.fiap.model.dto.ProfissionalSaudeDTO;

import java.sql.*;


/**
 * Classe responsável por realizar operações de acesso a dados dos profissionais da saude no banco de dados
 * @version 1.0
 */
public class ProfissionalSaudeDAO {
    private Connection con;
    public ProfissionalSaudeDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }


    /**
     * Insere um novo profissional da saude no banco de dados
     * @param profissionalSaudeDTO objeto contendo os dados do profissional a ser inserido
     * @return mensagem informando o resultado da operação
     */
    public String inserir(ProfissionalSaudeDTO profissionalSaudeDTO) {

        String sql = "insert into T_ELO_PROFISSIONAL_SAUDE (nc_nome_completo, dt_data_nascimento, dc_cpf, tl_telefone, em_email, tp_tipo_documento, nm_documento, es_especialidade) values (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, profissionalSaudeDTO.getNomeCompleto());
            ps.setDate(2, Date.valueOf(profissionalSaudeDTO.getDataNascimento()));
            ps.setString(3, profissionalSaudeDTO.getCpf());
            ps.setString(4, profissionalSaudeDTO.getTelefone());
            ps.setString(5, profissionalSaudeDTO.getEmail());
            ps.setString(6, profissionalSaudeDTO.getTipoDocumento());
            ps.setString(7, profissionalSaudeDTO.getDocumento());
            ps.setString(8, profissionalSaudeDTO.getEspecialidade());

            if (ps.executeUpdate() > 0) {
                return "Inserido com sucesso";
            } else {
                return "Erro ao inserir";
            }


        } catch (SQLException e) {
            return "Erro de SQL: " + e.getMessage();
        }

    }

    /**
     * Altera um profissional da saude no banco de dados
     * @param profissionalSaudeDTO objeto contendo os dados do profissional a ser alterado
     * @return mensagem informando o resultado da operação
     */
    public String alterar(ProfissionalSaudeDTO profissionalSaudeDTO) {

        String sql = "UPDATE T_ELO_PROFISSIONAL_SAUDE set nc_nome_completo=?, tl_telefone=?, em_email=?,tp_tipo_documento=?, nm_documento=?, es_especialidade=? where idProfissionalSaude=?";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, profissionalSaudeDTO.getNomeCompleto());
            ps.setString(2, profissionalSaudeDTO.getTelefone());
            ps.setString(3, profissionalSaudeDTO.getEmail());
            ps.setString(4, profissionalSaudeDTO.getTipoDocumento());
            ps.setString(5, profissionalSaudeDTO.getDocumento());
            ps.setString(6, profissionalSaudeDTO.getEspecialidade());
            ps.setInt(7, profissionalSaudeDTO.getIdProfissionalSaude());

            if (ps.executeUpdate() > 0) {
                return "Inserido com sucesso";
            } else {
                return "Erro ao Alterar";
            }


        } catch (SQLException e) {
            return "Erro de SQL: " + e.getMessage();
        }
    }


    /**
     * Exclui um profissional da saude no banco de dados
     * @param profissionalSaude objeto contendo os dados do profissional a ser excluído
     * @return mensagem informando o resultado da operação
     */
    public String excluir(ProfissionalSaudeDTO profissionalSaude) {

        String sql = "DELETE FROM T_ELO_PROFISSIONAL_SAUDE where id_profissional_saude=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)){
            ps.setInt(1, profissionalSaude.getIdProfissionalSaude());
            if (ps.executeUpdate() > 0) {
                return "Excluido com Sucesso";
            } else {
                return "Erro ao excluir";
            }
        }
        catch (SQLException e) {
            return "Erro no comando SQL " + e.getMessage();
        }
    }

    /**
     * Busca um profissional da saúde específico no banco de dados
     * @param profissional objeto contendo o ID do profissional a ser buscado
     * @return objeto ProfissionalSaudeDTO com os dados do profissional encontrado ou null
     */
    public ProfissionalSaudeDTO listarUm(ProfissionalSaudeDTO profissional) {
        String sql = "SELECT * FROM T_ELO_PROFISSIONAL_SAUDE where id_profissional_saude=?";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, profissional.getIdProfissionalSaude());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ProfissionalSaudeDTO profissionalSaudeDTO = new ProfissionalSaudeDTO();
                profissionalSaudeDTO.setIdProfissionalSaude(rs.getInt("id_profissional_saude"));
                profissionalSaudeDTO.setNomeCompleto(rs.getString("nc_nome_completo"));
                profissionalSaudeDTO.setTelefone(rs.getString("tl_telefone"));
                profissionalSaudeDTO.setCpf(rs.getString("dc_cpf"));
                profissionalSaudeDTO.setDataNascimento(rs.getDate("dt_data_nascimento").toLocalDate());
                profissionalSaudeDTO.setEmail(rs.getString("em_email"));
                profissionalSaudeDTO.setTipoDocumento(rs.getString("tp_tipo_documento"));
                profissionalSaudeDTO.setDocumento(rs.getString("nm_documento"));
                profissionalSaudeDTO.setEspecialidade(rs.getString("es_especialidade"));
                return profissionalSaudeDTO;
            }
        } catch (SQLException e) {
            System.out.println("Erro no comando SQL " + e.getMessage());
        }
        return null;
    }
}
