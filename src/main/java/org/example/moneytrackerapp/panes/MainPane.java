package org.example.moneytrackerapp.panes;

import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import org.example.moneytrackerapp.HelloApplication;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.tabs.AddTransactionTab;
import org.example.moneytrackerapp.tabs.ManageCategoriesTab;

public class MainPane extends BorderPane {
    public MainPane(){
        HelloApplication.mainStage.setTitle("Money Tracker App");

        // TabPane navigation bar
        TabPane tabPane = new TabPane();
        AddTransactionTab addTransactionTab = AddTransactionTab.getInstance();
        ManageCategoriesTab manageCategoriesTab = ManageCategoriesTab.getInstance();

        tabPane.getTabs().add(addTransactionTab);
        tabPane.getTabs().add(manageCategoriesTab);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Database db = Database.getInstance();
        this.setTop(tabPane);
    }
}
