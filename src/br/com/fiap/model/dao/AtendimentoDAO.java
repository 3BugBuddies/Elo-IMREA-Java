package br.com.fiap.model.dao;

import br.com.fiap.model.dao.interfaces.IDAO;
import br.com.fiap.model.dto.AtendimentoDTO;

import java.sql.*;

public class AtendimentoDAO{

        private Connection con;

        public AtendimentoDAO(Connection con) {
            this.con = con;
        }

        public Connection getCon() {
            return con;
        }

        public String inserir(AtendimentoDTO atendimentoDTO) {

            String sql = "insert into T_ELO_ATENDIMENTO (id_paciente, id_profissional_saude, fm_formato_atendimento, dt_data, hr_hora, lc_local, st_status) values (?,?,?,?,?,?,?)";

            try (PreparedStatement ps = getCon().prepareStatement(sql)) {
                ps.setInt(1, atendimentoDTO.getPaciente().getIdPaciente());
                ps.setInt(2, atendimentoDTO.getProfissionalSaude().getIdProfissionalSaude());
                ps.setString(3, atendimentoDTO.getTipoAtendimento());
                ps.setDate(4, Date.valueOf(atendimentoDTO.getData()));
                ps.setTime(5, Time.valueOf(atendimentoDTO.getHora()));
                ps.setString(6, atendimentoDTO.getLocal());
                ps.setString(7, atendimentoDTO.getStatus());

                if (ps.executeUpdate() > 0) {
                    return "Inserido com sucesso";
                } else {
                    return "Erro ao inserir";
                }


            } catch (SQLException e) {
                return "Erro de SQL: " + e.getMessage();
            }

        }

        public String alterar(AtendimentoDTO atendimentoDTO) {

            String sql = "UPDATE T_ATENDIMENTO set id_profissional_saude=?, tipo_atendimento=?, data=?, hora=?, local=?, status=? where id_atendimento=?";

            try (PreparedStatement ps = getCon().prepareStatement(sql)) {
                ps.setInt(1, atendimentoDTO.getProfissionalSaude().getIdProfissionalSaude());
                ps.setString(2, atendimentoDTO.getTipoAtendimento());
                ps.setDate(3, Date.valueOf(atendimentoDTO.getData()));
                ps.setTime(4, Time.valueOf(atendimentoDTO.getHora()));
                ps.setString(5, atendimentoDTO.getLocal());
                ps.setString(6, atendimentoDTO.getStatus());
                ps.setInt(7, atendimentoDTO.getIdAtendimento());

                if (ps.executeUpdate() > 0) {
                    return "Inserido com sucesso";
                } else {
                    return "Erro ao inserir";
                }


            } catch (SQLException e) {
                return "Erro de SQL: " + e.getMessage();
            }
        }

        public String excluir(AtendimentoDTO atendimentoDTO) {

            String sql = "DELETE FROM T_ATENDIMENTO where id_atendimento=?";

            try(PreparedStatement ps = getCon().prepareStatement(sql)){
                ps.setInt(1, atendimentoDTO.getIdAtendimento());
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

        public String listarUm(AtendimentoDTO atendimentoDTO) {
            String sql = "SELECT * FROM T_ATENDIMENTO where id_atendimento = ?";

            try(PreparedStatement ps = getCon().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();) {
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
