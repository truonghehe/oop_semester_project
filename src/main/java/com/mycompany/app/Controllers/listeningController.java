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
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.speech.AudioException;
import javax.speech.EngineException;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

import static com.mycompany.app.myApplication.textToSpeech;

public class listeningController implements Initializable {
    @FXML
    private Button[] buttons = new Button[4];

    @FXML
    private Button button0, button1, button2, button3;

    @FXML
    private Button check;
    @FXML
    private Button back;
    @FXML
    private Label percentage;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Tooltip tooltip1;
    @FXML
    private Tooltip tooltip2;
    @FXML
    private AnchorPane thisPane;
    private Alerts alerts = new Alerts();
    private double progress = 0;
    private String correctAnswer;
    private String clickAns ;
    @FXML
    private Button btVoice ;
    private Random random = new Random();
    private List<String> pairsList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadPairsList();
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

        percentage.setText((int) (progress) + "%");

        nextQuestion();
        for (Button button : buttons) {
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    for (Button value : buttons) {
                        value.setStyle("-fx-background-color: #c4d8e8;");
                    }
                    button.setStyle("-fx-background-color: #931DA3;");
                    clickAns = button.getText();
                }

            });
        }
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
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
        });
        btVoice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    textToSpeech.speak(correctAnswer);
                } catch (EngineException e) {
                    throw new RuntimeException(e);
                } catch (AudioException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        check.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (clickAns.equals(correctAnswer)) {
                    progress += 10;
                    progressBar.setProgress(progress/100);
                    percentage.setText((int) (progress) + "%");
                    if (progress == 100) {
                        Alert continueConfirmation = alerts.alertConfirmation("Chúc mừng!", "Bạn đã hoàn thành bài tập hôm nay" +
                                "\n bạn có muốn tiếp tục không?");
                        Optional<ButtonType> respond = continueConfirmation.showAndWait();
                        respond(respond);
                    } else {
                        nextQuestion();
                    }
                } else {
                    alerts.showAlertInfo("Đáp án sai!", "Bạn đã làm sai rồi.");
                    randomize();
                }
            }
        });
    }

    private void saveProgress() throws IOException {
        FileWriter fw = new FileWriter("src/main/resources/textFiles/progressListen");
        BufferedWriter br = new BufferedWriter(fw);
        br.write(String.valueOf(progress));
        br.close();
        fw.close();
    }

    private void loadPairsList() throws IOException {
        FileReader fr = new FileReader("src/main/resources/textFiles/listening");
        BufferedReader bf = new BufferedReader(fr);
        String line;
        while ((line = bf.readLine()) != null) {
            pairsList.add(line);
        }
    }
    private void getProgressBarInfo() throws IOException {
        FileReader fr = new FileReader("src/main/resources/textFiles/progressListen");
        BufferedReader br = new BufferedReader(fr);
        String temp = br.readLine();
        progress = Double.parseDouble(temp);
        progressBar.setProgress(progress/100);
    }
    private void nextQuestion() {
        int rand = random.nextInt(pairsList.size());
        correctAnswer = pairsList.get(rand).trim();
        buttons[0].setText(correctAnswer);
        for (int i = 1 ; i < buttons.length ; i++ ){
            int index = (rand + 2 * i) % 100 ;
            buttons[i].setText(pairsList.get(index).trim());
        }
        randomize();
    }
    private void randomize() {
        for (int i = 0; i < buttons.length; i++) {
            int rand = random.nextInt(buttons.length - i) + i;
            swap(i, rand);
        }
        for (Button button : buttons) {
            button.setStyle("-fx-background-color: #c4d8e8;");
        }
    }
    private void swap(int i, int rand) {
        String temp = buttons[i].getText();
        buttons[i].setText(buttons[rand].getText());
        buttons[rand].setText(temp);
    }
    private void respond(Optional<ButtonType> respond) {
        if (respond.get() == ButtonType.CANCEL || respond.isEmpty()) {
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
            progress = 0;
            progressBar.setProgress(progress);
            nextQuestion();
        }
    }
}
