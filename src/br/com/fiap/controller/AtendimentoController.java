package br.com.fiap.controller;

import br.com.fiap.exceptions.*;
import br.com.fiap.model.dao.*;
import br.com.fiap.model.dao.AtendimentoDAO;
import br.com.fiap.model.dto.*;
import br.com.fiap.model.enums.StatusAtendimento;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AtendimentoController {
    public String inserirAtendimento(int idProfissional, int idPaciente, LocalDate data, LocalTime hora, String formato, String local) throws ClassNotFoundException, SQLException {
        String resultado;

        ProfissionalSaudeDTO profissional = new ProfissionalSaudeDTO();
        profissional.setIdProfissionalSaude(idProfissional);

        PacienteDTO paciente = new PacienteDTO();
        paciente.setIdPaciente(idPaciente);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            ProfissionalSaudeDAO profissionalDAO = new ProfissionalSaudeDAO(con);
            ProfissionalSaudeDTO profissionalEncontrado = profissionalDAO.listarUm(profissional);

            PacienteDAO pacienteDAO = new PacienteDAO(con);
            PacienteDTO pacienteEncontrado = pacienteDAO.listarUm(paciente);


            if(profissionalEncontrado == null){
                throw new ProfissionalSaudeException("Profissional: Não foi encontrado nenhum profissional com esse id.");
            }

            if (pacienteEncontrado == null){
                throw new PacienteException("Paciente: Não foi encontrado nenhum paciente com esse id");
            }
            if (data.isBefore(LocalDate.now())) {
                throw new AtendimentoException("Atendimento: Não é possível marcar um atendimento para hoje ou uma data passada");
            }

            AtendimentoDAO atendimentoDAO = new AtendimentoDAO(con);
            ArrayList<AtendimentoDTO> atendimentos = atendimentoDAO.listarTodosPorPaciente(paciente);
            for (AtendimentoDTO atendimento : atendimentos) {
                if(data.isEqual(atendimento.getData()) && hora.equals(atendimento.getHora()) && atendimento.getStatus().equals(StatusAtendimento.MARCADO)) {
                    throw new AtendimentoException("Atendimento: O paciente já possui um atendimento marcado nessa data/hora");}
            }

            AtendimentoDTO atendimento = new AtendimentoDTO();
            atendimento.setData(data);
            atendimento.setHora(hora);
            atendimento.setStatus(StatusAtendimento.MARCADO);
            atendimento.setLocal(local);
            atendimento.setProfissionalSaude(profissionalEncontrado);
            atendimento.setPaciente(pacienteEncontrado);
            atendimento.setFormatoAtendimento(formato);
            resultado = atendimentoDAO.inserir(atendimento);

        } catch (ProfissionalSaudeException | PacienteException | AtendimentoException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

    public String atualizarAtendimento(int idAtendimento, int idProfissional, LocalDate data, LocalTime hora, String formato, String local, StatusAtendimento status) throws ClassNotFoundException, SQLException {
        String resultado = "";

        AtendimentoDTO atendimento = new AtendimentoDTO();
        atendimento.setIdAtendimento(idAtendimento);

        ProfissionalSaudeDTO profissional = new ProfissionalSaudeDTO();
        profissional.setIdProfissionalSaude(idProfissional);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            ProfissionalSaudeDAO profissionalDAO = new ProfissionalSaudeDAO(con);
            ProfissionalSaudeDTO profissionalEncontrado = profissionalDAO.listarUm(profissional);

            if(profissionalEncontrado == null){
                throw new ProfissionalSaudeException("Profissional: O profissional não pode ser nulo.");
            }

            if (data.isBefore(LocalDate.now())) {
                throw new AtendimentoException("Atendimento: Não é possível marcar um atendimento para hoje ou uma data passada");
            }

            AtendimentoDAO atendimentoDAO = new AtendimentoDAO(con);
            AtendimentoDTO atendimentoEncontrado = atendimentoDAO.listarUm(atendimento);

            if (!atendimentoEncontrado.getStatus().equals(StatusAtendimento.MARCADO)) {
                throw new AtendimentoException("Atendimento: Só é possível alterar um atendimento que está com o status de Marcado.");
            }

            atendimentoEncontrado.setData(data);
            atendimentoEncontrado.setHora(hora);
            atendimentoEncontrado.setStatus(status);
            atendimentoEncontrado.setLocal(local);
            atendimentoEncontrado.setProfissionalSaude(profissionalEncontrado);
            atendimentoEncontrado.setFormatoAtendimento(formato);
            resultado = atendimentoDAO.alterar(atendimentoEncontrado);

        } catch (ProfissionalSaudeException | AtendimentoException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

    public String deletarAtendimento(int idAtendimento) throws ClassNotFoundException, SQLException {
        String resultado = "";

        AtendimentoDTO atendimento = new AtendimentoDTO();
        atendimento.setIdAtendimento(idAtendimento);

        Connection con = ConnectionFactory.abrirConexao();
        AtendimentoDAO atendimentoDAO = new AtendimentoDAO(con);
        resultado = atendimentoDAO.excluir(atendimento);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }


    public String listarUmAtendimento(int idAtendimento) throws ClassNotFoundException, SQLException {
        String resultado = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        AtendimentoDTO atendimento = new AtendimentoDTO();
        atendimento.setIdAtendimento(idAtendimento);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            AtendimentoDAO atendimentoDAO = new AtendimentoDAO(con);
            AtendimentoDTO atendimentoEncontrado = atendimentoDAO.listarUm(atendimento);
            if (atendimentoEncontrado == null) {
                throw new AtendimentoException("Atendimento: Não existe atendimento com esse id");
            }

            LembreteDAO lembreteDAO = new LembreteDAO(con);
            ArrayList<LembreteDTO> lembretes = lembreteDAO.listarTodosPorAtendimento(atendimento);

            resultado = String.format("Id: %s \nEspecialidade: %s\nPaciente: %s \nProfissional: %s \nData: %s \nHora: %s \nFormato: %s \nStatus: %s \nLembretes: %s", atendimentoEncontrado.getIdAtendimento(), atendimentoEncontrado.getProfissionalSaude().getEspecialidade(), atendimentoEncontrado.getPaciente().getNomeCompleto(), atendimentoEncontrado.getProfissionalSaude().getNomeCompleto(), atendimentoEncontrado.getData().format(dtf), atendimentoEncontrado.getHora(), atendimentoEncontrado.getFormatoAtendimento(), atendimentoEncontrado.getStatus().name(), lembretes.size());
        } catch (AtendimentoException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

    public String listarTodosPorPaciente(int idPaciente) throws ClassNotFoundException, SQLException {
        String resultado = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        PacienteDTO paciente = new PacienteDTO();
        paciente.setIdPaciente(idPaciente);

        Connection con = ConnectionFactory.abrirConexao();
        try {
            AtendimentoDAO atendimentoDAO = new AtendimentoDAO(con);
            ArrayList<AtendimentoDTO> atendimentos = atendimentoDAO.listarTodosPorPaciente(paciente);
            if (atendimentos == null || atendimentos.isEmpty()) {
                throw new AtendimentoException("Não existe nenhum atendimento para esse paciente");
            }

            PacienteDAO pacienteDAO = new PacienteDAO(con);
            PacienteDTO pacienteEncontrado = pacienteDAO.listarUm(paciente);

            if (pacienteEncontrado == null ) {
                throw new PacienteException("Não existe nenhum paciente com o id informado");
            }

            for(AtendimentoDTO atendimentoEncontrado : atendimentos) {
                resultado += String.format("Id: %s \nPaciente: %s \nProfissional: %s \nData: %s \nHora: %s \nFormato: %s \nStatus: %s\n", atendimentoEncontrado.getIdAtendimento(), pacienteEncontrado.getNomeCompleto(), atendimentoEncontrado.getProfissionalSaude().getNomeCompleto(), atendimentoEncontrado.getData().format(dtf), atendimentoEncontrado.getHora(), atendimentoEncontrado.getFormatoAtendimento(), atendimentoEncontrado.getStatus().name());
            }
        } catch (AtendimentoException | PacienteException e) {
            return "Erro " + e.getMessage();
        } finally {
            ConnectionFactory.fecharConexao(con);
        }
        return resultado;
    }

}
