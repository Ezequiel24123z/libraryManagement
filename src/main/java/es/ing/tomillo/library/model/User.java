package es.ing.tomillo.library.model;

public class User {
    // TODO: Implementar los atributos según el ejercicio 2
    // - nombre (String)
    // - id (int)
    // - librosPrestados (Array de Libro)
    private String name;
    private int id;
    private Book[] borrowedBooks;
    private int bookCount;

    // TODO: Implementar constructor según el ejercicio 2
    public User(String name, int id) {
        this.name = name;
        this.id = id;
        this.borrowedBooks = new Book[5]; // Maximum 5 borrowed books
        this.bookCount = 0;
    }

    // TODO: Implementar getters y setters según el ejercicio 2
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book[] getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Book[] borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    // TODO: Implementar método prestarLibro según el ejercicio 2
    // Debe añadir un libro al array de libros prestados
    public void borrowBook(Book book) {
        if (bookCount < borrowedBooks.length) {
            borrowedBooks[bookCount] = book;
            bookCount++;
            book.setAvailable(false);
        } else {
            System.out.println("ERROR: El usuario ha alcanzado el límite máximo de libros prestados (5 libros)");
        }
    }

    // TODO: Implementar método devolverLibro según el ejercicio 2
    // Debe eliminar un libro del array de libros prestados
    public void returnBook(Book book) {
        for (int i = 0; i < bookCount; i++) {
            if (borrowedBooks[i].equals(book)) {
                borrowedBooks[i] = null;
                bookCount--;
                book.setAvailable(true);
                break;
            }
        }
    }

    // TODO: Implementar método reservarLibro según el ejercicio 6
    // Debe permitir reservar libros que no están disponibles
    public void reserveBook(Book book) {
        if (!book.isAvailable()) {
            System.out.println("El libro '" + book.getTitle() + "' ya está reservado por otro usuario");
        } else {
            System.out.println("No se puede reservar un libro que está disponible. Debe prestarlo directamente");
        }
    }

    // TODO: Implementar método toString para mostrar la información del usuario
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", borrowedBooks=" + bookCount +
                '}';
    }

    // TODO: Implementar método equals para comparar usuarios por ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id;
    }
}


