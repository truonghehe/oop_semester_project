package com.mycompany.app.Controller;

import com.mycompany.app.CommandLine.DictionaryManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import static com.mycompany.app.CommandLine.Dictionary.dictionary;
public class PracticeController implements Initializable {
    @FXML
    public ListView<String> listView;
    @FXML
    public WebView webView;
    @FXML
    public TextField findWord;
    @FXML
    public TextField addWord;
    @FXML
    public ObservableList<String> observable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DictionaryManagement.dictionaryImportFromFile("D:\\GitHub\\OOP_demo\\data\\E_V.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> items = FXCollections.observableArrayList(dictionary.keySet());
        listView.getItems().addAll(items);
//        listView.getSelectionModel().selectedItemProperty().addListener(
//                (observable, oldValue, newValue) -> {
//                    Word selectedWord = dictionary.get(newValue.trim());
//                    String definition = selectedWord.getWord_explain();
//                    webView.getEngine().loadContent(definition, "text/html");
//                }
//        );
    }


}
