package com.mycompany.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PracticeController implements Initializable {
    @FXML
    private ListView<String> listView = new ListView<>();
    @FXML
    private Button home;
    @FXML
    ObservableList<String> observableList ;
    @FXML
    private TextField searchField;
    @FXML
    private WebView webView;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DictionaryManagement.dictionaryImportFromFile("D:\\GitHub\\OOP_demo\\data\\E_V.txt");
            DictionaryManagement.setMap();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        observableList = FXCollections.observableArrayList();
        for ( int i = 0 ; i < DictionaryManagement.dictionary.size() ; i++){
            observableList.add(DictionaryManagement.dictionary.get(i).getWord_target());
        }
        listView.getItems().addAll(observableList);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String word_explainExplain = DictionaryManagement.data.get(newValue).getWord_explain();
                webView.getEngine().loadContent(word_explainExplain,"text/html");
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<String> filteredList = FXCollections.observableArrayList();
            for (String item : observableList) {
                if (item.toLowerCase().contains(newValue.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            listView.setItems(filteredList);
        });
    }

}
