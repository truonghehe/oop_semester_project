package com.mycompany.app.Controllers;

import com.mycompany.app.Alert.Alerts;
import com.mycompany.app.DictionaryManagement;
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
    private Alerts alerts = new Alerts();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DictionaryManagement.dictionaryImportFromFile("/Users/chuongdz/Downloads/OOP_demo/data/E_V.txt");
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
        btDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteWord();
                listView.refresh();
            }
        });
    }

    @FXML
    public void deleteWord() {
        String selectedItem = listView.getSelectionModel().getSelectedItem();
        Alert alertWarning = alerts.alertWarning("Delete", "Ban chac chan muon xoa tu nay");
        alertWarning.getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> optional = alertWarning.showAndWait();
        if (optional.get() == ButtonType.OK) {
            observableList.remove(selectedItem);
            filteredList.remove(selectedItem);
            Word selectedWord = DictionaryManagement.data.get(selectedItem);
            DictionaryManagement.dictionary.remove(selectedWord);
            alerts.showAlertInfo("Information" , "Xoa thanh cong");
        }
        else alerts.showAlertInfo("Information" , "Xoa khong thanh cong");
    }

    private void addToObservableList() {
        observableList.clear();
        for (int i = 0; i < DictionaryManagement.dictionary.size(); i++) {
            observableList.add(DictionaryManagement.dictionary.get(i).getWord_target());
        }
    }
}
