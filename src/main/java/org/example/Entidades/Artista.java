package org.example.Entidades;

import java.util.ArrayList;
import java.util.List;

public class Artista extends _EntidadeBase {
    private String nome;
    private String generoMusical;

    private List<Album> albums = new ArrayList<>();

    public Artista(String nome, String generoMusical) {
        this.nome = nome;
        this.generoMusical = generoMusical;
    }

    public Artista() {
    }

    public Artista(int idArtista, String nomeArtista) {
        super();
        this.id = idArtista;
        this.nome = nomeArtista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public void addAlbum(Album album){
        albums.add(album);
    }

    @Override
    public String toString() {
        return "Artista{" +
                "nome='" + nome + '\'' +
                ", generoMusical='" + generoMusical + '\'' +
                ", albums=" + albums +
                ", id=" + id +
                '}';
    }
}
