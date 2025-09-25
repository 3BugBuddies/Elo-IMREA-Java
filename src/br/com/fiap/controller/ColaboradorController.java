package br.com.fiap.controller;

import br.com.fiap.exceptions.DataNascimentoInvalida;
import br.com.fiap.model.dao.AcompanhanteDAO;
import br.com.fiap.model.dao.ColaboradorDAO;
import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dto.AcompanhanteDTO;
import br.com.fiap.model.dto.ColaboradorDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ColaboradorController {
    public String inserirColaborador(String nomeCompleto, String cpf, LocalDate dataNascimento, String email, String telefone, String unidade) throws ClassNotFoundException, SQLException {
        String resultado;
        Connection con = ConnectionFactory.abrirConexao();
        try{
            ColaboradorDTO colaborador = new ColaboradorDTO();
            colaborador.setNomeCompleto(nomeCompleto);
            colaborador.setCpf(cpf.replace(".", "").replace("-", ""));
            colaborador.setDataNascimento(dataNascimento);
            colaborador.setTelefone(telefone.replace("(", "").replace("-", "").replace(")", "").replace(" ", ""));
            colaborador.setEmail(email);
            colaborador.setUnidade(unidade);

            ColaboradorDAO colaboradorDAO = new ColaboradorDAO(con);
            resultado = colaboradorDAO.inserir(colaborador);
            return resultado;
        } finally{
            ConnectionFactory.fecharConexao(con);
        }
    }

    public String atualizarColaborador(String nomeCompleto, String email, String telefone, String unidade, int idColaborador) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();
        try{
            ColaboradorDTO colaborador = new ColaboradorDTO();
            colaborador.setNomeCompleto(nomeCompleto);
            colaborador.setTelefone(telefone.replace("(", "").replace("-", "").replace(")", "").replace(" ", ""));
            colaborador.setEmail(email);
            colaborador.setUnidade(unidade);
            colaborador.setIdColaborador(idColaborador);

            ColaboradorDAO colaboradorDAO = new ColaboradorDAO(con);
            resultado = colaboradorDAO.inserir(colaborador);
            return resultado;
        } finally{
            ConnectionFactory.fecharConexao(con);
        }
    }

    public String deletarColaborador(int idColaborador) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();
        try{
            ColaboradorDAO colaboradorDAO = new ColaboradorDAO(con);
            resultado = colaboradorDAO.excluir(idColaborador);
            return resultado;
        }finally {
            ConnectionFactory.fecharConexao(con);
        }
    }


    public String listarUmColaborador(int idColaborador) throws ClassNotFoundException, SQLException {
        String resultado;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Connection con = ConnectionFactory.abrirConexao();
        try{
            ColaboradorDAO colaboradorDAO = new ColaboradorDAO(con);
            ColaboradorDTO colaboradorEncontrado = colaboradorDAO.listarUm(idColaborador);

            if (colaboradorEncontrado == null) {
                return "Não existe colaborador com esse id";
            }
            resultado = String.format("Id: %s \nNome Completo: %s \nData de Nascimento: %s \nCPF: %s \nTelefone: %s \nE-mail: %s\nUnidade: %s", colaboradorEncontrado.getIdColaborador(), colaboradorEncontrado.getNomeCompleto(), colaboradorEncontrado.getDataNascimento().format(dtf), colaboradorEncontrado.getCpf(), colaboradorEncontrado.getTelefone(), colaboradorEncontrado.getEmail(), colaboradorEncontrado.getUnidade());
            return resultado;

        }finally {
            ConnectionFactory.fecharConexao(con);
        }
    }

    // todo enviar lembrete atendimento

    // todo criar atendimento

    // todo (maybe) alterar atendimentos
}
