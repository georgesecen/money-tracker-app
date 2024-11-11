package org.example.moneytrackerapp;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.moneytrackerapp.scenes.LoginScene;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        LoginScene scene = new LoginScene();
        stage.setTitle("Works");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}