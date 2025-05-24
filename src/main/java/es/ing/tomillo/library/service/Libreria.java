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
    private static final int MAX_LIBROS_RESERVADOS = 2;

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

    // Mostrar por pantalla todos los usuarios registrados en la biblioteca
    public void listUsers() {
        for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.getId());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Número de libros reservados: " + usuario.getLibrosPrestadosNumero());
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

    // Metodo para devolver libros
    public void devolverLibro(Usuario usuarioQueDevuelveElLibro, Libro libroDevuelto) {
        if (!usuarioQueDevuelveElLibro.getLibrosPrestados().contains(libroDevuelto)) {
            System.out.println("El usuario  " + usuarioQueDevuelveElLibro.getNombre() + " no tiene el libro prestado");
            return;
        }
        usuarioQueDevuelveElLibro.eliminarLibroPrestado(libroDevuelto);
        System.out.println(usuarioQueDevuelveElLibro.getNombre() + " ha devuelto el libro " + libroDevuelto.getTitulo());
        libroDevuelto.setDisponibilidad(true);

        for (Usuario usuario : usuarios) {
            if (usuario.equals(usuarioQueDevuelveElLibro)) continue;
            if (usuario.getLibrosReservado().contains(libroDevuelto)) {
                if (usuario.getLibrosPrestadosNumero() < 5) {
                    prestarLibro(usuario, libroDevuelto);
                    usuario.getLibrosReservado().remove(libroDevuelto);
                    libroDevuelto.setReservado(false);
                    libroDevuelto.setDisponibilidad(false);
                    System.out.println("El libro " + libroDevuelto.getTitulo() + " ha sido prestado automáticamente a " + usuario.getNombre());
                    break;
                }
            }
        }
    }

    // Metodo para prestar un libro
    public void prestarLibro(Usuario usuarioPrestar, Libro libroPrestar) {
        if (usuarioPrestar.getLibrosPrestadosNumero() >= MAX_LIBROS_PRESTADOS) {
            System.out.println("Máximo numero de libros prestados, imposible prestar mas");

        } else if (!libroPrestar.isDisponibilidad()) {
            System.out.println("El libro " + libroPrestar.getTitulo() + "no se encuentra disponible");

        } else {
            usuarioPrestar.agregarLibroPrestado(libroPrestar);
            libroPrestar.setDisponibilidad(false);
            System.out.println(libroPrestar.getTitulo() + " prestado");
        }
    }

    // Metodo para reservar libros
    public void reservarLibro(Usuario usuarioReservar, Libro libroReservar) {
        if (usuarioReservar.getLibrosReservado().contains(libroReservar)) {
            System.out.println("El usuario " + usuarioReservar.getNombre() + " ya tiene reservado el libro " + libroReservar.getTitulo() + ", imposible reservar");
        } else if (libroReservar.isReservado()) {
            System.out.println("El libro " + libroReservar.getTitulo() + " ya se encuentra reservado, imposible reservar");
        } else if (usuarioReservar.getLibrosPrestados().contains(libroReservar)) {
            System.out.println("El usuario " + usuarioReservar.getNombre() + " tiene prestado el libro " + libroReservar.getTitulo() + ", imposible reservar");
        } else if (libroReservar.isDisponibilidad()) {
            System.out.println("El libro " + libroReservar.getTitulo() + " se encuentra disponible, no se ha efectuado la reserva");
        } else if (usuarioReservar.getLibrosReservado().size() >= MAX_LIBROS_RESERVADOS) {
            System.out.println("Límite de reservas alcanzado para el usuario " + usuarioReservar.getNombre() + ". No puede reservar más libros.");
        } else {
            usuarioReservar.getLibrosReservado().add(libroReservar);
            libroReservar.setReservado(true);
            System.out.println("El libro " + libroReservar.getTitulo() + " ha sido reservado para el usuario " + usuarioReservar.getNombre());
        }
    }

    // Implementar metodo buscarLibroPorTitulo
    public Libro buscarLibroPorTitulo(String titulo) {
        for (Libro libroBuscado : libros) {
            if (libroBuscado.getTitulo().equalsIgnoreCase(titulo))
                return libroBuscado;
        }
        return null;
    }

    // Implementar metodo buscarLibroPorAutor
    public List<Libro> buscarLibroPorAutor(String autor) {
        List<Libro> librosPorAutor = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.getAutor().equalsIgnoreCase(autor)) {
                librosPorAutor.add(libro);
            }
        }
        return librosPorAutor;
    }

    // Implementar metodo listarLibrosDisponibles según el ejercicio 5
    public List<Libro> listarLibrosDisponibles() {
        List<Libro> librosDisponibles = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.isDisponibilidad()) {
                librosDisponibles.add(libro);
            }
        }
        return librosDisponibles;
    }

    public Usuario buscarUsuarioPorID(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Libreria libreria = new Libreria();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        String titulo;
        String isbn;
        Libro libro = null;
        Usuario usuario = null;
        int id = 0;

        while (!salir) {
            System.out.println("Opciones:");
            System.out.println("1. Añadir libro");
            System.out.println("2. Añadir usuario");
            System.out.println("3. Prestar libro");
            System.out.println("4. Devolver libro");
            System.out.println("5. Buscar libro por titulo");
            System.out.println("6. Buscar libro por autor");
            System.out.println("7. Mostrar libros disponibles");
            System.out.println("8. Mostrar usuarios");
            System.out.println("9. Salir");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter book title: ");
                    titulo = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String autor = scanner.nextLine();
                    System.out.print("Enter book ISBN: ");
                    isbn = scanner.nextLine();
                    //book = new Book(title, author, isbn);
                    libreria.addBook(book);
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

