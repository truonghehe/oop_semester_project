package com.mycompany.app.Controllers;


import com.mycompany.app.Alert.Alerts;
import com.mycompany.app.DictionaryManagement;
import com.mycompany.app.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;
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

    private Alerts alerts = new Alerts();

    @FXML
    void add(MouseEvent event) {
        if (englishWord.getText().isEmpty()|| vietnameseWord.getText().isEmpty()) {
            Alert alertWaring = alerts.alertWarning("Error!" , "không thể để trống \"New English word\"" +
                    "hoặc \"New Vietnamese word\"");
            Optional<ButtonType> result = alertWaring.showAndWait();
        } else if (DictionaryManagement.data.containsKey(englishWord.getText())) {
            Alert alertConfirmation = alerts.alertConfirmation("Confirmation!" , "Từ \"" + englishWord.getText() + "\" đã tồn tại, " +
                    "bạn có muốn thay đổi nghĩa của nó không?") ;
            Optional<ButtonType> result = alertConfirmation.showAndWait();
            if (result.isEmpty() || result.get() == ButtonType.CANCEL) {
                return;
            } else {
                Word word = DictionaryManagement.data.get(englishWord.getText());
                DictionaryManagement.data.remove(englishWord.getText());
                DictionaryManagement.dictionary.remove(word);
                addWord();
            }
        } else {
            Alert alertConfirmation = alerts.alertConfirmation("Confirmation!" , "Bạn có muốn thêm từ \"" + englishWord.getText()
                    + "\" với nghĩa \"" + vietnameseWord.getText() + "\" hay không?") ;
            Optional<ButtonType> result = alertConfirmation.showAndWait();
            if (result.isEmpty() || result.get() == ButtonType.CANCEL) {
                return;
            } else {
                addWord();
            }
        }
    }

    private void addWord() {
        Word word = new Word();
        word.setWord_target(englishWord.getText());
        if (!wordType.getText().isEmpty()) {
            String withWordType = SEPARATOR + "<i>" + englishWord.getText() + " " + spelling.getText() + "</i><br/><ul><li><b><i> "
                    + wordType.getText() + "</i></b><ul><li><font color='#cc0000'><b> "
                    + vietnameseWord.getText() + "</b></font></li></ul></li></ul>" + SEPARATOR;
            word.setWord_explain(withWordType);
        }  else {
            String withoutWordType = SEPARATOR + "<i>" + englishWord.getText() + " " + spelling.getText() + "</i><br/><ul><li><font color='#cc0000'><b> "
                    + vietnameseWord.getText() + "</b></font></li></ul>" + SEPARATOR;
            word.setWord_explain(withoutWordType);
        }
        DictionaryManagement.dictionary.add(word);
        DictionaryManagement.data.put(word.getWord_target() , word) ;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tooltip1.setShowDelay(Duration.seconds(0.5));
    }
}
