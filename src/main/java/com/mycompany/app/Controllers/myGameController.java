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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.*;
import java.net.URL;
import java.util.*;

import static com.mycompany.app.myApplication.*;
public class myGameController implements Initializable {
    @FXML
    private Button[] buttons = new Button[16];

    @FXML
    private Button button0, button1,  button2,  button3,  button4,  button5,  button6,  button7,  button8,  button9,  button10,  button11,  button12,  button13,  button14, button15 ;

    @FXML
    private Button check;

    @FXML
    private Button back;

    @FXML
    private Button reset;

    @FXML
    private Label question;

    @FXML
    private Label answer;

    @FXML
    private Label percentage;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Tooltip tooltip1;

    @FXML
    private Tooltip tooltip2;

    @FXML
    private Tooltip tooltip3;

    @FXML
    private AnchorPane thisPane;

    private Alerts alerts = new Alerts();

    private String correctAnswer;

    private List<String> randomWords = new ArrayList<>();

    private Random random = new Random();

    private List<String> pairsList = new ArrayList<>();

    private int progress = 0;
    private int index = 0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadRandomWords();
            loadPairsList();
            getProgressBarInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        buttons[0] = button0;
        buttons[1] = button1;
        buttons[2] = button2;
        buttons[3] = button3;
        buttons[4] = button4;
        buttons[5] = button5;
        buttons[6] = button6;
        buttons[7] = button7;
        buttons[8] = button8;
        buttons[9] = button9;
        buttons[10] = button10;
        buttons[11] = button11;
        buttons[12] = button12;
        buttons[13] = button13;
        buttons[14] = button14;
        buttons[15] = button15;

        tooltip1.setShowDelay(Duration.seconds(0.5));
        tooltip2.setShowDelay(Duration.seconds(0.5));
        tooltip3.setShowDelay(Duration.seconds(0.5));

        percentage.setText((int) (progress * 10) + "%");

        nextQuestion();

        for  (Button element : buttons) {
            element.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (answer.getText().isEmpty()) {
                        answer.setText(element.getText());
                    } else {
                        answer.setText(answer.getText() + " " + element.getText());
                    }
                    element.setVisible(false);
                    element.setDisable(true);
                }
            });
        }

        check.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (answer.getText().equals(correctAnswer)) {
                    progress += 1;
                    progressBar.setProgress(progress/10.0);
                    percentage.setText((int) (progress * 10) + "%");
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
                    reset();
                }
            }
        });

        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                reset();
            }
        });

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

    private void getProgressBarInfo(){
        String line = personList.get(personIndex).getMyGame();
        String[] a = line.split(" ");
        index = Integer.parseInt(a[0]);
        if (index == 0){
            index = random.nextInt(pairsList.size());
        }
        progress = Integer.parseInt(a[1]);
        progressBar.setProgress(progress/10.0);
    }

    private void saveProgress() throws IOException {
        personList.get(personIndex).setMyGame(index + " " + progress);
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
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Views/learnView.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setTitle("Learn");
            stage.setScene(new Scene(root));
        }
        else if (respond.get() == ButtonType.OK) {
            progressBar.setProgress(progress/10.0);
            percentage.setText((progress) * 10 + "%");
            nextQuestion();
        }
    }

    private void loadPairsList() throws IOException {
        FileReader fr = new FileReader("src/main/resources/textFiles/pairs");
        BufferedReader bf = new BufferedReader(fr);
        String line;
        while ((line = bf.readLine()) != null) {
            pairsList.add(line);
        }
    }

    private void loadRandomWords() throws IOException {
        FileReader fr = new FileReader("src/main/resources/textFiles/randomWords");
        BufferedReader bf = new BufferedReader(fr);
        String line;
        while((line = bf.readLine()) != null) {
            randomWords.add(line);
        }
    }

    private void reset() {
        for  (Button element : buttons) {
            element.setVisible(true);
            element.setDisable(false);
        }
        answer.setText("");
    }
    private void nextQuestion() {
        getNextQuestion();
        setNextQuestion();
    }

    private void getNextQuestion() {
        String[] temp = pairsList.get(index + progress - 1).split("\\|");
        question.setText(temp[0]);
        correctAnswer = temp[1];
    }
    private void setNextQuestion() {
        String[] words = correctAnswer.split(" ");
        for (int i = 0; i < buttons.length; i++) {
            if (i < words.length) {
                buttons[i].setText(words[i]);
            } else {
                int index = random.nextInt(randomWords.size());
                buttons[i].setText(randomWords.get(index));
            }
        }
        randomize();
        reset();
    }

    private void randomize() {
        for (int i = 0; i < buttons.length; i++) {
            int rand = random.nextInt(buttons.length);
            swap(i, rand);
        }
    }

    private void swap(int i, int rand) {
        String temp = buttons[i].getText();
        buttons[i].setText(buttons[rand].getText());
        buttons[rand].setText(temp);
    }
}
