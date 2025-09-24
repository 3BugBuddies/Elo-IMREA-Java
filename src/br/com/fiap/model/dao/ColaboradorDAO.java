package br.com.fiap.model.dao;

import br.com.fiap.model.dto.ColaboradorDTO;
import br.com.fiap.model.dto.AtendimentoDTO;
import br.com.fiap.model.dto.LembreteDTO;

import java.sql.*;
import java.time.LocalDate;

public class ColaboradorDAO {
    private Connection con;

    public ColaboradorDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String inserir(ColaboradorDTO colaboradorDTO) {

        String sql = "insert into T_ELO_COLABORADOR (nc_nome_completo, dt_data_nascimento, dc_cpf, tl_telefone, em_email, un_unidade) values (?,?,?,?,?,?)";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, colaboradorDTO.getNomeCompleto());
            ps.setDate(2, Date.valueOf(colaboradorDTO.getDataNascimento()));
            ps.setString(3, colaboradorDTO.getCpf());
            ps.setString(4, colaboradorDTO.getTelefone());
            ps.setString(5, colaboradorDTO.getEmail());
            ps.setString(6, colaboradorDTO.getUnidade());

            if (ps.executeUpdate() > 0) {
                return "Inserido com sucesso";
            } else {
                return "Erro ao inserir";
            }


        } catch (SQLException e) {
            return "Erro de SQL: " + e.getMessage();
        }

    }

    public String alterar(ColaboradorDTO colaboradorDTO) {

        String sql = "UPDATE T_ELO_COLABORADOR set nc_nome_completo=?, tl_telefone=?, em_email=?, un_unidade=? where id_colaborador=?";

        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, colaboradorDTO.getNomeCompleto());
            ps.setString(2, colaboradorDTO.getTelefone());
            ps.setString(3, colaboradorDTO.getEmail());
            ps.setString(4, colaboradorDTO.getUnidade());
            ps.setInt(5, colaboradorDTO.getIdColaborador());
            if (ps.executeUpdate() > 0) {
                return "Alterado com sucesso";
            } else {
                return "Erro ao alterar";
            }


        } catch (SQLException e) {
            return "Erro de SQL: " + e.getMessage();
        }
    }

    public String excluir(int idColaborador) {

        String sql = "DELETE FROM T_ELO_COLABORADOR where id_colaborador=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)){
            ps.setInt(1, idColaborador);
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

    public ColaboradorDTO listarUm(int idColaborador) {
        String sql = "SELECT * FROM T_ELO_COLABORADOR where id_colaborador=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, idColaborador);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ColaboradorDTO colaborador = new ColaboradorDTO();
                colaborador.setNomeCompleto(rs.getString("nc_nome_completo"));
                colaborador.setTelefone(rs.getString("tl_telefone"));
                colaborador.setDataNascimento(rs.getDate("dt_data_nascimento").toLocalDate());
                colaborador.setEmail(rs.getString("em_email"));
                colaborador.setUnidade(rs.getString("un_unidade"));

            } else {
                System.out.println("Registro não encontrado");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro no comando SQL " + e.getMessage());
        }
        return null;
    }

    public LembreteDTO enviarLembrete(AtendimentoDTO atendimento) {
        LembreteDTO lembrete = new LembreteDTO();

        String sql = "SELECT p.nc_nome_completo, ps.nc_nome_completo, a.fm_formato_atendimento, a.hr_hora, a.dt_data, a.lc_local from t_elo_atendimento a inner join t_elo_paciente p on a.id_paciente = p.id_paciente inner join t_elo_profissional_saude ps on a.id_profissional_saude = ps.id_profissional_saude where a.st_status = 'AGENDADO' and a.id_atendimento =?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, atendimento.getIdAtendimento());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rs.getString("ps.nc_nome_completo");

                String assunto = String.format("Consulta com %s no dia: %s", rs.getString("ps.nc_nome_completo"), rs.getDate("a.dt_data").toLocalDate());

                String mensagem = String.format("Olá, Sr(a) %s \nEste é um lembrete da sua consulta com o profissional %s no dia %s as %s \nFormato: %s \nLocal/Link: %s \nTe aguardamos ansiosamente e qualquer coisa só chamar.", rs.getString("p.nc_nome_completo"), rs.getString("ps.nc_nome_completo"),rs.getDate("a.dt_data").toLocalDate(), rs.getTime("a.hr_hora").toLocalTime(), rs.getString("a.fm_formato_atendimento"), rs.getString("a.lc_local"));

                lembrete.setMensagem(mensagem);
                lembrete.setAssunto(assunto);
                lembrete.setStatus("ENVIADO");
                lembrete.setDataEnvio(LocalDate.now());
                lembrete.setAtendimento(atendimento);
                return lembrete;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }
    
}
