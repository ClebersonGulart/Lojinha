package org.example.Dao;

import org.example.Domain.Pessoa;
import org.example.Interfaces.Crud;

import java.util.ArrayList;
import java.util.List;

public class PessoaDao implements Crud<Pessoa> {
    private final List<Pessoa> listaPessoas = new ArrayList<>();

    @Override
    public void salvar(Pessoa pessoa) {
        listaPessoas.add(pessoa);
    }

    @Override
    public List<Pessoa> listar() {
        return listaPessoas;
    }

    @Override
    public void atualizar(int indice, Pessoa pessoa) {
        listaPessoas.set(indice, pessoa);
    }

    @Override
    public Pessoa buscar(int indice) {
        return listaPessoas.get(indice);
    }

    @Override
    public void excluir(int indice) {
        listaPessoas.remove(indice);
    }
}
