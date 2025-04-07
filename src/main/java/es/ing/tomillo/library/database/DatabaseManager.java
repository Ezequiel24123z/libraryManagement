package es.ing.tomillo.library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:h2:mem:library;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public static void initializeDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Crear tabla de libros
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DROP TABLE IF EXISTS borrowed_books");
                stmt.execute("DROP TABLE IF EXISTS books");
                stmt.execute("DROP TABLE IF EXISTS users");
                
                stmt.execute("""
                    CREATE TABLE books (
                        title VARCHAR(100) NOT NULL,
                        author VARCHAR(100) NOT NULL,
                        isbn VARCHAR(20) PRIMARY KEY,
                        available BOOLEAN DEFAULT TRUE
                    )
                """);
                System.out.println("Tabla 'books' creada correctamente");
            }

            // Crear tabla de usuarios
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("""
                    CREATE TABLE users (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
                    )
                """);
                System.out.println("Tabla 'users' creada correctamente");
            }

            // Crear tabla de préstamos
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("""
                    CREATE TABLE borrowed_books (
                        user_id INT,
                        book_isbn VARCHAR(20),
                        PRIMARY KEY (user_id, book_isbn),
                        FOREIGN KEY (user_id) REFERENCES users(id),
                        FOREIGN KEY (book_isbn) REFERENCES books(isbn)
                    )
                """);
                System.out.println("Tabla 'borrowed_books' creada correctamente");
            }

            // Inicializar datos de ejemplo
            System.out.println("Inicializando datos de ejemplo...");
            DataInitializer.initializeSampleData();
            System.out.println("Datos de ejemplo inicializados correctamente");
        }
    }

    public static void closeConnection() throws SQLException {
        DatabaseConnection.closeConnection();
    }
} 