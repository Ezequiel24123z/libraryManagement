package es.ing.tomillo.library.util;

import es.ing.tomillo.library.model.Libro;
import es.ing.tomillo.library.model.Usuario;

import java.util.Arrays;
import java.util.List;

public class SampleData {

    public static final List<Libro> librosEjemplo = Arrays.asList(
            new Libro("Cien años de soledad", "Gabriel García Márquez", "978-0307474728"),
            new Libro("Don Quijote de la Mancha", "Miguel de Cervantes", "978-8491050299"),
            new Libro("El Principito", "Antoine de Saint-Exupéry", "978-0156012195"),
            new Libro("1984", "George Orwell", "978-0451524935"),
            new Libro("La sombra del viento", "Carlos Ruiz Zafón", "978-8408138024"),
            new Libro("El nombre de la rosa", "Umberto Eco", "978-0156001311"),
            new Libro("Orgullo y prejuicio", "Jane Austen", "978-1503290563"),
            new Libro("Crónica de una muerte anunciada", "Gabriel García Márquez", "978-0307389732"));

    public static final List<Usuario> usuariosEjemplo = Arrays.asList(
            new Usuario("Juan Pérez"),
            new Usuario("María García"),
            new Usuario("Carlos López"),
            new Usuario("Ana Martínez"),
            new Usuario("Pedro Sánchez"),
            new Usuario("Laura Fernández"),
            new Usuario("David González"),
            new Usuario("Sofía Rodríguez"),
            new Usuario("Javier Díaz"),
            new Usuario("Elena Ruiz")
    );
} 