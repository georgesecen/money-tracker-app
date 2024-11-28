package org.example.moneytrackerapp.panes;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
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
        navbar.getStyleClass().add("nav-bar");
        navbar.setMaxWidth(50);
        navbar.setMinWidth(50);
        navbar.setMaxHeight(250);

        // Circle behind navbar icons, by default behind display transactions icon
        Circle circle = new Circle(20);
        circle.setStyle("-fx-fill: #5b3c99");
        circle.setTranslateY(-86);


        VBox icons = new VBox();
        icons.setAlignment(Pos.CENTER);
        icons.setSpacing(15);

        // Get all the icons for the navbar
        Image displayTransactionsIcon = new Image(getClass().getResourceAsStream("/images/grid.png"));
        ImageView displayTransactionsImage = new ImageView(displayTransactionsIcon);
        displayTransactionsImage.setFitWidth(22);
        displayTransactionsImage.setFitHeight(22);

        Image addTransactionIcon = new Image(getClass().getResourceAsStream("/images/settings.png"));
        ImageView addTransactionImage = new ImageView(addTransactionIcon);
        addTransactionImage.setFitWidth(22);
        addTransactionImage.setFitHeight(22);

        Image statisticsIcon = new Image(getClass().getResourceAsStream("/images/chart.png"));
        ImageView statisticsImage = new ImageView(statisticsIcon);
        statisticsImage.setFitWidth(22);
        statisticsImage.setFitHeight(22);

        Image settingsIcon = new Image(getClass().getResourceAsStream("/images/settings.png"));
        ImageView settingsImage = new ImageView(settingsIcon);
        settingsImage.setFitWidth(22);
        settingsImage.setFitHeight(22);

        // Create buttons for every icon
        Button displayTransactionsButton = new Button();
        displayTransactionsButton.getStyleClass().add("nav-button");
        displayTransactionsButton.setGraphic(displayTransactionsImage);

        Button addTransactionButton = new Button();
        addTransactionButton.getStyleClass().add("nav-button");
        addTransactionButton.setGraphic(addTransactionImage);

        Button displayStatisticsButton = new Button();
        displayStatisticsButton.getStyleClass().add("nav-button");
        displayStatisticsButton.setGraphic(statisticsImage);

        Button settingsButton = new Button();
        settingsButton.getStyleClass().add("nav-button");
        settingsButton.setGraphic(settingsImage);

        // Animate the circle behind the icons to animate behind the icon
        // which is clicked
        displayTransactionsButton.setOnAction(e-> animateTranslateY(circle, 175, -86));
        addTransactionButton.setOnAction(e-> animateTranslateY(circle, 175, -29));
        displayStatisticsButton.setOnAction(e-> animateTranslateY(circle, 175, 28));
        settingsButton.setOnAction(e-> animateTranslateY(circle, 175, 86));

        icons.getChildren().addAll(displayTransactionsButton, addTransactionButton, displayStatisticsButton, settingsButton);



        navbar.getChildren().addAll(circle, icons);




        Database db = Database.getInstance();
        this.setLeft(navbar);
        this.setCenter(tabPane);
    }

    /**
     * Animates a node, so it translates to specified Y position.
     * @param node JavaFx node to be animated.
     * @param animationLength Length of animation in milliseconds.
     * @param toY The ending Y position of the translation animation.
     */
    private void animateTranslateY(Node node, double animationLength, double toY){
        TranslateTransition translate = new TranslateTransition(Duration.millis(animationLength), node);
        translate.setToY(toY);

        translate.setInterpolator(Interpolator.EASE_BOTH);

        translate.play();
    }
}
