package br.com.fiap.model.dao;

import br.com.fiap.model.dto.ColaboradorDTO;
import br.com.fiap.model.dto.AtendimentoDTO;
import br.com.fiap.model.dto.LembreteDTO;

import java.sql.*;
import java.time.LocalDate;

/**
 * Classe responsável por realizar operações de acesso a dados dos colaboradores no banco de dados
 * @version 1.0
 */
public class ColaboradorDAO {
    private Connection con;

    public ColaboradorDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    /**
     * Insere um novo colaborador no banco de dados
     * @param colaboradorDTO objeto contendo os dados do colaborador a ser inserido
     * @return mensagem informando o resultado da operação
     */
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

    /**
     * Altera os dados de um colaborador no banco de dados
     * @param colaboradorDTO objeto contendo os novos dados do colaborador
     * @return mensagem informando o resultado da operação
     */
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

    /**
     * Exclui um colaborador do banco de dados
     * @param colaborador objeto contendo o ID do colaborador a ser excluído
     * @return mensagem informando o resultado da operação
     */
    public String excluir(ColaboradorDTO colaborador) {

        String sql = "DELETE FROM T_ELO_COLABORADOR where id_colaborador=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)){
            ps.setInt(1, colaborador.getIdColaborador());
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
     * Busca um colaborador no banco de dados
     * @param colaborador objeto contendo o ID do colaborador a ser buscado
     * @return objeto ColaboradorDTO com os dados do colaborador ou null se não encontrar
     */
    public ColaboradorDTO listarUm(ColaboradorDTO colaborador) {
        String sql = "SELECT * FROM T_ELO_COLABORADOR where id_colaborador=?";

        try(PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, colaborador.getIdColaborador());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ColaboradorDTO colaboradorEncontrado = new ColaboradorDTO();
                colaboradorEncontrado.setIdColaborador(rs.getInt("id_colaborador"));
                colaboradorEncontrado.setNomeCompleto(rs.getString("nc_nome_completo"));
                colaboradorEncontrado.setTelefone(rs.getString("tl_telefone"));
                colaboradorEncontrado.setCpf(rs.getString("dc_cpf"));
                colaboradorEncontrado.setDataNascimento(rs.getDate("dt_data_nascimento").toLocalDate());
                colaboradorEncontrado.setEmail(rs.getString("em_email"));
                colaboradorEncontrado.setUnidade(rs.getString("un_unidade"));
                return colaboradorEncontrado;
            }
        } catch (SQLException e) {
            System.out.println("Erro no comando SQL " + e.getMessage());
        }
        return null;
    }

}
