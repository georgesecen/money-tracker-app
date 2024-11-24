package org.example.moneytrackerapp.panes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.moneytrackerapp.HelloApplication;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.scenes.MainScene;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

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

        Text testText = new Text("12346");
        Button testButton = new Button("change all text");

        HBox testTextContainer = new HBox();
        testTextContainer.getChildren().addAll(testText, testButton);

        testButton.setOnAction(e->{

            animateTextChange(testText, "im changed", 0.5);
        });


        // Pane will hold the greeting text welcoming and showing them the signup/login
        StackPane CTAContainer = new StackPane();

        // Vbox will hold sign in/login information and button
        VBox greetingInfoContainer = new VBox();

        // Add all CTA text and button to the greetingInfoContainer container
        Text welcomeText = new Text("Hello, Friend!");
        Text provideInfoText = new Text("Register with your database details to use all app features.");
        Button animateCTAButton = new Button("Sign Up");
        greetingInfoContainer.getChildren().addAll(welcomeText, provideInfoText, animateCTAButton);
        greetingInfoContainer.setAlignment(Pos.CENTER);

        CTAContainer.getChildren().add(greetingInfoContainer);
        CTAContainer.setMaxWidth(400);

        // Create properties for each of the border corner radius (default radius is 0px for each corner)
        IntegerProperty topLeft = new SimpleIntegerProperty(0);
        IntegerProperty topRight = new SimpleIntegerProperty(0);
        IntegerProperty bottomRight = new SimpleIntegerProperty(0);
        IntegerProperty bottomLeft = new SimpleIntegerProperty(0);

        // Bind the border radius properties to the pane style
        CTAContainer.styleProperty().bind(Bindings.format("-fx-background-color: lightblue; -fx-background-radius: %dpx %dpx %dpx %dpx; -fx-border-radius: %dpx %dpx %dpx %dpx;",
                topLeft, topRight, bottomRight, bottomLeft, topLeft, topRight, bottomRight, bottomLeft));


        // Animate the CTA on click
        // TODO: Potentially change flag to a timer so animation must finish before it can run again
        AtomicBoolean flag = new AtomicBoolean(true);
        animateCTAButton.setOnAction(e->{

            // If the CTA is showing signup text, change it to show sign in text, vice versa
            if (flag.get()){
                animateTextChange(welcomeText, "Welcome Back!", 225);
                animateTextChange(provideInfoText, "Enter your database details.", 225);
                animateButtonChange(animateCTAButton, "Sign In", 225);
                flag.set(false);
            }
            else{
                animateTextChange(welcomeText, "Hello, Friend!", 225);
                animateTextChange(provideInfoText, "Register with your database details to use all app features.", 225);
                animateButtonChange(animateCTAButton, "Sign Up", 225);
                flag.set(true);
            }

        });
//        animateProperties(30000, new double[]{300, 500}, new Property[]{topLeft, topRight});


        this.setTop(CTAContainer);
        this.setCenter(loginContainer);
    }


    /**
     * Animates bound properties of a JavaFx node to specified target values.
     * @param animationLength Length of the animation in milliseconds.
     * @param targetValues Values to update properties to.
     * @param properties Properties to be updated.
     */
    public void animateProperties(double animationLength, double[] targetValues, Property[] properties){

        Timeline timeline = new Timeline();

        for (int i = 0; i < properties.length; i++) {

            // Add the property with its corresponding target value to the keyframe and timeline
            KeyValue keyValue = new KeyValue(properties[i], targetValues[i]);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(animationLength), keyValue);
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.play();
    }

    /**
     * Animates a text node, so it fades out, changes text, and fades in with new text.
     * @param textNode Text node which you want to animate.
     * @param newText Text that you want to replace the current text node text with.
     * @param animationLength Length of the animation in milliseconds.
     */
    public void animateTextChange(Text textNode, String newText, double animationLength){
        // Fade text node out
        FadeTransition fadeOut = new FadeTransition(Duration.millis(animationLength), textNode);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        // Fade text node in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(animationLength), textNode);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        SequentialTransition transitions = new SequentialTransition();
        transitions.getChildren().addAll(fadeOut, fadeIn);

        // After the text fades out change it so that the new text fades in
        fadeOut.setOnFinished(e->{
            textNode.setText(newText);
        });

        transitions.play();
    }

    /**
     * Animates a button node, so it fades out, changes text, and fades in with new text.
     * @param buttonNode Button node which you want to animate.
     * @param newText Text that you want to replace the current button node text with.
     * @param animationLength Length of the animation in milliseconds.
     */
    public void animateButtonChange(Button buttonNode, String newText, double animationLength){
        // Fade button node out
        FadeTransition fadeOut = new FadeTransition(Duration.millis(animationLength), buttonNode);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        // Fade button node in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(animationLength), buttonNode);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        SequentialTransition transitions = new SequentialTransition();
        transitions.getChildren().addAll(fadeOut, fadeIn);

        // After the text fades out change it so that the new text fades in
        fadeOut.setOnFinished(e->{
            buttonNode.setText(newText);
        });

        transitions.play();
    }
}
