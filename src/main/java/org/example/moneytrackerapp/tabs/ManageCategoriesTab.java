package org.example.moneytrackerapp.tabs;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.example.moneytrackerapp.pojo.Category;
import org.example.moneytrackerapp.tables.CategoryTable;

/**
 * ManageCategoriesTab class represents a page that will allow the user
 * to customize the app by deleting or adding additional
 * category types.
 * @author Wania Sharif
 */
public class ManageCategoriesTab extends Tab {
    private static ManageCategoriesTab instance;

    public ManageCategoriesTab() {
        this.setText("Manage Categories");

        CategoryTable categoryTable = CategoryTable.getInstance();

        Text title = new Text("Manage Categories");

        Text userCategories = new Text("Your categories");
        ComboBox<Category> categories = new ComboBox<>();

        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);
        content.add(userCategories, 0, 0,3,1);
        content.add(categories, 1, 0);


        // Set up the view
        BorderPane root = new BorderPane();

        root.setTop(title);
        root.setCenter(content);
        this.setContent(root);
    }

    public static ManageCategoriesTab getInstance(){
        if(instance == null){
            instance = new ManageCategoriesTab();
        }
        return instance;
    }
}
