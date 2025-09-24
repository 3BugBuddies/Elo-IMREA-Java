package br.com.fiap.model.dao;

import br.com.fiap.model.dao.interfaces.IDAO;
import br.com.fiap.model.dto.ProfissionalSaudeDTO;

import java.sql.*;
import java.util.Map;

public class ProfissionalSaudeDAO implements IDAO {
    private Connection con;
    private ProfissionalSaudeDTO profissionalSaudeDTO;

    public ProfissionalSaudeDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    @Override
    public String inserir(Object object) {
        profissionalSaudeDTO = (ProfissionalSaudeDTO) object;

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

    @Override
    public String alterar(Object object) {
        profissionalSaudeDTO = (ProfissionalSaudeDTO) object;

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

    @Override
    public String excluir(Object object) {
        profissionalSaudeDTO = (ProfissionalSaudeDTO) object;

        String sql = "DELETE FROM T_PROFISSIONAL_SAUDE where id_profissional_saude=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)){
            ps.setInt(1, profissionalSaudeDTO.getIdProfissionalSaude());
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

    @Override
    public String listarUm(Object object) {
        profissionalSaudeDTO = (ProfissionalSaudeDTO) object;
        String sql = "SELECT * FROM T_PROFISSIONAL_SAUDE where id_profissional_saude=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, profissionalSaudeDTO.getIdProfissionalSaude());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String mensagem = String.format("Id: %s \nNome Completo: %s \nData de Nascimento: %s \nCPF: %s \nTelefone: %s \nE-mail: %s \nDocumento: %s-%s \nEspecialidade: %s",rs.getInt("id_profissional_saude"), rs.getString("nome_completo"), rs.getDate("data_nasc"), rs.getString("cpf"), rs.getString("telefone"), rs.getString("email"), rs.getString("tipo_documento"), rs.getString("documento"), rs.getString("especialidade"));
                return mensagem;
            } else {
                return "Registro não encontrado";
            }
        } catch (SQLException e) {
            return "Erro no comando SQL " + e.getMessage();
        }
    }
}
