package org.example.moneytrackerapp.tabs;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.moneytrackerapp.pojo.Category;
import org.example.moneytrackerapp.tables.CategoryTable;
import org.example.moneytrackerapp.tables.TransactionTable;

import static org.example.moneytrackerapp.tabs.AddTransactionTab.refreshCategoryBox;

/**
 * ManageCategoriesTab class represents a page that will allow the user
 * to customize the app by deleting or adding additional
 * category types.
 *
 * @author Wania Sharif
 */
public class ManageCategoriesTab extends Tab {
    private static ManageCategoriesTab instance;

    /**
     * Constructor with no args that holds all of the
     * page's elements.
     */
    public ManageCategoriesTab() {
        this.setText("Manage Categories");

        TransactionTable transactionTable = TransactionTable.getInstance();
        CategoryTable categoryTable = CategoryTable.getInstance();

        Text title = new Text("Manage Categories");
        title.getStyleClass().add("title-text");

        // Delete category section
        Text userCategories = new Text("Your categories: ");
        userCategories.getStyleClass().add("description-text");

        ComboBox<Category> categories = new ComboBox<>();
        categories.setItems(FXCollections.observableArrayList(categoryTable.getAllCategories()));
        categories.getSelectionModel().select(0);
        categories.setMinWidth(160);

        Button deleteCat = new Button();
        deleteCat.getStyleClass().addAll("light-themed-button", "small-button");
        Image trashIcon = new Image(getClass().getResourceAsStream("/images/trash-bin.png"));
        ImageView trashIconImg = new ImageView(trashIcon);
        trashIconImg.setFitHeight(18);
        trashIconImg.setFitWidth(18);
        deleteCat.setGraphic(trashIconImg);


        Text errorMsg = new Text("Cannot delete a default category!");
        errorMsg.setTranslateY(-20);
        errorMsg.setOpacity(0);

        Text deleteSuccessMsg = new Text("Deleted category");
        deleteSuccessMsg.setTranslateY(-20);
        deleteSuccessMsg.setTranslateX(-190);
        deleteSuccessMsg.setOpacity(0);

        deleteCat.setOnAction(e -> {
            int id = categories.getSelectionModel().getSelectedItem().getId();
            if(id > 4){     // if the selected category is NOT a default category
                // Delete all transactions with that category
                transactionTable.deleteTransactionByCategory(id);

                categoryTable.deleteCategory(id);
                categories.setItems(FXCollections.observableArrayList(categoryTable.getAllCategories()));
                categories.getSelectionModel().select(0);

                // Refresh combobox on add entry page
                refreshCategoryBox(categoryTable);

                // Refresh transactions page table
                DisplayTransactionsTab.getInstance().refreshTable();

                //Display success message
                displayMessage(deleteSuccessMsg);
            }
            else{
                // Display error message
                displayMessage(errorMsg);
            }
        });

        // Add category section
        Text addLabel = new Text("Add a new category");
        addLabel.getStyleClass().add("description-text");

        TextField catName = new TextField();
        catName.setPromptText("Category name");
        catName.getStyleClass().add("input-box");

        Button addCat = new Button(" + ");
        addCat.getStyleClass().addAll("light-themed-button", "small-button");

        ToggleGroup typeToggleGroup = new ToggleGroup();
        RadioButton income = new RadioButton("Income");
        income.setToggleGroup(typeToggleGroup);
        RadioButton expense = new RadioButton("Expense");
        expense.setToggleGroup(typeToggleGroup);
        expense.setSelected(true);

        HBox typeButtons = new HBox(income, expense);
        typeButtons.setSpacing(50);
        typeButtons.setTranslateY(-12);

        Text addSuccessMsg = new Text("Successfully added category");
        addSuccessMsg.setTranslateY(-20);
        addSuccessMsg.setOpacity(0);

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

                // Refresh combobox on add entry page
                refreshCategoryBox(categoryTable);

                // Display add success message
                displayMessage(addSuccessMsg);
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
        content.add(deleteSuccessMsg, 11, 1);

        content.add(addLabel, 0, 2);
        content.add(catName, 10, 2);
        content.add(addCat, 11, 2);
        content.add(typeButtons, 10, 3,2,1);
        content.add(addSuccessMsg, 10, 4);





        // Set up the view
        BorderPane root = new BorderPane();

        root.setTop(title);
        root.setAlignment(title, Pos.CENTER);
        root.setMargin(title, new Insets(0, 30, 0, 30));


        root.setCenter(content);
        root.setAlignment(content, Pos.CENTER);
        root.setMargin(content, new Insets(30, 200, 30, 200));

        this.setContent(root);
    }

    /**
     * This method returns the current instance of the
     * tab ManageCategoriesTab. If none exists, it will
     * return a new one.
     *
     * @return instance of tab
     */
    public static ManageCategoriesTab getInstance(){
        if(instance == null){
            instance = new ManageCategoriesTab();
        }
        return instance;
    }

    /**
     * method takes in a text and animates it to display it on
     * the page momentarily using FadeTransition.
     *
     * @param message Text to display
     */
    private void displayMessage(Text message){
        FadeTransition fadeIn = new FadeTransition(Duration.millis(200), message);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), message);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        SequentialTransition st = new SequentialTransition();
        st.getChildren().addAll(fadeIn, new PauseTransition(Duration.millis(1200)), fadeOut);
        st.play();
    }
}
