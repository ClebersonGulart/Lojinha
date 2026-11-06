package org.example.Dao;

import org.example.Domain.Produto;
import org.example.Interfaces.Crud;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDao implements Crud<Produto> {
    private final List<Produto> produtos = new ArrayList<>();

    @Override
    public void salvar(Produto produto) {
        produtos.add(produto);
    }

    @Override
    public List<Produto> listar() {
        return produtos;
    }

    @Override
    public void atualizar(int indice, Produto produto) {
        produtos.set(indice, produto);
    }

    @Override
    public Produto buscar(int indice) {
        return produtos.get(indice);
    }

    @Override
    public void excluir(int indice) {
        produtos.remove(indice);
    }
}
