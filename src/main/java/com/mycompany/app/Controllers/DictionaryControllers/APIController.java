package com.mycompany.app.Controllers.DictionaryControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.mycompany.app.TranslateApi.TranslateApi;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class APIController implements Initializable {

    @FXML
    private TextArea firstLang;

    @FXML
    private TextArea secondLang;

    @FXML
    private Button translate;

    @FXML
    private Button swap;

    @FXML
    private Label To;

    @FXML
    private Label From;

    String langFrom;

    String langTo;

    @FXML
    private Tooltip tooltip1;

    @FXML
    private Tooltip tooltip2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        langFrom = From.getText();
        langTo = To.getText();

        setLabel();
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

        tooltip1.setShowDelay(Duration.seconds(0.5));
        tooltip2.setShowDelay(Duration.seconds(0.5));
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