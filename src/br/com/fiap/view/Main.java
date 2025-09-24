package br.com.fiap.view;

import br.com.fiap.controller.AcompanhanteController;
import br.com.fiap.controller.ColaboradorController;
import br.com.fiap.controller.PacienteController;

import java.sql.SQLException;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        ColaboradorController colaboradorController = new ColaboradorController();
        System.out.println(colaboradorController.deletarColaborador(1)
);


    }
}
