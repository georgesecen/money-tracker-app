package org.example.moneytrackerapp.panes;

import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.example.moneytrackerapp.HelloApplication;
import org.example.moneytrackerapp.tabs.StatisticsTab;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.tabs.AddTransactionTab;
import org.example.moneytrackerapp.tabs.DisplayTransactionsTab;

import java.io.FileInputStream;
import java.util.Objects;

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

//        // Change tab
//        tabPane.getSelectionModel().select(statisticsTab);


        // StackPane will hold all the nav bar components
        StackPane navbar = new StackPane();

        // Circle behind navbar icons
        Circle circle = new Circle(50);



        VBox icons = new VBox();

        // Get all the icons for the navbar
        Image displayTransactionsIcon = new Image(getClass().getResourceAsStream("/images/grid.png"));
        ImageView displayTransactionsImage = new ImageView(displayTransactionsIcon);
        Image statisticsIcon = new Image(getClass().getResourceAsStream("/images/chart.png"));
        ImageView statisticsImage = new ImageView(statisticsIcon);
        Image settingsIcon = new Image(getClass().getResourceAsStream("/images/settings.png"));
        ImageView settingsImage = new ImageView(settingsIcon);

        // Create buttons for every icon
        Button displayTransactionsButton = new Button();
        displayTransactionsButton.setGraphic(displayTransactionsImage);

        Button displayStatisticsButton = new Button();
        displayStatisticsButton.setGraphic(statisticsImage);

        Button settingsButton = new Button();
        settingsButton.setGraphic(settingsImage);

        icons.getChildren().addAll(displayTransactionsButton, displayStatisticsButton, settingsButton);



        navbar.getChildren().addAll(icons);




        Database db = Database.getInstance();
        this.setLeft(navbar);
        this.setCenter(tabPane);
    }
}
