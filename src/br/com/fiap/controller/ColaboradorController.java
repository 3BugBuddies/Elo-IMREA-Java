package br.com.fiap.controller;

import br.com.fiap.model.dao.ColaboradorDAO;
import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dto.ColaboradorDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class ColaboradorController {
    public String inserirColaborador(String nomeCompleto, String cpf, LocalDate dataNascimento, String email, String telefone, String unidade) throws ClassNotFoundException, SQLException {
        String resultado;
        
        Connection con = ConnectionFactory.abrirConexao();

        ColaboradorDTO colaborador = new ColaboradorDTO();
        colaborador.setNomeCompleto(nomeCompleto);
        colaborador.setCpf(cpf);
        colaborador.setDataNascimento(dataNascimento);
        colaborador.setTelefone(telefone);
        colaborador.setEmail(email);
        colaborador.setUnidade(unidade);

        ColaboradorDAO colaboradorDAO = new ColaboradorDAO(con);
        resultado = colaboradorDAO.inserir(colaborador);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

    public String atualizarColaborador(String nomeCompleto, String email, String telefone, String unidade, int idColaborador) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        ColaboradorDTO colaborador = new ColaboradorDTO();
        colaborador.setNomeCompleto(nomeCompleto);
        colaborador.setTelefone(telefone);
        colaborador.setEmail(email);
        colaborador.setUnidade(unidade);
        colaborador.setIdColaborador(idColaborador);

        ColaboradorDAO colaboradorDAO = new ColaboradorDAO(con);
        resultado = colaboradorDAO.alterar(colaborador);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

    public String deletarColaborador(int idColaborador) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        ColaboradorDTO colaborador = new ColaboradorDTO();
        colaborador.setIdColaborador(idColaborador);

        ColaboradorDAO colaboradorDAO = new ColaboradorDAO(con);
        resultado = colaboradorDAO.excluir(colaborador);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }


    public String listarUmColaborador(int idColaborador) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        ColaboradorDTO colaborador = new ColaboradorDTO();
        colaborador.setIdColaborador(idColaborador);

        ColaboradorDAO colaboradorDAO = new ColaboradorDAO(con);
        resultado = colaboradorDAO.listarUm(colaborador);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }
}
