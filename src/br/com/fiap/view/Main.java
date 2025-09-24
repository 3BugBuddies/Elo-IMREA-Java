package br.com.fiap.view;

import br.com.fiap.controller.AcompanhanteController;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        AcompanhanteController ac = new AcompanhanteController();

        System.out.println(ac.listarUmAcompanhante(4));


    }
}
