package com.mycompany.app.Controllers.DictionaryControllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mycompany.app.DictionaryManagement;
import com.mycompany.app.Word;
import com.mycompany.app.Alert.Alerts;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * The addWordController class handles the logic for adding new words in the application.
 */
public class addWordController implements Initializable {

    private static final String SEPARATOR = "<html>";

    @FXML
    private TextField englishWord;

    @FXML
    private TextArea vietnameseWord;

    @FXML
    private TextField wordType;

    @FXML
    private TextField spelling;

    @FXML
    private Tooltip tooltip1;

    private final Alerts alerts = new Alerts();

    /**
     * Handles the "Add" button click event.
     *
     * @param event The mouse click event.
     */
    @FXML
    void add(MouseEvent event) {
        if (fieldsAreEmpty()) {
            showAlert("Error!", "Cannot leave 'New English word' or 'New Vietnamese word' empty.");
        } else if (wordExists()) {
            handleExistingWord();
        } else {
            handleNewWord();
        }
    }

    private boolean fieldsAreEmpty() {
        return englishWord.getText().isEmpty() || vietnameseWord.getText().isEmpty();
    }

    private boolean wordExists() {
        return DictionaryManagement.data.containsKey(englishWord.getText());
    }

    private void handleExistingWord() {
        Alert alertConfirmation = alerts.alertConfirmation("Confirmation!",
                "The word \"" + englishWord.getText() + "\" already exists. Do you want to change its meaning?");
        Optional<ButtonType> result = alertConfirmation.showAndWait();
        if (result.isEmpty() || result.get() == ButtonType.CANCEL) {
            return;
        }
        Word word = DictionaryManagement.data.get(englishWord.getText());
        DictionaryManagement.data.remove(englishWord.getText());
        DictionaryManagement.dictionary.remove(word);
        addWord();
    }

    private void handleNewWord() {
        Alert alertConfirmation = alerts.alertConfirmation("Confirmation",
                "Do you want to add the word \"" + englishWord.getText() + "\" with meaning \"" + vietnameseWord.getText() + "\"?");
        Optional<ButtonType> result = alertConfirmation.showAndWait();
        if (result.isEmpty() || result.get() == ButtonType.CANCEL) {
            return;
        }
        addWord();
    }

    private void addWord() {
        Word word = new Word();
        word.setWord_target(englishWord.getText());
        String wordExplain = generateWordExplain();
        word.setWord_explain(wordExplain);
        DictionaryManagement.dictionary.add(word);
        DictionaryManagement.data.put(word.getWord_target(), word);
    }

    private String generateWordExplain() {
        String baseExplain = SEPARATOR + "<i>" + englishWord.getText() + " " + spelling.getText() + "</i><br/>";
        String vietnameseExplain = "<ul><li><font color='#cc0000'><b> " + vietnameseWord.getText() + "</b></font></li></ul>";
        return (wordType.getText().isEmpty()) ? baseExplain + vietnameseExplain + SEPARATOR :
                baseExplain + "<ul><li><b><i> " + wordType.getText() + "</i></b><ul><li>" + vietnameseExplain + "</li></ul></li></ul>" + SEPARATOR;
    }

    private void showAlert(String title, String content) {
        Alert alertWarning = alerts.alertWarning(title, content);
        alertWarning.showAndWait();
    }

    /**
     * Initializes the controller.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources specific to the locale.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tooltip1.setShowDelay(Duration.seconds(0.5));
    }
}
