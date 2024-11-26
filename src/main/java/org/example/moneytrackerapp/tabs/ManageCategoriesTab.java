package org.example.moneytrackerapp.tabs;

import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.example.moneytrackerapp.tables.CategoryTable;

/**
 * ManageCategoriesTab class represents a page that will allow the user
 * to customize the app by changing styles or adding additional
 * category types.
 * @author Wania Sharif
 */
public class ManageCategoriesTab extends Tab {
    private static ManageCategoriesTab instance;

    public ManageCategoriesTab() {
        this.setText("Manage Categories");

        CategoryTable categoryTable = CategoryTable.getInstance();

        Text title = new Text("Manage Categories");

        // Set up the view
        BorderPane root = new BorderPane();

        root.setTop(title);
        this.setContent(root);
    }

    public static ManageCategoriesTab getInstance(){
        if(instance == null){
            instance = new ManageCategoriesTab();
        }
        return instance;
    }
}
