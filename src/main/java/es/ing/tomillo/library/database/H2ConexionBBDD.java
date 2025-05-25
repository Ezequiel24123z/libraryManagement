package es.ing.tomillo.library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.tools.Server;

public class H2ConexionBBDD {

    private static final String URL_BaseDeDatos = "jdbc:h2:mem:testdb";
    private static final String Usuario = "sa";
    private static final String Password = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_BaseDeDatos, Usuario, Password);
    }

    public static void main(String[] args) {
        String crearTablaUsuariosSQL = """
                CREATE TABLE usuarios (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            nombre VARCHAR(50) NOT NULL
                        );""";

        String crearTablaLibrosSQL = """
                CREATE TABLE libros (
                    LibroID INT PRIMARY KEY AUTO_INCREMENT,
                    titulo VARCHAR(255) NOT NULL,
                    autor VARCHAR(255) NOT NULL,
                    isbn VARCHAR(50) NOT NULL,
                    disponibilidad BOOLEAN NOT NULL,
                    reservado BOOLEAN NOT NULL
                );
                """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(crearTablaUsuariosSQL);
            stmt.execute(crearTablaLibrosSQL);

            System.out.println("Tablas 'usuarios' y 'libros' creadas correctamente.");
            Server server = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
            System.out.println("H2 Console started at http://localhost:8082");

            // Mantener la conexión abierta y el programa corriendo
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("El hilo fue interrumpido, cerrando...");
                    break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}