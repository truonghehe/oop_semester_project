package com.mycompany.app.Controllers.GameControllers;

import static com.mycompany.app.myApplication.personIndex;
import static com.mycompany.app.myApplication.personList;
import static com.mycompany.app.myApplication.textToSpeech;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.mycompany.app.Alert.Alerts;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * The vocabularyController class manages the actions and updates in the vocabulary view of the application.
 */
public class vocabularyController extends gameUtils implements Initializable {

    @FXML
    private Button[] buttons = new Button[4];

    @FXML
    private Button[] bts = new Button[10];

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
    private VBox subjectPane;

    @FXML
    private VBox pane;

    @FXML
    private Button backSubject;

    @FXML
    private Button speak;

    private int indexSubject;

    private Alerts alerts = new Alerts();

    /**
     * Opens the subject vocabulary pane.
     */
    public void mySubject() {
        subjectPane.setVisible(false);
        pane.setVisible(true);
        pairsList.clear();
        try {
            loadPairsList("src/main/resources/textFiles/Vocabulary/" + bts[indexSubject].getText() + ".txt");
            getProgressBarInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        buttons[0] = button0;
        buttons[1] = button1;
        buttons[2] = button2;
        buttons[3] = button3;

        percentage.setText((progress) * 10 + "%");

        nextQuestion();

        for (Button button : buttons) {
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    for (Button value : buttons) {
                        value.getStyleClass().clear();
                        value.getStyleClass().add("bigButton");
                    }
                    button.getStyleClass().clear();
                    button.getStyleClass().add("choosingButton");
                    answer.setText(button.getText());
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
            public void handle(ActionEvent event) {
                checkAnswer();
            }
        });

        speak.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String[] arr = question.getText().split("\\[");
                    textToSpeech.speak(arr[0].trim());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Initializes the vocabulary view.
     */
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
                    mySubject();
                }
            });
        }

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                backToLearn();
            }
        });
    }

    /**
     * Saves the progress in the vocabulary game.
     */
    @Override
    protected void saveProgress() {
        String line = index + " " + progress;
        personList.get(personIndex).getVocab().set(indexSubject, line);
    }

    /**
     * Gets information about the progress bar in the vocabulary game.
     */
    @Override
    protected void getProgressBarInfo() {
        String line = personList.get(personIndex).getVocab().get(indexSubject);
        String[] a = line.split(" ");
        index = Integer.parseInt(a[0]);
        while (index == 0) {
            index = random.nextInt(pairsList.size());
        }
        progress = Integer.parseInt(a[1]);
        progressBar.setProgress(progress / 10.0);
    }

    /**
     * Sets the next vocabulary question.
     */
    @Override
    protected void setNextQuestion() {
        buttons[0].setText(correctAnswer);
        for (int i = 1; i < 4; i++) {
            int newIndex = (index + progress + 2 * i) % pairsList.size();
            String[] tmp = pairsList.get(newIndex).split("\\|");
            buttons[i].setText(tmp[1].trim());
        }
        randomize(buttons);
        reset();
    }

    /**
     * Resets the styling of the buttons in the vocabulary game.
     */
    @Override
    protected void reset() {
        for (Button button : buttons) {
            button.getStyleClass().clear();
            button.getStyleClass().add("bigButton");
        }
    }
}
