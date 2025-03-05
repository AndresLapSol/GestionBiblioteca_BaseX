package org.example.gestionbiblioteca_basex.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.gestionbiblioteca_basex.Repositories.CreateRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CreateController {

    CreateRepository createRepository = new CreateRepository();

    @FXML private Button buttonInsert;
    @FXML private ChoiceBox choiceBoxGene;
    @FXML private TextField textFieldYear;
    @FXML private TextField textFieldAuthor;
    @FXML private TextField textFieldTitle;

    @FXML
    public void initialize() throws SQLException {

        textFieldYear.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textFieldYear.setText(oldValue);
            }
        });

        cargarGeneros();


    }

    @FXML
    public void insertAction() throws SQLException {
        String title = textFieldTitle.getText();
        String author = textFieldAuthor.getText();
        String year = textFieldYear.getText();
        String genre = choiceBoxGene.getSelectionModel().getSelectedItem().toString();
        createRepository.insertBook(title, author, year, genre);

    }

    @FXML
    public void crudGenre() throws SQLException {
        try {
            // Cargar el archivo FXML de la nueva ventana
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/gestionbiblioteca_basex/create-genre.fxml"));
            Scene nuevaEscena = new Scene(fxmlLoader.load());

            // Crear un nuevo Stage para la ventana secundaria
            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(nuevaEscena);

            // Mostrar la nueva ventana
            nuevaVentana.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cerrarVentana() throws SQLException {
        Stage stage = (Stage) buttonInsert.getScene().getWindow();
        stage.close();
    }

    private void cargarGeneros() {
        // Obtener la lista de géneros desde el repositorio
        List<String> generos = createRepository.getGeneros();

        // Cargar la lista de géneros en el ChoiceBox
        ObservableList<String> generosObservable = FXCollections.observableArrayList(generos);
        choiceBoxGene.setItems(generosObservable);

        // Opcional: Seleccionar el primer género por defecto
        if (!generos.isEmpty()) {
            choiceBoxGene.setValue(generos.get(0));
        }
    }

}
