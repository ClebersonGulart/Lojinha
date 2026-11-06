package org.example.Interfaces;

import java.util.List;

public interface Crud<T> {
    void salvar(T objeto);

    List<T> listar();

    void atualizar(int indice, T objeto);

    T buscar(int indice);

    void excluir(int indice);
}
