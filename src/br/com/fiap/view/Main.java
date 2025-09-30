package br.com.fiap.view;
import br.com.fiap.controller.*;

import br.com.fiap.model.enums.StatusAtendimento;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        int  opcao;
        String[] escolha = {"Paciente", "Colaborador"};

        do{
            try{
                opcao = JOptionPane.showOptionDialog(null, "Seja bem-vindo ao ELO IMREA\nSelecione abaixo o seu perfil para manipular", "Escolha um menu", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, escolha, escolha[0]);
                switch (opcao){
                    case 0:
                        menuPaciente();
                        break;
                    case 1:
                        menuColaborador();
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Escolha um menu válido para manipular");
                }
            }catch (Exception e){
                System.out.println("Erro: " + e.getMessage());
            }
        } while (JOptionPane.showConfirmDialog(null, "Deseja continuar?", "Atencão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0);
        JOptionPane.showMessageDialog(null, "Fim de programa");
    }

    private static void menuPaciente() {
        int opcao;
        String[] escolha = {"CRUD Paciente", "CRUD Acompanhante", "Visualizar último lembrete", };

        try {
            opcao = JOptionPane.showOptionDialog(null, "Seja Bem-vindo ao Menu do Paciente do IMREA\n Escolha um item para manipular", "Menu de Paciente", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, escolha, escolha[0]);

            switch (opcao) {
                case 0:
                    manipularPaciente();
                    break;
                case 1:
                    manipularAcompanhante();
                    break;
                case 2:
                    break;
                case 3:

                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Escolha incorreta!");

            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro de Conversão!\n" + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void menuColaborador() {
        int opcao, idAtendimento;

        LembreteController lembreteController = new LembreteController();
        String[] escolha = {"CRUD Colaborador", "CRUD Atendimento", "CRUD Profissional da Saúde", "CRUD Lembrete", "Enviar Lembretes Pendente"};

        try {
            opcao = JOptionPane.showOptionDialog(null, "Seja Bem-vindo ao Menu do Colaborador do IMREA\n Escolha um item para manipular", "Menu de Colaborador", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, escolha, escolha[0]);

            switch (opcao) {
                case 0:
                    manipularColaborador();
                    break;
                case 1:
                    manipularAtendimento();
                    break;
                case 2:
                    manipularProfissionalSaude();
                    break;
                case 3:
                    manipularLembrete();
                    break;
                case 4:
                    idAtendimento = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do atendimento que possui lembretes pendentes?"));
                    JOptionPane.showMessageDialog(null, lembreteController.enviarLembretesPendentes(idAtendimento));
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Escolha incorreta!");

            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro de Conversão!\n" + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void manipularProfissionalSaude() {
        int opcao, idProfissionalSaude;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String nome, email, telefone, cpf, documento, tipoDocumento, especialidade;
        LocalDate dataNascimento;

        ProfissionalSaudeController profissionalSaudeController = new ProfissionalSaudeController();

        String[] escolha = {"Criar", "Alterar", "Excluir", "Listar um"};

        try {
            opcao = JOptionPane.showOptionDialog(null, "Seja Bem-vindo ao CRUD dos Profissionais da Sáude do IMREA\n Escolha um item para manipular", "Menu de Profissional da Saúde", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, escolha, escolha[0]);

            switch (opcao) {
                case 0:
                    nome = JOptionPane.showInputDialog("Nome do Profissional: ");
                    cpf = JOptionPane.showInputDialog("CPF do Profissional: ");
                    dataNascimento = LocalDate.parse(JOptionPane.showInputDialog("Data de Nascimento: "), dtf);
                    email = JOptionPane.showInputDialog("Email do Profissional: ");
                    telefone = JOptionPane.showInputDialog("Telefone do Profissional: ");
                    especialidade = JOptionPane.showInputDialog("Qual a especialidade?");
                    tipoDocumento = JOptionPane.showInputDialog("Qual o tipo do documento (CREFITO, CRM, etc): ");
                    documento = JOptionPane.showInputDialog("Digite o documento: ");

                    JOptionPane.showMessageDialog(null, profissionalSaudeController.inserirProfissionalSaude(nome, cpf, dataNascimento, email, telefone, tipoDocumento, documento, especialidade));
                    break;
                case 1:
                    idProfissionalSaude = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do Profissional que deseja alterar?"));
                    nome = JOptionPane.showInputDialog("Nome do Profissional: ");
                    email = JOptionPane.showInputDialog("Email do Profissional: ");
                    telefone = JOptionPane.showInputDialog("Telefone do Profissional: ");
                    especialidade = JOptionPane.showInputDialog("Qual a especialidade?");
                    tipoDocumento = JOptionPane.showInputDialog("Qual o tipo do documento (CREFITO, CRM, etc): ");
                    documento = JOptionPane.showInputDialog("Digite o documento: ");

                    JOptionPane.showMessageDialog(null, profissionalSaudeController.atualizarProfissionalSaude(nome, email, telefone, tipoDocumento, documento, especialidade, idProfissionalSaude));

                    break;
                case 2:
                    idProfissionalSaude = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do Profissional que deseja Excluir?"));
                    JOptionPane.showMessageDialog(null, profissionalSaudeController.deletarProfissionalSaude(idProfissionalSaude));
                    break;
                case 3:
                    idProfissionalSaude = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do Profissional que deseja listar?"));
                    JOptionPane.showMessageDialog(null, profissionalSaudeController.listarUmProfissionalSaude(idProfissionalSaude) );
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Escolha incorreta!");

            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro de Conversão!\n" + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }

    }

    private static void manipularColaborador() {
        int opcao, idColaborador;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String nome, email, telefone, cpf, unidade;
        LocalDate dataNascimento;

        ColaboradorController colaboradorController = new ColaboradorController();

        String[] escolha = {"Criar", "Alterar", "Excluir", "Listar um"};

        try {
            opcao = JOptionPane.showOptionDialog(null, "Seja Bem-vindo ao Menu do Colaborador do IMREA\n Escolha um item para manipular", "Menu de Colaborador", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, escolha, escolha[0]);

            switch (opcao) {
                case 0:
                    nome = JOptionPane.showInputDialog("Nome do Colaborador: ");
                    cpf = JOptionPane.showInputDialog("CPF do Colaborador: ");
                    dataNascimento = LocalDate.parse(JOptionPane.showInputDialog("Data de Nascimento: "), dtf);
                    email = JOptionPane.showInputDialog("Email do Colaborador: ");
                    telefone = JOptionPane.showInputDialog("Telefone do Colaborador: ");
                    unidade = JOptionPane.showInputDialog("Unidade do Colaborador: ");

                    JOptionPane.showMessageDialog(null, colaboradorController.inserirColaborador(nome, cpf, dataNascimento, email, telefone, unidade));
                    break;
                case 1:
                    idColaborador = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do Colaborador que deseja alterar?"));
                    nome = JOptionPane.showInputDialog("Nome do Colaborador: ");
                    email = JOptionPane.showInputDialog("Email do Colaborador: ");
                    telefone = JOptionPane.showInputDialog("Telefone do Colaborador: ");
                    unidade = JOptionPane.showInputDialog("Unidade do Colaborador: ");

                    JOptionPane.showMessageDialog(null, colaboradorController.atualizarColaborador(nome, email, telefone, unidade, idColaborador));

                    break;
                case 2:
                    idColaborador = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do Colaborador que deseja Excluir?"));
                    JOptionPane.showMessageDialog(null, colaboradorController.deletarColaborador(idColaborador));
                    break;
                case 3:
                    idColaborador = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do Colaborador que deseja listar?"));
                    JOptionPane.showMessageDialog(null, colaboradorController.listarUmColaborador(idColaborador) );
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Escolha incorreta!");

            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro de Conversão!\n" + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }

    }

    private static void manipularPaciente() {
        int opcao, idPaciente;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String nome, email, telefone, cpf, diagnostico;
        LocalDate dataNascimento;

        PacienteController pacienteController = new PacienteController();

        String[] escolha = {"Criar", "Alterar", "Excluir", "Listar um"};

        try {
            opcao = JOptionPane.showOptionDialog(null, "Seja Bem-vindo ao Menu do Paciente do IMREA\n Escolha um item para manipular", "Menu de Paciente", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, escolha, escolha[0]);

            switch (opcao) {
                case 0:
                    nome = JOptionPane.showInputDialog("Nome do Paciente: ");
                    cpf = JOptionPane.showInputDialog("CPF do Paciente: ");
                    dataNascimento = LocalDate.parse(JOptionPane.showInputDialog("Data de Nascimento: "), dtf);
                    email = JOptionPane.showInputDialog("Email do Paciente: ");
                    telefone = JOptionPane.showInputDialog("Telefone do Paciente: ");
                    diagnostico = JOptionPane.showInputDialog("Diagnóstico do Paciente: ");

                    JOptionPane.showMessageDialog(null, pacienteController.inserirPaciente(nome, cpf, dataNascimento, email, telefone, diagnostico));
                    break;
                case 1:
                    idPaciente = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do Paciente que deseja alterar?"));
                    nome = JOptionPane.showInputDialog("Nome do Paciente: ");
                    email = JOptionPane.showInputDialog("Email do Paciente: ");
                    telefone = JOptionPane.showInputDialog("Telefone do Paciente: ");
                    diagnostico = JOptionPane.showInputDialog("Diagnóstico do Paciente: ");

                    JOptionPane.showMessageDialog(null, pacienteController.alterarPaciente(nome, email, telefone, diagnostico, idPaciente));

                    break;
                case 2:
                    idPaciente = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do Paciente que deseja Excluir?"));
                    JOptionPane.showMessageDialog(null, pacienteController.excluirPaciente(idPaciente));
                    break;
                case 3:
                    idPaciente = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do Paciente que deseja listar?"));
                    JOptionPane.showMessageDialog(null, pacienteController.listarUmPaciente(idPaciente) );
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Escolha incorreta!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro de Conversão!\n" + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }

    }

    private static void manipularAcompanhante() {
        int opcao, idAcompanhante, idPaciente;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String nome, email, telefone, cpf, parentesco;
        LocalDate dataNascimento;

        AcompanhanteController acompanhanteController = new AcompanhanteController();

        String[] escolha = {"Criar", "Alterar", "Excluir", "Listar um","Listar Todos por Paciente"};

        try {
            opcao = JOptionPane.showOptionDialog(null, "Seja Bem-vindo ao Menu do Acompanhante\n Escolha um item para manipular", "Menu de Acompanhante", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, escolha, escolha[0]);

            switch (opcao) {
                case 0:
                    idPaciente = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do paciente que esse acompanhante está relacionado?"));
                    parentesco = JOptionPane.showInputDialog("Parentesco do Acompanhante: ");
                    nome = JOptionPane.showInputDialog("Nome do Acompanhante: ");
                    cpf = JOptionPane.showInputDialog("CPF do Acompanhante: ");
                    dataNascimento = LocalDate.parse(JOptionPane.showInputDialog("Data de Nascimento: "), dtf);
                    email = JOptionPane.showInputDialog("Email do Acompanhante: ");
                    telefone = JOptionPane.showInputDialog("Telefone do Acompanhante: ");

                    JOptionPane.showMessageDialog(null, acompanhanteController.inserirAcompanhante(nome, cpf, dataNascimento, email, telefone, parentesco, idPaciente));
                    break;
                case 1:
                    idAcompanhante = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do Acompanhante que deseja alterar?"));
                    nome = JOptionPane.showInputDialog("Nome do Acompanhante: ");
                    email = JOptionPane.showInputDialog("Email do Acompanhante: ");
                    telefone = JOptionPane.showInputDialog("Telefone do Acompanhante: ");
                    parentesco = JOptionPane.showInputDialog("Parentesco do Acompanhante: ");

                    JOptionPane.showMessageDialog(null, acompanhanteController.atualizarAcompanhante(nome, email, telefone, parentesco, idAcompanhante));

                    break;
                case 2:
                    idAcompanhante = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do Acompanhante que deseja Excluir?"));
                    JOptionPane.showMessageDialog(null, acompanhanteController.deletarAcompanhante(idAcompanhante));
                    break;
                case 3:
                    idAcompanhante = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do Acompanhante que deseja listar?"));
                    JOptionPane.showMessageDialog(null, acompanhanteController.listarUmAcompanhante(idAcompanhante) );
                    break;
                case 4:
                    idPaciente = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do Paciente que você deseja listar os acompanhantes vinculados?"));
                    JOptionPane.showMessageDialog(null, acompanhanteController.listarTodosPorPaciente(idPaciente));
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Escolha incorreta!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro de Conversão!\n" + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }

    }


    private static void manipularLembrete(){
        int opcao, idAtendimento, idColaborador, idLembrete;
        LembreteController lembreteController = new LembreteController();

        String[] escolha = {"Criar", "Alterar", "Remover", "Listar um"};

        try {
            opcao = JOptionPane.showOptionDialog(null, "Seja Bem-vindo ao Menu de Lembretes do IMREA\n Escolha um item para manipular", "Menu de Lembretes", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, escolha, escolha[0]);

            switch (opcao) {
                case 0:
                    idColaborador = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do colaborador que está gerando o lembrete?"));
                    idAtendimento = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do atendimento que deseja enviar um lembrete?"));

                    JOptionPane.showMessageDialog(null, lembreteController.inserirLembrete(idColaborador, idAtendimento), "Criação de lembrete", JOptionPane.INFORMATION_MESSAGE);

                    break;
                case 1:
                    idColaborador = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do colaborador que está reenviando o lembrete?"));
                    idAtendimento = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do atendimento alterado que deseja reenviar um lembrete?"));
                    idLembrete = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do lembrete que deseja alterar?"));

                    JOptionPane.showMessageDialog(null, lembreteController.atualizarLembrete(idColaborador,idLembrete, idAtendimento), "Alteração de lembrete", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 2:
                    idLembrete = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do lembrete que deseja deletar?"));
                    JOptionPane.showMessageDialog(null,  lembreteController.deletarLembrete(idLembrete), "Deleção de lembrete", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 3:
                    idLembrete = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do lembrete que deseja listar?"));
                    JOptionPane.showMessageDialog(null, lembreteController.deletarLembrete(idLembrete), "Listagem de lembrete", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Escolha incorreta!");

            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro de Conversão!\n" + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }


    private static void manipularAtendimento() {
        int opcao, idAtendimento, idPaciente, idProfissionalSaude;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String formatoAtendimento, localAtendimento;
        LocalTime horaAtendimento;
        LocalDate dataAtendimento;
        AtendimentoController atendimentoController = new AtendimentoController();

        String[] escolha = {"Criar", "Alterar", "Remover", "Listar um"};

        try {
            opcao = JOptionPane.showOptionDialog(null, "Seja Bem-vindo ao Menu de Atendimento do IMREA\n Escolha um item para manipular", "Menu de Atendimento", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, escolha, escolha[0]);

            switch (opcao) {
                case 0:
                    idProfissionalSaude = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do profissional que atenderá a consulta?"));
                    idPaciente = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do paciente que ira se consultar?"));

                    dataAtendimento = LocalDate.parse(JOptionPane.showInputDialog("Qual a data que será realizada a consulta? "), dtf);
                    horaAtendimento = LocalTime.parse(JOptionPane.showInputDialog("Qual o horário da consulta (HH:mm): "), timeFormatter);
                    formatoAtendimento = String.valueOf(JOptionPane.showInputDialog("Qual o formato de atendimento? (Remoto ou Presencial)"));
                    localAtendimento = JOptionPane.showInputDialog(null, "Onde será realizado o atendimento (local ou link):");

                    JOptionPane.showMessageDialog(null, atendimentoController.inserirAtendimento(idProfissionalSaude, idPaciente, dataAtendimento, horaAtendimento, formatoAtendimento, localAtendimento));

                    break;
                case 1:
                    idAtendimento = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do atendimento que deseja alterar?"));
                    idProfissionalSaude = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do profissional que atenderá a consulta?"));

                    dataAtendimento = LocalDate.parse(JOptionPane.showInputDialog("Qual a nova data o atendimento que será realizado? "), dtf);
                    horaAtendimento = LocalTime.parse(JOptionPane.showInputDialog("Qual o novo horário do atendimento (HH:mm): "), timeFormatter);
                    formatoAtendimento = JOptionPane.showInputDialog("Qual o novo formato de atendimento? (Remoto ou Presencial)");
                    localAtendimento = JOptionPane.showInputDialog(null, "Onde será realizado o atendimento (local ou link):");

                    JOptionPane.showMessageDialog(null, atendimentoController.atualizarAtendimento(idAtendimento,idProfissionalSaude, dataAtendimento, horaAtendimento, formatoAtendimento, localAtendimento, StatusAtendimento.REMARCADO),"Atualização de atendimento",JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 2:
                    idAtendimento = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do atendimento que deseja remover?"));
                    JOptionPane.showMessageDialog(null,  atendimentoController.deletarAtendimento(idAtendimento), "Deleção de atendimento", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 3:
                    idAtendimento = Integer.parseInt(JOptionPane.showInputDialog("Qual o id do atendimento que deseja listar?"));
                    JOptionPane.showMessageDialog(null, atendimentoController.listarUmAtendimento(idAtendimento), "Listagem de atendimento", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Escolha incorreta!");

            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro de Conversão!\n" + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }


}
