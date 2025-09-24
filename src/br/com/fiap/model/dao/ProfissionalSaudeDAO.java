package br.com.fiap.model.dao;

import br.com.fiap.model.dto.ProfissionalSaudeDTO;

import java.sql.*;


public class ProfissionalSaudeDAO {
    private Connection con;
    public ProfissionalSaudeDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    
    public String inserir(ProfissionalSaudeDTO profissionalSaudeDTO) {

        String sql = "insert into T_PROFISSIONAL_SAUDE (nome_completo, data_nasc, cpf, telefone, email, tipo_documento, documento, especialidade) values (?, ?, ?, ?, ?, ?, ?, ?)";

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

    
    public String alterar(ProfissionalSaudeDTO profissionalSaudeDTO) {

        String sql = "UPDATE T_PROFISSIONAL_SAUDE set nomeCompleto=?, telefone=?, email=?,tipoDocumento=?, documento=?, especialidade=? where idProfissionalSaude=?";

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

    
    public String excluir(int idProfissionalSaude) {

        String sql = "DELETE FROM T_PROFISSIONAL_SAUDE where id_profissional_saude=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)){
            ps.setInt(1, idProfissionalSaude);
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

    
    public ProfissionalSaudeDTO listarUm(int idProfissionalSaude) {
        String sql = "SELECT * FROM T_PROFISSIONAL_SAUDE where id_profissional_saude=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, idProfissionalSaude);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ProfissionalSaudeDTO profissionalSaudeDTO = new ProfissionalSaudeDTO();
                profissionalSaudeDTO.setIdProfissionalSaude(rs.getInt("id_profissional_saude"));
                profissionalSaudeDTO.setNomeCompleto(rs.getString("nc_nome_completo"));
                profissionalSaudeDTO.setTelefone(rs.getString("tl_telefone"));
                profissionalSaudeDTO.setDataNascimento(rs.getDate("dt_data_nascimento").toLocalDate());
                profissionalSaudeDTO.setEmail(rs.getString("em_email"));
                profissionalSaudeDTO.setTipoDocumento(rs.getString("tipo_documento"));
                profissionalSaudeDTO.setDocumento(rs.getString("documento"));
                profissionalSaudeDTO.setEspecialidade(rs.getString("especialidade"));
                return profissionalSaudeDTO;

            } else {
                System.out.println("Registro não encontrado");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro no comando SQL " + e.getMessage());
            return null;
        }
    }
}
