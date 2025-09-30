package br.com.fiap.model.dao;

import br.com.fiap.model.dto.AcompanhanteDTO;
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

        String sql = "UPDATE T_ELO_PACIENTE set nomeCompleto=?, telefone=?, email=?, diagnostico=? where id_acompanhante=?";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, pacienteDTO.getNomeCompleto());
            ps.setDate(2, Date.valueOf(pacienteDTO.getDataNascimento()));
            ps.setString(3, pacienteDTO.getTelefone());
            ps.setString(4, pacienteDTO.getEmail());
            ps.setString(5, pacienteDTO.getDiagnostico());
            ps.setInt(6, pacienteDTO.getIdPaciente());

            if (ps.executeUpdate() > 0) {
                return "Inserido com sucesso";
            } else {
                return "Erro ao inserir";
            }


        } catch (SQLException e) {
            return "Erro de SQL: " + e.getMessage();
        }
    }

    
    public String excluir(PacienteDTO paciente) {

        String sql = "DELETE FROM T_ELO_PACIENTE where id_paciente=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)){
            ps.setInt(1, paciente.getIdPaciente());
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

    
    public PacienteDTO listarUm(PacienteDTO paciente) {
        
        String sql = "SELECT * FROM T_ELO_PACIENTE where id_paciente=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, paciente.getIdPaciente());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PacienteDTO pacienteEncontrado = new PacienteDTO();
                pacienteEncontrado.setIdPaciente(rs.getInt("id_paciente"));
                pacienteEncontrado.setNomeCompleto(rs.getString("nc_nome_completo"));
                pacienteEncontrado.setDataNascimento(rs.getDate("dt_data_nascimento").toLocalDate());
                pacienteEncontrado.setTelefone(rs.getString("tl_telefone"));
                pacienteEncontrado.setEmail(rs.getString("em_email"));
                pacienteEncontrado.setDiagnostico(rs.getString("dg_diagnostico"));
                return pacienteEncontrado;
            }
        } catch (SQLException e) {
            System.out.println("Erro no comando SQL " + e.getMessage());
        }
        return null;
    }

    public PacienteDTO listarUmPorAcompanhante(AcompanhanteDTO acompanhante) {

        String sql = "SELECT p.* FROM T_ELO_PACIENTE p inner join T_ELO_ACOMPANHANTE a ON p.id_paciente = a.id_paciente where a.id_acompanhante=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, acompanhante.getIdAcompanhante());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PacienteDTO paciente = new PacienteDTO();
                paciente.setIdPaciente(rs.getInt("id_paciente"));
                paciente.setNomeCompleto(rs.getString("nc_nome_completo"));
                paciente.setCpf(rs.getString("dc_cpf"));
                paciente.setDataNascimento(rs.getDate("dt_data_nascimento").toLocalDate());
                paciente.setTelefone(rs.getString("tl_telefone"));
                paciente.setEmail(rs.getString("em_email"));
                paciente.setDiagnostico(rs.getString("dg_diagnostico"));
                return paciente;
            }
        } catch (SQLException e) {
            System.out.println("Erro no comando SQL " + e.getMessage());
        }
        return null;
    }
}
