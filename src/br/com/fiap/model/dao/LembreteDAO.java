package br.com.fiap.model.dao;

import br.com.fiap.model.dto.AtendimentoDTO;
import br.com.fiap.model.dto.LembreteDTO;
import br.com.fiap.model.dto.PacienteDTO;
import br.com.fiap.model.enums.StatusLembrete;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por realizar operações de acesso a dados dos lembretes no banco de dados
 * @version 1.0
 */
public class LembreteDAO{
    private Connection con;

    public LembreteDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    /**
     * Insere um novo lembrete no banco de dados
     * @param lembreteDTO objeto contendo os dados do lembrete a ser inserido
     * @return mensagem informando o resultado da operação
     */
    public String inserir(LembreteDTO lembreteDTO) {

        String sql = "insert into T_ELO_LEMBRETE (as_assunto, ms_mensagem, dt_data_envio, st_status, id_colaborador, id_atendimento) values (?,?,?,?,?,?)";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, lembreteDTO.getAssunto());
            ps.setString(2, lembreteDTO.getMensagem());
            ps.setDate(3, Date.valueOf(lembreteDTO.getDataEnvio()));
            ps.setString(4, lembreteDTO.getStatus().name());
            ps.setInt(5, lembreteDTO.getIdColaborador());
            ps.setInt(6, lembreteDTO.getAtendimento().getIdAtendimento());
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
     * Altera os dados de um lembrete existente no banco de dados
     * @param lembreteDTO objeto contendo os novos dados do lembrete
     * @return mensagem informando o resultado da operação
     */
    public String alterar(LembreteDTO lembreteDTO) {

        String sql = "UPDATE T_ELO_LEMBRETE set as_assunto=?, ms_mensagem=?, dt_data_envio=?, st_status=?, id_colaborador=? where id_lembrete=?";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, lembreteDTO.getAssunto());
            ps.setString(2, lembreteDTO.getMensagem());
            ps.setDate(3, Date.valueOf(lembreteDTO.getDataEnvio()));
            ps.setString(4, lembreteDTO.getStatus().name());
            ps.setInt(5, lembreteDTO.getIdColaborador());
            ps.setInt(6, lembreteDTO.getIdLembrete());

            if (ps.executeUpdate() > 0) {
                return "Alterado com sucesso";
            } else {
                return "Erro ao alterar";
            }


        } catch (SQLException e) {
            return "Erro de SQL: " + e.getMessage();
        }
    }


    /**
     * Exclui um lembrete do banco de dados
     * @param lembrete objeto contendo o ID do lembrete a ser excluído
     * @return mensagem informando o resultado da operação
     */
    public String excluir(LembreteDTO lembrete) {

        String sql = "DELETE FROM T_ELO_LEMBRETE where id_lembrete=?";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, lembrete.getIdLembrete());
            if (ps.executeUpdate() > 0) {
                return "Excluido com Sucesso";
            } else {
                return "Erro ao excluir";
            }
        } catch (SQLException e) {
            return "Erro no comando SQL " + e.getMessage();
        }
    }


    /**
     * Busca um lembrete específico no banco de dados
     * @param lembrete objeto contendo o ID do lembrete a ser buscado
     * @return objeto LembreteDTO com os dados do lembrete encontrado ou null se não encontrar
     */
    public LembreteDTO listarUm(LembreteDTO lembrete) {

        String sql = "SELECT * FROM T_ELO_LEMBRETE WHERE id_lembrete= ?";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, lembrete.getIdLembrete());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LembreteDTO lembreteDTO = new LembreteDTO();
                lembreteDTO.setIdLembrete(rs.getInt("id_lembrete"));
                lembreteDTO.setIdColaborador(rs.getInt("id_colaborador"));
                lembreteDTO.setAssunto(rs.getString("as_assunto"));
                lembreteDTO.setMensagem(rs.getString("ms_mensagem"));
                lembreteDTO.setStatus(StatusLembrete.valueOf(rs.getString("st_status")));
                lembreteDTO.setDataEnvio(rs.getDate("dt_data_envio").toLocalDate());
                return lembreteDTO;
            }
        } catch (SQLException e) {
            System.out.println("Erro no comando SQL " + e.getMessage());
        }
        return null;
    }


    /**
     * Busca todos os lembretes de um paciente específico
     * @param paciente objeto contendo o ID do paciente
     * @return lista de objetos LembreteDTO com os dados dos lembretes encontrados
     */
    public ArrayList<LembreteDTO> listarTodosPorPaciente(PacienteDTO paciente) {

        String sql = "SELECT l.* FROM T_ELO_LEMBRETE l INNER JOIN T_ELO_ATENDIMENTO a ON a.id_atendimento = l.id_atendimento INNER JOIN T_ELO_PACIENTE p ON p.id_paciente = a.id_paciente WHERE p.id_paciente=? order by dt_data_envio desc";

        ArrayList<LembreteDTO> lembretes = new ArrayList<LembreteDTO>();

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, paciente.getIdPaciente());
            ResultSet rs = ps.executeQuery();
            if(rs != null) {
                while(rs.next()) {
                    LembreteDTO lembreteDTO = new LembreteDTO();
                    lembreteDTO.setIdLembrete(rs.getInt("id_lembrete"));
                    lembreteDTO.setIdColaborador(rs.getInt("id_colaborador"));
                    lembreteDTO.setAssunto(rs.getString("as_assunto"));
                    lembreteDTO.setMensagem(rs.getString("ms_mensagem"));
                    lembreteDTO.setStatus(StatusLembrete.valueOf(rs.getString("st_status")));
                    lembreteDTO.setDataEnvio(rs.getDate("dt_data_envio").toLocalDate());
                    lembretes.add(lembreteDTO);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no comando SQL " + e.getMessage());
        }
        return lembretes;
    }

    /**
     * Busca o último lembrete enviado para um paciente
     * @param paciente objeto contendo o ID do paciente
     * @return objeto LembreteDTO com os dados do último lembrete ou null
     */
    public LembreteDTO listarUltimoPorPaciente(PacienteDTO paciente) {

        String sql = "SELECT l.* FROM T_ELO_LEMBRETE l INNER JOIN T_ELO_ATENDIMENTO a ON a.id_atendimento = l.id_atendimento WHERE a.id_paciente=? " +
                "ORDER BY l.dt_data_envio DESC ";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, paciente.getIdPaciente());
            ResultSet rs = ps.executeQuery();
            if(rs != null) {
                while(rs.next()) {
                    LembreteDTO lembreteDTO = new LembreteDTO();
                    lembreteDTO.setIdLembrete(rs.getInt("id_lembrete"));
                    lembreteDTO.setIdColaborador(rs.getInt("id_colaborador"));
                    lembreteDTO.setAssunto(rs.getString("as_assunto"));
                    lembreteDTO.setMensagem(rs.getString("ms_mensagem"));
                    lembreteDTO.setStatus(StatusLembrete.valueOf(rs.getString("st_status")));
                    lembreteDTO.setDataEnvio(rs.getDate("dt_data_envio").toLocalDate());
                    return lembreteDTO;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no comando SQL " + e.getMessage());
        }
        return null;
    }
}
