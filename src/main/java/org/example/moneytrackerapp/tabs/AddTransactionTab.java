package org.example.moneytrackerapp.tabs;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.moneytrackerapp.pojo.Category;
import org.example.moneytrackerapp.pojo.Transaction;
import org.example.moneytrackerapp.tables.CategoryTable;
import org.example.moneytrackerapp.tables.TransactionTable;
import java.sql.Date;

import java.time.LocalDate;

/**
 * This class represents a tab which will display a form
 * to the user for inserting new Transaction records.
 *
 * @author Wania Sharif
 */
public class AddTransactionTab extends Tab {
    private static AddTransactionTab instance;

    private static ComboBox<Category> cat;
    private static ToggleGroup typeToggleGroup;
    private static RadioButton type1;
    private static RadioButton type2;

    /**
     * Constructor with no args that holds all the
     * page's elements.
     */
    public AddTransactionTab() {
        this.setText("Add Transaction");
        BorderPane root = new BorderPane();

        // Get tables
        TransactionTable transactionTable = TransactionTable.getInstance();
        CategoryTable categoryTable = CategoryTable.getInstance();

        // Title
        Text title = new Text("Add Transaction");
        title.getStyleClass().add("title-text");

        // Transaction type
        Text typeLabel = new Text("Transaction type");
        typeLabel.getStyleClass().add("description-text");

        typeToggleGroup = new ToggleGroup();
        type1 = new RadioButton("Income");
        type1.setToggleGroup(typeToggleGroup);
        type2 = new RadioButton("Expense");
        type2.setToggleGroup(typeToggleGroup);
        type2.setSelected(true);

        HBox typeBox = new HBox(type1, type2);
        typeBox.alignmentProperty().set(Pos.CENTER);
        typeBox.setSpacing(20);
        typeBox.setTranslateY(-6);

        // Transaction amount
        Text amountLabel = new Text("Amount");
        amountLabel.getStyleClass().add("description-text");
        TextField amount = new TextField();
        amount.setMaxWidth(130);
        amount.alignmentProperty().set(Pos.CENTER);
        amount.setPromptText("00.00");
        amount.getStyleClass().add("input-box");
        amount.setTranslateY(-10);

        // Description
        Text descLabel = new Text("Description");
        descLabel.getStyleClass().add("description-text");
        TextField desc = new TextField();
        desc.setMaxWidth(350);
        desc.getStyleClass().add("input-box");
        desc.setTranslateY(-10);

        // Category
        System.out.println(categoryTable.getAllCategories());
        Text catLabel = new Text("Category");
        catLabel.getStyleClass().add("description-text");
        cat = new ComboBox<>();
        cat.setMinWidth(140);
        cat.getStyleClass().add("input-box");
        cat.setItems(FXCollections.observableArrayList(categoryTable.getAllExpenseCategories()));
        cat.getSelectionModel().select(0);

        type1.setOnAction(e -> {
            refreshCategoryBox(categoryTable);
        });
        type2.setOnAction(e -> {
            refreshCategoryBox(categoryTable);
        });

        // Date
        Text dateLabel = new Text("Date");
        dateLabel.getStyleClass().add("description-text");
        DatePicker date = new DatePicker();
        date.setValue(LocalDate.now());
        date.setMinWidth(120);


        // Pane holding category and date selection
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.add(catLabel, 0, 0);
        pane.add(cat, 0, 1);
        pane.add(dateLabel, 1, 0);
        pane.add(date, 1, 1);
        pane.setHgap(20);
        pane.setVgap(10);

        // Texts to display messages
        Text errorMessage = new Text("Invalid input, please try again");
        errorMessage.setTranslateY(35);

        Text successMessage = new Text("Entry has been added!");




        // Submit
        Button submit = new Button("Add Transaction");
        submit.setTranslateY(-40);
        submit.getStyleClass().addAll("button-dimensions", "light-themed-button");
        submit.setOnAction(e -> {
            // ensure all fields are valid before proceeding
            try {
                // First sanitize the amount input
                // If income is selected, change a negative input to positive
                // If expense is selected, change a positive input to negative
                double amtEntered = Double.parseDouble(amount.getText());
                if (type1.isSelected()) {
                    if (amtEntered < 0) {
                        amtEntered *= -1;
                    }
                } else {
                    if (amtEntered > 0) {
                        amtEntered *= -1;
                    }
                }

                // Create and add the new transaction
                Transaction transaction = new Transaction(
                        0,
                        amtEntered,
                        desc.getText(),
                        Date.valueOf(date.getValue()),
                        cat.getSelectionModel().getSelectedItem().getDBId()
                );
                transactionTable.createTransaction(transaction);

                // Display success message
                displayMessage(successMessage);

                // Refresh transactions page table
                DisplayTransactionsTab.getInstance().refreshTable();

                // Update charts after adding item
                StatisticsTab.getInstance().generateCharts(30);

            } catch (Exception ex) {
                System.out.println("Invalid input");

                // display error message
                displayMessage(errorMessage);
            }
        });



        // Vbox to hold form
        VBox form = new VBox(typeLabel, typeBox, amountLabel, amount, descLabel, desc,
                                        pane, errorMessage, successMessage, submit);
        form.setSpacing(15);

        // Display elements
        root.setTop(title);
        root.setAlignment(title, Pos.BOTTOM_CENTER);
        root.setCenter(form);
        //root.setMargin(title, new Insets(0, 30, 20, 30));
        root.setMargin(form, new Insets(0, 200, 0, 200));

        form.setAlignment(Pos.CENTER);
        this.setContent(root);
    }

    /**
     * This method returns the current instance of the
     * AddTransactionTab. If none exists, it will
     * return a new one.
     *
     * @return instance of tab
     */
    public static AddTransactionTab getInstance(){
        if(instance == null){
            instance = new AddTransactionTab();
        }
        return instance;
    }

    /**
     * Refreshes the combobox and its contents with categories
     * corresponding to the type selected
     *
     * @param categoryTable Category table instance to get records from
     */
    public static void refreshCategoryBox(CategoryTable categoryTable){
        if(typeToggleGroup.getSelectedToggle() == type2){
            cat.setItems(FXCollections.observableArrayList(categoryTable.getAllExpenseCategories()));
            cat.getSelectionModel().select(0);
        } else if (typeToggleGroup.getSelectedToggle() == type1) {
            cat.setItems(FXCollections.observableArrayList(categoryTable.getAllIncomeCategories()));
            cat.getSelectionModel().select(0);
        }
    }

    /**
     * Takes a message and plays an animation on it to
     * display it. The animation lowers the text by 30 units
     * and then returns it to its original position.
     *
     * @param message text to be animated
     */
    private void displayMessage(Text message){
        double initialPos = message.getTranslateY();
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(300), message);
        slideIn.setToY(30 + initialPos);

        TranslateTransition slideOut = new TranslateTransition(Duration.millis(300), message);
        slideOut.setToY(initialPos);

        SequentialTransition st = new SequentialTransition();
        st.getChildren().addAll(slideIn, new PauseTransition(Duration.millis(1400)), slideOut);
        st.play();
    }
}
