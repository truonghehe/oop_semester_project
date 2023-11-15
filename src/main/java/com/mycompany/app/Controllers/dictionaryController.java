package com.mycompany.app.Controllers;

import com.mycompany.app.DictionaryManagement;
import com.mycompany.app.myApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.speech.EngineException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.mycompany.app.myApplication.textToSpeech;

public class dictionaryController implements Initializable {
    @FXML
    private AnchorPane container;

    @FXML
    private Tooltip tooltip1;

    @FXML
    private Tooltip tooltip2;

    @FXML
    private Tooltip tooltip3;

    @FXML
    private Tooltip tooltip4;

    @FXML
    private Tooltip tooltip5;

    @FXML
    void exit(MouseEvent event) {
        DictionaryManagement.dictionaryExportToFile("src/main/resources/E_V.txt");
        try {
            textToSpeech.terminate();
        } catch (EngineException e) {
            throw new RuntimeException(e);
        }
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
        tooltip1.setShowDelay(Duration.seconds(0.5));
        tooltip2.setShowDelay(Duration.seconds(0.5));
        tooltip3.setShowDelay(Duration.seconds(0.5));
        tooltip4.setShowDelay(Duration.seconds(0.5));
        tooltip5.setShowDelay(Duration.seconds(0.5));
        try {
            DictionaryManagement.dictionaryImportFromFile("src/main/resources/textFiles/E_V.txt");
            DictionaryManagement.setMap();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        showComponent("/Views/searchView.fxml");
    }
}
