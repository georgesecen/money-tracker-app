package org.example.moneytrackerapp.tabs;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
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

        Text nameLabel = new Text("Category Name");
        TextField name = new TextField();

        Text typeLabel = new Text("Category type");
        ToggleGroup typeToggleGroup = new ToggleGroup();
        RadioButton type1 = new RadioButton("Income");
        type1.setToggleGroup(typeToggleGroup);
        RadioButton type2 = new RadioButton("Expense");
        type2.setToggleGroup(typeToggleGroup);
        type2.setSelected(true);

        this.add(nameLabel, 0, 0);
        this.add(name, 1, 0, 2, 1);

        this.add(typeLabel, 0, 1);
        this.add(type1, 1, 1);
        this.add(type2, 2, 1);
    }
}
