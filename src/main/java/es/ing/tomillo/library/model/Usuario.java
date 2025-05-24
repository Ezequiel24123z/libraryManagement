package es.ing.tomillo.library.model;

import es.ing.tomillo.library.service.Library;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;

public class Usuario {

    // Inicialización de atributos para la clase Usuario
    private String nombre;
    private int id;
    private final List<Libro> librosPrestados;
    private static final int MAX_LIBROS_PRESTADOS = 5;
    private final List<Libro> librosReservado;
    private static final int MAX_LIBROS_RESERVADOS = 2;

    // Constructor con un maximo de 5 libros prestados
    public Usuario(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
        this.librosReservado = new ArrayList<>();
        this.librosPrestados = new ArrayList<>();
    }

    // Implementación de getters
    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public List<Libro> getLibrosPrestados() {
        return Collections.unmodifiableList(librosPrestados);   // Modificación: devolver librosPrestados() puede implicar un riesgo
                                                                // de seguridad y rompe la encapsulación, por lo tanto es mejor devolver una lista
                                                                // inmodificable que protege ante cambios
    }

    public int getLibrosPrestadosNumero() {
        return librosPrestados.size();
    }

    // Implementación de setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Metodo para prestar un libro
    public void prestarLibro(Libro libro) {
        if (librosPrestados.size() >= MAX_LIBROS_PRESTADOS) {
            System.out.println("Máximo numero de libros prestados, imposible prestar mas");

        } else if (!libro.isDisponibilidad()) {
            System.out.println("El libro "+libro.getTitulo()+"no se encuentra disponible");

        } else {
            librosPrestados.add(libro);
            libro.setDisponibilidad(false);
            System.out.println(libro.getTitulo() + " prestado");
        }
    }


    // Metodo para reservar libros
    public void reservarLibro(Libro libro) {
        if (librosReservado.contains(libro)) {
            System.out.println("Usted ya tiene reservado el libro " + libro.getTitulo() + ", imposible reservar");
        }
        else if (libro.isReservado()) {
            System.out.println("El libro " + libro.getTitulo() + " ya se encuentra reservado, imposible reservar");
        }
        else if (librosPrestados.contains(libro)) {
            System.out.println("Usted tiene prestado el libro " + libro.getTitulo() + ", imposible reservar");
        }
        else if (libro.isDisponibilidad()) {
            System.out.println("El libro " + libro.getTitulo() + " se encuentra disponible, no se ha efectuado la reserva");
        }
        else if (librosReservado.size() >= MAX_LIBROS_RESERVADOS) {
            System.out.println("Límite de reservas alcanzado. No puede reservar más libros.");
        }
        else {
            librosReservado.add(libro);
            libro.setReservado(true);
            System.out.println(libro.getTitulo() + " reservado");
        }
    }

    // TODO: Implementar método toString para mostrar la información del usuario
    @Override
    public String toString() {
        return "User{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                ", librosPrestados=" + librosPrestados.size() +
                '}';
    }

    // TODO: Implementar método equals para comparar usuarios por ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario user = (Usuario) obj;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


