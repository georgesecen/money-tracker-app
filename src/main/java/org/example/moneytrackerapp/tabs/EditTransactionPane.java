package org.example.moneytrackerapp.tabs;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.example.moneytrackerapp.pojo.Category;
import org.example.moneytrackerapp.pojo.DatabaseItem;
import org.example.moneytrackerapp.pojo.Transaction;
import org.example.moneytrackerapp.tables.CategoryTable;
import org.example.moneytrackerapp.tables.TransactionTable;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Class EditTransactionsPane is a pane that is displayed
 * in the DisplayTransactionsTab. It allows users to update transactions
 *
 * @author cameronmcrae
 */
public class EditTransactionPane extends GridPane {
    /**
     * @param arrayList;
     * @param id;
     * @return searchList array position
     */
    public int find(ArrayList<?> arrayList, int id){
        ArrayList<DatabaseItem> searchList = (ArrayList<DatabaseItem>) ((ArrayList<?>) arrayList);
        for(int i = 0; i < searchList.size(); i++){
            if(searchList.get(i).getDBId() == id){
                return i;
            }
        }
        return 0;
    }
    /**
     * Class constructor that takes transaction as argument
     * @param transaction;
     */
    public EditTransactionPane(Transaction transaction) {
        this.setPadding(new Insets(10,10,10,10));
        this.setVgap(10);
        this.setHgap(10);
        Transaction transaction1 = transaction;
        CategoryTable categoryTable = CategoryTable.getInstance();
        TransactionTable transactionTable = TransactionTable.getInstance();
        // Amount Form Field
        Text amountLabel = new Text("Amount:");
        TextField amount = new TextField();
        amount.getStyleClass().add("input-box");
        amount.setText(String.valueOf(transaction.getAmt()));
        this.add(amountLabel, 0,0);
        this.add(amount, 1,0);
        // Description Form Field
        Text descriptionLabel = new Text("Description");
        TextField description = new TextField();
        description.getStyleClass().add("input-box");
        description.setText(transaction.getDesc());
        this.add(descriptionLabel,0,1);
        this.add(description,1,1);
        // Date Form Field - Date-Picker
        Text date = new Text("Date");
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(transaction.getDate().toLocalDate());
        datePicker.getValue();
        this.add(date,0,3);
        this.add(datePicker,1,3);
        // Categories Form Field - ComboBox
        Text categories = new Text("Categories:");
        ComboBox<Category> categoryComboBox = new ComboBox<>();
        ArrayList<Category> allCategories = categoryTable.getAllCategories();
        categoryComboBox.setItems(FXCollections.observableArrayList(allCategories));
        categoryComboBox.getSelectionModel().select(find(allCategories, transaction.getCat_id()));
        this.add(categories,0,2);
        this.add(categoryComboBox,1,2);
        // Create Update Button
        Button update = new Button("Update");
        update.getStyleClass().addAll("light-themed-button", "button-dimensions");
        /**
         * Runs update query on transactions table and refresh table
         */
        update.setOnAction(e->{
            transaction.setAmt(Double.parseDouble(amount.getText()));
            transaction.setDesc(String.valueOf(description.getText()));
            transaction.setDate(Date.valueOf(datePicker.getValue()));
            transaction.setCat_id(categoryComboBox.getSelectionModel().getSelectedItem().getDBId());
            transactionTable.updateTransaction(transaction);
            DisplayTransactionsTab.getInstance().refreshTable();

            // Update charts after updating item
            StatisticsTab.getInstance().generateCharts(30);
        });
        this.add(update, 1,4);
    }
}
