package org.example.moneytrackerapp.tabs;

import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import org.example.moneytrackerapp.tables.TransactionTable;

public class AddTransactionTab extends Tab {
    private static AddTransactionTab instance;
    public AddTransactionTab() {
        this.setText("Add Transaction");
        BorderPane root = new BorderPane();

    }

    public static AddTransactionTab getInstance(){
        if(instance == null){
            instance = new AddTransactionTab();
        }
        return instance;
    }
}
