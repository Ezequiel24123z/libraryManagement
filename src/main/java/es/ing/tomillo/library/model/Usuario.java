package es.ing.tomillo.library.model;

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
    public Usuario(String nombre, int id, List<Libro> librosReservado) {
        this.nombre = nombre;
        this.id = id;
        this.librosReservado = new ArrayList<>(librosReservado);
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

    // Función para prestar un libro
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

    // Debe eliminar un libro a lista de libros prestados
    public void devolverLibro(Libro libro) {
            if (librosPrestados.contains(libro)) {
                librosPrestados.remove(libro);
            }
            else { System.out.println(libro.getTitulo() + " imposible devolver, no esta prestado");
            }
    }

    // TODO: Implementar método reservarLibro según el ejercicio 2
    // Debe permitir reservar libros que no están disponibles
    public void reserveBook(Book book) {

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


