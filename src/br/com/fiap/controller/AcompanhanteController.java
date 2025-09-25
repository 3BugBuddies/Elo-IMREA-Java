package br.com.fiap.controller;

import br.com.fiap.exceptions.DataNascimentoInvalida;
import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dao.AcompanhanteDAO;
import br.com.fiap.model.dao.PacienteDAO;
import br.com.fiap.model.dto.AcompanhanteDTO;
import br.com.fiap.model.dto.PacienteDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AcompanhanteController {
    public String inserirAcompanhante(String nomeCompleto, String cpf, LocalDate dataNascimento, String email, String telefone, String parentesco, int idPaciente) throws ClassNotFoundException, SQLException, DataNascimentoInvalida {
        String resultado;
        Connection con = ConnectionFactory.abrirConexao();

        LocalDate dataLimiteNascimento = LocalDate.now().minusYears(18);

        try{
            if(dataNascimento.isAfter(dataLimiteNascimento)){
                throw new DataNascimentoInvalida("O acompanhante deve ter pelo menos 18 anos");
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
            return resultado;
        } finally{
            ConnectionFactory.fecharConexao(con);
        }
    }

    public String atualizarAcompanhante(String nomeCompleto, String email, String telefone, String parentesco, int idAcompanhante) throws ClassNotFoundException, SQLException {
        String resultado;
        Connection con = ConnectionFactory.abrirConexao();
        try{
            AcompanhanteDTO acompanhante = new AcompanhanteDTO();
            acompanhante.setNomeCompleto(nomeCompleto);
            acompanhante.setTelefone(telefone.replace("(", "").replace("-", "").replace(")", "").replace(" ", ""));
            acompanhante.setEmail(email);
            acompanhante.setParentesco(parentesco);
            acompanhante.setIdAcompanhante(idAcompanhante);

            AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(con);
            resultado = acompanhanteDAO.alterar(acompanhante);
            return resultado;
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
    }

    public String deletarAcompanhante(int idAcompanhante) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();
        try{
            AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(con);
            resultado = acompanhanteDAO.excluir(idAcompanhante);
            return resultado;
        }finally {
            ConnectionFactory.fecharConexao(con);
        }
    }


    public String listarUmAcompanhante(int idAcompanhante) throws ClassNotFoundException, SQLException {
        String resultado;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Connection con = ConnectionFactory.abrirConexao();

        try{
            AcompanhanteDAO acompanhanteDAO = new AcompanhanteDAO(con);
            AcompanhanteDTO acompanhanteEncontrado = acompanhanteDAO.listarUm(idAcompanhante);

            if(acompanhanteEncontrado == null){
                return "Não existe acompanhante com esse id";
            }
            PacienteDAO pacienteDAO = new PacienteDAO(con);
            PacienteDTO paciente = pacienteDAO.listarUmPorAcompanhante(idAcompanhante);

            if(paciente == null){
                return "Não existe paciente ligado ao acompanhante";
            }

            resultado = String.format("Id: %s \nNome Completo: %s \nData de Nascimento: %s \nCPF: %s \nTelefone: %s \nE-mail: %s \nPaciente Associado: %s \nParentesco: %s", acompanhanteEncontrado.getIdAcompanhante(), acompanhanteEncontrado.getNomeCompleto(), acompanhanteEncontrado.getDataNascimento().format(dtf), acompanhanteEncontrado.getCpf(), acompanhanteEncontrado.getTelefone(), acompanhanteEncontrado.getEmail(),paciente.getNomeCompleto(), acompanhanteEncontrado.getParentesco());
            return resultado;
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
    }
}
