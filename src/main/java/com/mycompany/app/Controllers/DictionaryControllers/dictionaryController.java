package com.mycompany.app.Controllers.DictionaryControllers;

import static com.mycompany.app.myApplication.textToSpeech;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.speech.EngineException;

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

/**
 * The dictionaryController class manages the functionality of the main dictionary view.
 */
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

    /**
     * Exits the application, exports the dictionary, and terminates text-to-speech.
     *
     * @param event The mouse click event.
     */
    @FXML
    void exit(MouseEvent event) {
        DictionaryManagement.dictionaryExportToFile("src/main/resources/textFiles/E_V.txt");
        try {
            textToSpeech.terminate();
        } catch (EngineException e) {
            throw new RuntimeException(e);
        }
        Platform.exit();
    }

    /**
     * Navigates to the API view.
     *
     * @param event The mouse click event.
     */
    @FXML
    void goToAPI(MouseEvent event) {
        showComponent("/Views/APIView.fxml");
    }

    /**
     * Navigates to the add word view.
     *
     * @param event The mouse click event.
     */
    @FXML
    void goToAdd(MouseEvent event) {
        showComponent("/Views/addWordView.fxml");
    }

    /**
     * Navigates to the search view.
     *
     * @param event The mouse click event.
     */
    @FXML
    void goToSearch(MouseEvent event) {
        showComponent("/Views/searchView.fxml");
    }

    /**
     * Navigates back to the starting view.
     *
     * @param event The mouse click event.
     * @throws IOException If an error occurs during navigation.
     */
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

    /**
     * Shows the specified FXML component in the container.
     *
     * @param path The path to the FXML component.
     */
    @FXML
    private void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource(path));
            setNode(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller, sets tooltips, sets up the dictionary data, and displays the search view.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources specific to the locale.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tooltip1.setShowDelay(Duration.seconds(0.5));
        tooltip2.setShowDelay(Duration.seconds(0.5));
        tooltip3.setShowDelay(Duration.seconds(0.5));
        tooltip4.setShowDelay(Duration.seconds(0.5));
        tooltip5.setShowDelay(Duration.seconds(0.5));
        DictionaryManagement.setMap();
        showComponent("/Views/searchView.fxml");
    }
}
