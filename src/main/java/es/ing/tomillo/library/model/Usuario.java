package es.ing.tomillo.library.model;

import es.ing.tomillo.library.service.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;



public class Usuario {

    // Inicialización de atributos para la clase Usuario
    private static int nextUsuarioID = 0;
    private final int id;

    private String nombre;
    private final List<Libro> librosPrestados;
    private final List<Libro> librosReservado;
    private static final int MAX_LIBROS_RESERVADOS = 2;
    private static final int MAX_LIBROS_PRESTADOS = 5;

    // Constructor con un maximo de 5 libros prestados
    public Usuario(String nombre) {
        this.nombre = nombre;
        this.id = nextUsuarioID++;;
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
        return (librosPrestados);
    }

    public int getLibrosPrestadosNumero() {
        return librosPrestados.size();
    }

    public List<Libro> getLibrosReservado() {
        return librosReservado;
    }

    public void agregarLibroReservado(Libro libro) {
        if (librosReservado.size() >= MAX_LIBROS_RESERVADOS) {
            System.out.println("Has alcanzado el máximo de libros reservados.");
            return; }
            librosReservado.add(libro);
    }

    // Implementación de setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Agregar Libro a la lista de libros prestados
    public void agregarLibroPrestado(Libro libro) {
        if (librosPrestados.size() >= MAX_LIBROS_PRESTADOS) {
            System.out.println("Máximo numero de libros prestados para " + nombre);
            return;
        }
        librosPrestados.add(libro);
    }


    // Eliminar Libro de la lista de libros prestados
    public void eliminarLibroPrestado(Libro libro) {
        librosPrestados.remove(libro);
    }


    // Metodo para comprobar si el libro esta prestado
    public boolean tieneLibroPrestado(Libro libro) {
        return librosPrestados.contains(libro);
    }

    // Metodo toString para mostrar la información del usuario
    @Override
    public String toString() {
        return "User{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                ", librosPrestados=" + librosPrestados.size() +
                '}';
    }

    // Metodo equals para comparar usuarios por ID
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


