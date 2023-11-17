package com.mycompany.app.Controllers;


import com.mycompany.app.myApplication;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;


import java.io.IOException;


import static com.mycompany.app.myApplication.exportAccount;


public class startingController {

    @FXML
    private AnchorPane thisPane;

    @FXML
    private Tooltip tooltip1;

    @FXML
    private Tooltip tooltip2;

    @FXML
    private Tooltip tooltip3;

    @FXML
    void logOut(MouseEvent event) throws IOException {
        exportAccount();
        Stage stage = (Stage) thisPane.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(myApplication.class.getResource("/Views/loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dictionary");
        stage.setHeight(400);
        stage.setWidth(600);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void goToDictionary(MouseEvent event) throws IOException {
        Stage stage = (Stage) thisPane.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(myApplication.class.getResource("/Views/dictionaryView.fxml"));
        Scene scene = new Scene(fxmlLoader. load());
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void goToGame(MouseEvent event) throws IOException {
        Stage stage = (Stage) thisPane.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Views/learnView.fxml"));
        stage.setTitle("Learning");
        stage.setHeight(500);
        stage.setWidth(800);
        stage.setScene(new Scene(root));
    }
}
