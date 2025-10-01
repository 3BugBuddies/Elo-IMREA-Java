package br.com.fiap.controller;

import br.com.fiap.exceptions.AcompanhanteException;
import br.com.fiap.exceptions.PacienteException;
import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dao.AcompanhanteDAO;
import br.com.fiap.model.dao.PacienteDAO;
import br.com.fiap.model.dto.AcompanhanteDTO;
import br.com.fiap.model.dto.PacienteDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Classe responsável por controlar as operações relacionadas aos acompanhantes
 * @version 1.0
 */
public class AcompanhanteController {
    /**
     * Realiza a inserção de um novo acompanhante no sistema
     * @param nomeCompleto nome completo do acompanhante
     * @param cpf CPF do acompanhante
     * @param dataNascimento data de nascimento do acompanhante
     * @param email email do acompanhante
     * @param telefone telefone do acompanhante
     * @param parentesco relação de parentesco com o paciente
     * @param idPaciente ID do paciente ao qual o acompanhante será vinculado
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String inserirAcompanhante(String nomeCompleto, String cpf, LocalDate dataNascimento, String email, String telefone, String parentesco, int idPaciente) throws ClassNotFoundException,SQLException {
        String resultado = "";

        LocalDate dataLimiteNascimento = LocalDate.now().minusYears(18);

        PacienteDTO paciente = new PacienteDTO();
        paciente.setIdPaciente(idPaciente);

        Connection con = ConnectionFactory.abrirConexao();
        try{
            PacienteDAO pacienteDAO = new PacienteDAO(con);
            PacienteDTO pacienteEncontrado = pacienteDAO.listarUm(paciente);

            if(pacienteEncontrado == null){
                throw new PacienteException("Paciente: Não foi encontrado nenhum paciente para vincular a esse acompanhante");
            }

            if (dataNascimento.isAfter(dataLimiteNascimento) || dataNascimento.minusDays(1).isEqual(dataLimiteNascimento)) {
                throw new AcompanhanteException("Acompanhante: O acompanhante deve ter pelo menos 18 anos");
            }

            AcompanhanteDTO acompanhante = new AcompanhanteDTO();
            acompanhante.setNomeCompleto(nomeCompleto);
            acompanhante.setCpf(cpf.replace(".", "").replace("-", ""));
            acompanhante.setDataNascimento(dataNascimento);
            acompanhante.setTelefone(telefone.replace("(", "").replace("-", "").replace(")", "").replace(" ", ""));
            acompanhante.setEmail(email);
            acompanhante.setParentesco(parentesco);
            acompanhante.setIdPaciente(idPaciente);

            AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(con);
            resultado = acompanhanteDAO.inserir(acompanhante);
        } catch (AcompanhanteException | PacienteException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }

        return resultado;
    }

    /**
     * Atualiza os dados de um acompanhante existente no sistema
     * @param nomeCompleto novo nome completo do acompanhante
     * @param email novo email do acompanhante
     * @param telefone novo telefone do acompanhante
     * @param parentesco novo parentesco com o paciente
     * @param idAcompanhante ID do acompanhante a ser atualizado
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String atualizarAcompanhante(String nomeCompleto, String email, String telefone, String parentesco, int idAcompanhante) throws ClassNotFoundException, SQLException {
        String resultado;

        AcompanhanteDTO acompanhante = new AcompanhanteDTO();
        acompanhante.setNomeCompleto(nomeCompleto);
        acompanhante.setTelefone(telefone.replace("(", "").replace("-", "").replace(")", "").replace(" ", ""));
        acompanhante.setEmail(email);
        acompanhante.setParentesco(parentesco);
        acompanhante.setIdAcompanhante(idAcompanhante);

        Connection con = ConnectionFactory.abrirConexao();
        AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(con);
        resultado = acompanhanteDAO.alterar(acompanhante);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

    /**
     * Exclui um acompanhante do sistema
     * @param idAcompanhante ID do acompanhante a ser excluído
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String deletarAcompanhante(int idAcompanhante) throws ClassNotFoundException, SQLException {
        String resultado;

        AcompanhanteDTO acompanhante = new AcompanhanteDTO();
        acompanhante.setIdAcompanhante(idAcompanhante);

        Connection con = ConnectionFactory.abrirConexao();
        AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(con);
        resultado = acompanhanteDAO.excluir(acompanhante);
        ConnectionFactory.fecharConexao(con);
        return resultado;

    }


    /**
     * Busca e retorna os dados de um acompanhante específico
     * @param idAcompanhante ID do acompanhante a ser buscado
     * @return string formatada com os dados do acompanhante
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String listarUmAcompanhante(int idAcompanhante) throws ClassNotFoundException, SQLException {
        String resultado = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        AcompanhanteDTO acompanhante = new AcompanhanteDTO();
        acompanhante.setIdAcompanhante(idAcompanhante);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(con);
            AcompanhanteDTO acompanhanteEncontrado = acompanhanteDAO.listarUm(acompanhante);

            if(acompanhanteEncontrado == null){
                throw new AcompanhanteException("Acompanhante: Não existe acompanhante cadastrado com esse id");
            }
            PacienteDAO pacienteDAO = new PacienteDAO(con);
            PacienteDTO paciente = pacienteDAO.listarUmPorAcompanhante(acompanhante);

            if(paciente == null){
                throw new PacienteException("Paciente: Não foi localizado nenhum paciente vinculado a esse acompanhante");
            }

            resultado += String.format("Id: %s \nNome Completo: %s \nData de Nascimento: %s \nCPF: %s \nTelefone: %s \nE-mail: %s \nPaciente Associado: %s \nParentesco: %s", acompanhanteEncontrado.getIdAcompanhante(), acompanhanteEncontrado.getNomeCompleto(), acompanhanteEncontrado.getDataNascimento().format(dtf), acompanhanteEncontrado.getCpf(), acompanhanteEncontrado.getTelefone(), acompanhanteEncontrado.getEmail(),paciente.getNomeCompleto(), acompanhanteEncontrado.getParentesco());
        } catch (AcompanhanteException | PacienteException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }

        return resultado;
    }

    /**
     * Lista todos os acompanhantes de um paciente
     * @param idPaciente ID do paciente
     * @return string formatada com os dados de todos os acompanhantes do paciente
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String listarTodosPorPaciente(int idPaciente) throws ClassNotFoundException, SQLException {
        String resultado = "";
        String registroAcompanhante = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        PacienteDTO paciente = new PacienteDTO();
        paciente.setIdPaciente(idPaciente);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            PacienteDAO pacienteDAO = new PacienteDAO(con);
            PacienteDTO pacienteEncontrado = pacienteDAO.listarUm(paciente);

            if(pacienteEncontrado == null){
                throw new PacienteException("Paciente: Não foi localizado nenhum paciente vinculado a esse acompanhante");
            }

            AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(con);
            ArrayList<AcompanhanteDTO> acompanhantesEncontrado = acompanhanteDAO.listarTodosPorPaciente(paciente);

            if(acompanhantesEncontrado == null || acompanhantesEncontrado.isEmpty()){
                throw new AcompanhanteException("Acompanhante: Não existe acompanhante com esse id");
            }

            for(AcompanhanteDTO acompanhante :acompanhantesEncontrado ){
                registroAcompanhante = String.format("\nId: %s \nNome Completo: %s \nData de Nascimento: %s \nCPF: %s \nTelefone: %s \nE-mail: %s \nPaciente Associado: %s \nParentesco: %s\n", acompanhante.getIdAcompanhante(), acompanhante.getNomeCompleto(), acompanhante.getDataNascimento().format(dtf), acompanhante.getCpf(), acompanhante.getTelefone(), acompanhante.getEmail(),pacienteEncontrado.getNomeCompleto(), acompanhante.getParentesco());

                resultado += registroAcompanhante + "\n";
            }

        } catch (AcompanhanteException | PacienteException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }

        return resultado;
    }

}
