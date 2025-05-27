package org.example.dao.impl;

import org.example.dao.AutorDAO;
import org.example.model.Autor;
import org.example.util.ConexionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAOImpl implements AutorDAO {
    private static final Logger logger = LogManager.getLogger(AutorDAOImpl.class);

    @Override
    public void crear(Autor autor) {
        String sql = "INSERT INTO autor (nombre) VALUES (?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, autor.getNombre());
            stmt.executeUpdate();
            logger.info("Autor creado: {}", autor.getNombre());

        } catch (SQLException e) {
            logger.error("Error al crear autor", e);
        }
    }

    @Override
    public Autor buscarPorId(int id) {
        String sql = "SELECT * FROM autor WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Autor autor = new Autor(rs.getInt("id"), rs.getString("nombre"));
                logger.info("Autor encontrado: {}", autor);
                return autor;
            }

        } catch (SQLException e) {
            logger.error("Error al buscar autor", e);
        }
        return null;
    }

    @Override
    public List<Autor> listarTodos() {
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autor";

        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Autor autor = new Autor(rs.getInt("id"), rs.getString("nombre"));
                lista.add(autor);
            }
            logger.info("Se listaron {} autores", lista.size());

        } catch (SQLException e) {
            logger.error("Error al listar autores", e);
        }

        return lista;
    }

    @Override
    public void actualizar(Autor autor) {
        String sql = "UPDATE autor SET nombre = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, autor.getNombre());
            stmt.setInt(2, autor.getId());
            stmt.executeUpdate();
            logger.info("Autor actualizado: {}", autor);

        } catch (SQLException e) {
            logger.error("Error al actualizar autor", e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM autor WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            logger.info("Autor eliminado con ID: {}", id);

        } catch (SQLException e) {
            logger.error("Error al eliminar autor", e);
        }
    }

    public void eliminarConLibros(int id) {
        try (Connection conn = ConexionDB.getConnection()) {
            conn.setAutoCommit(false); // Iniciar transacción

            try (
                    PreparedStatement stmtLibros = conn.prepareStatement("DELETE FROM libro WHERE autor_id = ?");
                    PreparedStatement stmtAutor = conn.prepareStatement("DELETE FROM autor WHERE id = ?")
            ) {
                stmtLibros.setInt(1, id);
                stmtLibros.executeUpdate();

                stmtAutor.setInt(1, id);
                stmtAutor.executeUpdate();

                conn.commit(); // Confirmar transacción
                logger.info("Transacción exitosa: autor y libros eliminados (ID autor: {})", id);
            } catch (SQLException e) {
                conn.rollback(); // Revertir en caso de error
                logger.error("Error en transacción al eliminar autor y sus libros. Se hizo rollback.", e);
            }

        } catch (SQLException e) {
            logger.error("Error al conectar para transacción de eliminación", e);
        }
    }

}
