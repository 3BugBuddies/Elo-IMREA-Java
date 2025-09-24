package br.com.fiap.model.dao;

import br.com.fiap.model.dao.interfaces.IDAO;
import br.com.fiap.model.dto.LembreteDTO;

import java.sql.*;

public class LembreteDAO implements IDAO {
    private Connection con;
    private LembreteDTO lembreteDTO;

    public LembreteDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    @Override
    public String inserir(Object object) {
        lembreteDTO = (LembreteDTO) object;

        String sql = "insert into T_ELO_LEMBRETE (as_assunto, ms_mensagem, dt_data_envio, st_status, id_colaborador, id_atendimeto) values (?,?,?,?,?,?)";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, lembreteDTO.getAssunto());
            ps.setString(2, lembreteDTO.getMensagem());
            ps.setDate(3, Date.valueOf(lembreteDTO.getDataEnvio()));
            ps.setString(4, lembreteDTO.getStatus());
            ps.setInt(5, lembreteDTO.getIdColaborador());
            ps.setInt(6, lembreteDTO.getIdAtendimento());

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
        lembreteDTO = (LembreteDTO) object;

        String sql = "UPDATE T_ELO_LEMBRETE set as_assunto=?, ms_mensagem=?, dt_data_envio=?, st_status=?, id_colaborador=? where id_lembrete=?";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, lembreteDTO.getAssunto());
            ps.setString(2, lembreteDTO.getMensagem());
            ps.setDate(3, Date.valueOf(lembreteDTO.getDataEnvio()));
            ps.setString(4, lembreteDTO.getStatus());
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

    @Override
    public String excluir(Object object) {
        lembreteDTO = (LembreteDTO) object;

        String sql = "DELETE FROM T_ELO_LEMBRETE where id_lembrete=?";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, lembreteDTO.getIdLembrete());
            if (ps.executeUpdate() > 0) {
                return "Excluido com Sucesso";
            } else {
                return "Erro ao excluir";
            }
        } catch (SQLException e) {
            return "Erro no comando SQL " + e.getMessage();
        }
    }

    @Override
    public String listarUm(Object object) {
        lembreteDTO = (LembreteDTO) object;
        String sql = "SELECT * FROM T_ELO_LEMBRETE WHERE id_lembrete= ?";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, lembreteDTO.getIdLembrete());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return String.format("ID: %s\nAssunto: %s \nMensagem: %s \nData de Envio: %s\nStatus: %s ",rs.getInt("id_lembrete"),rs.getString("as_assunto"), rs.getString("ms_mensagem"),rs.getDate("dt_data_envio"), rs.getString("st_status"));
            } else {
                return "Registro não encontrado";
            }
        } catch (SQLException e) {
            return "Erro no comando SQL " + e.getMessage();
        }
    }
}
