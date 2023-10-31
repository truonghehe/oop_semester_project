package com.mycompany.app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DictionaryController implements Initializable {
    @FXML
    private Button practice;
    @FXML
    private Button game;
    @FXML
    private Button exit;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        practice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showComponent("Practice.fxml");
            }
        });
        exit.setOnMouseClicked(e -> {
            DictionaryManagement.dictionaryExportToFile("");
            System.exit(0);
        });
    }
    @FXML
    private void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource(path));
            setNode(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }
    @FXML
    private AnchorPane container;
}
