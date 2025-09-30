package br.com.fiap.model.dao;

import br.com.fiap.model.dto.AtendimentoDTO;
import br.com.fiap.model.dto.LembreteDTO;
import br.com.fiap.model.dto.PacienteDTO;
import br.com.fiap.model.enums.StatusLembrete;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LembreteDAO{
    private Connection con;

    public LembreteDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String inserir(LembreteDTO lembreteDTO) {

        String sql = "insert into T_ELO_LEMBRETE (as_assunto, ms_mensagem, dt_data_envio, st_status, id_colaborador, id_atendimeto) values (?,?,?,?,?,?)";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, lembreteDTO.getAssunto());
            ps.setString(2, lembreteDTO.getMensagem());
            ps.setDate(3, Date.valueOf(lembreteDTO.getDataEnvio()));
            ps.setString(4, lembreteDTO.getStatus().name());
            ps.setInt(5, lembreteDTO.getAtendimento().getIdAtendimento());
            if (ps.executeUpdate() > 0) {
                return "Inserido com sucesso";
            } else {
                return "Erro ao inserir";
            }


        } catch (SQLException e) {
            return "Erro de SQL: " + e.getMessage();
        }

    }

    
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
                return "Erro ao inserir";
            }


        } catch (SQLException e) {
            return "Erro de SQL: " + e.getMessage();
        }
    }

    
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

    public ArrayList<LembreteDTO> listarTodosPorAtendimento(AtendimentoDTO atendimento) {

        String sql = "SELECT * FROM T_ELO_LEMBRETE WHERE id_atendimento= ?";

        ArrayList<LembreteDTO> lembretes = new ArrayList<LembreteDTO>();

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, atendimento.getIdAtendimento());
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

    public ArrayList<LembreteDTO> listarPendentesPorAtendimento(AtendimentoDTO atendimento) {

        String sql = "SELECT * FROM T_ELO_LEMBRETE WHERE id_atendimento= ? AND st_status='PENDENTE'";

        ArrayList<LembreteDTO> lembretes = new ArrayList<>();

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, atendimento.getIdAtendimento());
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

    public ArrayList<LembreteDTO> listarPorPaciente(PacienteDTO paciente) {

        String sql = "SELECT l.* FROM T_ELO_LEMBRETE AS l INNER JOIN T_ELO_ATENDIMENTO AS a ON a.id_atendimento = l.id_atendimento INNER JOIN T_ELO_PACIENTE AS p ON p.id_paciente = a.id_paciente WHERE p.id_paciente=?";

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
}
