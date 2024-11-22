package org.example.moneytrackerapp.tabs;

import javafx.scene.control.Tab;

/**
 * SettingsTab class represents the Settings page. This will display to the user
 * a page that allows the user to customize the app by changing styles or adding
 * additional category types.
 *
 * @author Wania Sharif
 */

public class SettingsTab extends Tab {
    private static SettingsTab instance;

    public SettingsTab() {

    }

    public static SettingsTab getInstance(){
        if(instance == null){
            instance = new SettingsTab();
        }
        return instance;
    }
}
