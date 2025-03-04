package org.example.gestionbiblioteca_basex;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.gestionbiblioteca_basex.Repositories.Repository;

import java.io.IOException;
import java.util.List;

public class MainController {
    @FXML
    private Label welcomeText;
    Repository repository = new Repository();

    @FXML
    protected void abrirCreate(){
        try {
            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("create-view.fxml"));
            Scene nuevaEscena = new Scene(fxmlLoader.load());

            // Crear un nuevo Stage para la ventana secundaria
            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(nuevaEscena);

            // Mostrar la nueva ventana
            nuevaVentana.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    @FXML
    protected void abrirOther(){
        try {
            System.out.println("Cargando deleteupdate.fxml...");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("deleteupdate.fxml"));
            Parent root = fxmlLoader.load(); // Cargar el FXML
            System.out.println("Archivo FXML cargado correctamente.");

            Scene nuevaEscena = new Scene(root);
            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(nuevaEscena);
            nuevaVentana.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar deleteupdatefxml: " + e.getMessage());
        }
    };

    @FXML
    private void handleShowBooksButton() {
        List<String> books = repository.getBooks();
        System.out.println("Lista de libros en la base de datos:");
        System.out.println("====================================");
        for (String book : books) {
            System.out.println(book);
            System.out.println("====================================");
        }
    }
}
