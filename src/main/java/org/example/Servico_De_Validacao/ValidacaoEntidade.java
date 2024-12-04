package org.example.Servico_De_Validacao;

import org.example.Entidades.Album;
import org.example.Entidades.Artista;
import org.example.Entidades.Musica;

public class ValidacaoEntidade {

    public static boolean vaidacaoArtista(Artista artista){
        return artista.getNome() != null && !artista.getNome().isEmpty()
                && artista.getGeneroMusical() != null && !artista.getGeneroMusical().isEmpty();
    }

    public static boolean vaidacaoAlbum(Album album){
        return album.getTitulo() != null && !album.getTitulo().isEmpty()
                && album.getAnoLancamento() >0 && album.getArtista() != null;
    }

    public static boolean validacaoMusica(Musica musica){
        return musica.getTitulo() != null && !musica.getTitulo().isEmpty()
                && musica.getDuracao() > 0 && musica.getAlbum() != null;
    }
}
