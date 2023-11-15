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
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

import static com.mycompany.app.myApplication.*;

public class vocabularyController extends gameUtils implements Initializable {
    @FXML
    private Button[] buttons = new Button[4];
    @FXML
    private Button[] bts = new Button[10] ;
    @FXML
    private Button button0, button1, button2, button3;

    @FXML
    private Button bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9, bt10;

    @FXML
    private Button check;

    @FXML
    private Button back;

    @FXML
    private Label percentage;

    @FXML
    private Tooltip tooltip1;

    @FXML
    private Tooltip tooltip2;

    @FXML
    private AnchorPane subjectPane;
    @FXML
    private AnchorPane pane;
    @FXML
    private Button backSubject;
    @FXML
    private Button btVoice;
    @FXML
    private Label subject;
    private int indexSubject ;

    public void mySubject() {
        subjectPane.setVisible(false);
        pane.setVisible(true);
        pairsList.clear();
        try {
            loadPairsList("src/main/resources/textFiles/Vocabulary/" + bts[indexSubject].getText());
            getProgressBarInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        subject.setText("Chủ đề: " + bts[indexSubject].getText());
        buttons[0] = button0;
        buttons[1] = button1;
        buttons[2] = button2;
        buttons[3] = button3;

        tooltip1.setShowDelay(Duration.seconds(0.5));
        tooltip2.setShowDelay(Duration.seconds(0.5));

        percentage.setText((progress) * 10 + "%");

        nextQuestion();
        for (int i = 0; i < 4; i++) {
            int tmp = i;
            buttons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    for (int j = 0; j < 4; j++) {
                        buttons[j].setStyle("-fx-background-color: #c4d8e8;");
                    }
                    buttons[tmp].setStyle("-fx-background-color: #931DA3;");
                    answer.setText(buttons[tmp].getText());
                }
            });
        }
        backSubject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveProgress();
                pane.setVisible(false);
                subjectPane.setVisible(true);
            }
        });
        check.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                checkAnswer();
            }
        });
        btVoice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String[] arr = question.getText().split("\\[");
                    textToSpeech.speak(arr[0].trim());
                } catch (EngineException | AudioException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.setVisible(false);
        subjectPane.setVisible(true);
        bts[0] = bt1;
        bts[1] = bt2;
        bts[2] = bt3;
        bts[3] = bt4;
        bts[4] = bt5;
        bts[5] = bt6;
        bts[6] = bt7;
        bts[7] = bt8;
        bts[8] = bt9;
        bts[9] = bt10;
        for (int i = 0; i < 10; i++) {
            int tmp = i;
            bts[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    indexSubject = tmp;
                    System.out.println(indexSubject);
                    mySubject();
                }
            });
        }
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                backToLearn();
            }
        });
    }

    @Override
    protected void saveProgress() {
        String line = index + " " + progress;
        personList.get(personIndex).getVocab().set(indexSubject, line);
    }

    @Override
    protected void getProgressBarInfo() {
        String line = "";
        line = personList.get(personIndex).getVocab().get(indexSubject);
        String[] a = line.split(" ");
        index = Integer.parseInt(a[0]);
        while (index == 0) {
            index = random.nextInt(pairsList.size());
        }
        progress = Integer.parseInt(a[1]);
        progressBar.setProgress(progress / 10.0);
    }

    @Override
    protected void setNextQuestion() {
        buttons[0].setText(correctAnswer);
        for (int i = 1; i < 4 ; i++) {
            int newIndex = (index + progress + 2 * i) % pairsList.size();
            String[] tmp = pairsList.get(newIndex).split("\\|");
            buttons[i].setText(tmp[1].trim());
        }
        randomize(buttons);
    }
    @Override
    protected void reset() {
        for (int i = 0; i < 4; i++) {
            buttons[i].setStyle("-fx-background-color: #c4d8e8;");
        }
    }
}
