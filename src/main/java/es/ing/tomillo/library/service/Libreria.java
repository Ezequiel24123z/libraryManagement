package es.ing.tomillo.library.service;

import es.ing.tomillo.library.model.Libro;
import es.ing.tomillo.library.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Libreria {
    // Atributos
    private final List<Usuario> usuarios;
    private final List<Libro> libros;
    private static final int MAX_LIBROS_PRESTADOS = 5;

    // Constructor
    public Libreria() {
        this.usuarios = new ArrayList<>();
        this.libros = new ArrayList<>();
    }

    public List<Usuario> getListaUsuarios() {
        return usuarios;
    }
    public List<Libro> getListaLibros() {
        return libros;
    }

    // Metodo para devolver libros
    public void devolverLibro(Usuario usuarioQueDevuelveElLibro, Libro libroDevuelto) {
        if (!usuarioQueDevuelveElLibro.getLibrosPrestados().contains(libroDevuelto)) {
            System.out.println("El usuario  " + usuarioQueDevuelveElLibro.getNombre() + " no tiene el libro prestado");
            return;
        }
        usuarioQueDevuelveElLibro.getLibrosPrestados().remove(libroDevuelto);
        System.out.println(usuarioQueDevuelveElLibro.getNombre() + " ha devuelto el libro " + libroDevuelto.getTitulo());
        libroDevuelto.setDisponibilidad(true);

        for (Usuario usuario : usuarios) {
            if (usuario.equals(usuarioQueDevuelveElLibro)) continue;
            if (usuario.getLibrosReservado().contains(libroDevuelto)) {
                if (usuario.getLibrosPrestadosNumero() < 5) {
                    prestarLibro(libroDevuelto);
                    usuario.getLibrosReservado().remove(libroDevuelto);
                    libroDevuelto.setReservado(false);
                    libroDevuelto.setDisponibilidad(false);
                    System.out.println("El libro " + libroDevuelto.getTitulo() + " ha sido prestado automáticamente a "+ usuario.getNombre());
                    break;
                    }
                }
            }
        }

        // Cargar datos de ejemplo
    private void loadSampleData() {
        // Cargar usuarios de ejemplo
        // Cargar libros de ejemplo
        System.out.println("Datos de ejemplo cargados:");
        System.out.println("- " + usuarios.size() + " usuarios");
        System.out.println("- " + libros.size() + " libros");
    }

    // Mostrar por pantalla todos los usuarios registrados en la biblioteca
    public void listUsers() {
        for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.getId());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Número de libros reservados: " + usuario.getLibrosPrestadosNumero());
        }
    }

    // TODO: Implementar método prestarLibro según el ejercicio 3

    // Metodo para prestar un libro
    public void prestarLibro(Usuario usuarioPrestar, Libro libroPrestar) {
        if (usuarioPrestar.getLibrosPrestadosNumero() >= MAX_LIBROS_PRESTADOS) {
            System.out.println("Máximo numero de libros prestados, imposible prestar mas");

        } else if (!libroPrestar.isDisponibilidad()) {
            System.out.println("El libro "+libroPrestar.getTitulo()+"no se encuentra disponible");

        } else {
            usuarioPrestar.agregarLibroPrestado(libroPrestar);
            libroPrestar.setDisponibilidad(false);
            System.out.println(libroPrestar.getTitulo() + " prestado");
        }
    }

    // TODO: Implementar método devolverLibro según el ejercicio 3
    public void returnBook(User user, Book book) {
        user.returnBook(book);
    }

    // TODO: Implementar método buscarLibroPorTitulo según el ejercicio 4
    public Book searchBookByTitle(String title) {

        return null;
    }

    // TODO: Implementar método buscarLibroPorAutor según el ejercicio 4
    public Book searchBookByAuthor(String author) {

        return null;
    }

    // TODO: Implementar método listarLibrosDisponibles según el ejercicio 5
    // Debe mostrar por pantalla todos los libros que están disponibles (isAvailable = true)
    public void listAvailableBooks() {

    }

    public User getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        String title;
        String isbn;
        Book book = null;
        User user = null;
        int id = 0;

        while (!exit) {
            System.out.println("Menu Options:");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book by Title");
            System.out.println("6. Search Book by Author");
            System.out.println("7. List Available Books");
            System.out.println("8. List Users");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter book title: ");
                    title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter book ISBN: ");
                    isbn = scanner.nextLine();
                    //book = new Book(title, author, isbn);
                    library.addBook(book);
                    break;
                case 2:
                    System.out.print("Enter user name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter user ID: ");
                    id = scanner.nextInt();
                    user = new User(name, id);
                    library.addUser(user);
                    break;
                case 3:
                    System.out.print("Enter user ID: ");
                    id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter book title: ");
                    title = scanner.nextLine();
                    user = library.getUserById(id);
                    book = library.searchBookByTitle(title);
                    if (user != null && book != null) {
                        library.borrowBook(user, book);
                    } else {
                        System.out.println("User or book not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter user ID: ");
                    id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter book title: ");
                    title = scanner.nextLine();
                    user = library.getUserById(id);
                    book = library.searchBookByTitle(title);
                    if (user != null && book != null) {
                        library.returnBook(user, book);
                    } else {
                        System.out.println("User or book not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter book title: ");
                    title = scanner.nextLine();
                    book = library.searchBookByTitle(title);
                    if (book != null) {
                        System.out.println(book);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 6:
                    System.out.print("Enter book author: ");
                    author = scanner.nextLine();
                    book = library.searchBookByAuthor(author);
                    if (book != null) {
                        System.out.println(book);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 7:
                    library.listAvailableBooks();
                    break;
                case 8:
                    library.listUsers();
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}

