package br.com.fiap.controller;

import br.com.fiap.exceptions.ProfissionalSaudeException;
import br.com.fiap.model.dao.ProfissionalSaudeDAO;
import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dao.ProfissionalSaudeDAO;
import br.com.fiap.model.dto.ProfissionalSaudeDTO;
import br.com.fiap.model.dto.ProfissionalSaudeDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe responsável por controlar as operações relacionadas aos profissionais de saúde e fazer contato com a classe que acessa ao banco
 * @version 1.0
 */
public class ProfissionalSaudeController {
    /**
     * Realiza a inserção de um novo profissional de saúde no sistema
     * @param nomeCompleto nome completo do profissional
     * @param cpf CPF do profissional
     * @param dataNascimento data de nascimento do profissional
     * @param email email do profissional
     * @param telefone telefone do profissional
     * @param tipoDocumento tipo do documento profissional (CRM, CREFITO, etc.)
     * @param documento número do documento profissional
     * @param especialidade especialidade do profissional
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String inserirProfissionalSaude(String nomeCompleto, String cpf, LocalDate dataNascimento, String email, String telefone, String tipoDocumento, String documento, String especialidade) throws ClassNotFoundException, SQLException {
        String resultado;


        ProfissionalSaudeDTO profissionalSaude = new ProfissionalSaudeDTO();
        profissionalSaude.setNomeCompleto(nomeCompleto);
        profissionalSaude.setCpf(cpf.replace(".", "").replace("-", ""));
        profissionalSaude.setDataNascimento(dataNascimento);
        profissionalSaude.setTelefone(telefone.replace("(", "").replace("-", "").replace(")", "").replace(" ", ""));
        profissionalSaude.setEmail(email);
        profissionalSaude.setTipoDocumento(tipoDocumento);
        profissionalSaude.setDocumento(documento);
        profissionalSaude.setEspecialidade(especialidade);

        Connection con = ConnectionFactory.abrirConexao();
        ProfissionalSaudeDAO profissionalSaudeDAO = new ProfissionalSaudeDAO(con);
        resultado = profissionalSaudeDAO.inserir(profissionalSaude);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

    /**
     * Atualiza os dados de um profissional de saúde no sistema
     * @param nomeCompleto novo nome completo do profissional
     * @param email novo email do profissional
     * @param telefone novo telefone do profissional
     * @param tipoDocumento novo tipo do documento profissional
     * @param documento novo número do documento profissional
     * @param especialidade nova especialidade do profissional
     * @param idProfissionalSaude ID do profissional a ser atualizado
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String atualizarProfissionalSaude(String nomeCompleto, String email, String telefone, String tipoDocumento, String documento, String especialidade,  int idProfissionalSaude) throws ClassNotFoundException, SQLException {
        String resultado;

        ProfissionalSaudeDTO profissionalSaude = new ProfissionalSaudeDTO();
        profissionalSaude.setNomeCompleto(nomeCompleto);
        profissionalSaude.setTelefone(telefone.replace("(", "").replace("-", "").replace(")", "").replace(" ", ""));
        profissionalSaude.setEmail(email);
        profissionalSaude.setTipoDocumento(tipoDocumento);
        profissionalSaude.setDocumento(documento);
        profissionalSaude.setEspecialidade(especialidade);
        profissionalSaude.setIdProfissionalSaude(idProfissionalSaude);

        Connection con = ConnectionFactory.abrirConexao();
        ProfissionalSaudeDAO profissionalSaudeDAO = new ProfissionalSaudeDAO(con);
        resultado = profissionalSaudeDAO.alterar(profissionalSaude);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

    /**
     * Exclui um profissional de saúde do sistema
     * @param idProfissionalSaude ID do profissional a ser excluído
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String deletarProfissionalSaude(int idProfissionalSaude) throws ClassNotFoundException, SQLException {
        String resultado = "";

        ProfissionalSaudeDTO profissionalSaude = new ProfissionalSaudeDTO();
        profissionalSaude.setIdProfissionalSaude(idProfissionalSaude);

        Connection con = ConnectionFactory.abrirConexao();
        ProfissionalSaudeDAO profissionalSaudeDAO = new ProfissionalSaudeDAO(con);
        resultado = profissionalSaudeDAO.excluir(profissionalSaude);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }


    /**
     * Busca e retorna os dados de um profissional de saúde
     * @param idProfissionalSaude ID do profissional a ser buscado
     * @return string formatada com os dados do profissional
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String listarUmProfissionalSaude(int idProfissionalSaude) throws ClassNotFoundException, SQLException {
        String resultado = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Connection con = ConnectionFactory.abrirConexao();
        try {
            ProfissionalSaudeDTO profissionalSaude = new ProfissionalSaudeDTO();
            profissionalSaude.setIdProfissionalSaude(idProfissionalSaude);

            ProfissionalSaudeDAO profissionalSaudeDAO = new ProfissionalSaudeDAO(con);
            ProfissionalSaudeDTO profissionalSaudeEncontrado = profissionalSaudeDAO.listarUm(profissionalSaude);

            if (profissionalSaudeEncontrado == null) {
                throw new ProfissionalSaudeException("Profissional: Não existe um profissional da saúde com esse id.");
            }
            resultado = String.format("Id: %s \nNome Completo: %s \nData de Nascimento: %s \nCPF: %s \nTelefone: %s \nE-mail: %s\nDocumento: %s %s", profissionalSaudeEncontrado.getIdProfissionalSaude(), profissionalSaudeEncontrado.getNomeCompleto(), profissionalSaudeEncontrado.getDataNascimento().format(dtf), profissionalSaudeEncontrado.getCpf(), profissionalSaudeEncontrado.getTelefone(), profissionalSaudeEncontrado.getEmail(), profissionalSaudeEncontrado.getTipoDocumento(), profissionalSaudeEncontrado.getDocumento());

        } catch (ProfissionalSaudeException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }





}
