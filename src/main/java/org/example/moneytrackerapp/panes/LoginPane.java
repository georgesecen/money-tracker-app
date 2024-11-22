package org.example.moneytrackerapp.panes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import org.example.moneytrackerapp.HelloApplication;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.scenes.MainScene;

import java.io.*;

import static org.example.moneytrackerapp.HelloApplication.mainScene;

/**
 * Contains sign in/sign up and load/save user credentials functionality.
 */
public class LoginPane extends BorderPane {

    /**
     * Forms for user sign in/sign up and logs user into main app.
     * @throws FileNotFoundException
     */
    public LoginPane() throws FileNotFoundException {

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

        // TODO: Add input validation and error messages if user logs in with invalid credentials

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

        signUpContainer.getChildren().addAll(signUpTitle, signUpInstructions, signUpUserField, signUpNameField, signUpPassField, signUpButton);
        signUpContainer.setAlignment(Pos.CENTER);

        // Setting the sign up nodes focus to false so none of them are focused by default when the scene loads
        for (Node node : signUpContainer.getChildren()){
            node.setFocusTraversable(false);
        }

        // Sign in component
        VBox signInContainer = new VBox();
        Text signInTitle = new Text("Sign In");
        Text signInInstructions = new Text("Enter your username");

        TextField signInUserField = new TextField();
        signInUserField.setPromptText("Username");
        signInUserField.setMaxWidth(100);

        Button signInButton = new Button("Sign In");

        signInContainer.getChildren().addAll(signInTitle, signInInstructions, signInUserField, signInButton);
        signInContainer.setAlignment(Pos.CENTER);

        // Setting the sign in nodes focus to false so none of them are focused by default when the scene loads
        for (Node node : signInContainer.getChildren()){
            node.setFocusTraversable(false);
        }

        loginContainer.getChildren().addAll(signUpContainer, signInContainer);
        loginContainer.setAlignment(Pos.CENTER);

        // Get json object from credentials file
        // {username: {dbname: name, password: pass}}
        JsonElement jsonElement = JsonParser.parseReader(new FileReader(credentialsFile));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // Save user credentials on sign up
        signUpButton.setOnAction(e->{

            // Attempt to connect user to database
            Database.setDbCredentials(signUpNameField.getText(), signUpUserField.getText(), signUpPassField.getText());
            Database db = Database.getInstance();

            // If user successfully connected to database
            if (db.getConnection() != null){
                // Add username, dbname, and pass to the json object
                JsonObject credentials = new JsonObject();
                credentials.addProperty("dbname", signUpNameField.getText());
                credentials.addProperty("password", signUpPassField.getText());
                jsonObject.add(signUpUserField.getText(), credentials);

                // Update the credentials file with the new json object
                try {
                    FileWriter writer = new FileWriter(credentialsFile.getPath());
                    writer.write(jsonObject.toString());
                    writer.close();
                } catch (IOException err) {
                    System.out.println("Error writing user details to credentials in LoginPane.java");
                    err.printStackTrace();
                }

                // Log user into app
//                mainScene.getStylesheets().add(this.getClass().getResource("main.css").toExternalForm());
//                HelloApplication.mainStage.setScene(new MainScene());
                mainScene = new MainScene();
                mainScene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());

                HelloApplication.sceneSwap(new MainScene());

            }
            else{
                // Reset database instance so user can try again
                Database.resetInstance();
            }
        });

        // Automatically log the user into the app on sign in
        signInButton.setOnAction(e->{
            // If username is saved in credentials
            if (jsonObject.has(signInUserField.getText())){

                // Load saved user credentials
                JsonObject userCredentials = (JsonObject) jsonObject.get(signInUserField.getText());

                // Attempt to connect user to database
                Database.setDbCredentials(userCredentials.get("dbname").getAsString(), signInUserField.getText(), userCredentials.get("password").getAsString());
                Database db = Database.getInstance();

                // If user successfully connected to database
                if (db.getConnection() != null){
                    // Log user into app
                    HelloApplication.mainStage.setScene(new MainScene());
                }
                else{
                    // Reset database instance so user can try again
                    Database.resetInstance();

                    System.out.println("Error connecting to db");
                    // TODO: Give error message to user
                }
            }
            else{
                // TODO: Give error message to user
                System.out.println("Username does not exist");
            }
        });


        this.setCenter(loginContainer);
    }
}
