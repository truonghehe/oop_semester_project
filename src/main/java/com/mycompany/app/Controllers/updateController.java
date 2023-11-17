package com.mycompany.app.Controllers;

import com.mycompany.app.Alert.Alerts;
import com.mycompany.app.DictionaryManagement;
import com.mycompany.app.Word;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import static com.mycompany.app.Controllers.searchController.selectedItem;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class updateController implements Initializable {

    @FXML
    private HTMLEditor htmlEditor = new HTMLEditor();
    @FXML
    private Button btSave;
    private Alerts alerts = new Alerts();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String INITIAL_TEXT = DictionaryManagement.data.get(selectedItem).getWord_explain();
        htmlEditor.setHtmlText(INITIAL_TEXT);
        btSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SaveOnClick();
                alerts.showAlertInfo("Information!" , "Sửa từ thành công!");
                btSave.setDisable(true);
            }
        });
        btSave.setDisable(true);
    }
    @FXML
    public void SaveOnClick() {
        Word selected = DictionaryManagement.data.get(selectedItem);
        DictionaryManagement.dictionary.remove(selected);
        DictionaryManagement.data.remove(selectedItem);
        String newWord_Explain = htmlEditor.getHtmlText() ;
        selected.setWord_explain(newWord_Explain.replace("<html dir=\"ltr\">" , "<html>"));
        DictionaryManagement.dictionary.add(selected);
        DictionaryManagement.data.put(selectedItem, selected);
    }
    @FXML
    public void htmlEditorMouseClick(MouseEvent event){
        btSave.setDisable(false);
    }
}
