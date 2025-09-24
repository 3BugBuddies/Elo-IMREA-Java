package br.com.fiap.controller;

import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dao.ProfissionalSaudeDAO;
import br.com.fiap.model.dto.ProfissionalSaudeDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class ProfissionalSaudeController {
    public String inserirProfissional(String nomeCompleto, String cpf,LocalDate dataNascimento, String email, String telefone, String tipoDocumento, String documento, String especialidade) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        ProfissionalSaudeDTO profissional = new ProfissionalSaudeDTO();
        profissional.setNomeCompleto(nomeCompleto);
        profissional.setCpf(cpf);
        profissional.setDataNascimento(dataNascimento);
        profissional.setTelefone(telefone);
        profissional.setEmail(email);
        profissional.setTipoDocumento(tipoDocumento);
        profissional.setDocumento(documento);
        profissional.setEspecialidade(especialidade);

        ProfissionalSaudeDAO profissionalSaudeDAO = new ProfissionalSaudeDAO(con);
        resultado = profissionalSaudeDAO.inserir(profissional);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

    public String listarUmProfissionalSaude(int id) throws ClassNotFoundException, SQLException{
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        ProfissionalSaudeDTO profissional = new ProfissionalSaudeDTO();
        profissional.setIdProfissionalSaude(id);

        ProfissionalSaudeDAO profissionalSaudeDAO = new ProfissionalSaudeDAO(con);
        resultado = profissionalSaudeDAO.listarUm(profissional);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

    



}
