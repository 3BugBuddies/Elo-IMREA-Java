package br.com.fiap.model.dao;

import br.com.fiap.model.dto.ProfissionalSaudeDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

        String sql = "insert into T_PROFISSIONALSAUDE (nomeCompleto, dataNascimento, cpf, telefone, email, tipoDocumento, documento, especialidade) values (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, profissionalSaudeDTO.getNomeCompleto());
            ps.setDate(2, Date.valueOf(profissionalSaudeDTO.getDataNascimento()));
            ps.setString(3, profissionalSaudeDTO.getCpf());
            ps.setString(4, profissionalSaudeDTO.getTelefone());
            ps.setString(5, profissionalSaudeDTO.getEmail());
            for(Map.Entry<String, String> valor : profissionalSaudeDTO.getDocumento().entrySet()){
                ps.setString(6, valor.getKey());
                ps.setString(7, valor.getValue());
            }
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
            for(Map.Entry<String, String> valor : profissionalSaudeDTO.getDocumento().entrySet()){
                ps.setString(4, valor.getKey());
                ps.setString(5, valor.getValue());
            }
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

        String sql = "DELETE FROM T_PROFISSIONAL_SAUDE where idProfissionalSaude=?";

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
        return "";
    }
}
