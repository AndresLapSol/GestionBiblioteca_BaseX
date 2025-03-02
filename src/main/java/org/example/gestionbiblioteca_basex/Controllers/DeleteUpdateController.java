package org.example.gestionbiblioteca_basex.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.gestionbiblioteca_basex.Repositories.Repository;

import java.util.List;

public class DeleteUpdateController {

    @FXML private ChoiceBox<String> choiceBoxTitle;
    @FXML private TextField textFieldAuthor;
    @FXML private TextField textFieldYear;
    @FXML private ChoiceBox<String> choiceBoxGene;

    private Repository repository = new Repository();

    @FXML
    public void initialize() {
        // Llenar el ChoiceBox con los títulos de los libros
        List<String> titles = repository.getAllTitles();
        choiceBoxTitle.getItems().addAll(titles);

        // Manejar la selección de un título
        choiceBoxTitle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Aquí puedes cargar los detalles del libro seleccionado si lo necesitas
            }
        });

        choiceBoxGene.getItems().addAll(
                "Romantico",
                "Biblico",
                "Cocina",
                "Aventura"
        );
    }

    @FXML
    private void handleUpdateButton() {
        String selectedTitle = choiceBoxTitle.getValue();
        String newTitle = choiceBoxTitle.getValue(); // Puedes cambiar esto si tienes un campo para el nuevo título
        String newAuthor = textFieldAuthor.getText();
        String newYear = textFieldYear.getText();
        String newGenre = choiceBoxGene.getValue();

        if (selectedTitle != null && newAuthor != null && newYear != null && newGenre != null) {
            repository.updateBook(selectedTitle, newTitle, newAuthor, newYear, newGenre);
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
        } else {
            System.out.println("Por favor, seleccione un título para eliminar.");
        }
    }
}