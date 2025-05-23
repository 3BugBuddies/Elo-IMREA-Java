package br.com.fiap.bean;

import java.time.LocalDate;

/**
 * Classe que representa um paciente no sistema, com dados pessoais, prontuário, acompanhante e
 * lembrete que tem dos atendimentos
 * @version 1.0
 */
public class Paciente {
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String cpf;
    private Acompanhante acompanhante;
    private Prontuario prontuario;
    private String telefone;
    private String email;
    private Lembrete lembrete;

    public Paciente() {
    }

    public Paciente(String nomeCompleto, LocalDate dataNascimento, String cpf, Acompanhante acompanhante, Prontuario prontuario, String telefone, String email, Lembrete lembrete) {
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.acompanhante = acompanhante;
        this.prontuario = prontuario;
        this.telefone = telefone;
        this.email = email;
        this.lembrete = lembrete;

    }

    public Paciente(String nomeCompleto, LocalDate dataNascimento, String cpf, String telefone, String email) {
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Acompanhante getAcompanhante() {
        return acompanhante;
    }

    public void setAcompanhante(Acompanhante acompanhante) {
        this.acompanhante = acompanhante;
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void setProntuario(Prontuario prontuario) {
        this.prontuario = prontuario;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Lembrete getLembrete() {
        return lembrete;
    }
    public void setLembrete(Lembrete lembrete) {
        this.lembrete = lembrete;
    }


    /**
     * Metodo para exibir o lembrete do próximo atendimento se ainda estiver pendente.
     * Caso o atendimento já tenha sido realizado ou cancelado, uma mensagem de erro será exibida.
     */
    public void visualizarLembreteProximoAtendimento() {
        try{
            if (lembrete.getAtendimento() != null && !lembrete.getAtendimento().getStatus().equalsIgnoreCase("Realizado") && !lembrete.getAtendimento().getStatus().equalsIgnoreCase("Cancelado") ){
                System.out.println(getLembrete().getMensagem());
            } else{
                throw new Exception("Não é possivel ver lembretes de consultas que já aconteceram ou foram canceladas");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Metodo que exibe os dados do prontuário médico do paciente.
     * Se não houver prontuário disponível, uma mensagem de erro será exibida.
     */
    public void visualizarProntuario(){
        try{
            if(prontuario != null){
                System.out.println("\n============== Prontuário do Paciente ==============");
                System.out.printf("Nome Completo: %s \nDiagnostico : %s  \nMeta: %s  \nEvolução: %s \nMedicações: %s \nAlergia: %s\nApoio para locomoção: %s\nFase atual do tratamento: %s\n", this.getNomeCompleto(),prontuario.getDiagnostico(), prontuario.getMeta(), prontuario.getEvolucao(), prontuario.getMedicacao(), prontuario.getAlergia(), prontuario.getApoioLocomocao(), prontuario.getFaseTratamento());
            }else{
                throw new Exception("Prontuário não disponível para este paciente.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
