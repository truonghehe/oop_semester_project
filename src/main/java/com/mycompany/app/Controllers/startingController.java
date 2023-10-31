package com.mycompany.app.Controllers;

import com.mycompany.app.myApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class startingController {

    @FXML
    private AnchorPane thisPane;

    @FXML
    void exit(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void goToDictionary(MouseEvent event) throws IOException {
        Stage stage = (Stage) thisPane.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(myApplication.class.getResource("/Views/dictionaryView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void goToGame(MouseEvent event) throws IOException {
        Stage stage = (Stage) thisPane.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Views/searchView.fxml"));
        stage.setTitle("Game");
        stage.setScene(new Scene(root));
    }

}
