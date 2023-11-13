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
        FXMLLoader fxmlLoader = new FXMLLoader(myApplication.class.getResource("/Views/startingView.fxml"));
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
    //gfgfg
    public static void main(String[] args) {
        launch();
    }
    private void importAccount() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/textFiles/Account"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] a = line.split("\\|");
            personList.add(new Person(a[0], a[1], a[2], a[3] , a[4] , a[5] , a[6] , a[7] , a[8] , a[9]));
        }
        reader.close();
    }

    private void exportAccount() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/textFiles/Account"));
        for (Person person : personList) {
            String personData = person.getUsername() + "|" + person.getPassword() + "|"
                    + person.getQuestion() + "|" + person.getAnswer()  +"|"+ person.getMyGame() + "|" +
                    person.getVocab1() + "|" + person.getVocab2() + "|" + person.getVocab3() + "|"+
                    person.getVocab4() + "|" + person.getListen() ;
            writer.write(personData);
            writer.newLine();
        }
        writer.close();
    }
}