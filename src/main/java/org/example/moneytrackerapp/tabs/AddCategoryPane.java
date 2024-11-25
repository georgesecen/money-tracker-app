package org.example.moneytrackerapp.tabs;

import javafx.scene.layout.GridPane;
import org.example.moneytrackerapp.tables.CategoryTable;
import org.example.moneytrackerapp.tables.TransactionTable;

/**
 * This class represents a pane which will hold the form that will
 * allow the user to add a Category object.
 * @author Wania Sharif
 */
public class AddCategoryPane extends GridPane {
    public AddCategoryPane() {
        CategoryTable categoryTable = CategoryTable.getInstance();
        TransactionTable transactionTable = TransactionTable.getInstance();

    }
}
