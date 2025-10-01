package br.com.fiap.model.dao;
import br.com.fiap.model.dto.AcompanhanteDTO;
import br.com.fiap.model.dto.AtendimentoDTO;
import br.com.fiap.model.dto.PacienteDTO;
import br.com.fiap.model.dto.ProfissionalSaudeDTO;

import java.sql.*;
import java.util.ArrayList;

/**
 * Classe responsável por realizar operações de acesso a dados dos acompanhantes no banco de dados
 * @version 1.0
 */
public class AcompanhanteDAO {
    private Connection con;

    public AcompanhanteDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    /**
     * Insere um novo acompanhante no banco de dados
     * @param acompanhanteDTO objeto contendo os dados do acompanhante a ser cadastrado
     * @return mensagem informando o resultado da operação
     */
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

    /**
     * Altera os dados de um acompanhante no banco de dados
     * @param acompanhanteDTO objeto contendo os novos dados do acompanhante
     * @return mensagem informando o resultado da operação
     */
    public String alterar(AcompanhanteDTO acompanhanteDTO) {

        String sql = "UPDATE T_ELO_ACOMPANHANTE set nc_nome_completo=?, tl_telefone=?, em_email=?, pr_parentesco=? where id_acompanhante=?";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, acompanhanteDTO.getNomeCompleto());
            ps.setString(2, acompanhanteDTO.getTelefone());
            ps.setString(3, acompanhanteDTO.getEmail());
            ps.setString(4, acompanhanteDTO.getParentesco());
            ps.setInt(5, acompanhanteDTO.getIdAcompanhante());

            if (ps.executeUpdate() > 0) {
                return "Inserido com sucesso";
            } else {
                return "Erro ao alterar";
            }


        } catch (SQLException e) {
            return "Erro de SQL: " + e.getMessage();
        }
    }

    /**
     * Exclui um acompanhante do banco de dados
     * @param acompanhanteDTO objeto contendo o ID do acompanhante a ser excluído
     * @return mensagem informando o resultado da operação
     */
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

    /**
     * Busca um acompanhante no banco de dados
     * @param acompanhanteDTO objeto contendo o ID do acompanhante a ser buscado
     * @return objeto de Acompanhante com os dados encontrados ou null
     */
    public AcompanhanteDTO listarUm(AcompanhanteDTO acompanhanteDTO) {
        String sql = "SELECT * FROM T_ELO_ACOMPANHANTE WHERE id_acompanhante=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, acompanhanteDTO.getIdAcompanhante());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                AcompanhanteDTO acompanhante = new AcompanhanteDTO();
                acompanhante.setIdPaciente(rs.getInt("id_paciente"));
                acompanhante.setIdAcompanhante(rs.getInt("id_acompanhante"));
                acompanhante.setNomeCompleto(rs.getString("nc_nome_completo"));
                acompanhante.setCpf(rs.getString("dc_cpf"));
                acompanhante.setTelefone(rs.getString("tl_telefone"));
                acompanhante.setDataNascimento(rs.getDate("dt_data_nascimento").toLocalDate());
                acompanhante.setEmail(rs.getString("em_email"));
                acompanhante.setParentesco(rs.getString("pr_parentesco"));
                return acompanhante;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar SQL: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca todos os acompanhantes de um paciente específico
     * @param paciente objeto contendo o ID do paciente
     * @return lista de objetos AcompanhanteDTO com os dados dos acompanhantes encontrados
     */
    public ArrayList<AcompanhanteDTO> listarTodosPorPaciente(PacienteDTO paciente) {
        String sql = "SELECT * FROM T_ELO_ACOMPANHANTE WHERE id_paciente=?";

        ArrayList<AcompanhanteDTO> acompanhantes = new ArrayList<>();

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, paciente.getIdPaciente());
            ResultSet rs = ps.executeQuery();
            if(rs != null){
                while (rs.next()) {
                    AcompanhanteDTO acompanhante = new AcompanhanteDTO();
                    acompanhante.setIdPaciente(rs.getInt("id_paciente"));
                    acompanhante.setIdAcompanhante(rs.getInt("id_acompanhante"));
                    acompanhante.setNomeCompleto(rs.getString("nc_nome_completo"));
                    acompanhante.setCpf(rs.getString("dc_cpf"));
                    acompanhante.setDataNascimento(rs.getDate("dt_data_nascimento").toLocalDate());
                    acompanhante.setTelefone(rs.getString("tl_telefone"));
                    acompanhante.setEmail(rs.getString("em_email"));
                    acompanhante.setParentesco(rs.getString("pr_parentesco"));
                    acompanhantes.add(acompanhante);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no comando SQL " + e.getMessage());
        }
        return acompanhantes;
    }
}
