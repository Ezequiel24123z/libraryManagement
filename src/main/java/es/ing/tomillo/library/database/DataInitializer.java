package es.ing.tomillo.library.database;

import es.ing.tomillo.library.model.Book;
import es.ing.tomillo.library.model.User;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DataInitializer {
    
    private static final List<Book> SAMPLE_BOOKS = Arrays.asList(
        new Book("El Quijote", "Miguel de Cervantes", "978-84-376-0494-7"),
        new Book("Cien años de soledad", "Gabriel García Márquez", "978-84-376-0494-8"),
        new Book("1984", "George Orwell", "978-84-376-0494-9"),
        new Book("El señor de los anillos", "J.R.R. Tolkien", "978-84-376-0494-0"),
        new Book("Orgullo y prejuicio", "Jane Austen", "978-84-376-0494-1"),
        new Book("Crimen y castigo", "Fiódor Dostoyevski", "978-84-376-0494-2"),
        new Book("El principito", "Antoine de Saint-Exupéry", "978-84-376-0494-3"),
        new Book("Don Juan Tenorio", "José Zorrilla", "978-84-376-0494-4"),
        new Book("La sombra del viento", "Carlos Ruiz Zafón", "978-84-376-0494-5"),
        new Book("El código Da Vinci", "Dan Brown", "978-84-376-0494-6")
    );

    private static final List<User> SAMPLE_USERS = Arrays.asList(
        new User("Juan Pérez"),
        new User("María García"),
        new User("Carlos López"),
        new User("Ana Martínez"),
        new User("Pedro Sánchez")
    );

    public static void initializeSampleData() throws SQLException {
        // Insertar libros
        for (Book book : SAMPLE_BOOKS) {
            BookDAO.insertBook(book);
        }

        // Insertar usuarios
        for (User user : SAMPLE_USERS) {
            UserDAO.insertUser(user);
        }

        // Crear algunos préstamos de ejemplo
        UserDAO.borrowBook(1, "978-84-376-0494-7"); // Juan Pérez presta El Quijote
        UserDAO.borrowBook(2, "978-84-376-0494-8"); // María García presta Cien años de soledad
        UserDAO.borrowBook(3, "978-84-376-0494-9"); // Carlos López presta 1984
        UserDAO.borrowBook(4, "978-84-376-0494-0"); // Ana Martínez presta El señor de los anillos
        UserDAO.borrowBook(5, "978-84-376-0494-1"); // Pedro Sánchez presta Orgullo y prejuicio
    }
} 