package org.example.moneytrackerapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.moneytrackerapp.scenes.LoginScene;
import org.example.moneytrackerapp.scenes.MainScene;

import java.io.IOException;

public class HelloApplication extends Application {

    public static LoginScene loginScene;
    public static MainScene mainScene;

    public static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        mainStage.setTitle("Login");
        loginScene = new LoginScene();
        loginScene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        mainStage.setScene(loginScene);
        mainStage.setResizable(false);
        mainStage.show();
    }
    public static void sceneSwap(Scene scene){
        mainStage.setScene(scene);
    }
    public static void main(String[] args) {
        launch();
    }
}