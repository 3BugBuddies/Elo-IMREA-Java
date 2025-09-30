package br.com.fiap.controller;

import br.com.fiap.exceptions.AtendimentoException;
import br.com.fiap.exceptions.LembreteException;
import br.com.fiap.model.dao.AtendimentoDAO;
import br.com.fiap.model.dao.LembreteDAO;
import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dto.AtendimentoDTO;
import br.com.fiap.model.dto.LembreteDTO;
import br.com.fiap.model.dto.ProfissionalSaudeDTO;
import br.com.fiap.model.enums.StatusLembrete;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LembreteController {
    public String inserirLembrete(int idAtendimento, int idColaborador) throws ClassNotFoundException, SQLException {
        String resultado = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        AtendimentoDTO atendimento = new AtendimentoDTO();
        atendimento.setIdAtendimento(idAtendimento);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            AtendimentoDAO atendimentoDAO = new AtendimentoDAO(con);
            AtendimentoDTO atendimentoEncontrado= atendimentoDAO.listarUm(atendimento);
            if(atendimentoEncontrado == null) {
                throw new AtendimentoException("Não existe um atendimento com este id.");
            }

            ProfissionalSaudeDTO profissional = atendimentoEncontrado.getProfissionalSaude();
            String assunto = String.format("IMREA: Consulta com %s dia %s",profissional.getEspecialidade(),atendimentoEncontrado.getData().format(dtf) );
            String mensagem = String.format(
                    "Olá Sr(a) %s,\nEstamos passando para lembrar que seu atendimento foi %s. \nProfissional: %s (%s)\nFormato:%s \nLocal/link:%s \nData: Dia %s às %s",
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
            lembrete.setIdColaborador(idColaborador);
            lembrete.setAtendimento(atendimentoEncontrado);
            lembrete.setStatus(StatusLembrete.PENDENTE);

            LembreteDAO lembreteDAO = new LembreteDAO(con);
            resultado = lembreteDAO.inserir(lembrete);
        } catch (AtendimentoException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

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

            if (atendimentoEncontrado == null) {
                throw new AtendimentoException("Não foi encontrado nenhum atendimento com o id informado");
            }

            ProfissionalSaudeDTO profissional = atendimentoEncontrado.getProfissionalSaude();
            String assunto = String.format("IMREA: Consulta com %s dia %s\n",profissional.getEspecialidade(),atendimentoEncontrado.getData().format(dtf) );

            String mensagem = String.format(
                    "Olá Sr(a) %s,\nEstamos passando para lembrar que seu atendimento foi %s. \nProfissional: %s (%s)\nFormato:%s \nLocal/link:%s \nData: Dia %s às %s",
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

            LembreteDAO lembreteDAO = new LembreteDAO(con);
            resultado = lembreteDAO.alterar(lembreteAtualizado);
        } catch (AtendimentoException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

    public String deletarLembrete(int idLembrete) throws ClassNotFoundException, SQLException {
        String resultado;

        LembreteDTO lembrete = new LembreteDTO();
        lembrete.setIdLembrete(idLembrete);

        Connection con = ConnectionFactory.abrirConexao();
        LembreteDAO lembreteDAO = new LembreteDAO(con);
        resultado = lembreteDAO.excluir(lembrete);
        ConnectionFactory.fecharConexao(con);
        return resultado;

    }


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
                throw new LembreteException("Não foi encontrado nenhum lembrete com o id informado");
            }

            resultado = String.format("Assunto:%s \nMensagem:%s \nStatus:%s \nData de Envio: %s", lembreteEncontrado.getAssunto(), lembreteEncontrado.getMensagem(), lembreteEncontrado.getStatus().name(), lembreteEncontrado.getDataEnvio().format(dtf));
            return resultado;
        } catch (LembreteException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

    public String enviarLembretesPendentes(int idAtendimento) throws ClassNotFoundException, SQLException {
        String resultado = "";
        ArrayList<LembreteDTO> lembretes;

        AtendimentoDTO atendimento = new AtendimentoDTO();
        atendimento.setIdAtendimento(idAtendimento);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            LembreteDAO lembreteDAO = new LembreteDAO(con);
            lembretes = lembreteDAO.listarPendentesPorAtendimento(atendimento);

            if(lembretes == null || lembretes.isEmpty()) {
                throw new LembreteException("Lembrete: Não existem lembretes pendentes de envio para o atendimento");
            }
            for (LembreteDTO lembrete : lembretes) {
                lembrete.setStatus(StatusLembrete.ENVIADO);
                resultado = lembreteDAO.alterar(lembrete);
            }
            return resultado;
        } catch (LembreteException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

}
