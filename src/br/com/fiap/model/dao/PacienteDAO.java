package br.com.fiap.model.dao;

import br.com.fiap.model.dto.PacienteDTO;

import java.sql.*;

public class PacienteDAO{
    private Connection con;

    public PacienteDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String inserir(PacienteDTO pacienteDTO) {

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

    
    public String alterar(PacienteDTO pacienteDTO) {

        String sql = "UPDATE T_ELO_PACIENTE set nomeCompleto=?, telefone=?, email=? where id_acompanhante=?";

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

    
    public String excluir(int idPaciente) {

        String sql = "DELETE FROM T_ELO_PACIENTE where id_paciente=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)){
            ps.setInt(1, idPaciente);
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

    
    public PacienteDTO listarUm(int idPaciente) {
        
        String sql = "SELECT * FROM T_ELO_PACIENTE where id_paciente = ?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PacienteDTO paciente = new PacienteDTO();
                paciente.setIdPaciente(rs.getInt("id_paciente"));
                paciente.setNomeCompleto(rs.getString("nc_nome_completo"));
                paciente.setDataNascimento(rs.getDate("dt_data_nascimento").toLocalDate());
                paciente.setTelefone(rs.getString("tl_telefone"));
                paciente.setEmail(rs.getString("em_email"));
                paciente.setDiagnostico(rs.getString("dg_diagnostico"));
                return paciente;
            } else {
                System.out.println("Registro não encontrado");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro no comando SQL " + e.getMessage());
        }
        return null;
    }

    public PacienteDTO listarUmPorAcompanhante(int idAcompanhante) {

        String sql = "SELECT p.* FROM T_ELO_PACIENTE AS p inner join T_ELO_ACOMPANHANTE AS a ON p.id_paciente = a.id_paciente where a.id_acompanhante= ? ";

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, idAcompanhante);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PacienteDTO paciente = new PacienteDTO();
                paciente.setIdPaciente(rs.getInt("id_paciente"));
                paciente.setNomeCompleto(rs.getString("nc_nome_completo"));
                paciente.setDataNascimento(rs.getDate("dt_data_nascimento").toLocalDate());
                paciente.setTelefone(rs.getString("tl_telefone"));
                paciente.setEmail(rs.getString("em_email"));
                paciente.setDiagnostico(rs.getString("dg_diagnostico"));
                return paciente;
            } else {
                System.out.println("Registro não encontrado");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro no comando SQL " + e.getMessage());
        }
        return null;
    }
}
