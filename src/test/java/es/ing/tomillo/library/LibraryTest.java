package es.ing.tomillo.library;

import es.ing.tomillo.library.model.Book;
import es.ing.tomillo.library.model.User;
import es.ing.tomillo.library.service.Library;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    @Test
    public void testAddBook() {
        Library library = new Library();
        Book book = new Book("Don Quixote", "Cervantes", "123456789");
        library.addBook(book);
        assertEquals(book, library.searchBookByTitle(book.getTitle()));
    }

    @Test
    public void testBorrowBook() {
        Library library = new Library();
        User user = new User("John", 1);
        Book book = new Book("Don Quixote", "Cervantes", "123456789");
        library.addBook(book);
        library.addUser(user);
        library.borrowBook(user, book);
        assertFalse(book.isAvailable());
        assertEquals(book, user.getBorrowedBooks()[0]);
    }

    @Test
    public void testReturnBook() {
        Library library = new Library();
        User user = new User("John", 1);
        Book book = new Book("Don Quixote", "Cervantes", "123456789");
        library.addBook(book);
        library.addUser(user);
        library.borrowBook(user, book);
        library.returnBook(user, book);
        assertTrue(book.isAvailable());
        assertNull(user.getBorrowedBooks()[0]);
    }
}
