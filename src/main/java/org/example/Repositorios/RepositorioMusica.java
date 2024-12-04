package org.example.Repositorios;

import org.example.Entidades.Album;
import org.example.Entidades.Musica;
import org.example.InfraestruturaDB.DataBaseConfig;
import org.example.Log.Loggable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioMusica implements RepositorioGenerico<Musica>, Loggable<Musica> {
    private List<Musica> musicas = new ArrayList<>();
    private RepositorioAlbum repositorioAlbum = new RepositorioAlbum();

    public RepositorioMusica(){
        this.musicas = new ArrayList<>();
    }

    @Override
    public void adicionar(Musica musica) {
        String sql = "INSERT INTO Musica (titulo_musica, duracao, album_id) VALUES (?, ?, ?) ";

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, musica.getTitulo());
            stmt.setDouble(2, musica.getDuracao());
            stmt.setInt(3, musica.getAlbum().getId());

            stmt.executeUpdate();
            logInfo("Música inserida com sucesso");

        } catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao inserir Música");
        }
    }

    @Override
    public ArrayList<Musica> exibir() {
        String sql = "SELECT * FROM Musica";

        ArrayList<Musica> musicas = new ArrayList<>();

        try(Connection conn = DataBaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Musica musica = new Musica();
                musica.setId(rs.getInt("id_musica"));
                musica.setTitulo(rs.getString("titulo_musica"));
                musica.setDuracao(rs.getDouble("duracao"));

                Album album = repositorioAlbum.buscarPorId(rs.getInt("album_id"));
                musica.setAlbum(album);
                musicas.add(musica);
            }

        }catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao exibir Musica");
        }

        return musicas;
    }

    @Override
    public void editar(Musica musica) {
        String sql = "UPDATE Musica SET titulo = ?, duracao = ?, album_id = ? WHERE id =?";

        try(Connection conn = DataBaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, musica.getTitulo());
            stmt.setDouble(2, musica.getDuracao());
            stmt.setInt(3, musica.getAlbum().getId());
            stmt.setInt(4, musica.getId());

            int rowsUpdated = stmt.executeUpdate();
            if(rowsUpdated > 0){
                logInfo("Musica atualizada com sucesso!");
            }
        }catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao atualizar musica");

        }

    }

    @Override
    public void excluir(int id_musica) {
        String sql = "DELETE FROM Musica WHERE id_musica = ?";

        try(Connection conn = DataBaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, id_musica);
            int rowsDeleted = stmt.executeUpdate();
            if(rowsDeleted > 0){
                logInfo("Musica removida");
            }

        }catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao excluir");
        }
    }

    @Override
    public Musica buscarPorId(int id_musica) {
        String sql = "SELECT id_musica, titulo_musica, duracao, id_album AS album_id, tiulo_album AS album_nome FROM Musica JOIN Album on album_id WHERE id_musica=? ";
        Musica musica = null;

        try(Connection conn = DataBaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, id_musica);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                musica = new Musica();
                musica.setId(rs.getInt("id_musica"));
                musica.setTitulo(rs.getString("titulo_musica"));
                musica.setDuracao(rs.getDouble("duracao"));

                Album album = repositorioAlbum.buscarPorId(rs.getInt("album_id"));

                musica.setAlbum(album);
                return  musica;
            }

        }catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao buscar por id");
        }

        return musica;
    }
}
