package br.com.fiap.controller;

import br.com.fiap.model.dao.LembreteDAO;
import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dto.LembreteDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class LembreteController {
    public String inserirLembrete(String assunto, String mensagem, LocalDate dataEnvio, int idColaborador, int idAtendimento) throws ClassNotFoundException, SQLException {
        String resultado;
        
        Connection con = ConnectionFactory.abrirConexao();

        LembreteDTO lembrete = new LembreteDTO();
        lembrete.setAssunto(assunto);
        lembrete.setMensagem(mensagem);
        lembrete.setDataEnvio(dataEnvio);
        lembrete.setIdColaborador(idColaborador);
        lembrete.setIdAtendimento(idAtendimento);

        LembreteDAO lembreteDAO = new LembreteDAO(con);
        resultado = lembreteDAO.inserir(lembrete);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

    public String atualizarLembrete(String assunto, String mensagem, LocalDate dataEnvio, int idColaborador, int idLembrete) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        LembreteDTO lembrete = new LembreteDTO();
        lembrete.setAssunto(assunto);
        lembrete.setMensagem(mensagem);
        lembrete.setDataEnvio(dataEnvio);
        lembrete.setIdColaborador(idColaborador);
        lembrete.setIdLembrete(idLembrete);

        LembreteDAO lembreteDAO = new LembreteDAO(con);
        resultado = lembreteDAO.alterar(lembrete);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

    public String deletarLembrete(int idLembrete) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        LembreteDTO lembrete = new LembreteDTO();
        lembrete.setIdLembrete(idLembrete);

        LembreteDAO lembreteDAO = new LembreteDAO(con);
        resultado = lembreteDAO.excluir(lembrete);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }


    public String listarLembrete(int idLembrete) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        LembreteDTO lembrete = new LembreteDTO();
        lembrete.setIdLembrete(idLembrete);

        LembreteDAO lembreteDAO = new LembreteDAO(con);
        resultado = lembreteDAO.listarUm(lembrete);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }
}
