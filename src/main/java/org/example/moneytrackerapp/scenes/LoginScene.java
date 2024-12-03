package org.example.moneytrackerapp.scenes;

import javafx.scene.Scene;
import org.example.moneytrackerapp.Const;
import org.example.moneytrackerapp.panes.LoginPane;

import java.io.IOException;

/**
 * Scene which user can sign in or sign up.
 */
public class LoginScene extends Scene {

    /**
     * Creates a login scene with LoginPane and screen dimensions.
     * @throws IOException
     */
    public LoginScene() throws IOException {
        super(new LoginPane(), Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
    }

}
