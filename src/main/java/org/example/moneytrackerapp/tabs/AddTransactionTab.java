package org.example.moneytrackerapp.tabs;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.example.moneytrackerapp.pojo.Category;
import org.example.moneytrackerapp.pojo.Transaction;
import org.example.moneytrackerapp.tables.CategoryTable;
import org.example.moneytrackerapp.tables.TransactionTable;
import org.example.moneytrackerapp.tables.TransactionTypeTable;
import java.sql.Date;

import java.time.LocalDate;

public class AddTransactionTab extends Tab {
    private static AddTransactionTab instance;
    public AddTransactionTab() {
        this.setText("Add Transaction");
        BorderPane root = new BorderPane();

        // Get tables
        TransactionTable transactionTable = TransactionTable.getInstance();
        CategoryTable categoryTable = CategoryTable.getInstance();
        TransactionTypeTable transactionTypeTable = new TransactionTypeTable();

        // Title
        Text title = new Text("Add Transaction");

        // Transaction type
        Text typeLabel = new Text("Transaction Type");
        ToggleGroup typeToggleGroup = new ToggleGroup();
        RadioButton type1 = new RadioButton("Income");
        type1.setToggleGroup(typeToggleGroup);
        RadioButton type2 = new RadioButton("Expense");
        type2.setToggleGroup(typeToggleGroup);

        HBox typeBox = new HBox(type1, type2);
        typeBox.alignmentProperty().set(Pos.CENTER);
        typeBox.setSpacing(20);


        type2.setSelected(true);

        // Transaction amount
        Text amountLabel = new Text("Amount");
        TextField amount = new TextField();

        // Description
        Text descLabel = new Text("Description");
        TextField desc = new TextField();

        // Category
        Text catLabel = new Text("Category");
        ComboBox<Category> cat = new ComboBox<>();
        cat.setItems(FXCollections.observableArrayList(categoryTable.getAllExpenseCategories()));
        cat.getSelectionModel().select(0);

        type1.setOnAction(e -> {
            cat.setItems(FXCollections.observableArrayList(categoryTable.getAllIncomeCategories()));
            cat.getSelectionModel().select(0);


        });
        type2.setOnAction(e -> {
            cat.setItems(FXCollections.observableArrayList(categoryTable.getAllExpenseCategories()));
            cat.getSelectionModel().select(0);

        });

        // Date
        Text dateLabel = new Text("Date");
        DatePicker date = new DatePicker();
        date.setValue(LocalDate.now());

        // Submit
        Button submit = new Button("Add Transaction");
        submit.setOnAction(e -> {
//            Transaction transaction = new Transaction(
//                    0,
//                    Double.parseDouble(amount.getText()),
//                    desc.getText(),
//                    Date.valueOf(date.getValue()),
//                    cat.getSelectionModel().getSelectedItem().getId()
//            );
            //System.out.println(Date.valueOf(date.getValue()));
            System.out.println(cat.getSelectionModel().getSelectedItem().getId());
            Transaction transaction = new Transaction(
                    0,
                    21.00,
                    "description",
                    Date.valueOf(date.getValue()),
                    cat.getSelectionModel().getSelectedItem().getId()
            );
            //Transaction transaction = new Transaction();
            transactionTable.createTransaction(transaction);
        });



        // Vbox to hold form
        VBox form = new VBox(typeLabel, typeBox, amountLabel, amount, descLabel, desc,
                                        catLabel, cat, dateLabel, date, submit);
        form.setSpacing(10);


        // Display elements
        root.setTop(title);
        root.setAlignment(title, Pos.BOTTOM_CENTER);
        root.setCenter(form);
        root.setMargin(title, new Insets(100, 30, 30, 30));
        root.setMargin(form, new Insets(30, 200, 30, 200));

        form.setAlignment(Pos.CENTER);
        this.setContent(root);
    }

    public static AddTransactionTab getInstance(){
        if(instance == null){
            instance = new AddTransactionTab();
        }
        return instance;
    }
}
