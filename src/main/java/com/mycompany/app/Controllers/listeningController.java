package com.mycompany.app.Controllers;

import com.mycompany.app.Alert.Alerts;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.speech.AudioException;
import javax.speech.EngineException;
import java.awt.*;
import java.beans.Expression;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

import static com.mycompany.app.myApplication.*;

public class listeningController extends gameUtils implements Initializable {
    @FXML
    private Button[] buttons = new Button[4];

    @FXML
    private Button button0, button1, button2, button3;

    @FXML
    private Button check;

    @FXML
    private Button back;

    @FXML
    private Tooltip tooltip1;

    @FXML
    private Tooltip tooltip2;

    @FXML
    private Button btVoice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadPairsList("src/main/resources/textFiles/listening");
            getProgressBarInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        buttons[0] = button0;
        buttons[1] = button1;
        buttons[2] = button2;
        buttons[3] = button3;

        tooltip1.setShowDelay(Duration.seconds(0.5));
        tooltip2.setShowDelay(Duration.seconds(0.5));

        percentage.setText((progress) * 10 + "%");

        nextQuestion();
        for (Button button : buttons) {
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    for (Button value : buttons) {
                        value.setStyle("-fx-background-color: #c4d8e8;");
                    }
                    button.setStyle("-fx-background-color: #931DA3;");
                    answer.setText(button.getText());
                }

            });
        }
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                backToLearn();
            }
        });
        btVoice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    textToSpeech.speak(correctAnswer);
                } catch (EngineException | AudioException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        check.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                checkAnswer();
            }
        });
    }

    @Override
    protected void saveProgress() {
        personList.get(personIndex).setListen(index + " " + progress);
    }
    @Override
    protected void setNextQuestion() {
        buttons[0].setText(correctAnswer);
        for (int i = 1 ; i < buttons.length ; i++ ){
            int newIndex = (index + progress + 2 * i) % pairsList.size() ;
            String tmp = pairsList.get(newIndex) ;
            buttons[i].setText(tmp.trim());
        }
        randomize(buttons);
        reset();
    }
    @Override
    protected void getNextQuestion() {
        String temp = pairsList.get(index + progress - 1);
        correctAnswer = temp.trim();
    }
    @Override
    protected void getProgressBarInfo(){
        String line = personList.get(personIndex).getListen();
        String[] a = line.split(" ");
        index = Integer.parseInt(a[0]);
        while (index == 0){
            index = random.nextInt(pairsList.size());
        }
        progress = Integer.parseInt(a[1]);
        progressBar.setProgress(progress/10.0);
    }
    @Override
    protected void reset() {
        for (Button button : buttons) {
            button.setStyle("-fx-background-color: #c4d8e8;");
        }
    }
}
