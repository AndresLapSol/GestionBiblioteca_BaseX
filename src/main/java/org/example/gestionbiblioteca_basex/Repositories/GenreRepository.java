package org.example.gestionbiblioteca_basex.Repositories;

import org.basex.api.client.ClientSession;
import java.util.ArrayList;
import java.util.List;

public class GenreRepository {

    private static final String HOST = "localhost";
    private static final int PORT = 1984;
    private static final String USER = "Goku";
    private static final String PASSWORD = "Goku";

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

    public static void deleteBooksByGenre(String genre) {
        ClientSession session = null;
        try {
            session = new ClientSession(HOST, PORT, USER, PASSWORD);
            System.out.println("Eliminando libros con género: " + genre);

            // Consulta XQuery para eliminar libros con el género seleccionado
            String query = "delete node doc('biblioteca')/library/books/book[genre = '" + genre + "']";

            session.execute("XQUERY " + query);
            System.out.println("Se eliminaron los libros del género: " + genre);
        } catch (Exception e) {
            System.err.println("Error al eliminar libros: " + e.getMessage());
        } finally {
            if (session != null) {
                try {
                    session.close();
                    System.out.println("Sesión cerrada correctamente.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertGenre(String genre) {
        ClientSession session = null;
        try {
            session = new ClientSession(HOST, PORT, USER, PASSWORD);
            System.out.println("Agregando nuevo género: " + genre);

            // Verificar si el género ya existe antes de agregarlo
            String checkQuery = "count(doc('biblioteca')/library/genres/genre[text() = '" + genre + "'])";
            String countResult = session.execute("XQUERY " + checkQuery);

            if ("0".equals(countResult.trim())) {
                // Insertar género si no existe
                String query = "insert node <genre>" + genre + "</genre> into doc('biblioteca')/library/genres";
                session.execute("XQUERY " + query);
                System.out.println("Género agregado correctamente: " + genre);
            } else {
                System.out.println("El género ya existe en la base de datos.");
            }

        } catch (Exception e) {
            System.err.println("Error al agregar género: " + e.getMessage());
        } finally {
            if (session != null) {
                try {
                    session.close();
                    System.out.println("Sesión cerrada correctamente.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
