package br.com.fiap.old;

import br.com.fiap.model.dto.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int opcao;
        String escolha = "sim";
        RecepcaoDTO colaborador;
        PacienteDTO paciente;
        Medico medico;
        Fisioterapeuta fisioterapeuta;
        Psicologo psicologo;
        AtendimentoDTO consultaTriagem, sessaoFisio, sessaoPsico;
        ProntuarioDTO prontuarioPaciente;
        AcompanhanteDTO acompanhanteDTO;


        Scanner sc = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate formataData;

        // criação do colaborador administrativo
        colaborador = new RecepcaoDTO("Joana Gomes", LocalDate.of(1990, 9, 10), "321.654.987-00", "(11) 98765-4321", "joana.gomes@hc.fm.usp.br", "Recepção");

        //criação do paciente base
        paciente = new PacienteDTO("André Guimarães", LocalDate.of(1990, 1, 1), "987.654.321-11", "(11)95555-5555", "andre@email.com");

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
        prontuarioPaciente = new ProntuarioDTO("Fratura no fêmur", "Recuperar mobilidade total", "Não avaliado", "Analgesico e anti-inflamatório", "Nenhuma alergia conhecida", "Uso de andador", "Encaminhamento para Fisioterapeuta");
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
                        acompanhanteDTO = new AcompanhanteDTO();
                        System.out.println("\n============== Acompanhante ==============");


                        System.out.println("\nDigite o nome completo do acompanhante: ");
                        acompanhanteDTO.setNomeCompleto(sc.nextLine());


                        System.out.println("Digite a data de nascimento do acompanhante: ");
                        formataData = LocalDate.parse(sc.nextLine(), dtf);
                        acompanhanteDTO.setDataNascimento(formataData);

                        System.out.println("Digite o CPF do acompanhante: ");
                        acompanhanteDTO.setCpf(sc.nextLine());

                        System.out.println("Digite o telefone do acompanhante: ");
                        acompanhanteDTO.setTelefone(sc.nextLine());

                        System.out.println("Digite o email do acompanhante: ");
                        acompanhanteDTO.setEmail(sc.nextLine());

                        System.out.println("Qual a relação de parentesco do acompanhante com você?");
                        acompanhanteDTO.setParentesco(sc.nextLine());

                        paciente.setAcompanhante(acompanhanteDTO);

                        System.out.printf("Acompanhante cadastrado com sucesso! \nDados do Acompanhante: \nNome: %s \nParentesco: %s \nData de Nascimento: %s \nCPF: %s \nTelefone: %s \nEmail: %s", acompanhanteDTO.getNomeCompleto(), acompanhanteDTO.getParentesco(), acompanhanteDTO.getDataNascimento().format(dtf), acompanhanteDTO.getCpf(), acompanhanteDTO.getTelefone(), acompanhanteDTO.getEmail());
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
