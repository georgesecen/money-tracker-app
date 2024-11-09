package org.example.moneytrackerapp.panes;

import javafx.scene.layout.BorderPane;

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
    }
}
