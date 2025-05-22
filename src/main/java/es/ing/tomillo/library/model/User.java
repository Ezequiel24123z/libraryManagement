package es.ing.tomillo.library.model;

public class User {
    // TODO: Implementar los atributos según el ejercicio 2
    // - nombre (String)
    // - id (int)
    // - librosPrestados (Array de Libro)
    private String name;
    private int id;
    private Book[] borrowedBooks;
    private int borrowedBooksCount;

    // TODO: Implementar constructor según el ejercicio 2
    public User(String name) {
        this.name = name;
        this.id = 0; // Se asignará al insertar en la base de datos
        this.borrowedBooks = new Book[5]; // Máximo 5 libros prestados
        this.borrowedBooksCount = 0;
    }

    public User(String name, int id) {
        this.name = name;
        this.id = id;
        this.borrowedBooks = new Book[5]; // Máximo 5 libros prestados
        this.borrowedBooksCount = 0;
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

    // TODO: Implementar método prestarLibro según el ejercicio 2
    public void borrowBook(Book book) {
        if (borrowedBooksCount < borrowedBooks.length) {
            borrowedBooks[borrowedBooksCount] = book;
            borrowedBooksCount++;
        }
    }

    // TODO: Implementar método devolverLibro según el ejercicio 2
    public void returnBook(Book book) {
        for (int i = 0; i < borrowedBooksCount; i++) {
            if (borrowedBooks[i].equals(book)) {
                // Mover el último libro a la posición actual
                borrowedBooks[i] = borrowedBooks[borrowedBooksCount - 1];
                borrowedBooks[borrowedBooksCount - 1] = null;
                borrowedBooksCount--;
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

    // TODO: Implementar método mostrarInformacion según el ejercicio 2
    @Override
    public String toString() {
        return "User: " +
                "name='" + name + '\'' +
                ", id=" + id;
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


