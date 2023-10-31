package com.mycompany.app.Controllers;

import com.mycompany.app.Alert.Alerts;
import com.mycompany.app.DictionaryManagement;
import com.mycompany.app.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

public class searchController implements Initializable {
    @FXML
    private ListView<String> listView;

    @FXML
    private ObservableList<String> observableList = FXCollections.observableArrayList();

    @FXML
    private ObservableList<String> filteredList = FXCollections.observableArrayList();

    @FXML
    private TextField searchField;

    @FXML
    private WebView webView;

    @FXML
    private Button btDelete;

    @FXML
    private Button btChange;

    @FXML
    private Alerts alerts = new Alerts();

    @FXML
    private Tooltip tooltip1;

    @FXML
    private Tooltip tooltip2;

    @FXML
    private Tooltip tooltip3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DictionaryManagement.dictionaryImportFromFile("src/main/resources/E_V.txt");
            DictionaryManagement.setMap();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addToObservableList();
        observableList.sort(String::compareTo);
        listView.setItems(observableList);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String word_explainExplain = DictionaryManagement.data.get(newValue).getWord_explain();
                webView.getEngine().loadContent(word_explainExplain, "text/html");
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.clear();
            for (String item : observableList) {
                if (item.toLowerCase().contains(newValue.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            listView.setItems(filteredList);
        });
        tooltip1.setShowDelay(Duration.seconds(0.5));
        tooltip2.setShowDelay(Duration.seconds(0.5));
        tooltip3.setShowDelay(Duration.seconds(0.5));
        btDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteWord();
                listView.refresh();
            }
        });

        // sua vao day nha Tung oi!!!!
        btChange.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
    }

    @FXML
    public void deleteWord() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        Alert alertWarning = alerts.alertWarning("Delete", "Bạn chắc chắn muốn xoá từ này?");
        alertWarning.getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> optional = alertWarning.showAndWait();
        if (optional.get() == ButtonType.OK) {
            observableList.remove(selectedItem);
            filteredList.remove(selectedItem);
            Word selectedWord = DictionaryManagement.data.get(selectedItem);
            DictionaryManagement.dictionary.remove(selectedWord);
            alerts.showAlertInfo("Information" , "Xoá thành công");
        }
        else alerts.showAlertInfo("Information" , "Xoá thất bại");
    }

    private void addToObservableList() {
        observableList.clear();
        for (int i = 0; i < DictionaryManagement.dictionary.size(); i++) {
            observableList.add(DictionaryManagement.dictionary.get(i).getWord_target());
        }
    }
}
