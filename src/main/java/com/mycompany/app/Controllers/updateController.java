package com.mycompany.app.Controllers;

import com.mycompany.app.DictionaryManagement;
import com.mycompany.app.Word;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class updateController implements Initializable {

    @FXML
    private HTMLEditor htmlEditor = new HTMLEditor();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String INITIAL_TEXT = DictionaryManagement.data.get(searchController.selectedItem).getWord_explain();
        htmlEditor.setHtmlText(INITIAL_TEXT);
    }

    @FXML
    void SaveOnClick(MouseEvent event) {
        Word selected = DictionaryManagement.data.get(searchController.selectedItem);
        DictionaryManagement.dictionary.remove(selected);
        DictionaryManagement.data.remove(searchController.selectedItem);
        selected.setWord_explain(htmlEditor.getHtmlText());
        DictionaryManagement.dictionary.add(selected);
        DictionaryManagement.data.put(searchController.selectedItem, selected);
        exit();
    }

    @FXML
    void ReturnOnClick(MouseEvent event) {
        exit();
    }

    @FXML
    void exit() {
        Stage stage = (Stage) htmlEditor.getScene().getWindow();
        stage.close();
    }
}
