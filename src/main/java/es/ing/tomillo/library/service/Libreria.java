package es.ing.tomillo.library.service;

import es.ing.tomillo.library.model.Libro;
import es.ing.tomillo.library.model.Usuario;
import es.ing.tomillo.library.util.SampleData;

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
    private void cargarDatosDeEjemplo() {
        usuarios.addAll(SampleData.usuariosEjemplo);
        libros.addAll(SampleData.librosEjemplo);
        System.out.println("Datos de ejemplo cargados:");
        System.out.println("- " + usuarios.size() + " usuarios");
        System.out.println("- " + libros.size() + " libros");
    }

    // Metodo para crear un libro
    public void crearYAgregarLibro(String titulo, String autor, String isbn) {
        Libro nuevoLibro = new Libro(titulo, autor, isbn);
        libros.add(nuevoLibro);
        System.out.println("Libro creado y añadido: " + nuevoLibro);
    }

    // Metodo para crear un usuario
    public void crearYAgregarUsuario(String nombre) {
        Usuario nuevoUsuario = new Usuario(nombre);
        usuarios.add(nuevoUsuario);
        System.out.println("Usuario creado y añadido: " + nuevoUsuario);
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

    // Implementar metodo buscarUsuarioPorNombre

    public Usuario buscarUsuarioPorNombre(String nombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                return usuario;
            }
        }
        return null;
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
        libreria.cargarDatosDeEjemplo();

        while (!salir) {
            System.out.println("Opciones:");
            System.out.println("1. Añadir libro");
            System.out.println("2. Añadir usuario");
            System.out.println("3. Prestar libro");
            System.out.println("4. Reservar libro");
            System.out.println("5. Devolver libro");
            System.out.println("6. Buscar libro por titulo");
            System.out.println("7. Buscar libro por autor");
            System.out.println("8. Mostrar libros disponibles");
            System.out.println("9. Mostrar usuarios");
            System.out.println("10. Salir");
            System.out.print("Escoge una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {

                case 1: // Añadir libro a la biblioteca
                    System.out.print("Insertar titulo del libro: ");
                    titulo = scanner.nextLine().trim();
                    System.out.print("Insertar autor del libro: ");
                    String autor = scanner.nextLine().trim();
                    System.out.print("Insertar ISBN del libro: ");
                    isbn = scanner.nextLine().trim();
                    if (titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty()) {
                        System.out.println("Error: No se pueden dejar campos vacíos.");
                    } else {
                        libreria.crearYAgregarLibro(titulo, autor, isbn);
                    }
                    break;

                case 2: // Añadir usuario a la biblioteca
                    System.out.print("Insertar nombre del usuario: ");
                    String nombre = scanner.nextLine().trim();
                    if (nombre.isEmpty()) {
                        System.out.println("Error, el nombre no puede estar vacío");
                    }
                    else {
                        libreria.crearYAgregarUsuario(nombre);
                    }
                    break;

                case 3: // Prestar libro
                    System.out.print("Insertar nombre del usuario: ");
                    nombre = scanner.nextLine().trim();
                    System.out.print("Insertar titulo del libro: ");
                    titulo = scanner.nextLine().trim();
                    if (titulo.isEmpty() || nombre.isEmpty()) {
                        System.out.println("Error: No se pueden dejar campos vacíos");
                    } else {
                        usuario = libreria.buscarUsuarioPorNombre(nombre);
                        libro = libreria.buscarLibroPorTitulo(titulo);

                        if (usuario == null) {
                            System.out.println("Error: Usuario no encontrado");
                        } else if (libro == null) {
                            System.out.println("Error: Libro no encontrado");
                        } else libreria.prestarLibro(usuario, libro);
                    }
                    break;

                case 4: // Reservar libro
                    System.out.print("Insertar nombre del usuario: ");
                    nombre = scanner.nextLine().trim();
                    System.out.print("Insertar titulo del libro: ");
                    titulo = scanner.nextLine().trim();
                    if (titulo.isEmpty() || nombre.isEmpty()) {
                        System.out.println("Error: No se pueden dejar campos vacíos");
                    } else {
                        usuario = libreria.buscarUsuarioPorNombre(nombre);
                        libro = libreria.buscarLibroPorTitulo(titulo);

                        if (usuario == null) {
                            System.out.println("Error: Usuario no encontrado");
                        } else if (libro == null) {
                            System.out.println("Error: Libro no encontrado");
                        } else libreria.reservarLibro(usuario, libro);
                    }
                    break;

                case 5: // Devolver libro
                    System.out.print("Insertar titulo del libro a devolver: ");
                    titulo = scanner.nextLine().trim();
                    System.out.print("Insertar nombre del usuario que devuelve el libro: ");
                    nombre = scanner.nextLine().trim();
                    if (titulo.isEmpty() || nombre.isEmpty()) {
                        System.out.println("Error, no se pueden dejar campos vacíos");
                    } else {
                        libro = libreria.buscarLibroPorTitulo(titulo);
                        usuario = libreria.buscarUsuarioPorNombre(nombre);
                        if (libro == null) {
                            System.out.println("Error: Libro no encontrado");
                        } else if (usuario == null) {
                            System.out.println("Error: Usuario no encontrado");
                        } else if (!usuario.tieneLibroPrestado(libro)) {
                            System.out.println("Error: El usuario no tiene este libro prestado");
                        } else {
                            libreria.devolverLibro(usuario, libro);
                        }
                    }
                    break;

                case 6: // Buscar libro por título
                    System.out.print("Insertar nombre del titulo del libro que desea buscar: ");
                    titulo = scanner.nextLine().trim();
                    if (titulo.isEmpty()) {
                        System.out.println("Error, el titulo no puede estar vacio");
                    } else {
                        libro = libreria.buscarLibroPorTitulo(titulo);
                        if (libro != null) {
                            System.out.println(libro);
                        } else {
                            System.out.println("Libro no encontrado.");
                        }
                    }
                    break;

                case 7: // Buscar libro por autor
                    System.out.print("Insertar nombre del autor de el/los libro/s que desea buscar: ");
                    autor = scanner.nextLine().trim();
                    if (autor.isEmpty()) {
                        System.out.println("Error, el nombre del autor no puede estar vacío");
                    } else {
                        List<Libro> librosPorAutor = libreria.buscarLibroPorAutor(autor);
                        if (librosPorAutor.isEmpty()) {
                            System.out.println("Libro/s no encontrado.");
                        } else {
                            System.out.println("Libro/s de " + autor + ":");
                            for (Libro libroEncontrado : librosPorAutor) {
                                System.out.println(libroEncontrado);
                            }
                        }
                    }
                    break;

                case 8: // Listar libros disponibles
                    List<Libro> librosDisponibles = libreria.listarLibrosDisponibles();
                    if (librosDisponibles.isEmpty()) {
                        System.out.println("No hay libros disponibles.");
                    } else {
                        System.out.println("Libros disponibles:");
                        for (Libro libroEncontrado : librosDisponibles) {
                            System.out.println(libroEncontrado);
                        }
                    }
                    break;

                case 9: // Mostrar usuarios
                    List<Usuario> usuariosDisponibles = libreria.getListaUsuarios();
                    if (usuariosDisponibles.isEmpty()) {
                        System.out.println("No hay usuarios.");
                    } else {
                        System.out.println("Usuarios disponibles:");
                        for (Usuario usuarioEncontrado : usuariosDisponibles) {
                            System.out.println(usuarioEncontrado);
                        }
                    }
                    break;

                case 10: // Salir
                    salir = true;
                    break;
                default:
                    System.out.println("Esta opción no existe.");
            }
        }

        scanner.close();
    }
}

