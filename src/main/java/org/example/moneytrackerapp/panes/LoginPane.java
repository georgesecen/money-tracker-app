package org.example.moneytrackerapp.panes;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Contains sign in/sign up and load/save user credentials functionality.
 */
public class LoginPane extends BorderPane {

    public LoginPane(){

        File credentialsFile = new File("src/main/java/org/example/moneytrackerapp/credentials.json");

        // If credentials file does not exist create it with an empty object
        if (!credentialsFile.exists()){
            try {
                FileWriter writer = new FileWriter(credentialsFile.getPath());
                writer.write("{}");
                writer.close();
            } catch (IOException e) {
                System.out.println("Error creating file in LoginPane.java");
                e.printStackTrace();
            }
        }

        // Container will hold sign in and sign up components
        HBox loginContainer = new HBox();

        // Sign up component
        VBox signUpContainer = new VBox();
        Text signUpTitle = new Text("Sign Up");
        Text signUpInstructions = new Text("Enter your database credentials");

        TextField signUpNameField = new TextField();
        signUpNameField.setPromptText("DB Name");
        signUpNameField.setMaxWidth(100);

        TextField signUpUserField = new TextField();
        signUpUserField.setPromptText("Username");
        signUpUserField.setMaxWidth(100);

        TextField signUpPassField = new TextField();
        signUpPassField.setPromptText("Password");
        signUpPassField.setMaxWidth(100);

        Button signUpButton = new Button("Sign Up");

        signUpContainer.getChildren().addAll(signUpTitle, signUpInstructions, signUpNameField, signUpUserField, signUpPassField, signUpButton);
        signUpContainer.setAlignment(Pos.CENTER);

        // Setting the nodes focus to false so none of them are focused by default when the scene loads
        for (Node node : signUpContainer.getChildren()){
            node.setFocusTraversable(false);
        }

        this.setCenter(signUpContainer);
    }
}
