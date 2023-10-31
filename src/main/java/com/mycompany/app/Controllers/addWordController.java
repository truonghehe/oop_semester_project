package com.mycompany.app.Controllers;


import com.mycompany.app.Word;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import static com.mycompany.app.Dictionary.dictionary;

public class addWordController {

    private static final String SEPARATOR = "<html>";

    @FXML
    private TextField englishWord;

    @FXML
    private TextField vietnameseWord;

    @FXML
    private TextField wordType;

    private final Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    private final Alert error = new Alert(Alert.AlertType.ERROR);
    @FXML
    void add(MouseEvent event) {
        if (englishWord.getText().isEmpty()|| vietnameseWord.getText().isEmpty()) {
            error.setTitle("Error!");
            error.setContentText("không thể để trống \"New English word\"" +
                                 "hoặc \"New Vietnamese word\"");
            Optional<ButtonType> result = error.showAndWait();
        } else {
            confirm.setTitle("Confirmation!");
            confirm.setContentText("Bạn có muốn thêm từ \"" + englishWord.getText()
                    + "\" với nghĩa \"" + vietnameseWord.getText() + "\" hay không?");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isEmpty() || result.get() == ButtonType.CANCEL) {
                return;
            } else {
                Word word = new Word();
                word.setWord_target(englishWord.getText());
                if (!wordType.getText().isEmpty()) {
                    String withWordType = SEPARATOR + "<i>etude</i><br/><ul><li><b><i> "
                            + wordType.getText() + "</i></b><ul><li><font color='#cc0000'><b> "
                            + vietnameseWord.getText() + "</b></font></li></ul></li></ul>" + SEPARATOR;
                    word.setWord_explain(withWordType);
                }  else {
                    String withoutWordType = SEPARATOR + "</i><br/><ul><li><font color='#cc0000'><b> "
                            + vietnameseWord.getText() + "</b></font></li></ul>" + SEPARATOR;
                    word.setWord_explain(withoutWordType);
                }
                dictionary.add(word);
            }
        }
    }

}
