package br.com.fiap.main;

import br.com.fiap.bean.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int opcao;
        String escolha = "sim";
        ColaboradorAdministrativo colaborador;
        Paciente paciente;
        Medico medico;
        Fisioterapeuta fisioterapeuta;
        Psicologo psicologo;
        Atendimento consultaTriagem, sessaoFisio, sessaoPsico;
        Prontuario prontuarioPaciente;
        Acompanhante acompanhante;


        Scanner sc = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate formataData;

        // criação do colaborador administrativo
        colaborador = new ColaboradorAdministrativo("Joana Gomes", LocalDate.of(1990, 9, 10), "321.654.987-00", "(11) 98765-4321", "joana.gomes@hc.fm.usp.br", "Recepção");

        //criação do paciente base
        paciente = new Paciente("André Guimarães", LocalDate.of(1990, 1, 1), "987.654.321-11", "(11)95555-5555", "andre@email.com");

        // criação de profissionais da saúde
        medico = new Medico("Maria Oliveira", LocalDate.of(1980, 3, 22), "987.654.321-00", "(11) 99876-5432", "maria.oliveira@hc.fm.usp.br", "Fisiatra", "CRM123456");

        fisioterapeuta = new Fisioterapeuta("Carlos Maurício Silva", LocalDate.of(1995, 6, 19), "454.235.321-00", "(11) 98888-5432", "carlos.silva@hc.fm.usp.br", "Traumato-Ortopedia", "CREFITO-3 10002-F");
        psicologo = new Psicologo("Joana Marques", LocalDate.of(1995, 6, 19), "123.453.687-00", "(11) 948323-5432", "joana.marques@hc.fm.usp.br", "Psicoterapia Cognitivo-Comportamental", "CRP 06/123456");

        //primeira sessão de triagem com o psicologo
        sessaoPsico = colaborador.criarAtendimento("Sessão - Triagem", paciente, psicologo, LocalDate.of(2025, 5, 25),LocalTime.of(10,30), "linkconsulta.com.br");
        sessaoPsico.setStatus("Realizado");

        // primeira consulta de triagem com médico fisiatra
        consultaTriagem = colaborador.criarAtendimento("Consulta - Triagem", paciente, medico,LocalDate.of(2025, 6, 1), LocalTime.of(8,30),"Sala 3 - IMREA Vila Mariana - Rua Domingo de Soto, 100 - Jardim Vila Mariana");
        consultaTriagem.setStatus("Realizado");

        // Adicionando prontuário atualizado pós triagem
        prontuarioPaciente = new Prontuario("Fratura no fêmur", "Recuperar mobilidade total", "Não avaliado", "Analgesico e anti-inflamatório", "Nenhuma alergia conhecida", "Uso de andador", "Encaminhamento para Fisioterapeuta");
        paciente.setProntuario(prontuarioPaciente);

        sessaoFisio = colaborador.criarAtendimento("Sessão - Fisioterapia", paciente, fisioterapeuta,LocalDate.of(2025, 6, 10), LocalTime.of(8,30),"Sala 5 - IMREA Vila Mariana - Rua Domingo de Soto, 100 - Jardim Vila Mariana");
        paciente.setLembrete(colaborador.enviarLembrete(sessaoFisio));

        while (escolha.equalsIgnoreCase("sim")) {

            try {
                System.out.printf("\nSeja Bem-vindo ao Menu do paciente do ELO IMREA \nOlá, %s!\nComo paciente você deseja: \n1) Visualizar lembrete da próxima consulta \n2) Visualizar prontuário \n3) Adicionar acompanhante \n0) Sair\n", paciente.getNomeCompleto());
                System.out.print("Escolha: ");
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        paciente.visualizarLembreteProximoAtendimento();
                        break;
                    case 2:
                        paciente.visualizarProntuario();
                        break;
                    case 3:
                        acompanhante = new Acompanhante();
                        System.out.println("\n============== Acompanhante ==============");


                        System.out.println("\nDigite o nome completo do acompanhante: ");
                        acompanhante.setNomeCompleto(sc.nextLine());


                        System.out.println("Digite a data de nascimento do acompanhante: ");
                        formataData = LocalDate.parse(sc.nextLine(), dtf);
                        acompanhante.setDataNascimento(formataData);

                        System.out.println("Digite o CPF do acompanhante: ");
                        acompanhante.setCpf(sc.nextLine());

                        System.out.println("Digite o telefone do acompanhante: ");
                        acompanhante.setTelefone(sc.nextLine());

                        System.out.println("Digite o email do acompanhante: ");
                        acompanhante.setEmail(sc.nextLine());

                        System.out.println("Qual a relação de parentesco do acompanhante com você?");
                        acompanhante.setParentesco(sc.nextLine());

                        paciente.setAcompanhante(acompanhante);

                        System.out.printf("Acompanhante cadastrado com sucesso! \nDados do Acompanhante: \nNome: %s \nParentesco: %s \nData de Nascimento: %s \nCPF: %s \nTelefone: %s \nEmail: %s", acompanhante.getNomeCompleto(),acompanhante.getParentesco(), acompanhante.getDataNascimento().format(dtf), acompanhante.getCpf(), acompanhante.getTelefone(),acompanhante.getEmail());
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        escolha = "nao";
                        break;
                    default:
                        throw new Exception("Escolha inválida, tente novamente");
                }
                System.out.print("\n\nDeseja continuar? (sim/nao): ");
                escolha = sc.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Obrigado por utilizar o Elo IMREA");
    }

}
