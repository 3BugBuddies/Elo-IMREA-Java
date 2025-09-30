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

public class AcompanhanteController {
    public String inserirAcompanhante(String nomeCompleto, String cpf, LocalDate dataNascimento, String email, String telefone, String parentesco, int idPaciente) throws ClassNotFoundException,SQLException {
        String resultado = "";

        LocalDate dataLimiteNascimento = LocalDate.now().minusYears(18);

        Connection con = ConnectionFactory.abrirConexao();
        try{
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
        } catch (AcompanhanteException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }

        return resultado;
    }

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
                registroAcompanhante += String.format("\nId: %s \nNome Completo: %s \nData de Nascimento: %s \nCPF: %s \nTelefone: %s \nE-mail: %s \nPaciente Associado: %s \nParentesco: %s\n", acompanhante.getIdAcompanhante(), acompanhante.getNomeCompleto(), acompanhante.getDataNascimento().format(dtf), acompanhante.getCpf(), acompanhante.getTelefone(), acompanhante.getEmail(),pacienteEncontrado.getNomeCompleto(), acompanhante.getParentesco());

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
