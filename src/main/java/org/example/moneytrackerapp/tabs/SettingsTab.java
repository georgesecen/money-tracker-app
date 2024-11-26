package org.example.moneytrackerapp.tabs;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import org.example.moneytrackerapp.pojo.Category;
import org.example.moneytrackerapp.tables.CategoryTable;

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
        this.setText("Settings");

        BorderPane root = new BorderPane();

        CategoryTable categoryTable = CategoryTable.getInstance();

        Text title = new Text("Manage Categories");

        // Headers

        // Content
        ComboBox<Category> catComboBox = new ComboBox<>();
        catComboBox.setItems(FXCollections.observableArrayList(categoryTable.getAllCategories()));
        catComboBox.getSelectionModel().select(0);

        Button deleteCat = new Button("Delete Category");
        Button addCat = new Button("Add Category");

        HBox manageCats = new HBox(catComboBox, deleteCat, addCat);
        manageCats.setAlignment(Pos.TOP_RIGHT);

//        VBox content = new VBox(manageCats);
//        content.setSpacing(20);
//        content.setAlignment(Pos.TOP_RIGHT);
//        content.setMinWidth(500);

        AddCategoryPane pane = new AddCategoryPane();
        root.setRight(pane);
        pane.setVisible(false);

        addCat.setOnAction(e -> {
            // set add pane to be visible
            pane.setVisible(true);

        });

        root.setTop(title);
        root.setAlignment(title, Pos.BOTTOM_CENTER);

        root.setCenter(manageCats);

        root.setMargin(title, new Insets(100, 30, 30, 30));
        root.setMargin(manageCats, new Insets(30, 200, 30, 200));
        root.setMargin(pane, new Insets(30, 50, 0, 0));


        this.setContent(root);
    }

    public static SettingsTab getInstance(){
        if(instance == null){
            instance = new SettingsTab();
        }
        return instance;
    }
}
