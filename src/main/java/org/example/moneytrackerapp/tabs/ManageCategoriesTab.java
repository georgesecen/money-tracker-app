package org.example.moneytrackerapp.tabs;

import javafx.scene.control.Tab;

/**
 * ManageCategoriesTab class represents a page that will allow the user
 * to customize the app by changing styles or adding additional
 * category types.
 * @author Wania Sharif
 */
public class ManageCategoriesTab extends Tab {
    private static ManageCategoriesTab instance;

    public ManageCategoriesTab() {}

    public static ManageCategoriesTab getInstance(){
        if(instance == null){
            instance = new ManageCategoriesTab();
        }
        return instance;
    }
}
