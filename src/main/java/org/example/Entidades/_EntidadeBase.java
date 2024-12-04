package org.example.Entidades;

public abstract class _EntidadeBase {
    public int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "_EntidadeBase{" +
                "id=" + id +
                '}';
    }
}
