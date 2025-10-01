package br.com.fiap.model.dao;

import br.com.fiap.model.dto.*;
import br.com.fiap.model.enums.StatusAtendimento;

import java.sql.*;
import java.util.ArrayList;

/**
 * Classe responsável por realizar operações de acesso a dados dos atendimentos no banco de dados
 * @version 1.0
 */
public class AtendimentoDAO{
        private Connection con;

        public AtendimentoDAO(Connection con) {
            this.con = con;
        }

        public Connection getCon() {
            return con;
        }

        /**
         * Insere um novo atendimento no banco de dados
         * @param atendimentoDTO objeto contendo os dados do atendimento a ser inserido
         * @return mensagem informando o resultado da operação
         */
        public String inserir(AtendimentoDTO atendimentoDTO) {

            String sql = "insert into T_ELO_ATENDIMENTO (id_paciente, id_profissional_saude, fm_formato_atendimento, dt_data, hr_hora, lc_local, st_status) values (?,?,?,?,?,?,?)";

            try (PreparedStatement ps = getCon().prepareStatement(sql)) {
                ps.setInt(1, atendimentoDTO.getPaciente().getIdPaciente());
                ps.setInt(2, atendimentoDTO.getProfissionalSaude().getIdProfissionalSaude());
                ps.setString(3, atendimentoDTO.getFormatoAtendimento());
                ps.setDate(4, Date.valueOf(atendimentoDTO.getData()));
                ps.setTime(5, Time.valueOf(atendimentoDTO.getHora()));
                ps.setString(6, atendimentoDTO.getLocal());
                ps.setString(7, atendimentoDTO.getStatus().name());

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
         * Altera os dados de um atendimento no banco de dados
         * @param atendimentoDTO objeto contendo os novos dados do atendimento
         * @return mensagem informando o resultado da operação
         */
        public String alterar(AtendimentoDTO atendimentoDTO) {

            String sql = "UPDATE T_ELO_ATENDIMENTO set id_profissional_saude=?, fm_formato_atendimento=?, dt_data=?, hr_hora=?,lc_local=?, st_status=? where id_atendimento=?";

            try (PreparedStatement ps = getCon().prepareStatement(sql)) {
                ps.setInt(1, atendimentoDTO.getProfissionalSaude().getIdProfissionalSaude());
                ps.setString(2, atendimentoDTO.getFormatoAtendimento());
                ps.setDate(3, Date.valueOf(atendimentoDTO.getData()));
                ps.setTime(4, Time.valueOf(atendimentoDTO.getHora()));
                ps.setString(5, atendimentoDTO.getLocal());
                ps.setString(6, atendimentoDTO.getStatus().name());
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

        /**
         * Exclui um atendimento do banco de dados
         * @param atendimentoDTO objeto contendo o ID do atendimento a ser excluído
         * @return mensagem informando o resultado da operação
         */
        public String excluir(AtendimentoDTO atendimentoDTO) {

            String sql = "DELETE FROM T_ELO_ATENDIMENTO where id_atendimento=?";

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

    /**
     * Busca um atendimento no banco de dados
     * @param atendimentoDTO objeto contendo o ID do atendimento a ser buscado
     * @return objeto AtendimentoDTO com os dados do atendimento ou null se não encontrar
     */
    public AtendimentoDTO listarUm(AtendimentoDTO atendimentoDTO) {
        String sql = "SELECT a.id_atendimento, a.fm_formato_atendimento, a.dt_data, a.hr_hora, a.lc_local, a.st_status, p.id_paciente, p.nc_nome_completo AS nome_paciente,ps.id_profissional_saude, ps.nc_nome_completo AS nome_profissional, ps.es_especialidade FROM T_ELO_ATENDIMENTO a INNER JOIN T_ELO_PACIENTE p ON a.id_paciente = p.id_paciente INNER JOIN T_ELO_PROFISSIONAL_SAUDE ps ON a.id_profissional_saude = ps.id_profissional_saude WHERE a.id_atendimento=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, atendimentoDTO.getIdAtendimento());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                PacienteDTO paciente = new PacienteDTO();
                paciente.setIdPaciente(rs.getInt("id_paciente"));
                paciente.setNomeCompleto(rs.getString("nome_paciente"));

                ProfissionalSaudeDTO profissional = new ProfissionalSaudeDTO();
                profissional.setIdProfissionalSaude(rs.getInt("id_profissional_saude"));
                profissional.setNomeCompleto(rs.getString("nome_profissional"));
                profissional.setEspecialidade(rs.getString("es_especialidade"));

                AtendimentoDTO atendimento = new AtendimentoDTO();
                atendimento.setIdAtendimento(rs.getInt("id_atendimento"));
                atendimento.setFormatoAtendimento(rs.getString("fm_formato_atendimento"));
                atendimento.setData(rs.getDate("dt_data").toLocalDate());
                atendimento.setHora(rs.getTime("hr_hora").toLocalTime());
                atendimento.setLocal(rs.getString("lc_local"));
                atendimento.setStatus(StatusAtendimento.valueOf(rs.getString("st_status")));
                atendimento.setPaciente(paciente);
                atendimento.setProfissionalSaude(profissional);
                return atendimento;
            }
        } catch (SQLException e) {
            System.out.println("Erro no comando SQL " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca todos os atendimentos de um paciente
     * @param paciente objeto contendo o ID do paciente
     * @return lista de objetos com os dados dos atendimentos encontrados
     */
    public ArrayList<AtendimentoDTO> listarTodosPorPaciente(PacienteDTO paciente) {
        String sql = "SELECT a.id_atendimento, a.fm_formato_atendimento, a.dt_data, a.hr_hora, a.lc_local, a.st_status,ps.id_profissional_saude, ps.nc_nome_completo AS nome_profissional, ps.es_especialidade FROM T_ELO_ATENDIMENTO a INNER JOIN T_ELO_PROFISSIONAL_SAUDE ps ON a.id_profissional_saude = ps.id_profissional_saude WHERE a.id_paciente=?";

        ArrayList<AtendimentoDTO> atendimentos = new ArrayList<>();

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, paciente.getIdPaciente());
            ResultSet rs = ps.executeQuery();
            if(rs != null){
                while (rs.next()) {
                    ProfissionalSaudeDTO profissional = new ProfissionalSaudeDTO();
                    profissional.setIdProfissionalSaude(rs.getInt("id_profissional_saude"));
                    profissional.setNomeCompleto(rs.getString("nome_profissional"));
                    profissional.setEspecialidade(rs.getString("es_especialidade"));

                    AtendimentoDTO atendimento = new AtendimentoDTO();
                    atendimento.setIdAtendimento(rs.getInt("id_atendimento"));
                    atendimento.setFormatoAtendimento(rs.getString("fm_formato_atendimento"));
                    atendimento.setData(rs.getDate("dt_data").toLocalDate());
                    atendimento.setHora(rs.getTime("hr_hora").toLocalTime());
                    atendimento.setLocal(rs.getString("lc_local"));
                    atendimento.setStatus(StatusAtendimento.valueOf(rs.getString("st_status")));
                    atendimento.setProfissionalSaude(profissional);

                    atendimentos.add(atendimento);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no comando SQL " + e.getMessage());
        }
        return atendimentos;
        }
    }
