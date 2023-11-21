package com.mycompany.app.Controllers.GameControllers;

import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The learnController class manages the navigation within the learning section of the application.
 */
public class learnController {

    @FXML
    private AnchorPane thisPane;

    /**
     * Switches to the My Game view when triggered by a mouse click event.
     *
     * @param event The mouse click event.
     * @throws IOException If an error occurs during the loading of the My Game view.
     */
    @FXML
    void goToMyGame(MouseEvent event) throws IOException {
        Stage stage = (Stage) thisPane.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/myGameView.fxml")));
        stage.setTitle("Word Graft");
        stage.setHeight(500);
        stage.setWidth(800);
        stage.setScene(new Scene(root));
    }

    /**
     * Switches to the Vocabulary view when triggered by a mouse click event.
     *
     * @param event The mouse click event.
     * @throws IOException If an error occurs during the loading of the Vocabulary view.
     */
    @FXML
    void goToVocab(MouseEvent event) throws IOException {
        Stage stage = (Stage) thisPane.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/vocabularyView.fxml")));
        stage.setTitle("Vocabulary");
        stage.setHeight(500);
        stage.setWidth(800);
        stage.setScene(new Scene(root));
    }

    /**
     * Switches to the Listening view when triggered by a mouse click event.
     *
     * @param event The mouse click event.
     * @throws IOException If an error occurs during the loading of the Listening view.
     */
    @FXML
    void goToListen(MouseEvent event) throws IOException {
        Stage stage = (Stage) thisPane.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/listeningView.fxml")));
        stage.setTitle("Listening");
        stage.setHeight(500);
        stage.setWidth(800);
        stage.setScene(new Scene(root));
    }

    /**
     * Navigates back to the starting view when triggered by a mouse click event.
     *
     * @param event The mouse click event.
     * @throws IOException If an error occurs during the loading of the Starting view.
     */
    @FXML
    void back(MouseEvent event) throws IOException {
        Stage stage = (Stage) thisPane.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/startingView.fxml")));
        stage.setTitle("Welcome to our application!");
        stage.setHeight(500);
        stage.setWidth(800);
        stage.setScene(new Scene(root));
    }
}
