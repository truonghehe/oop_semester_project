package com.mycompany.app.Controllers;

import com.mycompany.app.Alert.Alerts;
import com.mycompany.app.DictionaryManagement;
import com.mycompany.app.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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

    @FXML
    void add(MouseEvent event) {
        if (fieldsAreEmpty()) {
            showAlert("Error!", "Không thể để trống \"New English word\" hoặc \"New Vietnamese word\"");
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
        Alert alertConfirmation = alerts.alertConfirmation("Confirmation!", "Từ \"" + englishWord.getText() + "\" đã tồn tại, " +
                "bạn có muốn thay đổi nghĩa của nó không?");
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
        Alert alertConfirmation = alerts.alertConfirmation("Confirmation!",
                "Bạn có muốn thêm từ \"" + englishWord.getText() + "\" với nghĩa \"" + vietnameseWord.getText() + "\" hay không?");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tooltip1.setShowDelay(Duration.seconds(0.5));
    }
}
