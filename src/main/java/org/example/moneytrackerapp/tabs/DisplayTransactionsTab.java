package org.example.moneytrackerapp.tabs;

import javafx.scene.control.Tab;

public class DisplayTransactionsTab extends Tab {
    private static DisplayTransactionsTab instance;
    public DisplayTransactionsTab() {

    }
    public static DisplayTransactionsTab getInstance(){
        if(instance == null){
            instance = new DisplayTransactionsTab();
        }
        return instance;
    }
}
