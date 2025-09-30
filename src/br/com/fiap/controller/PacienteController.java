package br.com.fiap.controller;

import br.com.fiap.exceptions.PacienteException;
import br.com.fiap.model.dao.PacienteDAO;
import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dto.AcompanhanteDTO;
import br.com.fiap.model.dto.PacienteDTO;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PacienteController {
    public String inserirPaciente(String nomeCompleto, String cpf,LocalDate dataNascimento, String email, String telefone, String diagnostico) throws ClassNotFoundException, SQLException {
        String resultado;

        PacienteDTO paciente = new PacienteDTO();
        paciente.setNomeCompleto(nomeCompleto);
        paciente.setCpf(cpf);
        paciente.setDataNascimento(dataNascimento);
        paciente.setTelefone(telefone);
        paciente.setEmail(email);
        paciente.setDiagnostico(diagnostico);

        Connection con = ConnectionFactory.abrirConexao();
        PacienteDAO pacienteDAO = new PacienteDAO(con);
        resultado = pacienteDAO.inserir(paciente);
        ConnectionFactory.fecharConexao(con);
        return resultado;

    }


    public String alterarPaciente(String nomeCompleto, String email, String telefone, String diagnostico, int idPaciente) throws ClassNotFoundException, SQLException {
        String resultado;

        PacienteDTO paciente = new PacienteDTO();
        paciente.setNomeCompleto(nomeCompleto);
        paciente.setTelefone(telefone);
        paciente.setEmail(email);
        paciente.setDiagnostico(diagnostico);
        paciente.setIdPaciente(idPaciente);

        Connection con = ConnectionFactory.abrirConexao();
        PacienteDAO pacienteDAO = new PacienteDAO(con);
        resultado = pacienteDAO.alterar(paciente);
        ConnectionFactory.fecharConexao(con);
        return resultado;

    }

    public String excluirPaciente(int idPaciente) throws ClassNotFoundException, SQLException {
        String resultado;

        PacienteDTO paciente = new PacienteDTO();
        paciente.setIdPaciente(idPaciente);

        Connection con = ConnectionFactory.abrirConexao();
        PacienteDAO pacienteDAO = new PacienteDAO(con);
        resultado = pacienteDAO.excluir(paciente);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

    public String listarUmPaciente(int idPaciente) throws ClassNotFoundException, SQLException {
        String resultado = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        PacienteDTO paciente = new PacienteDTO();
        paciente.setIdPaciente(idPaciente);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            PacienteDAO pacienteDAO = new PacienteDAO(con);
            PacienteDTO pacienteEncontrado = pacienteDAO.listarUm(paciente);

            if (pacienteEncontrado == null) {
                throw new PacienteException("Paciente: Não existe um paciente com esse id");
            }

            resultado = String.format("Id: %s \nNome Completo: %s \nData de Nascimento: %s \nCPF: %s \nTelefone: %s \nE-mail: %s\nDiagnostico: %s", pacienteEncontrado.getIdPaciente(), pacienteEncontrado.getNomeCompleto(), pacienteEncontrado.getDataNascimento().format(dtf), pacienteEncontrado.getCpf(), pacienteEncontrado.getTelefone(), pacienteEncontrado.getEmail(), pacienteEncontrado.getDiagnostico());
        } catch (PacienteException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

    public String listarUmPacientePorAcompanhante(int idAcompanhante) throws ClassNotFoundException, SQLException {
        String resultado = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        AcompanhanteDTO acompanhante = new AcompanhanteDTO();
        acompanhante.setIdAcompanhante(idAcompanhante);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            PacienteDAO pacienteDAO = new PacienteDAO(con);
            PacienteDTO pacienteEncontrado = pacienteDAO.listarUmPorAcompanhante(acompanhante);

            if (pacienteEncontrado == null) {
                throw new PacienteException("Paciente: Não existe um paciente vinculado a esse acompanhante");
            }

            resultado = String.format("Id: %s \nNome Completo: %s \nData de Nascimento: %s \nCPF: %s \nTelefone: %s \nE-mail: %s\nDiagnostico: %s", pacienteEncontrado.getIdPaciente(), pacienteEncontrado.getNomeCompleto(), pacienteEncontrado.getDataNascimento().format(dtf), pacienteEncontrado.getCpf(), pacienteEncontrado.getTelefone(), pacienteEncontrado.getEmail(), pacienteEncontrado.getDiagnostico());
        } catch (PacienteException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }
}
