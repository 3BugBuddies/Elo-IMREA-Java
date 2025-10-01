package br.com.fiap.controller;

import br.com.fiap.exceptions.*;
import br.com.fiap.model.dao.AtendimentoDAO;
import br.com.fiap.model.dao.ColaboradorDAO;
import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dao.LembreteDAO;
import br.com.fiap.model.dto.*;
import br.com.fiap.model.enums.StatusAtendimento;
import br.com.fiap.model.enums.StatusLembrete;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Classe responsável por controlar as operações relacionadas aos colaboradores e fazer contato com a classe que acessa ao banco
 * @version 1.0
 */
public class ColaboradorController {
    /**
     * Realiza a inserção de um novo colaborador no sistema
     * @param nomeCompleto nome completo do colaborador
     * @param cpf CPF do colaborador
     * @param dataNascimento data de nascimento do colaborador
     * @param email email do colaborador
     * @param telefone telefone do colaborador
     * @param unidade unidade de trabalho do colaborador
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String inserirColaborador(String nomeCompleto, String cpf, LocalDate dataNascimento, String email, String telefone, String unidade) throws ClassNotFoundException, SQLException {
        String resultado;


        ColaboradorDTO colaborador = new ColaboradorDTO();
        colaborador.setNomeCompleto(nomeCompleto);
        colaborador.setCpf(cpf.replace(".", "").replace("-", ""));
        colaborador.setDataNascimento(dataNascimento);
        colaborador.setTelefone(telefone.replace("(", "").replace("-", "").replace(")", "").replace(" ", ""));
        colaborador.setEmail(email);
        colaborador.setUnidade(unidade);
        Connection con = ConnectionFactory.abrirConexao();
        ColaboradorDAO colaboradorDAO = new ColaboradorDAO(con);
        resultado = colaboradorDAO.inserir(colaborador);
        ConnectionFactory.fecharConexao(con);
        return resultado;

    }

    /**
     * Atualiza os dados de um colaborador existente no sistema
     * @param nomeCompleto novo nome completo do colaborador
     * @param email novo email do colaborador
     * @param telefone novo telefone do colaborador
     * @param unidade nova unidade de trabalho do colaborador
     * @param idColaborador ID do colaborador a ser atualizado
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String atualizarColaborador(String nomeCompleto, String email, String telefone, String unidade, int idColaborador) throws ClassNotFoundException, SQLException {
        String resultado;
        Connection con = ConnectionFactory.abrirConexao();
        ColaboradorDTO colaborador = new ColaboradorDTO();
        colaborador.setNomeCompleto(nomeCompleto);
        colaborador.setTelefone(telefone.replace("(", "").replace("-", "").replace(")", "").replace(" ", ""));
        colaborador.setEmail(email);
        colaborador.setUnidade(unidade);
        colaborador.setIdColaborador(idColaborador);
        ColaboradorDAO colaboradorDAO = new ColaboradorDAO(con);
        resultado = colaboradorDAO.alterar(colaborador);
        ConnectionFactory.fecharConexao(con);
        return resultado;

    }

    /**
     * Exclui um colaborador do sistema
     * @param idColaborador ID do colaborador a ser excluído
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String excluirColaborador(int idColaborador) throws ClassNotFoundException, SQLException {
        String resultado;

        ColaboradorDTO colaborador = new ColaboradorDTO();
        colaborador.setIdColaborador(idColaborador);

        Connection con = ConnectionFactory.abrirConexao();
        ColaboradorDAO colaboradorDAO = new ColaboradorDAO(con);
        resultado = colaboradorDAO.excluir(colaborador);
        ConnectionFactory.fecharConexao(con);
        return resultado;

    }

    /**
     * Busca e retorna os dados de um colaborador
     * @param idColaborador ID do colaborador a ser buscado
     * @return string formatada com os dados do colaborador
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String listarUmColaborador(int idColaborador) throws ClassNotFoundException, SQLException {
        String resultado = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        ColaboradorDTO colaborador = new ColaboradorDTO();
        colaborador.setIdColaborador(idColaborador);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            ColaboradorDAO colaboradorDAO = new ColaboradorDAO(con);
            ColaboradorDTO colaboradorEncontrado = colaboradorDAO.listarUm(colaborador);

            if (colaboradorEncontrado == null) {
                throw new ColaboradorException("Não existe colaborador com esse id");
            }
            resultado = String.format("Id: %s \nNome Completo: %s \nData de Nascimento: %s \nCPF: %s \nTelefone: %s \nE-mail: %s\nUnidade: %s", colaboradorEncontrado.getIdColaborador(), colaboradorEncontrado.getNomeCompleto(), colaboradorEncontrado.getDataNascimento().format(dtf), colaboradorEncontrado.getCpf(), colaboradorEncontrado.getTelefone(), colaboradorEncontrado.getEmail(), colaboradorEncontrado.getUnidade());
        } catch (ColaboradorException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

}
