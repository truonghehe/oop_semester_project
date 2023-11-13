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
public class vocabularyController implements Initializable {
    @FXML
    private Button[] buttons = new Button[4];

    @FXML
    private Button button0, button1, button2, button3;
    @FXML
    private Button bt0 , bt1 , bt2 , bt3 ;
    @FXML
    private Button check;
    @FXML
    private Label question;
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
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane subjectPane ;
    private Alerts alerts = new Alerts();
    private int progress = 0;
    @FXML
    private Button btVoice ;
    private String correctAnswer;

    private String clickAns ;
    private int index = 0 ;
    private int indexSubject ;
    private Random random = new Random();
    private List<String> pairsList = new ArrayList<>();
    public void mySubject(){
        subjectPane.setVisible(false);
        progressBar.setVisible(true);
        question.setVisible(true);
        gridPane.setVisible(true);
        check.setVisible(true);
        percentage.setVisible(true);
        btVoice.setVisible(true);
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
                    clickAns = button.getText();
                }
            });
        }
        check.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (clickAns.equals(correctAnswer)) {
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
                    }
                } else {
                    alerts.showAlertInfo("Đáp án sai!", "Bạn đã làm sai rồi.");
                    randomize();
                }
            }
        });
        btVoice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    textToSpeech.speak(question.getText());
                } catch (EngineException e) {
                    throw new RuntimeException(e);
                } catch (AudioException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressBar.setVisible(false);
        question.setVisible(false);
        gridPane.setVisible(false);
        check.setVisible(false);
        percentage.setVisible(false);
        subjectPane.setVisible(true);
        btVoice.setVisible(false);
        buttons[0] = bt0;
        buttons[1] = bt1;
        buttons[2] = bt2;
        buttons[3] = bt3;
        for (int i = 0 ; i < buttons.length ; i++) {
            int tmp = i ;
            buttons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    mySubject();
                    indexSubject = tmp ;
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
    }

    private void saveProgress() {
        String line = index + " " + progress ;
        switch (indexSubject){
            case 0 : personList.get(personIndex).setVocab1(line); break ;
            case 1 : personList.get(personIndex).setVocab2(line); break ;
            case 2 : personList.get(personIndex).setVocab3(line); break ;
            case 3 : personList.get(personIndex).setVocab4(line); break ;
        }
    }

    private void loadPairsList() throws IOException {
        FileReader fr = new FileReader("src/main/resources/textFiles/vocabulary");
        BufferedReader bf = new BufferedReader(fr);
        String line;
        while ((line = bf.readLine()) != null) {
            pairsList.add(line);
        }
    }
    private void getProgressBarInfo() {
        String line = "" ;
        switch (indexSubject){
            case 0 : line = personList.get(personIndex).getVocab1(); break ;
            case 1 : line = personList.get(personIndex).getVocab2(); break ;
            case 2 : line = personList.get(personIndex).getVocab3(); break ;
            case 3 : line = personList.get(personIndex).getVocab4(); break ;
        }
        String[] a = line.split(" ");
        index = Integer.parseInt(a[0]);
        if ( index == 0 ){
            index = random.nextInt(pairsList.size());
        }
        progress = Integer.parseInt(a[1]) ;
        progressBar.setProgress(progress/10.0);
    }
    private void nextQuestion() {
        String[] temp = pairsList.get(index + progress - 1).split("\\|");
        question.setText(temp[0].trim());
        correctAnswer = temp[1].trim();
        buttons[0].setText(correctAnswer);
        for (int i = 1 ; i < buttons.length ; i++ ){
            int newIndex = (index + progress + 2 * i) % 50 ;
            String[] tmp = pairsList.get(newIndex).split("\\|") ;
            buttons[i].setText(tmp[1].trim());
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
        progress = 0;
        int newIndex = random.nextInt(pairsList.size()) ;
        while (Math.abs(index - newIndex) <= 10){
            newIndex = random.nextInt(pairsList.size());
        }
        index = newIndex ;
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
            progressBar.setProgress(progress/10.0);
            percentage.setText(progress * 10 + "%");
            nextQuestion();
        }
    }
}
