package es.ing.tomillo.library.database;

import es.ing.tomillo.library.model.Book;
import es.ing.tomillo.library.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO extends BaseDAO {
    private static final String INSERT_USER = "INSERT INTO users (name) VALUES (?)";
    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private static final String UPDATE_USER = "UPDATE users SET name = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String BORROW_BOOK = "INSERT INTO borrowed_books (user_id, book_isbn) VALUES (?, ?)";
    private static final String RETURN_BOOK = "DELETE FROM borrowed_books WHERE user_id = ? AND book_isbn = ?";
    private static final String GET_BORROWED_BOOKS = """
        SELECT b.* FROM books b
        JOIN borrowed_books bb ON b.isbn = bb.book_isbn
        WHERE bb.user_id = ?
    """;

    public static void insertUser(User user) {
        executeUpdate(INSERT_USER, user.getName());
    }

    public static User getUserById(int id) {
        return executeQuerySingle(GET_USER_BY_ID, UserDAO::createUserFromResultSet, id);
    }

    public static List<User> getAllUsers() {
        return executeQuery(GET_ALL_USERS, UserDAO::createUserFromResultSet);
    }

    public static void updateUser(User user) {
        executeUpdate(UPDATE_USER, user.getName(), user.getId());
    }

    public static void deleteUser(int userId) {
        executeUpdate(DELETE_USER, userId);
    }

    public static void borrowBook(int userId, String bookIsbn) {
        executeUpdate(BORROW_BOOK, userId, bookIsbn);
    }

    public static void returnBook(int userId, String bookIsbn) {
        executeUpdate(RETURN_BOOK, userId, bookIsbn);
    }

    public static List<Book> getBorrowedBooks(int userId) {
        return executeQuery(GET_BORROWED_BOOKS, UserDAO::createBookFromResultSet, userId);
    }

    protected static User createUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(rs.getString("name"), rs.getInt("id"));
    }

    protected static Book createBookFromResultSet(ResultSet rs) throws SQLException {
        Book book = new Book(
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("isbn")
        );
        book.setAvailable(rs.getBoolean("available"));
        return book;
    }
} 