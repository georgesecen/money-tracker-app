package org.example.moneytrackerapp.tabs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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

        Text title = new Text("Settings");

        Text addCategoryLabel = new Text("Add Category");

        Text appearance = new Text("Appearance");

        VBox body = new VBox(addCategoryLabel, appearance);
        body.setSpacing(30);

        root.setTop(title);
        root.setAlignment(title, Pos.BOTTOM_CENTER);

        root.setCenter(body);

        root.setMargin(title, new Insets(100, 30, 30, 30));
        root.setMargin(body, new Insets(30, 200, 30, 200));

        this.setContent(root);
    }

    public static SettingsTab getInstance(){
        if(instance == null){
            instance = new SettingsTab();
        }
        return instance;
    }
}
