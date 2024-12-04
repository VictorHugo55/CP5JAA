package org.example.Repositorios;

import oracle.jdbc.proxy.annotation.Pre;
import org.example.Entidades.Artista;
import org.example.InfraestruturaDB.DataBaseConfig;
import org.example.Log.Loggable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioArtista implements  RepositorioGenerico<Artista>, Loggable<Artista> {
    @Override
    public void adicionar(Artista artista) {
        String sql = "INSERT INTO Artista(Nome, generoMusical) VALUES (?,?)";

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,artista.getNome());
            stmt.setString(2,artista.getGeneroMusical());

            stmt.executeUpdate();
            logInfo("Artista cadastrado com sucesso!");
        }catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao cadastrar artista");
        }
    }

    @Override
    public ArrayList<Artista> exibir() {
        String sql = "SELECT * FROM Artista";
        ArrayList<Artista> artistas = new ArrayList<>();

        try(Connection conn = DataBaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){

            while (rs.next()){
                Artista artista = new Artista();
                artista.setId(rs.getInt("id"));
                artista.setNome(rs.getString("nome"));
                artista.setGeneroMusical(rs.getString("generoMusical"));
                artistas.add(artista);

            }
        }catch (SQLException e) {
            e.printStackTrace();
            logInfo("Erro ao mostrar");
        }

        return artistas;
    }

    @Override
    public void editar(Artista artista) {
        String sql = "UPDATE Artista SET nome = ?, generoMusical = ? WHERE id = ?";

        try(Connection conn = DataBaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, artista.getNome());
            stmt.setString(2, artista.getGeneroMusical());
            stmt.setInt(3, artista.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0){
                logInfo("Artista Atualizado com sucesso");
            }

        }catch (SQLException e){
            e.printStackTrace();
            logInfo("Erroa ao atualizar");
        }
    }

    @Override
    public void excluir(int id) {
        String sql = "DELETE FROM Artista WHERE id = ?";

        try(Connection conn = DataBaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1,id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted >0){
                logInfo("Artista excluido com sucesso!");
            }
        }catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao excluir");
        }


    }

    @Override
    public Artista buscarPorId(int id) {
        String sql = "SELECT * FROM Artista WHERE id = ?";
        Artista artista = null;

        try(Connection conn = DataBaseConfig.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                artista = new Artista();
                artista.setId(rs.getInt("id"));
                artista.setNome(rs.getString("nome"));
                artista.setGeneroMusical(rs.getString("generoMusical"));
            }
            return artista;
        }catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao buscar o id");
        }
        return artista;

    }

    public List<Artista> buscarPorGenero(String genero) {
        List<Artista> artistas = new ArrayList<>();

        String sql = "SELECT * FROM Artista WHERE generoMusical = ?";

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,genero);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                int idArtista = rs.getInt("id");
                String nomeArtista = rs.getString("nome");

                Artista artista = new Artista(idArtista, nomeArtista);
                artistas.add(artista);
            }
            return artistas;

        }catch (SQLException e){
            e.printStackTrace();
            logInfo("Erro ao buscar por gênero");
        }
        return artistas;
    }


}
