package org.example.dao.impl;

import org.example.dao.LibroDAO;
import org.example.model.Libro;
import org.example.util.ConexionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO {
    private static final Logger logger = LogManager.getLogger(LibroDAOImpl.class);

    @Override
    public void crear(Libro libro) {
        String sql = "INSERT INTO libro (titulo, autor_id) VALUES (?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setInt(2, libro.getAutorId());
            stmt.executeUpdate();
            logger.info("Libro creado: " + libro.getTitulo());

        } catch (SQLException e) {
            logger.error("Error al crear libro", e);
        }
    }

    @Override
    public Libro buscarPorId(int id) {
        String sql = "SELECT * FROM libro WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Libro(rs.getInt("id"), rs.getString("titulo"), rs.getInt("autor_id"));
            }

        } catch (SQLException e) {
            logger.error("Error al buscar libro", e);
        }
        return null;
    }

    @Override
    public List<Libro> listarTodos() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libro";
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Libro(rs.getInt("id"), rs.getString("titulo"), rs.getInt("autor_id")));
            }

        } catch (SQLException e) {
            logger.error("Error al listar libros", e);
        }
        return lista;
    }

    @Override
    public void actualizar(Libro libro) {
        String sql = "UPDATE libro SET titulo = ?, autor_id = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setInt(2, libro.getAutorId());
            stmt.setInt(3, libro.getId());
            stmt.executeUpdate();
            logger.info("Libro actualizado: " + libro);

        } catch (SQLException e) {
            logger.error("Error al actualizar libro", e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM libro WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Libro eliminado con ID: " + id);

        } catch (SQLException e) {
            logger.error("Error al eliminar libro", e);
        }
    }

    @Override
    public List<Libro> listarPorAutorId(int autorId) {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libro WHERE autor_id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, autorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getInt("autor_id")
                ));
            }
            logger.info("Libros listados por autorId {}", autorId);
        } catch (SQLException e) {
            logger.error("Error al listar libros por autor", e);
        }
        return lista;
    }

}
