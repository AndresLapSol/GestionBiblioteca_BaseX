package org.example.gestionbiblioteca_basex.Repositories;

import org.basex.api.client.ClientSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteUpdateRepository {

    private static final String HOST = "localhost";
    private static final int PORT = 1984;
    private static final String USER = "Goku";
    private static final String PASSWORD = "Goku";
    private static final String DATABASE_NAME = "biblioteca";

    // Método para actualizar un libro
    public void updateBook(String oldTitle, String newTitle, String newAuthor, String newYear, String newGenre) {
        try (ClientSession session = new ClientSession(HOST, PORT, USER, PASSWORD)) {
            session.execute("OPEN " + DATABASE_NAME); // Abrir la base de datos

            // Escapar comillas
            String safeOldTitle = oldTitle.replace("'", "''");
            String safeNewTitle = newTitle.replace("'", "''");

            String queryUpdate =
                    "declare option output:indent \"yes\";\n" +
                            "for $book in doc('" + DATABASE_NAME + "')/library/books/book[title='" + safeOldTitle + "']\n" +
                            "return (\n" +
                            "   replace node $book/title with <title>" + safeNewTitle + "</title>,\n" +
                            "   replace node $book/author with <author>" + newAuthor + "</author>,\n" +
                            "   replace node $book/year with <year>" + newYear + "</year>,\n" +
                            "   replace node $book/genre with <genre>" + newGenre + "</genre>\n" +
                            ")";

            session.execute("XQUERY " + queryUpdate);
            System.out.println("Libro actualizado correctamente.");

        } catch (Exception e) {
            System.err.println("Error al actualizar el libro: " + e.getMessage());
        }
    }

    // Método para eliminar un libro
    public void deleteBook(String title) {
        try (ClientSession session = new ClientSession(HOST, PORT, USER, PASSWORD)) {
            session.execute("OPEN " + DATABASE_NAME);

            // Escapar comillas simples en el título
            String safeTitle = title.replace("'", "''");

            String queryDelete =
                    "delete node doc('" + DATABASE_NAME + "')/library/books/book[title='" + safeTitle + "']";

            session.execute("XQUERY " + queryDelete);
            System.out.println("Libro eliminado correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar el libro: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public List<String> getAllTitles() {
        List<String> titles = new ArrayList<>();
        try (ClientSession session = new ClientSession(HOST, PORT, USER, PASSWORD)) {
            session.execute("OPEN " + DATABASE_NAME); // Abrir la base de datos
            String result = session.execute("XQUERY for $t in doc('" + DATABASE_NAME + "')/library/books/book/title return string($t)");
            for (String title : result.split("\\r?\\n")) {
                if (!title.isBlank()) titles.add(title.trim());
            }
        } catch (Exception e) {
            System.err.println("Error en getAllTitles(): " + e.getMessage());
        }
        return titles;
    }

    public Map<String, String> getBookByTitle(String title) {
        Map<String, String> bookData = new HashMap<>();
        try (ClientSession session = new ClientSession(HOST, PORT, USER, PASSWORD)) {
            // Escapar comillas simples en el título si las tiene
            String safeTitle = title.replace("'", "''");
            String query =
                    "let $b := doc('" + DATABASE_NAME + "')/library/books/book[title='" + safeTitle + "'] " +
                            "return (data($b/author), data($b/year), data($b/genre))";

            String result = session.execute("XQUERY " + query);
            String[] details = result.split("\n");

            if (details.length >= 3) {
                bookData.put("author", details[0].trim());
                bookData.put("year", details[1].trim());
                bookData.put("genre", details[2].trim());
            }
        } catch (Exception e) {
            System.err.println("Error al obtener datos del libro: " + e.getMessage());
        }
        return bookData;
    }

    public List<String> getGeneros() {
        List<String> generos = new ArrayList<>();
        ClientSession session = null;

        try {
            session = new ClientSession(HOST, PORT, USER, PASSWORD);
            System.out.println("Conectado a BaseX para obtener géneros.");

            // XQuery para obtener todos los géneros desde <genres> en el XML
            String query = "doc('biblioteca')/library/genres/genre/string()";

            // Ejecutar consulta en BaseX
            String result = session.execute("XQUERY " + query);

            // Separar por líneas
            String[] genreArray = result.split("\n");
            for (String genre : genreArray) {
                generos.add(genre.trim());
            }

        } catch (Exception e) {
            System.err.println("Error al obtener géneros: " + e.getMessage());
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

        return generos;
    }

}
