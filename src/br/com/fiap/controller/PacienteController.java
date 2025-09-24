package br.com.fiap.controller;

import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dao.PacienteDAO;
import br.com.fiap.model.dao.ProfissionalSaudeDAO;
import br.com.fiap.model.dto.PacienteDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class PacienteController {
    public String inserirPaciente(String nomeCompleto, String cpf,LocalDate dataNascimento, String email, String telefone, String diagnostico) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        PacienteDTO paciente = new PacienteDTO();
        paciente.setNomeCompleto(nomeCompleto);
        paciente.setCpf(cpf);
        paciente.setDataNascimento(dataNascimento);
        paciente.setTelefone(telefone);
        paciente.setEmail(email);
        paciente.setDiagnostico(diagnostico);

        PacienteDAO pacienteDAO = new PacienteDAO(con);
        resultado = pacienteDAO.inserir(paciente);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }
}
