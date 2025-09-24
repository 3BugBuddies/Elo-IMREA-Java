package br.com.fiap.controller;

import br.com.fiap.model.dao.LembreteDAO;
import br.com.fiap.model.dao.ConnectionFactory;
import br.com.fiap.model.dto.AtendimentoDTO;
import br.com.fiap.model.dto.LembreteDTO;
import br.com.fiap.model.dto.ProfissionalSaudeDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LembreteController {
    public String inserirLembrete(int idColaborador, AtendimentoDTO atendimentoDTO) throws ClassNotFoundException, SQLException {
        String resultado;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Connection con = ConnectionFactory.abrirConexao();

        ProfissionalSaudeDTO profissional = atendimentoDTO.getProfissionalSaude();



        String assunto = String.format("IMREA: Consulta com %s dia %s",profissional.getEspecialidade(),atendimentoDTO.getData().format(dtf) );

        String mensagem = String.format(
                    "Olá Sr(a) %s,\nEstamos passando para lembrar que você tem um atendimento %s com %s (%s). \nFormato:%s \nLocal/link:%s \nData: Dia %s às %s",
                atendimentoDTO.getPaciente().getNomeCompleto(),
                atendimentoDTO.getStatus(),
                profissional.getNomeCompleto(),
                profissional.getEspecialidade(),
                atendimentoDTO.getFormatoAtendimento(),
                atendimentoDTO.getLocal(),
                atendimentoDTO.getData().format(dtf),
                atendimentoDTO.getHora().format(dtf)
        );
        LembreteDTO lembrete = new LembreteDTO();
        lembrete.setAssunto(assunto);
        lembrete.setMensagem(mensagem);
        lembrete.setDataEnvio(LocalDate.now());
        lembrete.setIdColaborador(idColaborador);
        lembrete.setAtendimento(atendimentoDTO);

         List<LembreteDTO> lembretesPorAtendimento = atendimentoDTO.getLembrete();
         lembretesPorAtendimento.add(lembrete);

        LembreteDAO lembreteDAO = new LembreteDAO(con);
        resultado = lembreteDAO.inserir(lembrete);

        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

    public String atualizarLembrete(AtendimentoDTO atendimentoDTO, int idColaborador, int idLembrete) throws ClassNotFoundException, SQLException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        ProfissionalSaudeDTO profissional = atendimentoDTO.getProfissionalSaude();
        String assunto = String.format("IMREA: Consulta com %s dia %s\n",profissional.getEspecialidade(),atendimentoDTO.getData().format(dtf) );

        String mensagem = String.format(
                "Olá Sr(a) %s,\nEstamos passando para lembrar que você tem um atendimento %s com %s (%s). \nFormato:%s \nLocal/link:%s \nData: Dia %s às %s",
                atendimentoDTO.getPaciente().getNomeCompleto(),
                atendimentoDTO.getStatus(),
                profissional.getNomeCompleto(),
                profissional.getEspecialidade(),
                atendimentoDTO.getFormatoAtendimento(),
                atendimentoDTO.getLocal(),
                atendimentoDTO.getData().format(dtf),
                atendimentoDTO.getHora().format(dtf)
        );
        LembreteDTO lembrete = new LembreteDTO();
        lembrete.setAssunto(assunto);
        lembrete.setMensagem(mensagem);
        lembrete.setDataEnvio(LocalDate.now());
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
        resultado = lembreteDAO.excluir(idLembrete);
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }


    public String listarUmLembrete(int idLembrete) throws ClassNotFoundException, SQLException {
        String resultado;

        Connection con = ConnectionFactory.abrirConexao();

        LembreteDTO lembrete = new LembreteDTO();
        lembrete.setIdLembrete(idLembrete);

        LembreteDAO lembreteDAO = new LembreteDAO(con);
        LembreteDTO dto = lembreteDAO.listarUm(idLembrete);

        if(dto != null){
            resultado = String.format("Assunto:%s \nMensagem:%s", dto.getAssunto(), dto.getMensagem());
        }else{
            resultado = "Não foi encontrado um lembrete associado a esse id";
        }
        ConnectionFactory.fecharConexao(con);
        return resultado;
    }

}
