package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
    private static final String URL = "jdbc:h2:./data/biblioteca"; // crea archivo en carpeta /data
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        try {
            crearTablas();
        } catch (SQLException e) {
            System.out.println("Error al crear tablas: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void crearTablas() throws SQLException {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // Tabla autor
            String sqlAutor = """
                CREATE TABLE IF NOT EXISTS autor (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL
                );
            """;

            // Tabla libro
            String sqlLibro = """
                CREATE TABLE IF NOT EXISTS libro (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    titulo VARCHAR(200) NOT NULL,
                    autor_id INT NOT NULL,
                    FOREIGN KEY (autor_id) REFERENCES autor(id) ON DELETE CASCADE
                );
            """;

            stmt.execute(sqlAutor);
            stmt.execute(sqlLibro);
        }
    }
}
