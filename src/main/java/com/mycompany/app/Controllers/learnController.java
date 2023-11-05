package com.mycompany.app.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class learnController {

    @FXML
    private AnchorPane thisPane;

    @FXML
    void goToMyGame(MouseEvent event) throws IOException {
        Stage stage = (Stage) thisPane.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/myGameView.fxml")));
        stage.setTitle("Game");
        stage.setScene(new Scene(root));
    }
    @FXML
    void goToVocab(MouseEvent event) throws IOException {
        Stage stage = (Stage) thisPane.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/vocabularyView.fxml")));
        stage.setTitle("Vocabulary");
        stage.setScene(new Scene(root));
    }

}