package br.com.fiap.model.dao;

import br.com.fiap.model.dao.interfaces.IDAO;
import br.com.fiap.model.dto.PacienteDTO;

import java.sql.*;

public class PacienteDAO implements IDAO {

    private Connection con;
    private PacienteDTO pacienteDTO;

    public PacienteDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    @Override
    public String inserir(Object object) {
        pacienteDTO = (PacienteDTO) object;

        String sql = "insert into T_ELO_PACIENTE (nc_nome_completo, dt_data_nascimento, dc_cpf, tl_telefone, em_email, dg_diagnostico) values (?,?,?,?,?,?)";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, pacienteDTO.getNomeCompleto());
            ps.setDate(2, Date.valueOf(pacienteDTO.getDataNascimento()));
            ps.setString(3, pacienteDTO.getCpf());
            ps.setString(4, pacienteDTO.getTelefone());
            ps.setString(5, pacienteDTO.getEmail());
            ps.setString(6, pacienteDTO.getDiagnostico());
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
        pacienteDTO = (PacienteDTO) object;

        String sql = "UPDATE T_PACIENTE set nomeCompleto=?, telefone=?, email=? where id_acompanhante=?";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, pacienteDTO.getNomeCompleto());
            ps.setDate(2, Date.valueOf(pacienteDTO.getDataNascimento()));
            ps.setString(3, pacienteDTO.getCpf());
            ps.setString(4, pacienteDTO.getTelefone());
            ps.setString(5, pacienteDTO.getEmail());

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
    public String excluir(Object object) {
        pacienteDTO = (PacienteDTO) object;

        String sql = "DELETE FROM T_PACIENTE where id_paciente=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)){
            ps.setInt(1, pacienteDTO.getIdPaciente());
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
        pacienteDTO = (PacienteDTO) object;
        String sql = "SELECT * FROM T_PACIENTE where id_paciente = ?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, pacienteDTO.getIdPaciente());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return String.format("Nome: %s \nCor: %s \nDescricao: %s", rs.getString("placa"), rs.getString("cor"), rs.getString("descricao"));
            } else {
                return "Registro não encontrado";
            }
        } catch (SQLException e) {
            return "Erro no comando SQL " + e.getMessage();
        }
    }
}
