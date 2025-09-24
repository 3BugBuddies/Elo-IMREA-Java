package br.com.fiap.controller;

import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dao.AcompanhanteDAO;
import br.com.fiap.model.dto.AcompanhanteDTO;
import br.com.fiap.model.dto.PacienteDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class AcompanhanteController {
    public String inserirAcompanhante(String nomeCompleto, String cpf, LocalDate dataNascimento, String email, String telefone, String parentesco, int idPaciente) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        AcompanhanteDTO acompanhante = new AcompanhanteDTO();
        acompanhante.setNomeCompleto(nomeCompleto);
        acompanhante.setCpf(cpf);
        acompanhante.setDataNascimento(dataNascimento);
        acompanhante.setTelefone(telefone);
        acompanhante.setEmail(email);
        acompanhante.setParentesco(parentesco);
        acompanhante.setIdPaciente(idPaciente);

        AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(con);
        resultado = acompanhanteDAO.inserir(acompanhante);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

    public String atualizarAcompanhante(String nomeCompleto, String email, String telefone, String parentesco) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        AcompanhanteDTO acompanhante = new AcompanhanteDTO();
        acompanhante.setNomeCompleto(nomeCompleto);
        acompanhante.setTelefone(telefone);
        acompanhante.setEmail(email);
        acompanhante.setParentesco(parentesco);

        AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(con);
        resultado = acompanhanteDAO.inserir(acompanhante);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

    public String deletarAcompanhante(int idAcompanhante) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        AcompanhanteDTO acompanhante = new AcompanhanteDTO();
        acompanhante.setIdAcompanhante(idAcompanhante);

        AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(con);
        resultado = acompanhanteDAO.excluir(acompanhante);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }


    public String listarAcompanhante(int idAcompanhante) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        AcompanhanteDTO acompanhante = new AcompanhanteDTO();
        acompanhante.setIdAcompanhante(idAcompanhante);

        AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(con);
        resultado = acompanhanteDAO.listarUm(acompanhante);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }
}
