package org.example;

import org.example.dao.AutorDAO;
import org.example.dao.LibroDAO;
import org.example.dao.impl.AutorDAOImpl;
import org.example.dao.impl.LibroDAOImpl;
import org.example.model.Autor;
import org.example.model.Libro;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final AutorDAO autorDAO = new AutorDAOImpl();
    private static final LibroDAO libroDAO = new LibroDAOImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;

        logger.info("Inicio del sistema Biblioteca.");

        do {
            System.out.println("\nMENU PRINCIPAL");
            System.out.println("1. Crear autor");
            System.out.println("2. Listar autores");
            System.out.println("3. Actualizar autor");
            System.out.println("4. Eliminar autor");
            System.out.println("5. Crear libro");
            System.out.println("6. Listar libros");
            System.out.println("7. Actualizar libro");
            System.out.println("8. Eliminar libro");
            System.out.println("9. Listar libros por autor");
            System.out.println("10. Eliminar autor y sus libros (transaccion)");
            System.out.println("0. Salir");
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> crearAutor();
                case 2 -> listarAutores();
                case 3 -> actualizarAutor();
                case 4 -> eliminarAutor();
                case 5 -> crearLibro();
                case 6 -> listarLibros();
                case 7 -> actualizarLibro();
                case 8 -> eliminarLibro();
                case 9 -> listarLibrosPorAutor();
                case 10 -> eliminarAutorConLibros();
                case 0 -> logger.info("Aplicacion finalizada.");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    private static int leerEntero(String mensaje) {
        int valor = -1;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print(mensaje);
                valor = Integer.parseInt(scanner.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Ingrese un numero entero.");
            }
        }
        return valor;
    }

    private static String leerTextoNoVacio(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Este campo no puede estar vacio. Intentelo de nuevo.");
            }
        } while (texto.isEmpty());
        return texto;
    }


    private static void crearAutor() {
        String nombre = leerTextoNoVacio("Nombre del autor: ");
        autorDAO.crear(new Autor(nombre));
    }


    private static void listarAutores() {
        List<Autor> autores = autorDAO.listarTodos();
        if (autores.isEmpty()) {
            System.out.println("No hay autores cargados.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private static void actualizarAutor() {
        int id = leerEntero("ID del autor a actualizar: ");
        Autor autor = autorDAO.buscarPorId(id);
        if (autor != null) {
            String nuevoNombre = leerTextoNoVacio("Nuevo nombre: ");
            autor.setNombre(nuevoNombre);
            autorDAO.actualizar(autor);
        } else {
            System.out.println("Autor no encontrado.");
        }
    }


    private static void eliminarAutor() {
        int id = leerEntero("ID del autor a eliminar: ");
        autorDAO.eliminar(id);
    }

    private static void crearLibro() {
        String titulo = leerTextoNoVacio("Titulo del libro: ");
        int autorId = leerEntero("ID del autor: ");
        libroDAO.crear(new Libro(titulo, autorId));
    }


    private static void listarLibros() {
        List<Libro> libros = libroDAO.listarTodos();
        if (libros.isEmpty()) {
            System.out.println("No hay libros cargados.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private static void actualizarLibro() {
        int id = leerEntero("ID del libro a actualizar: ");
        Libro libro = libroDAO.buscarPorId(id);
        if (libro != null) {
            String nuevoTitulo = leerTextoNoVacio("Nuevo titulo: ");
            int nuevoAutorId = leerEntero("Nuevo ID de autor: ");
            libro.setTitulo(nuevoTitulo);
            libro.setAutorId(nuevoAutorId);
            libroDAO.actualizar(libro);
        } else {
            System.out.println("Libro no encontrado.");
        }
    }


    private static void eliminarLibro() {
        int id = leerEntero("ID del libro a eliminar: ");
        libroDAO.eliminar(id);
    }

    private static void listarLibrosPorAutor() {
        int autorId = leerEntero("ID del autor: ");
        List<Libro> libros = libroDAO.listarPorAutorId(autorId);
        if (libros.isEmpty()) {
            System.out.println("Este autor no tiene libros.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private static void eliminarAutorConLibros() {
        int id = leerEntero("ID del autor: ");
        ((AutorDAOImpl) autorDAO).eliminarConLibros(id);
    }
}



