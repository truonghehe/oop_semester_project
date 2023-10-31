package com.mycompany.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class myApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Api.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("OhOhOh");
        FXMLLoader fxmlLoader = new FXMLLoader(myApplication.class.getResource("/Views/startingView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("firstView");
>>>>>>> 1815dcb4f7fb6b036f2a07a4a31205f221f31ae8:src/main/java/com/mycompany/app/myApplication.java
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}