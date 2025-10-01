package br.com.fiap.controller;

import br.com.fiap.exceptions.PacienteException;
import br.com.fiap.model.dao.PacienteDAO;
import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dto.AcompanhanteDTO;
import br.com.fiap.model.dto.PacienteDTO;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe responsável por controlar as operações relacionadas aos pacientes e fazer contato com a classe que acessa ao banco
 * @version 1.0
 */
public class PacienteController {
    /**
     * Realiza a inserção de um novo paciente no sistema
     * @param nomeCompleto nome completo do paciente
     * @param cpf CPF do paciente
     * @param dataNascimento data de nascimento do paciente
     * @param email email do paciente
     * @param telefone telefone do paciente
     * @param diagnostico diagnóstico do paciente
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String inserirPaciente(String nomeCompleto, String cpf,LocalDate dataNascimento, String email, String telefone, String diagnostico) throws ClassNotFoundException, SQLException {
        String resultado;

        PacienteDTO paciente = new PacienteDTO();
        paciente.setNomeCompleto(nomeCompleto);
        paciente.setCpf(cpf);
        paciente.setDataNascimento(dataNascimento);
        paciente.setTelefone(telefone);
        paciente.setEmail(email);
        paciente.setDiagnostico(diagnostico);

        Connection con = ConnectionFactory.abrirConexao();
        PacienteDAO pacienteDAO = new PacienteDAO(con);
        resultado = pacienteDAO.inserir(paciente);
        ConnectionFactory.fecharConexao(con);
        return resultado;

    }


    /**
     * Atualiza os dados de um paciente no sistema
     * @param nomeCompleto novo nome completo do paciente
     * @param email novo email do paciente
     * @param telefone novo telefone do paciente
     * @param diagnostico novo diagnóstico do paciente
     * @param idPaciente ID do paciente a ser atualizado
     * @return mensagem informando o resultado
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String atualizarPaciente(String nomeCompleto, String email, String telefone, String diagnostico, int idPaciente) throws ClassNotFoundException, SQLException {
        String resultado;

        PacienteDTO paciente = new PacienteDTO();
        paciente.setNomeCompleto(nomeCompleto);
        paciente.setTelefone(telefone.replace("(", "").replace("-", "").replace(")", "").replace(" ", ""));
        paciente.setEmail(email);
        paciente.setDiagnostico(diagnostico);
        paciente.setIdPaciente(idPaciente);

        Connection con = ConnectionFactory.abrirConexao();
        PacienteDAO pacienteDAO = new PacienteDAO(con);
        resultado = pacienteDAO.alterar(paciente);
        ConnectionFactory.fecharConexao(con);
        return resultado;

    }

    /**
     * Exclui um paciente do sistema
     * @param idPaciente ID do paciente a ser excluído
     * @return mensagem informando o resultado
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String excluirPaciente(int idPaciente) throws ClassNotFoundException, SQLException {
        String resultado;

        PacienteDTO paciente = new PacienteDTO();
        paciente.setIdPaciente(idPaciente);

        Connection con = ConnectionFactory.abrirConexao();
        PacienteDAO pacienteDAO = new PacienteDAO(con);
        resultado = pacienteDAO.excluir(paciente);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

    /**
     * Busca e retorna os dados de um paciente
     * @param idPaciente ID do paciente a ser buscado
     * @return string formatada com os dados do paciente
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String listarUmPaciente(int idPaciente) throws ClassNotFoundException, SQLException {
        String resultado = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        PacienteDTO paciente = new PacienteDTO();
        paciente.setIdPaciente(idPaciente);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            PacienteDAO pacienteDAO = new PacienteDAO(con);
            PacienteDTO pacienteEncontrado = pacienteDAO.listarUm(paciente);

            if (pacienteEncontrado == null) {
                throw new PacienteException("Paciente: Não existe um paciente com esse id");
            }

            resultado = String.format("Id: %s \nNome Completo: %s \nData de Nascimento: %s \nCPF: %s \nTelefone: %s \nE-mail: %s\nDiagnostico: %s", pacienteEncontrado.getIdPaciente(), pacienteEncontrado.getNomeCompleto(), pacienteEncontrado.getDataNascimento().format(dtf), pacienteEncontrado.getCpf(), pacienteEncontrado.getTelefone(), pacienteEncontrado.getEmail(), pacienteEncontrado.getDiagnostico());
        } catch (PacienteException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

}
