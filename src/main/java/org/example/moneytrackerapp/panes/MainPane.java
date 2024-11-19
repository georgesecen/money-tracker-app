package org.example.moneytrackerapp.panes;

import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.example.moneytrackerapp.HelloApplication;
import org.example.moneytrackerapp.tabs.StatisticsTab;

public class MainPane extends BorderPane {
    public MainPane(){
        HelloApplication.mainStage.setTitle("Money Tracker App");

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(new StatisticsTab());

        this.setCenter(tabPane);
    }
}
