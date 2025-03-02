package org.example.gestionbiblioteca_basex.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.example.gestionbiblioteca_basex.Repositories.Repository;

import java.sql.SQLException;

public class CreateController {

    Repository repository = new Repository();

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

        choiceBoxGene.getItems().addAll(
                "Romantico",
                "Biblico",
                "Cocina",
                "Aventura"
        );


    }

    public void insertAction() throws SQLException {
        String title = textFieldTitle.getText();
        String author = textFieldAuthor.getText();
        String year = textFieldYear.getText();
        String genre = choiceBoxGene.getSelectionModel().getSelectedItem().toString();
        repository.insertBook(title, author, year, genre);


    }
}
