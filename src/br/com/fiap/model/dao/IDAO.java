package br.com.fiap.model.dao;

public interface IDAO {
    String inserir (Object object);
    String alterar (Object object);
    String excluir (Object object);
    String listarUm (Object object);
}