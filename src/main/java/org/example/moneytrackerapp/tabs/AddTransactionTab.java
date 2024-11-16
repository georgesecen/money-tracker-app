package org.example.moneytrackerapp.tabs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.example.moneytrackerapp.tables.CategoryTable;
import org.example.moneytrackerapp.tables.TransactionTable;
import org.example.moneytrackerapp.tables.TransactionTypeTable;

public class AddTransactionTab extends Tab {
    private static AddTransactionTab instance;
    public AddTransactionTab() {
        this.setText("Add Transaction");
        BorderPane root = new BorderPane();

        // Get tables
//        TransactionTable transactionTable = TransactionTable.getInstance();
//        CategoryTable categoryTable = CategoryTable.getInstance();
//        TransactionTypeTable transactionTypeTable = new TransactionTypeTable();

        Text title = new Text("Add Transaction");
        title.setTextAlignment(TextAlignment.CENTER);

        // Transaction amount
        Text amountLabel = new Text("Amount");
        TextField amount = new TextField();

        // Vbox to hold form
        VBox form = new VBox(amountLabel, amount);
        form.maxHeight(200);
        form.maxWidth(200);

        form.setBorder(Border.stroke(Paint.valueOf("#000000")));

        // Display elements
        root.setTop(title);
        root.setCenter(form);
        root.setMargin(title, new Insets(100, 30, 30, 30));
        root.setMargin(form, new Insets(30, 100, 30, 100));

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
