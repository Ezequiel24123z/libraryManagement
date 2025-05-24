package es.ing.tomillo.library.model;

import java.util.Objects;
import java.util.Date;

public class Libro {

    // Atributos que tendrá la clase Libro

    private static int nextLibroID = 0;
    private final int LibroID;
    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponibilidad;
    private boolean reservado;

    // Implementación del constructor
    public Libro(String titulo, String autor, String isbn) {
        this.LibroID = nextLibroID++;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponibilidad = true;
        this.reservado = false;
    }
    // Implementación de los getters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }
    public boolean isReservado() {
        return reservado;
    }

    // Implementación de los setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    // Implementación del metodo toString
    @Override // Se sobreescribe el metodo toString que tienen todos los tipos de manera predeterminada para la clase "Libro"
    public String toString() {
        return "Libro{" + "titulo=" + titulo + ", autor=" + autor + ", isbn=" + isbn + ", disponibilidad=" + disponibilidad + '}';
    }

    // Implementar metodo equals para comparar libros por ISBN
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false; // Verifica si el libro (obj) existe, asi evitamos un posible error (NullPointerException)

        if (!(obj instanceof Libro)) return false; //Verifica si es un libro (obj) sea del tipo "Libro"

        Libro segundoLibro = (Libro) obj; //Convierte el tipo "obj" a tipo "Libro", lo que le permite usar metodos de "Libro"

        if (this.isbn == null)
            return segundoLibro.getIsbn() == null; //Si los dos ISBN son Null, da True

        return this.isbn.equals(segundoLibro.getIsbn()); //Devuelve un boolean (true o false) en la comparación de los dos libros

    }
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}

