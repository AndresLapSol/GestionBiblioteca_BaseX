package org.example.gestionbiblioteca_basex.Repositories;

import org.basex.api.client.ClientSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateRepository {
    private static final String HOST = "localhost";
    private static final int PORT = 1984;
    private static final String USER = "Goku";
    private static final String PASSWORD = "Goku";
    private static final String DATABASE_NAME = "biblioteca";


    public void insertBook(String title, String author, String year, String genre) {
        try (ClientSession session = new ClientSession(HOST, PORT, USER, PASSWORD)) {
            session.execute("OPEN " + DATABASE_NAME); // Abrir la base de datos

            String queryInsert =
                    "insert node " +
                            "<book>" +
                            "   <title>" + title + "</title>" +
                            "   <author>" + author + "</author>" +
                            "   <year>" + year + "</year>" +
                            "   <genre>" + genre + "</genre>" +
                            "</book> " +
                            "into doc('" + DATABASE_NAME + "')/library/books";

            session.execute("XQUERY " + queryInsert);
            System.out.println("Libro insertado correctamente.");
        } catch (Exception e) {
            System.err.println("Error al insertar el libro: " + e.getMessage());
        }
    }



    // Método para obtener todos los títulos de los libros
    public List<String> getAllTitles() {
        ClientSession session = null;
        List<String> titles = new ArrayList<>();
        try {
            session = new ClientSession(HOST, PORT, USER, PASSWORD);
            System.out.println("Conectado a BaseX.");

            // Consulta para obtener todos los títulos
            String queryTitles =
                    "for $title in doc('" + DATABASE_NAME + "')/library/books/book/title\n" +
                            "return string($title)";

            String resultTitles = session.execute("XQUERY " + queryTitles);
            String[] titleArray = resultTitles.split("\n");

            for (String title : titleArray) {
                titles.add(title.trim());
            }

        } catch (Exception e) {
            System.err.println("Error al obtener los títulos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                    System.out.println("Sesión cerrada correctamente.");
                } catch (Exception e) {
                    System.err.println("Error al cerrar la sesión: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return titles;
    }




    public List<String> getGeneros() {
        List<String> generos = new ArrayList<>();
        try (ClientSession session = new ClientSession(HOST, PORT, USER, PASSWORD)) {
            System.out.println("Conectado a BaseX para obtener géneros.");

            // 1. Abrir la base de datos explícitamente
            session.execute("OPEN " + DATABASE_NAME);

            // 2. Consulta XQuery para obtener géneros desde la base de datos
            String query = "doc('" + DATABASE_NAME + "')/library/genres/genre/string()";
            String result = session.execute("XQUERY " + query);

            // 3. Procesar el resultado
            String[] genreArray = result.split("\n");
            for (String genre : genreArray) {
                if (!genre.trim().isEmpty()) {
                    generos.add(genre.trim());
                }
            }

        } catch (Exception e) {
            System.err.println("Error al obtener géneros: " + e.getMessage());
            e.printStackTrace();
        }
        return generos;
    }




/*
*
* METODO PARA MOSTRAR
*
 */


    public List<String> getLibros() {
        List<String> books = new ArrayList<>();
        ClientSession session = null;

        try {
            // Conexión con BaseX: host, puerto, usuario y contraseña.
            session = new ClientSession(HOST, PORT, USER, PASSWORD);

            // Abrir la base de datos.
            session.execute("OPEN biblioteca");

            // Consulta XQuery que recorre todos los nodos <book> y concatena la información.
            String xquery = "for $book in //book " +
                    "return concat('Título: ', $book/title, ', Autor: ', $book/author, ', Año: ', $book/year, ', Género: ', $book/genre)";

            // Ejecutar la consulta y obtener el resultado (varias líneas separadas por saltos de línea).
            String result = session.query(xquery).execute();

            // Separar el resultado por líneas para crear la lista de libros.
            for (String line : result.split("\\r?\\n")) {
                if (!line.trim().isEmpty()) {
                    books.add(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // Depuración de errores
        } finally {
            try {
                // Asegurarse de cerrar la sesión si está abierta.
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace(); // En caso de error al cerrar la sesión
            }
        }
        return books;
    }

}
