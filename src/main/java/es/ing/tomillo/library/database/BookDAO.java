package es.ing.tomillo.library.database;

import es.ing.tomillo.library.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    
    private static final String INSERT_BOOK = "INSERT INTO books (title, author, isbn, available) VALUES (?, ?, ?, ?)";
    private static final String SEARCH_BY_TITLE = "SELECT * FROM books WHERE title LIKE ?";
    private static final String SEARCH_BY_AUTHOR = "SELECT * FROM books WHERE author LIKE ?";
    private static final String SEARCH_AVAILABLE = "SELECT * FROM books WHERE available = true";
    private static final String GET_BY_ISBN = "SELECT * FROM books WHERE isbn = ?";
    private static final String UPDATE_AVAILABILITY = "UPDATE books SET available = ? WHERE isbn = ?";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE isbn = ?";

    public static void insertBook(Book book) throws SQLException {
        DatabaseConnection.executeInTransaction(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(INSERT_BOOK)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setString(3, book.getIsbn());
                stmt.setBoolean(4, book.isAvailable());
                stmt.executeUpdate();
            }
        });
    }

    public static Book getBookByIsbn(String isbn) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BY_ISBN)) {
            stmt.setString(1, isbn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createBookFromResultSet(rs);
                }
            }
        }
        return null;
    }

    public static List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(new Book(
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("isbn")
                ));
            }
        }
        return books;
    }

    public static List<Book> searchBooksByTitle(String title) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_BY_TITLE)) {
            stmt.setString(1, "%" + title + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(createBookFromResultSet(rs));
                }
            }
        }
        return books;
    }

    public static List<Book> searchBooksByAuthor(String author) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_BY_AUTHOR)) {
            stmt.setString(1, "%" + author + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(createBookFromResultSet(rs));
                }
            }
        }
        return books;
    }

    public static List<Book> searchAvailableBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_AVAILABLE);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                books.add(createBookFromResultSet(rs));
            }
        }
        return books;
    }

    public static void updateBookAvailability(String isbn, boolean available) throws SQLException {
        DatabaseConnection.executeInTransaction(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(UPDATE_AVAILABILITY)) {
                stmt.setBoolean(1, available);
                stmt.setString(2, isbn);
                stmt.executeUpdate();
            }
        });
    }

    public static void deleteBook(String isbn) throws SQLException {
        DatabaseConnection.executeInTransaction(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(DELETE_BOOK)) {
                stmt.setString(1, isbn);
                stmt.executeUpdate();
            }
        });
    }

    private static Book createBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book(
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("isbn")
        );
        book.setAvailable(rs.getBoolean("available"));
        return book;
    }
} 