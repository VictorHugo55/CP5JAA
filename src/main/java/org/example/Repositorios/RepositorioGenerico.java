package org.example.Repositorios;

import java.util.ArrayList;

public interface RepositorioGenerico <T>{

    void adicionar(T entidade);
    ArrayList<T> exibir();
    void editar(T entidade);
    void excluir(int id);

    T buscarPorId(int id);
}
