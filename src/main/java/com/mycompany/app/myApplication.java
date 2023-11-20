package com.mycompany.app;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.speech.AudioException;
import javax.speech.EngineException;

import com.mycompany.app.TranslateApi.TextToSpeech;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The myApplication class represents the main application entry point and manages the application lifecycle.
 */
public class myApplication extends Application {

    /**
     * The TextToSpeech instance for handling text-to-speech functionality.
     */
    public static TextToSpeech textToSpeech = new TextToSpeech();

    /**
     * The list of Person objects representing user accounts.
     */
    public static List<Person> personList = new ArrayList<>();
    
    /**
     * The index of the currently active user account in the personList.
     */
    public static int personIndex;

    /**
     * The main entry point of the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Initializes the JavaFX application, loads the login view, and sets up necessary resources.
     *
     * @param stage the primary stage for the application
     * @throws IOException            if an I/O error occurs
     * @throws PropertyVetoException  if a property change is vetoed
     * @throws AudioException         if an error occurs in the audio system
     * @throws EngineException        if a problem occurs with the speech engine
     */
    @Override
    public void start(Stage stage) throws IOException, PropertyVetoException, AudioException, EngineException {
        FXMLLoader fxmlLoader = new FXMLLoader(myApplication.class.getResource("/Views/loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("firstView");
        stage.setScene(scene);
        textToSpeech.initialize("kevin16");
        importAccount();
        DictionaryManagement.dictionaryImportFromFile("src/main/resources/textFiles/E_V.txt");
        stage.setOnCloseRequest(windowEvent -> {
            try {
                textToSpeech.terminate();
                exportAccount();
                DictionaryManagement.dictionaryExportToFile("src/main/resources/textFiles/E_V.txt");
            } catch (EngineException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        stage.show();
    }

    /**
     * Imports user accounts from the "Account.txt" file.
     *
     * @throws IOException if an I/O error occurs
     */
    public static void importAccount() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/textFiles/Account.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] a = line.split("\\|");
            List<String> vocab = new ArrayList<>();
            for (int i = 6; i < a.length; i++) {
                vocab.add(a[i]);
            }
            personList.add(new Person(a[0], a[1], a[2], a[3], a[4], a[5], vocab));
        }
        reader.close();
    }

    /**
     * Exports user accounts to the "Account.txt" file.
     *
     * @throws IOException if an I/O error occurs
     */
    public static void exportAccount() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/textFiles/Account.txt"));
        for (Person person : personList) {
            StringBuilder personData = new StringBuilder(person.getUsername() + "|" + person.getPassword() + "|"
                    + person.getQuestion() + "|" + person.getAnswer() + "|" + person.getMyGame() + "|" +
                    person.getListen());
            for (int i = 0; i < person.getVocab().size(); i++) {
                personData.append("|").append(person.getVocab().get(i));
            }
            writer.write(personData.toString());
            writer.newLine();
        }
        writer.close();
    }
}
