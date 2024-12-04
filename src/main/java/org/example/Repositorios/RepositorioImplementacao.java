package org.example.Repositorios;

import org.example.Entidades._EntidadeBase;

import java.util.ArrayList;

public abstract class RepositorioImplementacao <T extends _EntidadeBase> implements RepositorioGenerico<T> {

    ArrayList<T> lista = new ArrayList<>();

    @Override
    public void adicionar(T entidade) {
        lista.add(entidade);
    }

    @Override
    public ArrayList<T> exibir() {
        return lista;
    }

    @Override
    public void editar(T entidade) {
        var entidadeAtualizada = lista.stream().filter(x -> x.getId() == entidade.getId()).findFirst().get();
        lista.remove(entidadeAtualizada);
        lista.add(entidade);
    }

    @Override
    public void excluir(int id) {
        lista.remove(id);
    }

    public RepositorioImplementacao(ArrayList<T> lista){
        this.lista = lista;
    }
}
