package com.mycompany.app;

import com.mycompany.app.TranslateApi.TextToSpeech;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.speech.AudioException;
import javax.speech.EngineException;
import java.beans.PropertyVetoException;
import java.io.IOException;

public class myApplication extends Application {
    public static TextToSpeech textToSpeech = new TextToSpeech();
    @Override
    public void start(Stage stage) throws IOException, PropertyVetoException, AudioException, EngineException {
        FXMLLoader fxmlLoader = new FXMLLoader(myApplication.class.getResource("/Views/startingView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("firstView");
        stage.setScene(scene);
        textToSpeech.initialize("kevin16");
        stage.setOnCloseRequest(windowEvent -> {
            try {
                textToSpeech.terminate();
            } catch (EngineException e) {
                throw new RuntimeException(e);
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}