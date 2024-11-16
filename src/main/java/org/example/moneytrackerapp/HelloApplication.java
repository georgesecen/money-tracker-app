package org.example.moneytrackerapp;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.moneytrackerapp.scenes.LoginScene;
import org.example.moneytrackerapp.scenes.MainScene;

import java.io.IOException;

public class HelloApplication extends Application {

    public static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {

        mainStage = stage;
        mainStage.setTitle("Login");
        mainStage.setScene(new MainScene());
        mainStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}