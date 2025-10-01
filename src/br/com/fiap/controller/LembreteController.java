package br.com.fiap.controller;

import br.com.fiap.exceptions.AtendimentoException;
import br.com.fiap.exceptions.ColaboradorException;
import br.com.fiap.exceptions.LembreteException;
import br.com.fiap.model.dao.AtendimentoDAO;
import br.com.fiap.model.dao.ColaboradorDAO;
import br.com.fiap.model.dao.LembreteDAO;
import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dto.*;
import br.com.fiap.model.enums.StatusLembrete;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Classe responsável por controlar as operações relacionadas aos lembretes e fazer contato com a classe que acessa ao banco
 * @version 1.0
 */
public class LembreteController {
    /**
     * Realiza a inserção de um novo lembrete no sistema
     * @param idAtendimento ID do atendimento ao qual o lembrete está associado
     * @param idColaborador ID do colaborador que está enviando o lembrete
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String inserirLembrete(int idAtendimento, int idColaborador) throws ClassNotFoundException, SQLException {
        String resultado = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        AtendimentoDTO atendimento = new AtendimentoDTO();
        atendimento.setIdAtendimento(idAtendimento);

        ColaboradorDTO colaborador = new ColaboradorDTO();
        colaborador.setIdColaborador(idColaborador);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            AtendimentoDAO atendimentoDAO = new AtendimentoDAO(con);

            AtendimentoDTO atendimentoEncontrado = atendimentoDAO.listarUm(atendimento);
            if(atendimentoEncontrado == null) {
                throw new AtendimentoException("Atendimento: Não existe um atendimento com este id.");
            }

            ColaboradorDAO colaboradorDAO = new ColaboradorDAO(con);
            ColaboradorDTO colaboradorEncontrado = colaboradorDAO.listarUm(colaborador);

            if(colaboradorEncontrado == null) {
                throw new ColaboradorException("Colaborador: Não existe um colaborador com este id.");
            }

            ProfissionalSaudeDTO profissional = atendimentoEncontrado.getProfissionalSaude();
            String assunto = String.format(" IMREA: Consulta com %s dia %s",profissional.getEspecialidade(),atendimentoEncontrado.getData().format(dtf) );
            String mensagem = String.format(
                    "\nOlá Sr(a) %s,\nEstamos passando para lembrar que seu atendimento foi %s.\n \nProfissional: %s (%s)\nFormato: %s \nLocal/Link: %s \nData: %s às %s",
                    atendimentoEncontrado.getPaciente().getNomeCompleto(),
                    atendimentoEncontrado.getStatus().name(),
                    profissional.getNomeCompleto(),
                    profissional.getEspecialidade(),
                    atendimentoEncontrado.getFormatoAtendimento(),
                    atendimentoEncontrado.getLocal(),
                    atendimentoEncontrado.getData().format(dtf),
                    atendimentoEncontrado.getHora());

            LembreteDTO lembrete = new LembreteDTO();
            lembrete.setAssunto(assunto);
            lembrete.setMensagem(mensagem);
            lembrete.setDataEnvio(LocalDate.now());
            lembrete.setIdColaborador(colaboradorEncontrado.getIdColaborador());
            lembrete.setAtendimento(atendimentoEncontrado);
            lembrete.setStatus(StatusLembrete.ENVIADO);

            LembreteDAO lembreteDAO = new LembreteDAO(con);
            resultado = lembreteDAO.inserir(lembrete);
        } catch (AtendimentoException | ColaboradorException e ) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

    /**
     * Atualiza os dados de um lembrete existente no sistema
     * @param idColaborador ID do colaborador que está atualizando o lembrete
     * @param idLembrete ID do lembrete a ser atualizado
     * @param idAtendimento ID do atendimento ao qual o lembrete está associado
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String atualizarLembrete(int idColaborador, int idLembrete, int idAtendimento) throws ClassNotFoundException, SQLException {
        String resultado = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LembreteDTO lembrete = new LembreteDTO();
        lembrete.setIdLembrete(idLembrete);

        AtendimentoDTO atendimento = new AtendimentoDTO();
        atendimento.setIdAtendimento(idAtendimento);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            AtendimentoDAO atendimentoDAO = new AtendimentoDAO(con);
            AtendimentoDTO atendimentoEncontrado = atendimentoDAO.listarUm(atendimento);

            LembreteDAO lembreteDAO = new LembreteDAO(con);
            LembreteDTO lembreteEncontrado = lembreteDAO.listarUm(lembrete);

            if (lembreteEncontrado == null) {
                throw new LembreteException("Lembrete: Não foi encontrado nenhum lembrete com o id informado");
            }

            if (atendimentoEncontrado == null) {
                throw new AtendimentoException("Atendimento: Não foi encontrado nenhum atendimento com o id informado");
            }


            ProfissionalSaudeDTO profissional = atendimentoEncontrado.getProfissionalSaude();
            String assunto = String.format("IMREA: Consulta com %s dia %s\n",profissional.getEspecialidade(),atendimentoEncontrado.getData().format(dtf) );

            String mensagem = String.format(
                    "\nOlá Sr(a) %s,\nEstamos passando para lembrar que seu atendimento foi %s.\n \nProfissional: %s (%s)\nFormato: %s \nLocal/Link: %s \nData: %s às %s",
                    atendimentoEncontrado.getPaciente().getNomeCompleto(),
                    atendimentoEncontrado.getStatus().name(),
                    profissional.getNomeCompleto(),
                    profissional.getEspecialidade(),
                    atendimentoEncontrado.getFormatoAtendimento(),
                    atendimentoEncontrado.getLocal(),
                    atendimentoEncontrado.getData().format(dtf),
                    atendimentoEncontrado.getHora());

            LembreteDTO lembreteAtualizado = new LembreteDTO();
            lembreteAtualizado.setAssunto(assunto);
            lembreteAtualizado.setMensagem(mensagem);
            lembreteAtualizado.setDataEnvio(LocalDate.now());
            lembreteAtualizado.setIdColaborador(idColaborador);
            lembreteAtualizado.setIdLembrete(idLembrete);
            lembreteAtualizado.setStatus(StatusLembrete.REENVIADO);

            resultado = lembreteDAO.alterar(lembreteAtualizado);
        } catch (AtendimentoException | LembreteException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

    /**
     * Exclui um lembrete do sistema
     * @param idLembrete ID do lembrete a ser excluído
     * @return mensagem informando o resultado da operação
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String excluirLembrete(int idLembrete) throws ClassNotFoundException, SQLException {
        String resultado;

        LembreteDTO lembrete = new LembreteDTO();
        lembrete.setIdLembrete(idLembrete);

        Connection con = ConnectionFactory.abrirConexao();
        LembreteDAO lembreteDAO = new LembreteDAO(con);
        resultado = lembreteDAO.excluir(lembrete);
        ConnectionFactory.fecharConexao(con);
        return resultado;

    }


    /**
     * Busca e retorna os dados de um lembrete específico
     * @param idLembrete ID do lembrete a ser buscado
     * @return string formatada com os dados do lembrete
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução da query SQL
     */
    public String listarUmLembrete(int idLembrete) throws ClassNotFoundException, SQLException {
        String resultado = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LembreteDTO lembrete = new LembreteDTO();
        lembrete.setIdLembrete(idLembrete);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            LembreteDAO lembreteDAO = new LembreteDAO(con);
            LembreteDTO lembreteEncontrado = lembreteDAO.listarUm(lembrete);

            if(lembreteEncontrado == null) {
                throw new LembreteException("Lembrete: Não foi encontrado nenhum lembrete com o id informado");
            }

            resultado = String.format("Assunto: \n%s \nMensagem: \n%s \n\nStatus do Lembrete: %s \nData de Envio: %s", lembreteEncontrado.getAssunto(), lembreteEncontrado.getMensagem(), lembreteEncontrado.getStatus().name(), lembreteEncontrado.getDataEnvio().format(dtf));
        } catch (LembreteException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

    /**
     * Lista todos os lembretes de um paciente
     * @param idPaciente ID do paciente
     * @return string formatada com os dados de todos os lembretes do paciente
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String listarTodosLembretesPorPaciente(int idPaciente) throws ClassNotFoundException, SQLException {
        String resultado = "";
        String lembreteIndividual = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        PacienteDTO paciente = new PacienteDTO();
        paciente.setIdPaciente(idPaciente);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            LembreteDAO lembreteDAO = new LembreteDAO(con);
            ArrayList<LembreteDTO> lembretesEncontrados = lembreteDAO.listarTodosPorPaciente(paciente);

            if(lembretesEncontrados == null || lembretesEncontrados.isEmpty()) {
                throw new LembreteException("Lembrete: Não foi encontrado nenhum lembrete para esse paciente");
            }

            for (LembreteDTO lembrete : lembretesEncontrados) {
                lembreteIndividual = String.format("ID:%s \nAssunto: \n%s \nMensagem: \n%s \nStatus do Lembrete: %s \nData de Envio: %s\n", lembrete.getIdLembrete(), lembrete.getAssunto(), lembrete.getMensagem(), lembrete.getStatus().name(), lembrete.getDataEnvio().format(dtf));
                resultado += lembreteIndividual + "\n";
            }
        } catch (LembreteException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

    /**
     * Busca e retorna o último lembrete enviado para um paciente
     * @param idPaciente ID do paciente
     * @return string formatada com os dados do último lembrete do paciente
     * @throws ClassNotFoundException se ocorrer erro ao carregar o driver do banco
     * @throws SQLException se ocorrer erro na execução do comando sql
     */
    public String listarUltimoLembretePorPaciente(int idPaciente) throws ClassNotFoundException, SQLException {
        String resultado;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        PacienteDTO paciente = new PacienteDTO();
        paciente.setIdPaciente(idPaciente);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            LembreteDAO lembreteDAO = new LembreteDAO(con);
            LembreteDTO lembreteEncontrado = lembreteDAO.listarUltimoPorPaciente(paciente);

            if(lembreteEncontrado == null) {
                throw new LembreteException("Lembrete: Não foi encontrado nenhum lembrete para esse paciente");
            }

            resultado = String.format("Assunto: \n%s \nMensagem: \n%s \nStatus do Lembrete: %s \nData de Envio: %s\n", lembreteEncontrado.getAssunto(), lembreteEncontrado.getMensagem(), lembreteEncontrado.getStatus().name(), lembreteEncontrado.getDataEnvio().format(dtf));
        } catch (LembreteException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

}
