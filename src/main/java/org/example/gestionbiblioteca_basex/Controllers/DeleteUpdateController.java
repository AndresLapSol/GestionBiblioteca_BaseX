package org.example.gestionbiblioteca_basex.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.gestionbiblioteca_basex.Repositories.CreateRepository;
import org.example.gestionbiblioteca_basex.Repositories.DeleteUpdateRepository;

import java.util.List;
import java.util.Map;

public class DeleteUpdateController {

    @FXML
    private ChoiceBox<String> choiceBoxTitle;
    @FXML
    private TextField textFieldAuthor;
    @FXML
    private TextField textFieldYear;
    @FXML
    private ChoiceBox<String> choiceBoxGene;

    private DeleteUpdateRepository repository = new DeleteUpdateRepository();

    @FXML
    public void initialize() {
        // Llenar el ChoiceBox con los títulos de los libros desde BaseX
        List<String> titles = repository.getAllTitles();
        choiceBoxTitle.getItems().addAll(titles);

        //Choicebox generos
        cargarGeneros();

        // Manejar la selección de un título y autocompletar los campos
        choiceBoxTitle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Obtener los datos del libro desde BaseX
                Map<String, String> bookData = repository.getBookByTitle(newValue);
                if (bookData != null) {
                    textFieldAuthor.setText(bookData.get("author"));
                    textFieldYear.setText(bookData.get("year"));
                    choiceBoxGene.setValue(bookData.get("genre"));
                }
            }
        });
    }

    @FXML
    private void handleUpdateButton() {
        String selectedTitle = choiceBoxTitle.getValue();
        String newTitle = choiceBoxTitle.getValue();
        String newAuthor = textFieldAuthor.getText();
        String newYear = textFieldYear.getText();
        String newGenre = choiceBoxGene.getValue();

        if (selectedTitle != null && newAuthor != null && newYear != null && newGenre != null) {
            repository.updateBook(selectedTitle, newTitle, newAuthor, newYear, newGenre);

            // Actualizar la lista de títulos
            choiceBoxTitle.getItems().setAll(repository.getAllTitles());

            // Limpiar campos
            textFieldAuthor.clear();
            textFieldYear.clear();
            choiceBoxGene.setValue(null);
        } else {
            System.out.println("Por favor, complete todos los campos.");
        }
    }

    @FXML
    private void handleDeleteButton() {
        String selectedTitle = choiceBoxTitle.getValue();

        if (selectedTitle != null) {
            repository.deleteBook(selectedTitle);
            // Actualizar la lista de títulos después de eliminar
            choiceBoxTitle.getItems().clear();
            choiceBoxTitle.getItems().addAll(repository.getAllTitles());
            textFieldAuthor.clear();
            textFieldYear.clear();
            choiceBoxGene.setValue(null);
        } else {
            System.out.println("Por favor, seleccione un título para eliminar.");
        }
    }


    private void cargarGeneros() {
        // Obtener la lista de géneros desde el repositorio
        List<String> generos = repository.getGeneros();

        // Cargar la lista de géneros en el ChoiceBox
        ObservableList<String> generosObservable = FXCollections.observableArrayList(generos);
        choiceBoxGene.setItems(generosObservable);

        if (!generos.isEmpty()) {
            choiceBoxGene.setValue(generos.get(0));
        }
    }
}
