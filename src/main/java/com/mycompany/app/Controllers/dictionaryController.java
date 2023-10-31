package com.mycompany.app.Controllers;

import com.mycompany.app.DictionaryManagement;
import com.mycompany.app.myApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class dictionaryController implements Initializable {

    @FXML
    void exit(MouseEvent event) {
        DictionaryManagement.dictionaryExportToFile("/E_V.txt");
        Platform.exit();
    }

    @FXML
    void goToAPI(MouseEvent event) {
        showComponent("/Views/APIView.fxml");
    }

    @FXML
    void goToAdd(MouseEvent event) {
        showComponent("/Views/addWordView.fxml");
    }

    @FXML
    void goToSearch(MouseEvent event) {
        showComponent("/Views/searchView.fxml");
    }

    @FXML
    void back(MouseEvent event) throws IOException {
        Stage stage = (Stage) container.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(myApplication.class.getResource("/Views/startingView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private AnchorPane container;

    private void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showComponent("/Views/searchView.fxml");
    }
}
