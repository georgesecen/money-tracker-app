package org.example.moneytrackerapp.tabs;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

        // Delete category section
        Text userCategories = new Text("Your categories: ");
        ComboBox<Category> categories = new ComboBox<>();
        categories.setItems(FXCollections.observableArrayList(categoryTable.getAllCategories()));
        categories.getSelectionModel().select(0);
        Button deleteCat = new Button("X");
        Text errorMsg = new Text("Cannot delete a default category!");
        errorMsg.setTranslateY(-12);
        errorMsg.setVisible(false);

        deleteCat.setOnAction(e -> {
            int id = categories.getSelectionModel().getSelectedItem().getId();
            if(id > 4){     // if the selected category is NOT a default category
                categoryTable.deleteCategory(id);
                errorMsg.setVisible(false);
                categories.setItems(FXCollections.observableArrayList(categoryTable.getAllCategories()));
                categories.getSelectionModel().select(0);
            }
            else{
                // Display error message
                errorMsg.setVisible(true);
            }
        });

        // Add category section
        Text addLabel = new Text("Add a new category");
        TextField catName = new TextField();
        catName.setPromptText("Category name");
        Button addCat = new Button("+");

        ToggleGroup typeToggleGroup = new ToggleGroup();
        RadioButton income = new RadioButton("Income");
        income.setToggleGroup(typeToggleGroup);
        RadioButton expense = new RadioButton("Expense");
        expense.setToggleGroup(typeToggleGroup);
        expense.setSelected(true);
        HBox typeButtons = new HBox(income, expense);
        typeButtons.setSpacing(50);
        typeButtons.setTranslateY(-12);

        // Add button action handler
        addCat.setOnAction(e -> {
            // Get correct id of transaction type
            int transTypeID;
            if(income.isSelected()){
                transTypeID = 1;
            }
            else{
                transTypeID = 2;
            }

            if (catName.getText() != null) {
                Category category = new Category(
                        0,
                        catName.getText(),
                        transTypeID
                );
                categoryTable.addCategory(category);
                categories.setItems(FXCollections.observableArrayList(categoryTable.getAllCategories()));
                categories.getSelectionModel().select(0);
            }
        });

        // Create pane to hold all page content
        GridPane content = new GridPane();
        content.setHgap(20);
        content.setVgap(40);
        content.alignmentProperty().set(Pos.CENTER);

        content.add(userCategories, 0, 0);
        content.add(categories, 10, 0);
        content.add(deleteCat, 11, 0);
        content.add(errorMsg, 10, 1, 2,1);

        content.add(addLabel, 0, 2);
        content.add(catName, 10, 2);
        content.add(addCat, 11, 2);
        content.add(typeButtons, 10, 3,2,1);





        // Set up the view
        BorderPane root = new BorderPane();

        root.setTop(title);
        root.setAlignment(title, Pos.CENTER);
        root.setMargin(title, new Insets(100, 30, 0, 30));


        root.setCenter(content);
        root.setAlignment(content, Pos.CENTER);
        root.setMargin(content, new Insets(30, 200, 30, 200));

        this.setContent(root);
    }

    public static ManageCategoriesTab getInstance(){
        if(instance == null){
            instance = new ManageCategoriesTab();
        }
        return instance;
    }
}
