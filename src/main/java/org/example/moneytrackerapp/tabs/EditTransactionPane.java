package org.example.moneytrackerapp.tabs;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.example.moneytrackerapp.pojo.Category;
import org.example.moneytrackerapp.pojo.DatabaseItem;
import org.example.moneytrackerapp.pojo.Transaction;
import org.example.moneytrackerapp.tables.CategoryTable;
import org.example.moneytrackerapp.tables.TransactionTable;
import org.example.moneytrackerapp.tables.TransactionTypeTable;

import java.util.ArrayList;

public class EditTransactionPane extends GridPane {
    public int find(ArrayList<?> arrayList, int id){
        ArrayList<DatabaseItem> searchList = (ArrayList<DatabaseItem>) ((ArrayList<?>) arrayList);
        for(int i = 0; i < searchList.size(); i++){
            if(searchList.get(i).getId() == id){
                return i;
            }
        }
        return 0;
    }
    public EditTransactionPane(Transaction transaction) {
        this.setPadding(new Insets(10,10,10,10));
        this.setVgap(10);
        this.setHgap(10);

        Transaction transaction1 = transaction;
        //TODO these have to be private but wont work if they are

        CategoryTable categoryTable = CategoryTable().getInstance();
        TransactionTable transactionTable = TransactionTable().getInstance();
        // Amount
        Text amount = new Text("Amount:");
        ComboBox<Transaction> comboName = new ComboBox<>();
        ArrayList<Transaction> allTransactions = transactionTable.getAllTransactions();
        comboName.setItems(FXCollections.observableArrayList(allTransactions));
        //TODO different arraylist??
        comboName.getSelectionModel().select(find(allTransactions, transaction.getId()));
        this.add(amount, 0,0);
        this.add(comboName, 1,0);
        // Description
        Text descriptionLabel = new Text("Description");
        TextField description = new TextField();
        year.setText(String.valueOf(transaction.getDesc()));
        this.add(descriptionLabel,0,1);
        this.add(description,1,1);
        // Date
        //TODO change!!!!!!
        Text date = new Text("Date");
        ComboBox<Transaction> locationComboBox = new ComboBox<>();
        ArrayList<Transaction> allTransactionDates = TransactionTable.getInstance().getAllTransactions();
        locationComboBox.setItems(FXCollections.observableArrayList(allTransactionDates));
        //TODO needs int - not date vvvvvvv
        locationComboBox.getSelectionModel().select(find(allTransactionDates, transaction.getId()));
        this.add(date,0,3);
        this.add(locationComboBox,1,3);
        Button update = new Button("Update");
        // Categories
        Text categories = new Text("Categories:");
        ComboBox<Category> conditionComboBox = new ComboBox<>();
        ArrayList<Category> allConditions = categoryTable.getAllCategories();
        conditionComboBox.setItems(FXCollections.observableArrayList(allConditions));
        //TODO check to see if transaction.getCat_id() needs to be name instead
        conditionComboBox.getSelectionModel().select(find(allConditions, transaction.getCat_id()));
        this.add(categories,0,2);
        this.add(conditionComboBox,1,2);

        update.setOnAction(e->{
            transaction.setAmt(locationComboBox.getSelectionModel().getSelectedItem().getId());
            transaction.setDesc(conditionComboBox.getSelectionModel().getSelectedItem().getName());
            transaction.setDate(comboName.getSelectionModel().getSelectedItem().getDate());
            //TODO not description??
            transaction.setCat_id(Integer.parseInt(categories.getText()));
            transactionTable.updateTransaction(transaction);
            DisplayTransactionsTab.getInstance().refreshTable();
//            StatisticsTab.getInstance().generateChart();
        });
        this.add(update, 1,4);
    }
}
