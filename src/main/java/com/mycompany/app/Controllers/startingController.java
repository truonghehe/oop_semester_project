package com.mycompany.app.Controllers;

import static com.mycompany.app.myApplication.exportAccount;

import java.io.IOException;

import com.mycompany.app.myApplication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The startingController class manages the actions and transitions in the starting view of the application.
 */
public class startingController {

    @FXML
    private AnchorPane thisPane;

    @FXML
    private Tooltip tooltip1;

    @FXML
    private Tooltip tooltip2;

    @FXML
    private Tooltip tooltip3;

    /**
     * Logs out the user and navigates to the login view.
     *
     * @param event The mouse event triggering the action.
     * @throws IOException If an error occurs while loading the login view.
     */
    @FXML
    void logOut(MouseEvent event) throws IOException {
        exportAccount();
        Stage stage = (Stage) thisPane.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(myApplication.class.getResource("/Views/loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dictionary");
        stage.setWidth(600);
        stage.setHeight(425);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates to the dictionary view.
     *
     * @param event The mouse event triggering the action.
     * @throws IOException If an error occurs while loading the dictionary view.
     */
    @FXML
    void goToDictionary(MouseEvent event) throws IOException {
        Stage stage = (Stage) thisPane.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(myApplication.class.getResource("/Views/dictionaryView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dictionary");
        stage.setWidth(800);
        stage.setHeight(500);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates to the game/learning view.
     *
     * @param event The mouse event triggering the action.
     * @throws IOException If an error occurs while loading the game/learning view.
     */
    @FXML
    void goToGame(MouseEvent event) throws IOException {
        Stage stage = (Stage) thisPane.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Views/learnView.fxml"));
        stage.setTitle("Learning");
        stage.setHeight(500);
        stage.setWidth(800);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
