package org.example.moneytrackerapp.panes;

import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.example.moneytrackerapp.HelloApplication;
import org.example.moneytrackerapp.tabs.StatisticsTab;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.tabs.AddTransactionTab;
import org.example.moneytrackerapp.tabs.DisplayTransactionsTab;

public class MainPane extends BorderPane {
    public MainPane(){
        HelloApplication.mainStage.setTitle("Money Tracker App");

        // TabPane navigation bar
        TabPane tabPane = new TabPane();
        AddTransactionTab addTransactionTab = AddTransactionTab.getInstance();
        DisplayTransactionsTab displayTransactionsTab = DisplayTransactionsTab.getInstance();
        StatisticsTab statisticsTab = StatisticsTab.getInstance();
        tabPane.getTabs().addAll(displayTransactionsTab, addTransactionTab, statisticsTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Database db = Database.getInstance();
        this.setTop(tabPane);
    }
}
