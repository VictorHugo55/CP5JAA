package org.example.Repositorios;

import org.example.Entidades.Album;
import org.example.Entidades.Artista;
import org.example.InfraestruturaDB.DataBaseConfig;
import org.example.Log.Loggable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioAlbum implements  RepositorioGenerico<Album>, Loggable<Album> {
    private List<Album> albums = new ArrayList<>();

    RepositorioArtista repositorioArtista = new RepositorioArtista();

    @Override
    public void adicionar(Album album) {

        String sql = "INSERT INTO Album(titulo_album, ano_lancamento_album, artista_id) VALUES(?, ?, ?)";

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, album.getTitulo());
            stmt.setInt(2, album.getAnoLancamento());
            stmt.setInt(3, album.getArtista().getId());

            stmt.executeUpdate();
            logInfo("Álbum cadastrado com sucesso!");
        }catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao cadastrar álbum");
        }
    }

    @Override
    public ArrayList<Album> exibir() {
        String sql = "SELECT * FROM Album";
        ArrayList<Album> albuns = new ArrayList<>();

        try(Connection conn = DataBaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){

            while (rs.next()){
                Album album = new Album();
                album.setId(rs.getInt("id_album"));
                album.setTitulo(rs.getString("titulo_album"));
                album.setAnoLancamento(rs.getInt("ano_lacamento_album"));

                Artista artista = repositorioArtista.buscarPorId(rs.getInt("artista_id"));
                album.setArtista(artista);
                albuns.add(album);

            }
            return albuns;

        }catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao exibir album");
        }


        return albuns;
    }

    @Override
    public void editar(Album album) {
        String sql = "UPDATE Album SET titulo_album = ?, ano_lancamento_album = ?, artista_id = ? WHERE id_album = ?";

        try(Connection conn = DataBaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, album.getTitulo());
            stmt.setInt(2, album.getAnoLancamento());
            stmt.setInt(3, album.getArtista().getId());

            int rowsUpdated = stmt.executeUpdate();
            if(rowsUpdated > 0){
                logInfo("Album atualizado com sucesso!");
            }
        }catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao editar album");
        }
    }

    @Override
    public void excluir(int id_album) {
        String sql = "DELETE FROM Album WHERE id_album = ?";

        try(Connection conn = DataBaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){


            stmt.setInt(1, id_album);
            int rowsDeleted = stmt.executeUpdate();
            if(rowsDeleted > 0){
                logInfo("Album deletado com sucesso!");
            }
        }catch (SQLException e){
                e.printStackTrace();
                logInfo("Erro ao excluir album");
        }


    }

    @Override
    public Album buscarPorId(int id_album) {
        String sql = "SELECT id_album, titulo_album, ano_lancamento_album, id AS artista_id, nome AS artista_nome FROM Album JOIN Artista ON artista_id = id WHERE id_album = ?";
        Album album = null;

        try(Connection conn = DataBaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, id_album);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                album = new Album();
                album.setId(rs.getInt("id_album"));
                album.setTitulo(rs.getString("titulo_album"));
                album.setAnoLancamento(rs.getInt("ano_lancamento_album"));

                Artista artista = repositorioArtista.buscarPorId(rs.getInt("artista_id"));

                album.setArtista(artista);
                return album;

            }
        }catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao buscar id ");
        }

        return album;
    }

    public List<Album> buscarPorAno(int ano){
        List<Album> albuns = new ArrayList<>();

        String sql = "SELECT id_album, titulo_album FROM Album WHERE ano_lancamento_album = ?";

        try(Connection conn = DataBaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1,ano);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int idAlbum = rs.getInt("id_album");
                String tituloAlbum = rs.getString("titulo_album");

                Album album = new Album(idAlbum, tituloAlbum);
                albuns.add(album);
            }
        }catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao buscar por ano");
        }

        return albuns;
    }
}
