package com.mycompany.app.Controllers;

import com.mycompany.app.Alert.Alerts;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

abstract public class gameUtils {

    protected int progress;
    protected int index;
    protected Random random = new Random();
    protected List<String> pairsList = new ArrayList<>();
    protected Alerts alerts = new Alerts();
    protected String correctAnswer;
    @FXML
    protected Label question;
    @FXML
    protected Label percentage;
    @FXML
    protected AnchorPane thisPane;
    @FXML
    protected ProgressBar progressBar;
    @FXML
    protected Label answer = new Label();

    protected abstract void saveProgress();

    abstract protected void setNextQuestion();
    abstract protected void reset();
    abstract protected void getProgressBarInfo();

    protected void randomize(Button[] buttons) {
        for (int i = 0; i < 4; i++) {
            int rand = random.nextInt(buttons.length)%4 ;
            swap(buttons, i, rand);
        }
    }

    private void swap(Button[] buttons, int i, int rand) {
        String temp = buttons[i].getText();
        buttons[i].setText(buttons[rand].getText());
        buttons[rand].setText(temp);
    }

    protected void backToLearn() {
        Stage stage = (Stage) thisPane.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Views/learnView.fxml"));
            stage.setTitle("Learning");
            stage.setScene(new Scene(root));
            saveProgress();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void respond(Optional<ButtonType> respond) {
         progress = 0;
         int newIndex = random.nextInt(pairsList.size()) ;
         while (Math.abs(index - newIndex) <= 10){
             newIndex = random.nextInt(pairsList.size());
         }
         index = newIndex ;
         if (respond.get() == ButtonType.CANCEL || respond.isEmpty()) {
             saveProgress();
             Stage stage = (Stage) thisPane.getScene().getWindow();
             Parent root = null;
             try {
                 root = FXMLLoader.load(getClass().getResource("/Views/learnView.fxml"));
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
             stage.setTitle("Learn");
             stage.setScene(new Scene(root));
         }
         else if (respond.get() == ButtonType.OK) {
             progressBar.setProgress(progress/10.0);
             percentage.setText(progress * 10 + "%");
             nextQuestion();
         }
    }

    protected void nextQuestion() {
         getNextQuestion();
         setNextQuestion();
    }

    protected void getNextQuestion() {
        String[] temp = pairsList.get((index + progress - 1)%pairsList.size()).split("\\|");
        question.setText(temp[0].trim());
        correctAnswer = temp[1].trim();
    }

    protected void checkAnswer() {
        if (answer.getText().equals(correctAnswer)) {
            progress += 1;
            progressBar.setProgress(progress/10.0);
            percentage.setText( (progress) * 10 + "%");
            if (progress == 10) {
                Alert continueConfirmation = alerts.alertConfirmation("Chúc mừng!", "Bạn đã hoàn thành bài tập hôm nay" +
                        "\n bạn có muốn tiếp tục không?");
                Optional<ButtonType> respond = continueConfirmation.showAndWait();
                respond(respond);
            } else {
                nextQuestion();
                reset();
            }
        } else {
            alerts.showAlertInfo("Đáp án sai!", "Bạn đã làm sai rồi.");
            reset();
        }
    }

    protected void loadPairsList(String path) throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader bf = new BufferedReader(fr);
        String line;
        while ((line = bf.readLine()) != null) {
            pairsList.add(line);
        }
    }

}
