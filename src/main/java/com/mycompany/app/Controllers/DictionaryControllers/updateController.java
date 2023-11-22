package com.mycompany.app.Controllers.DictionaryControllers;

import static com.mycompany.app.Controllers.DictionaryControllers.searchController.selectedItem;

import java.net.URL;
import java.util.ResourceBundle;

import com.mycompany.app.DictionaryManagement;
import com.mycompany.app.Word;
import com.mycompany.app.Alert.Alerts;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;

/**
 * The updateController class manages the actions and updates in the update word view of the application.
 */

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
                alerts.showAlertInfo("Information!", "Word updated successfully!");
                btSave.setDisable(true);
            }
        });

        btSave.setDisable(true);
    }

    /**
     * Saves the updated word when the save button is clicked.
     */
    @FXML
    public void SaveOnClick() {
        Word selected = DictionaryManagement.data.get(selectedItem);
        DictionaryManagement.dictionary.remove(selected);
        DictionaryManagement.data.remove(selectedItem);
        String newWord_Explain = htmlEditor.getHtmlText();
        selected.setWord_explain(newWord_Explain.replace("<html dir=\"ltr\">", "<html>"));
        DictionaryManagement.dictionary.add(selected);
        DictionaryManagement.data.put(selectedItem, selected);
    }

    /**
     * Enables the save button when the HTML editor is clicked.
     *
     * @param event The mouse event triggering the action.
     */
    @FXML
    public void htmlEditorMouseClick(MouseEvent event) {
        btSave.setDisable(false);
    }
}
