package com.mycompany.app;

import com.mycompany.app.TranslateApi.TextToSpeech;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.speech.AudioException;
import javax.speech.EngineException;
import java.beans.PropertyVetoException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class myApplication extends Application {
    public static TextToSpeech textToSpeech = new TextToSpeech();
    public static List<Person> personList = new ArrayList<>() ;
    public static int personIndex ;
    @Override
    public void start(Stage stage) throws IOException, PropertyVetoException, AudioException, EngineException {
        FXMLLoader fxmlLoader = new FXMLLoader(myApplication.class.getResource("/Views/loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("firstView");
        stage.setScene(scene);
        textToSpeech.initialize("kevin16");
        importAccount();
        stage.setOnCloseRequest(windowEvent -> {
            try {
                textToSpeech.terminate();
                exportAccount();
            } catch (EngineException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    private void importAccount() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/textFiles/Account"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] a = line.split("\\|");
            List<String> vocab = new ArrayList<>();
            for ( int i = 6 ; i < a.length ; i++){
                vocab.add(a[i]);
            }
            personList.add(new Person(a[0], a[1], a[2], a[3] , a[4] , a[5] , vocab));
        }
        reader.close();
    }

    private void exportAccount() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/textFiles/Account"));
        for (Person person : personList) {
            StringBuilder personData = new StringBuilder(person.getUsername() + "|" + person.getPassword() + "|"
                    + person.getQuestion() + "|" + person.getAnswer() + "|" + person.getMyGame() + "|" +
                    person.getListen());
            for (int i = 0 ; i < person.getVocab().size() ; i++){
                personData.append("|").append(person.getVocab().get(i));
            }
            writer.write(personData.toString());
            writer.newLine();
        }
        writer.close();
    }
}