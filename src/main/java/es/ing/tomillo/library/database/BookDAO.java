package es.ing.tomillo.library.database;

import es.ing.tomillo.library.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookDAO extends BaseDAO {
    private static final String INSERT_BOOK = "";
    private static final String SEARCH_BY_TITLE = "";
    private static final String SEARCH_BY_AUTHOR = "";
    private static final String SEARCH_AVAILABLE = "";
    private static final String GET_BY_ISBN = "";
    private static final String UPDATE_AVAILABILITY = "";
    private static final String DELETE_BOOK = "";

    public static void insertBook(Book book) {
        executeUpdate(INSERT_BOOK, book.getTitle(), book.getAuthor(), book.getIsbn(), book.isAvailable());
    }

    public static Book getBookByIsbn(String isbn) {
        return executeQuerySingle(GET_BY_ISBN, BookDAO::createBookFromResultSet, isbn);
    }

    public static List<Book> searchBooksByTitle(String title) {
        return executeQuery(SEARCH_BY_TITLE, BookDAO::createBookFromResultSet, "%" + title + "%");
    }

    public static List<Book> searchBooksByAuthor(String author) {
        return executeQuery(SEARCH_BY_AUTHOR, BookDAO::createBookFromResultSet, "%" + author + "%");
    }

    public static List<Book> searchAvailableBooks() {
        return executeQuery(SEARCH_AVAILABLE, BookDAO::createBookFromResultSet);
    }

    public static void updateBookAvailability(String isbn, boolean available) {
        executeUpdate(UPDATE_AVAILABILITY, available, isbn);
    }

    public static void deleteBook(String isbn) {
        executeUpdate(DELETE_BOOK, isbn);
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