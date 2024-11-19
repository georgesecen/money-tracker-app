package org.example.moneytrackerapp.panes;

import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.example.moneytrackerapp.HelloApplication;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.tabs.AddTransactionTab;

public class MainPane extends BorderPane {
    public MainPane(){
        HelloApplication.mainStage.setTitle("Money Tracker App");

        // TabPane navigation bar
        TabPane tabPane = new TabPane();
        AddTransactionTab addTransactionTab = AddTransactionTab.getInstance();
        tabPane.getTabs().add(addTransactionTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Database db = Database.getInstance();
        this.setTop(tabPane);
    }
}
