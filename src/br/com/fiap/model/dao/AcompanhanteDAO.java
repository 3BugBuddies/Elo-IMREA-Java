package br.com.fiap.model.dao;

import br.com.fiap.model.dao.interfaces.IDAO;
import br.com.fiap.model.dto.AcompanhanteDTO;

import java.sql.*;

public class AcompanhanteDAO {
    private Connection con;

    public AcompanhanteDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }
    
    public String inserir(AcompanhanteDTO acompanhanteDTO) {

        String sql = "insert into T_ELO_ACOMPANHANTE (nc_nome_completo, dt_data_nascimento, dc_cpf, tl_telefone, em_email, pr_parentesco, id_paciente) values (?,?,?,?,?,?, ?)";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, acompanhanteDTO.getNomeCompleto());
            ps.setDate(2, Date.valueOf(acompanhanteDTO.getDataNascimento()));
            ps.setString(3, acompanhanteDTO.getCpf());
            ps.setString(4, acompanhanteDTO.getTelefone());
            ps.setString(5, acompanhanteDTO.getEmail());
            ps.setString(6, acompanhanteDTO.getParentesco());
            ps.setInt(7, acompanhanteDTO.getIdPaciente());

            if (ps.executeUpdate() > 0) {
                return "Inserido com sucesso";
            } else {
                return "Erro ao inserir";
            }


        } catch (SQLException e) {
            return "Erro de SQL: " + e.getMessage();
        }
    }

    public String alterar(AcompanhanteDTO acompanhanteDTO) {

        String sql = "UPDATE T_ELO_ACOMPANHANTE set nc_nome_completo=?, tl_telefone=?, em_email=?, pr_parentesco=? where id_acompanhante=?";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, acompanhanteDTO.getNomeCompleto());
            ps.setString(2, acompanhanteDTO.getTelefone());
            ps.setString(3, acompanhanteDTO.getEmail());
            ps.setInt(4, acompanhanteDTO.getIdAcompanhante());

            if (ps.executeUpdate() > 0) {
                return "Inserido com sucesso";
            } else {
                return "Erro ao alterar";
            }


        } catch (SQLException e) {
            return "Erro de SQL: " + e.getMessage();
        }
    }

    public String excluir(AcompanhanteDTO acompanhanteDTO) {

        String sql = "DELETE FROM T_ELO_ACOMPANHANTE where id_acompanhante=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)){
            ps.setInt(1, acompanhanteDTO.getIdAcompanhante());
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

    public String listarUm(AcompanhanteDTO acompanhanteDTO) {
        String sql = "SELECT * FROM T_ELO_ACOMPANHANTE WHERE id_acompanhante=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, acompanhanteDTO.getIdAcompanhante());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String mensagem = String.format("Id: %s \nNome Completo: %s \nData de Nascimento: %s \nCPF: %s \nTelefone: %s \nE-mail: %s \nParentesco: %s", rs.getInt("id_acompanhante"), rs.getString("nc_nome_completo"), rs.getDate("dt_data_nascimento"), rs.getString("dc_cpf"), rs.getString("tl_telefone"), rs.getString("em_email"), rs.getString("pr_parentesco"));
                return mensagem;
            } else {
                return "Registro não encontrado";
            }
        } catch (SQLException e) {
            return "Erro no comando SQL " + e.getMessage();
        }
    }
}
