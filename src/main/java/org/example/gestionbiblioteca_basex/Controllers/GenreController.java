package org.example.gestionbiblioteca_basex.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.TextField;
import org.example.gestionbiblioteca_basex.Repositories.GenreRepository;

import java.util.List;

public class GenreController {
    @FXML private TextField textFieldNuevoGenero;
    @FXML private ChoiceBox choiceBoxGene;
    private GenreRepository genreRepository = new GenreRepository();


    public void initialize() {
            // Cargar géneros en el ChoiceBox
            cargarGeneros();
    }

    private void cargarGeneros() {
        // Obtener la lista de géneros desde el repositorio
        List<String> generos = genreRepository.getGeneros();

        // Cargar la lista de géneros en el ChoiceBox
        ObservableList<String> generosObservable = FXCollections.observableArrayList(generos);
        choiceBoxGene.setItems(generosObservable);

        if (!generos.isEmpty()) {
            choiceBoxGene.setValue(generos.get(0));
        }
    }

    @FXML
    private void eliminarLibrosPorGenero() {
        String generoSeleccionado = (String) choiceBoxGene.getValue();
        if (generoSeleccionado != null && !generoSeleccionado.isEmpty()) {
            GenreRepository.deleteBooksByGenre(generoSeleccionado);
        }
    }

    @FXML
    private void insertarNuevoGenero() {
        String nuevoGenero = textFieldNuevoGenero.getText();
        if (nuevoGenero != null && !nuevoGenero.isEmpty()) {
            genreRepository.insertGenre(nuevoGenero);
            cargarGeneros(); // Para actualizar la lista en la interfaz
        }
    }



}
