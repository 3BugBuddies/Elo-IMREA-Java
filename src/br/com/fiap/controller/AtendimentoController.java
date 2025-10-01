package br.com.fiap.controller;

import br.com.fiap.exceptions.*;
import br.com.fiap.model.dao.*;
import br.com.fiap.model.dao.AtendimentoDAO;
import br.com.fiap.model.dto.*;
import br.com.fiap.model.enums.StatusAtendimento;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Classe responsável por controlar as operações relacionadas aos atendimentos e fazer contato com a classe que acessa ao banco
 * @version 1.0
 */
public class AtendimentoController {
    /**
     * Realiza a inserção de um novo atendimento
     * @param idProfissional ID do profissional responsável pelo atendimento
     * @param idPaciente ID do paciente que será atendido
     * @param data data do atendimento
     * @param hora hora do atendimento
     * @param formato formato do atendimento
     * @param local local onde será realizado o atendimento
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String inserirAtendimento(int idProfissional, int idPaciente, LocalDate data, LocalTime hora, String formato, String local) throws ClassNotFoundException, SQLException {
        String resultado;

        ProfissionalSaudeDTO profissional = new ProfissionalSaudeDTO();
        profissional.setIdProfissionalSaude(idProfissional);

        PacienteDTO paciente = new PacienteDTO();
        paciente.setIdPaciente(idPaciente);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            ProfissionalSaudeDAO profissionalDAO = new ProfissionalSaudeDAO(con);
            ProfissionalSaudeDTO profissionalEncontrado = profissionalDAO.listarUm(profissional);

            PacienteDAO pacienteDAO = new PacienteDAO(con);
            PacienteDTO pacienteEncontrado = pacienteDAO.listarUm(paciente);


            if(profissionalEncontrado == null){
                throw new ProfissionalSaudeException("Profissional: Cadastre um profissional para vincula-lo a um atendimento");
            }

            if (pacienteEncontrado == null){
                throw new PacienteException("Paciente: Cadastre um paciente para vincula-lo a um atendimento");
            }
            if (data.isBefore(LocalDate.now())) {
                throw new AtendimentoException("Atendimento: Não é possível marcar um atendimento para hoje ou uma data passada");
            }

            AtendimentoDAO atendimentoDAO = new AtendimentoDAO(con);
            ArrayList<AtendimentoDTO> atendimentos = atendimentoDAO.listarTodosPorPaciente(paciente);
            for (AtendimentoDTO atendimento : atendimentos) {
                if(data.isEqual(atendimento.getData()) && hora.equals(atendimento.getHora())) {
                    throw new AtendimentoException("Atendimento: O paciente já possui um atendimento marcado nessa data/hora");}
            }

            AtendimentoDTO atendimento = new AtendimentoDTO();
            atendimento.setData(data);
            atendimento.setHora(hora);
            atendimento.setStatus(StatusAtendimento.MARCADO);
            atendimento.setLocal(local);
            atendimento.setProfissionalSaude(profissionalEncontrado);
            atendimento.setPaciente(pacienteEncontrado);
            atendimento.setFormatoAtendimento(formato);
            resultado = atendimentoDAO.inserir(atendimento);

        } catch (ProfissionalSaudeException | PacienteException | AtendimentoException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

    /**
     * Atualiza os dados de um atendimento no sistema
     * @param idAtendimento ID do atendimento a ser atualizado
     * @param idProfissional ID do profissional responsável pelo atendimento
     * @param data nova data do atendimento
     * @param hora nova hora do atendimento
     * @param formato novo formato do atendimento
     * @param local novo local do atendimento
     * @param status novo status do atendimento
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String atualizarAtendimento(int idAtendimento, int idProfissional, LocalDate data, LocalTime hora, String formato, String local, StatusAtendimento status) throws ClassNotFoundException, SQLException {
        String resultado = "";

        AtendimentoDTO atendimento = new AtendimentoDTO();
        atendimento.setIdAtendimento(idAtendimento);

        ProfissionalSaudeDTO profissional = new ProfissionalSaudeDTO();
        profissional.setIdProfissionalSaude(idProfissional);

        Connection con = ConnectionFactory.abrirConexao();
        try {

            if (data.isBefore(LocalDate.now())) {
                throw new AtendimentoException("Atendimento: Não é possível marcar um atendimento para hoje ou uma data passada");
            }

            AtendimentoDAO atendimentoDAO = new AtendimentoDAO(con);
            AtendimentoDTO atendimentoEncontrado = atendimentoDAO.listarUm(atendimento);

            if (atendimentoEncontrado == null) {
                throw new AtendimentoException("Atendimento: Não existe um atendimento para ser atualizado");
            }

            if (atendimentoEncontrado.getStatus().equals(StatusAtendimento.CANCELADO)) {
                throw new AtendimentoException("Atendimento: Não é possível remarcar um atendimento cancelado.");
            }

            ProfissionalSaudeDAO profissionalDAO = new ProfissionalSaudeDAO(con);
            ProfissionalSaudeDTO profissionalEncontrado = profissionalDAO.listarUm(profissional);

            if(profissionalEncontrado == null){
                throw new ProfissionalSaudeException("Profissional: O profissional não pode ser nulo.");
            }

            atendimentoEncontrado.setData(data);
            atendimentoEncontrado.setHora(hora);
            atendimentoEncontrado.setStatus(status);
            atendimentoEncontrado.setLocal(local);
            atendimentoEncontrado.setProfissionalSaude(profissionalEncontrado);
            atendimentoEncontrado.setFormatoAtendimento(formato);
            resultado = atendimentoDAO.alterar(atendimentoEncontrado);

        } catch (ProfissionalSaudeException | AtendimentoException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

    /**
     * Exclui um atendimento do sistema
     * @param idAtendimento ID do atendimento a ser excluído
     * @return mensagem informando o resultado
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String excluirAtendimento(int idAtendimento) throws ClassNotFoundException, SQLException {
        String resultado = "";

        AtendimentoDTO atendimento = new AtendimentoDTO();
        atendimento.setIdAtendimento(idAtendimento);

        Connection con = ConnectionFactory.abrirConexao();
        AtendimentoDAO atendimentoDAO = new AtendimentoDAO(con);
        resultado = atendimentoDAO.excluir(atendimento);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }


    /**
     * Busca e retorna os dados de um atendimento
     * @param idAtendimento ID do atendimento a ser buscado
     * @return string formatada com os dados do atendimento
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String listarUmAtendimento(int idAtendimento) throws ClassNotFoundException, SQLException {
        String resultado = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        AtendimentoDTO atendimento = new AtendimentoDTO();
        atendimento.setIdAtendimento(idAtendimento);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            AtendimentoDAO atendimentoDAO = new AtendimentoDAO(con);
            AtendimentoDTO atendimentoEncontrado = atendimentoDAO.listarUm(atendimento);
            if (atendimentoEncontrado == null) {
                throw new AtendimentoException("Atendimento: Não existe atendimento com esse id");
            }

            resultado = String.format("Id: %s \nEspecialidade: %s\nPaciente: %s \nProfissional: %s \nData: %s \nHora: %s \nFormato: %s \nStatus: %s", atendimentoEncontrado.getIdAtendimento(), atendimentoEncontrado.getProfissionalSaude().getEspecialidade(), atendimentoEncontrado.getPaciente().getNomeCompleto(), atendimentoEncontrado.getProfissionalSaude().getNomeCompleto(), atendimentoEncontrado.getData().format(dtf), atendimentoEncontrado.getHora(), atendimentoEncontrado.getFormatoAtendimento(), atendimentoEncontrado.getStatus().name());
        } catch (AtendimentoException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

    /**
     * Lista todos os atendimentos de um paciente
     * @param idPaciente ID do paciente
     * @return string formatada com os dados de todos os atendimentos do paciente
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução da query SQL
     */
    public String listarTodosPorPaciente(int idPaciente) throws ClassNotFoundException, SQLException {
        String resultado = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        PacienteDTO paciente = new PacienteDTO();
        paciente.setIdPaciente(idPaciente);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            PacienteDAO pacienteDAO = new PacienteDAO(con);
            PacienteDTO pacienteEncontrado = pacienteDAO.listarUm(paciente);

            if (pacienteEncontrado == null ) {
                throw new PacienteException("Paciente: Não existe nenhum paciente com o id informado");
            }

            AtendimentoDAO atendimentoDAO = new AtendimentoDAO(con);
            ArrayList<AtendimentoDTO> atendimentos = atendimentoDAO.listarTodosPorPaciente(paciente);
            if (atendimentos == null || atendimentos.isEmpty()) {
                throw new AtendimentoException("Atendimento: Não existe nenhum atendimento para esse paciente");
            }

            for(AtendimentoDTO atendimentoEncontrado : atendimentos) {
                resultado += String.format("Id: %s \nEspecialidade: %s \nPaciente: %s \nProfissional: %s \nData: %s \nHora: %s \nFormato: %s \nStatus: %s\n\n", atendimentoEncontrado.getIdAtendimento(),atendimentoEncontrado.getProfissionalSaude().getEspecialidade(),  pacienteEncontrado.getNomeCompleto(), atendimentoEncontrado.getProfissionalSaude().getNomeCompleto(), atendimentoEncontrado.getData().format(dtf), atendimentoEncontrado.getHora(), atendimentoEncontrado.getFormatoAtendimento(), atendimentoEncontrado.getStatus().name());
            }
        } catch (AtendimentoException | PacienteException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

}
