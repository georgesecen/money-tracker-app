package org.example.moneytrackerapp.scenes;

import javafx.scene.Scene;
import org.example.moneytrackerapp.Const;
import org.example.moneytrackerapp.HelloApplication;
import org.example.moneytrackerapp.panes.MainPane;

/**
 * Scene which contains all tabs such as add transactions, statistics etc.
 */
public class MainScene extends Scene {

    /**
     * Creates a main scene with MainPane and screen dimensions.
     */
    public MainScene(){
        super(new MainPane(), Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
        this.getStylesheets().add(HelloApplication.class.getResource("main.css").toExternalForm());
    }
}
