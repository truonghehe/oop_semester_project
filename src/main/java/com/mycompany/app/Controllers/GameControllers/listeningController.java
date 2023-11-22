package com.mycompany.app.Controllers.GameControllers;

import static com.mycompany.app.myApplication.personIndex;
import static com.mycompany.app.myApplication.personList;
import static com.mycompany.app.myApplication.textToSpeech;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.speech.AudioException;
import javax.speech.EngineException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

/**
 * The listeningController class manages the listening game within the application.
 */
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

    /**
     * Initializes the listeningController, setting up the game interface and handling button actions.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadPairsList("src/main/resources/textFiles/listening.txt");
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
                        value.getStyleClass().clear();
                        value.getStyleClass().add("bigButton");
                    }
                    button.getStyleClass().clear();
                    button.getStyleClass().add("choosingButton");
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

    /**
     * Saves the current progress of the listening game.
     */
    @Override
    protected void saveProgress() {
        personList.get(personIndex).setListen(index + " " + progress);
    }

    /**
     * Sets up the next question in the listening game.
     */
    @Override
    protected void setNextQuestion() {
        buttons[0].setText(correctAnswer);
        for (int i = 1; i < buttons.length; i++) {
            int newIndex = (index + progress + 2 * i) % pairsList.size();
            String tmp = pairsList.get(newIndex);
            buttons[i].setText(tmp.trim());
        }
        randomize(buttons);
        reset();
    }

    /**
     * Gets the next question in the listening game.
     */
    @Override
    protected void getNextQuestion() {
        String temp = pairsList.get(index + progress - 1);
        correctAnswer = temp.trim();
    }

    /**
     * Retrieves information about the progress bar from the user's data.
     */
    @Override
    protected void getProgressBarInfo() {
        String line = personList.get(personIndex).getListen();
        String[] a = line.split(" ");
        index = Integer.parseInt(a[0]);
        while (index == 0) {
            index = random.nextInt(pairsList.size());
        }
        progress = Integer.parseInt(a[1]);
        progressBar.setProgress(progress / 10.0);
    }

    /**
     * Resets the button styles in the listening game.
     */
    @Override
    protected void reset() {
        for (Button button : buttons) {
            button.getStyleClass().clear();
            button.getStyleClass().add("bigButton");
        }
    }
}
