package com.mycompany.app.Controllers;

import com.mycompany.app.TranslateApi.TranslateApi;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class APIController implements Initializable {
    @FXML
    private TextArea firstLang;
    @FXML
    private TextArea secondLang;
    @FXML
    private Button translate;
    private String langFrom = "vi" ;
    private String langTo = "en" ;
    @FXML
    private Label To ;
    @FXML
    private Label From;
    @FXML
    private Button swap;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabel();
        firstLang.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (firstLang.getText() == null) translate.setDisable(true);
                else translate.setDisable(false);
            }
        });
        translate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    handleTranslate();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        swap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String tmp = langFrom ;
                langFrom = langTo ;
                langTo = tmp ;
                setLabel();
            }
        });
        translate.setDisable(true);
    }
    @FXML
    public void handleTranslate() throws IOException {
        String TextFrom = firstLang.getText();
        secondLang.setText(TranslateApi.translate( langFrom, langTo , TextFrom)) ;
    }
    @FXML
    public void setLabel(){
        From.setText(langFrom);
        To.setText(langTo);
    }
}