package com.mycompany.app.Controllers.GameControllers;

import static com.mycompany.app.myApplication.personIndex;
import static com.mycompany.app.myApplication.personList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The myGameController class manages the gameplay in the "My Game" section of the application.
 */
public class myGameController extends gameUtils implements Initializable {
    
    @FXML
    private Button[] buttons = new Button[16];

    @FXML
    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14, button15;

    @FXML
    private Button back;

    @FXML
    private Button reset;

    @FXML
    private Button check;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Tooltip tooltip1;

    @FXML
    private Tooltip tooltip2;

    @FXML
    private Tooltip tooltip3;
    
    private List<String> randomWords = new ArrayList<>();

    /**
     * Initializes the controller.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadRandomWords();
            loadPairsList("src/main/resources/textFiles/pairs.txt");
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

        percentage.setText((progress * 10) + "%");

        nextQuestion();

        for (Button element : buttons) {
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
                checkAnswer();
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
                backToLearn();
            }
        });
    }

    /**
     * Retrieves the progress information for the "My Game" section from the user data.
     */
    @Override
    protected void getProgressBarInfo() {
        String line = personList.get(personIndex).getMyGame();
        String[] a = line.split(" ");
        index = Integer.parseInt(a[0]);
        while (index == 0) {
            index = random.nextInt(pairsList.size());
        }
        progress = Integer.parseInt(a[1]);
        progressBar.setProgress(progress / 10.0);
    }

    /**
     * Saves the progress information for the "My Game" section to the user data.
     */
    @Override
    protected void saveProgress() {
        personList.get(personIndex).setMyGame(index + " " + progress);
    }

    /**
     * Handles the response after checking an answer.
     *
     * @param respond The user's response.
     */
    protected void respond(Optional<ButtonType> respond) {
        progress = 0;
        int newIndex = random.nextInt(pairsList.size());
        while (Math.abs(index - newIndex) <= 10) {
            newIndex = random.nextInt(pairsList.size());
        }
        index = newIndex;
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
        } else if (respond.get() == ButtonType.OK) {
            progressBar.setProgress(progress / 10.0);
            percentage.setText((progress) * 10 + "%");
            nextQuestion();
        }
    }

    /**
     * Loads a list of random words from a file.
     *
     * @throws IOException If an error occurs while reading the file.
     */
    private void loadRandomWords() throws IOException {
        FileReader fr = new FileReader("src/main/resources/textFiles/randomWords.txt");
        BufferedReader bf = new BufferedReader(fr);
        String line;
        while ((line = bf.readLine()) != null) {
            randomWords.add(line);
        }
    }

    /**
     * Resets the buttons and answer field for the next question.
     */
    @Override
    protected void reset() {
        for (Button element : buttons) {
            element.setVisible(true);
            element.setDisable(false);
        }
        answer.setText("");
    }

    /**
     * Sets the next question by filling the buttons with words.
     */
    @Override
    protected void setNextQuestion() {
        String[] words = correctAnswer.split(" ");
        for (int i = 0; i < buttons.length; i++) {
            if (i < words.length) {
                buttons[i].setText(words[i]);
            } else {
                int index = random.nextInt(randomWords.size());
                buttons[i].setText(randomWords.get(index));
            }
        }
        randomize(buttons);
        reset();
    }
}
