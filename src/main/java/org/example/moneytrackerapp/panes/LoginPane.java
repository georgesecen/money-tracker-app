package org.example.moneytrackerapp.panes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.example.moneytrackerapp.HelloApplication;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.scenes.MainScene;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.example.moneytrackerapp.HelloApplication.mainScene;

/**
 * Contains sign in/sign up and load/save user credentials functionality.
 * @author George Secen
 */
public class LoginPane extends BorderPane {

    /**
     * Forms for user sign in/sign up and logs user into main app.
     * @throws FileNotFoundException
     */
    public LoginPane() throws FileNotFoundException {

        this.getStyleClass().add("gradient-background");

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
        StackPane loginContainer = new StackPane();
        loginContainer.getStyleClass().add("shadowed-pane");
        loginContainer.setMaxWidth(650);
        loginContainer.setMaxHeight(375);


        // Sign up component
        VBox signUpContainer = new VBox();
        signUpContainer.setMaxHeight(375);
        signUpContainer.setMaxWidth(325);
        signUpContainer.setTranslateX(-163);

        Text signUpTitle = new Text("Sign Up");
        signUpTitle.getStyleClass().addAll("title-text");
        VBox.setMargin(signUpTitle, new Insets(20, 0, 20, 0));

        Text signUpInstructions = new Text("Enter your database credentials");
        signUpInstructions.getStyleClass().add("description-text");
        signUpInstructions.setFont(new Font(15));
        VBox.setMargin(signUpInstructions, new Insets(0, 0, 5, 0));

        TextField signUpNameField = new TextField();
        signUpNameField.setPromptText("DB Name");
        signUpNameField.getStyleClass().add("description-text");
        signUpNameField.setMaxWidth(250);
        VBox.setMargin(signUpNameField, new Insets(0, 0, 12, 0));

        TextField signUpUserField = new TextField();
        signUpUserField.setPromptText("Username");
        signUpUserField.getStyleClass().add("description-text");
        signUpUserField.setMaxWidth(250);
        VBox.setMargin(signUpUserField, new Insets(0, 0, 12, 0));

        TextField signUpPassField = new TextField();
        signUpPassField.setPromptText("Password");
        signUpPassField.getStyleClass().add("description-text");
        signUpPassField.setMaxWidth(250);
        VBox.setMargin(signUpPassField, new Insets(0, 0, 3, 0));

        Text signUpError = new Text();
        signUpError.setStyle("-fx-fill: #fc4242");
        VBox.setMargin(signUpError, new Insets(0, 0, 3, 0));

        Button signUpButton = new Button("Sign Up");
        signUpButton.getStyleClass().addAll("button-dimensions", "light-themed-button");

        signUpContainer.getChildren().addAll(signUpTitle, signUpInstructions, signUpUserField, signUpNameField, signUpPassField, signUpError, signUpButton);
        signUpContainer.setAlignment(Pos.CENTER);

        // Setting the sign up nodes focus to false so none of them are focused by default when the scene loads
        for (Node node : signUpContainer.getChildren()){
            node.setFocusTraversable(false);
        }

        // Sign in component (by default is not shown at first)
        VBox signInContainer = new VBox();
        signInContainer.setOpacity(0);
        signInContainer.setViewOrder(10);
        signInContainer.setMaxHeight(375);
        signInContainer.setMaxWidth(325);

        Text signInTitle = new Text("Sign In");
        signInTitle.getStyleClass().addAll("title-text");
        VBox.setMargin(signInTitle, new Insets(20, 0, 20, 0));

        Text signInInstructions = new Text("Enter your username");
        signInInstructions.setFont(new Font(15));
        VBox.setMargin(signInInstructions, new Insets(0, 0, 5, 0));

        TextField signInUserField = new TextField();
        signInUserField.setPromptText("Username");
        signInUserField.getStyleClass().add("description-text");
        signInUserField.setMaxWidth(250);
        VBox.setMargin(signInUserField, new Insets(0, 0, 3, 0));

        Text signInError = new Text();
        signInError.setStyle("-fx-fill: #fc4242");
        VBox.setMargin(signInError, new Insets(0, 0, 3, 0));

        Button signInButton = new Button("Sign In");
        signInButton.getStyleClass().addAll("button-dimensions", "light-themed-button");

        signInContainer.getChildren().addAll(signInTitle, signInInstructions, signInUserField, signInError, signInButton);
        signInContainer.setAlignment(Pos.CENTER);

        // Setting the sign in nodes focus to false so none of them are focused by default when the scene loads
        for (Node node : signInContainer.getChildren()){
            node.setFocusTraversable(false);
        }

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
                HelloApplication.sceneSwap(new MainScene());
            }
            else{
                // Reset database instance so user can try again
                Database.resetInstance();

                // Give error message to user
                signUpError.setText("Error connecting to database!");
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

                    // Give error message to user
                    signInError.setText("Error connecting to database!");
                    System.out.println("Error connecting to db");

                }
            }
            else{
                // Give error message to user
                signInError.setText("Username not found!");
                System.out.println("Username does not exist");
            }
        });

        // Pane will hold the greeting text welcoming and showing them the signup/login
        StackPane CTAContainer = new StackPane();
        CTAContainer.setMaxHeight(375);
        CTAContainer.setMaxWidth(325);

        // Vbox will hold sign in/login information and button
        VBox greetingInfoContainer = new VBox();

        // Add all CTA text and button to the greetingInfoContainer container
        Text welcomeText = new Text("Hello, Friend!");
        welcomeText.getStyleClass().addAll("cta-text", "title-text");
        VBox.setMargin(welcomeText, new Insets(20, 0, 20, 0));

        Text provideInfoText = new Text("Register with your database details to use all app features.");
        provideInfoText.getStyleClass().addAll("cta-text", "description-text");
        provideInfoText.setWrappingWidth(290);
        provideInfoText.setTextAlignment(TextAlignment.CENTER);
        VBox.setMargin(provideInfoText, new Insets(0, 0, 20, 0));

        Button animateCTAButton = new Button("Sign In");
        animateCTAButton.getStyleClass().addAll("button-dimensions", "dark-themed-button");

        greetingInfoContainer.getChildren().addAll(welcomeText, provideInfoText, animateCTAButton);
        greetingInfoContainer.setAlignment(Pos.CENTER);

        CTAContainer.getChildren().add(greetingInfoContainer);
        CTAContainer.setTranslateX(163); // So CTA's right corner lines up with parent container right corner

        // Create properties for each of the border corner radius (default radius match radius of parent pane check css file)
        IntegerProperty topLeft = new SimpleIntegerProperty(120);
        IntegerProperty topRight = new SimpleIntegerProperty(30);
        IntegerProperty bottomRight = new SimpleIntegerProperty(30);
        IntegerProperty bottomLeft = new SimpleIntegerProperty(90);

        // Bind the border radius properties to the pane style
        CTAContainer.styleProperty().bind(Bindings.format("-fx-background-color: #9966ff; -fx-background-radius: %dpx %dpx %dpx %dpx; -fx-border-radius: %dpx %dpx %dpx %dpx;",
                topLeft, topRight, bottomRight, bottomLeft, topLeft, topRight, bottomRight, bottomLeft));



        // Animation which moves cta to left side of parent container
        TranslateTransition leftTransition = new TranslateTransition(Duration.millis(500), CTAContainer);
        leftTransition.setFromX(163);
        leftTransition.setToX(-163);

        // Animation which moves cta to right side of parent container
        TranslateTransition rightTransition = new TranslateTransition(Duration.millis(500), CTAContainer);
        rightTransition.setFromX(-163);
        rightTransition.setToX(163);

        // Animate the CTA on click
        // TODO: Potentially change flag to a timer so animation must finish before it can run again
        AtomicBoolean flag = new AtomicBoolean(true); // Used to know if the CTA is showing sign up or sign in

        animateCTAButton.setOnAction(e->{

            // If the CTA is not currently in an animation
            if (leftTransition.getStatus() == Animation.Status.STOPPED && rightTransition.getStatus() == Animation.Status.STOPPED){

                // If the CTA is showing signup text, change it to show sign in text, vice versa
                if (flag.get()){
                    // Animate text and buttons
                    animateTextChange(welcomeText, "Welcome Back!", 300);
                    animateTextChange(provideInfoText, "Enter your database details.", 300);
                    animateButtonChange(animateCTAButton, "Sign Up", 300);

                    // Animate CTA border radius
                    animateProperties(500, new double[]{30, 120, 90, 30}, new Property[]{topLeft, topRight, bottomRight, bottomLeft});

                    // Translate CTA to left side of parent container
                    leftTransition.play();

                    // Translate and vanish the sign up form behind the CTA as it passes by
                    animateTranslateFade(signUpContainer, 1, 250, 1, -163, 50, -1);
                    signUpContainer.setViewOrder(10);
                    signUpError.setText("");

                    // Animate the sign in form so it is now visible to the user
                    animateFadeTranslate(signInContainer,250, 1, 250, 1, 50, 163);
                    signInContainer.setViewOrder(0);

                    flag.set(false);
                }
                else{
                    // Animate text and buttons
                    animateTextChange(welcomeText, "Hello, Friend!", 300);
                    animateTextChange(provideInfoText, "Register with your database details to use all app features.", 300);
                    animateButtonChange(animateCTAButton, "Sign In", 300);

                    // Animate CTA border radius
                    animateProperties(500, new double[]{120, 30, 30, 90}, new Property[]{topLeft, topRight, bottomRight, bottomLeft});

                    // Translate CTA to right side of parent container
                    rightTransition.play();

                    // Translate and vanish the sign in form behind the CTA as it passes by
                    animateTranslateFade(signInContainer, 1, 250, 1, 163, 50, -1);
                    signInContainer.setViewOrder(10);
                    signInError.setText("");

                    // Animate the sign up form so it is now visible to the user
                    animateFadeTranslate(signUpContainer, 250, 1, 250, 1, 50, -163);
                    signUpContainer.setViewOrder(0);

                    flag.set(true);
                }
            }
        });


        // Add sign in/up forms and CTA to StackPane login container
        loginContainer.getChildren().addAll(signUpContainer, signInContainer, CTAContainer);
        this.setCenter(loginContainer);
    }


    /**
     * Animates a node, so it fades and then translates horizontally sequentially.
     * @param node JavaFx node to be animated.
     * @param delay Delay in milliseconds before the animation begins.
     * @param fadeAnimationLength Length of fade animation in milliseconds.
     * @param translateAnimationLength Length of translate animation in milliseconds.
     * @param fadeByValue Value to add to opacity property of node during animation.
     * @param fromX Starting x position of the translation animation.
     * @param toX The ending x position of the translation animation.
     */
    private void animateFadeTranslate(Node node, double delay, double fadeAnimationLength, double translateAnimationLength, double fadeByValue, double fromX, double toX){

        // Translate node
        TranslateTransition translate = new TranslateTransition(Duration.millis(translateAnimationLength), node);
        translate.setFromX(fromX);
        translate.setToX(toX);

        // Make node fade out/in
        FadeTransition fade = new FadeTransition(Duration.millis(fadeAnimationLength), node);
        fade.setByValue(fadeByValue);

        // Node should translate first, then fade out/in
        SequentialTransition sequentialTransition = new SequentialTransition(fade, translate);
        sequentialTransition.setDelay(Duration.millis(delay));
        sequentialTransition.play();
    }

    /**
     * Animates a node, so it translates horizontally and then fades sequentially.
     * @param node JavaFx node to be animated.
     * @param delay Delay in milliseconds before the animation begins.
     * @param translateAnimationLength Length of translate animation in milliseconds.
     * @param fadeAnimationLength Length of fade animation in milliseconds.
     * @param fromX Starting x position of the translation animation.
     * @param toX The ending x position of the translation animation.
     * @param fadeByValue Value to add to opacity property of node during animation.
     */
    private void animateTranslateFade(Node node, double delay, double translateAnimationLength, double fadeAnimationLength, double fromX, double toX, double fadeByValue){

        // Translate node
        TranslateTransition translate = new TranslateTransition(Duration.millis(translateAnimationLength), node);
        translate.setFromX(fromX);
        translate.setToX(toX);

        // Make node fade out/in
        FadeTransition fade = new FadeTransition(Duration.millis(fadeAnimationLength), node);
        fade.setByValue(fadeByValue);

        // Node should translate first, then fade out/in
        SequentialTransition sequentialTransition = new SequentialTransition(translate, fade);
        sequentialTransition.setDelay(Duration.millis(delay));
        sequentialTransition.play();
    }

    /**
     * Animates bound properties of a JavaFx node to specified target values.
     * @param animationLength Length of the animation in milliseconds.
     * @param targetValues Values to update properties to.
     * @param properties Properties to be updated.
     */
    private void animateProperties(double animationLength, double[] targetValues, Property[] properties){

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
    private void animateTextChange(Text textNode, String newText, double animationLength){
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
    private void animateButtonChange(Button buttonNode, String newText, double animationLength){
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
