package br.com.fiap.bean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que representa um colaborador Administrativo responsável por agendamentos e envio de lembretes.
 * @version 1.0
 */

public class ColaboradorAdministrativo {
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private String cpf;
    private String telefone;
    private String emailInstitucional;
    private String departamento;

    public ColaboradorAdministrativo() {
    }

    public ColaboradorAdministrativo(String nomeCompleto, LocalDate dataNascimento, String cpf, String telefone, String emailInstitucional, String departamento) {
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.telefone = telefone;
        this.emailInstitucional = emailInstitucional;
        this.departamento = departamento;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmailInstitucional() {
        return emailInstitucional;
    }

    public void setEmailInstitucional(String emailInstitucional) {
        this.emailInstitucional = emailInstitucional;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * Metodo que cria um novo atendimento se data for válida
     */
    public Atendimento criarAtendimento(String tipoAtendimento,Paciente paciente, ProfissionalSaude profissionalSaude, LocalDate data, LocalTime hora, String local){

    try{
        if(data.isAfter(LocalDate.now())){
            Atendimento atendimento = new Atendimento();

            atendimento.setTipoAtendimento(tipoAtendimento);
            atendimento.setProfissionalSaude(profissionalSaude);
            atendimento.setPaciente(paciente);
            atendimento.setData(data);
            atendimento.setHora(hora);
            atendimento.setLocal(local);
            atendimento.setStatus("Agendado");
            return atendimento;

        } else{
            throw new Exception("Atendimentos não podem ser marcados para uma data passada");
        }
    } catch (Exception e) {
    System.out.println(e.getMessage());
    }
       return null;
    }

    /**
     * Metodo que envia um lembrete para o paciente caso o atendimento esteja agendado
     */
    public Lembrete enviarLembrete(Atendimento atendimento){
        Lembrete lembrete = new Lembrete();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try{
            if(atendimento.getStatus()!= null && atendimento.getStatus().equalsIgnoreCase("Agendado")){
                lembrete.setAtendimento(atendimento);
                lembrete.setAssunto(atendimento.getTipoAtendimento());

                String mensagem = String.format("\nOlá, %s!\n\nDados do seu próximo atendimento:\n%s com o profissional %s (%s) \nData/hora: %s às %s \nLocal/Link: %s", atendimento.getPaciente().getNomeCompleto(), atendimento.getTipoAtendimento(),atendimento.getProfissionalSaude().getNomeCompleto(), atendimento.getProfissionalSaude().getEspecialidade(),atendimento.getData().format(dtf),atendimento.getHora(),atendimento.getLocal());

                lembrete.setMensagem(mensagem);
                lembrete.setDataEnvio(LocalDate.now());
                lembrete.setStatus("Enviado");

            } else{
                lembrete.setStatus("Falhou");
                throw new Exception("Lembretes podem ser enviados apenas para atendimentos agendados.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lembrete;

    }
}
