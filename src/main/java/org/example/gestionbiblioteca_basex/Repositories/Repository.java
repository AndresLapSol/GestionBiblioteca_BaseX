package org.example.gestionbiblioteca_basex.Repositories;

import org.basex.api.client.ClientSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Repository {



    public void insertBook(String title, String author, String year, String genre) {
        ClientSession session = null;
        try {
            // Crear sesión de cliente BaseX
            session = new ClientSession("localhost", 1984, "Goku", "Goku");
            System.out.println("Conectado a BaseX.");

            // Verificar si la base de datos 'biblioteca' existe
            String queryCheck = "if(db:exists('biblioteca')) then 'OK' else 'ERROR'";
            String resultCheck = session.execute("XQUERY " + queryCheck);

            if ("ERROR".equals(resultCheck)) {
                System.out.println("Error: La base de datos no existe.");
                return;
            } else {
                System.out.println("Base de datos encontrada.");
            }

            // Consulta para insertar un nuevo libro dentro de <library>
            String queryInsert = "declare option output:indent \"yes\";" +
                    "insert node " +
                            "<book>" +
                            "<title>" + title + "</title>" +
                            "<author>" + author + "</author>" +
                            "<year>" + year + "</year>" +
                            "<genre>" + genre + "</genre>" +
                            "</book> " +
                            "into doc('biblioteca')/library";

            // Ejecutar la inserción
            String resultInsert = session.execute("XQUERY " + queryInsert);
            System.out.println("Libro Insertado correctamente.");

        } catch (Exception e) {
            // Capturar y mostrar cualquier excepción
            System.err.println("Error al insertar el libro: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Asegurarse de cerrar la sesión si está abierta
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
    }

    // Método para actualizar un libro
    public void updateBook(String oldTitle, String newTitle, String newAuthor, String newYear, String newGenre) {
        ClientSession session = null;
        try {
            session = new ClientSession("localhost", 1984, "Goku", "Goku");
            System.out.println("Conectado a BaseX.");

            // Consulta para actualizar un libro
            String queryUpdate = "declare option output:indent \"yes\";" +
                    "for $book in doc('biblioteca')/library/book[title='" + oldTitle + "'] " +
                    "return (" +
                    "replace node $book/title with <title>" + newTitle + "</title>," +
                    "replace node $book/author with <author>" + newAuthor + "</author>," +
                    "replace node $book/year with <year>" + newYear + "</year>," +
                    "replace node $book/genre with <genre>" + newGenre + "</genre>" +
                    ")";

            String resultUpdate = session.execute("XQUERY " + queryUpdate);
            System.out.println("Libro actualizado correctamente.");

        } catch (Exception e) {
            System.err.println("Error al actualizar el libro: " + e.getMessage());
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
    }

    // Método para eliminar un libro
    public void deleteBook(String title) {
        ClientSession session = null;
        try {
            session = new ClientSession("localhost", 1984, "Goku", "Goku");
            System.out.println("Conectado a BaseX.");

            // Consulta para eliminar un libro
            String queryDelete = "declare option output:indent \"yes\";" +
                    "delete node doc('biblioteca')/library/book[title='" + title + "']";

            String resultDelete = session.execute("XQUERY " + queryDelete);
            System.out.println("Libro eliminado correctamente.");

        } catch (Exception e) {
            System.err.println("Error al eliminar el libro: " + e.getMessage());
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
    }

    // Método para obtener todos los títulos de los libros
    public List<String> getAllTitles() {
        ClientSession session = null;
        List<String> titles = new ArrayList<>();
        try {
            session = new ClientSession("localhost", 1984, "Goku", "Goku");
            System.out.println("Conectado a BaseX.");

            // Consulta para obtener todos los títulos
            String queryTitles = "for $title in doc('biblioteca')/library/book/title " +
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

}
