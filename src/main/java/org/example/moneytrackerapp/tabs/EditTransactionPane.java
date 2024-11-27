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
import java.time.LocalDate;
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

        CategoryTable categoryTable = CategoryTable.getInstance();
        TransactionTable transactionTable = TransactionTable.getInstance();
        // Amount
        Text amountLabel = new Text("Amount:");
        TextField amount = new TextField();
        amount.setText(String.valueOf(transaction.getAmt()));
        this.add(amountLabel, 0,0);
        this.add(amount, 1,0);
        // Description
        Text descriptionLabel = new Text("Description");
        TextField description = new TextField();
        description.setText(String.valueOf(transaction.getDesc()));
        this.add(descriptionLabel,0,1);
        this.add(description,1,1);
        // Date
        Text date = new Text("Date");
        DatePicker datePicker = new DatePicker();
        //TODO show date properly
        datePicker.setValue(LocalDate.now());
        this.add(date,0,3);
        this.add(datePicker,1,3);
        // Categories
        Text categories = new Text("Categories:");
        ComboBox<Category> conditionComboBox = new ComboBox<>();
        ArrayList<Category> allConditions = categoryTable.getAllCategories();
        conditionComboBox.setItems(FXCollections.observableArrayList(allConditions));
        conditionComboBox.getSelectionModel().select(find(allConditions, transaction.getCat_id()));
        this.add(categories,0,2);
        this.add(conditionComboBox,1,2);
        Button update = new Button("Update");

        update.setOnAction(e->{
            transaction.setAmt(Double.parseDouble(amount.getText()));
            transaction.setDesc(description.getText());
//            transaction.setDate(comboName.getSelectionModel().getSelectedItem().getDate());
//            transaction.setCat_id(Integer.parseInt(categories.getText()));
//            transactionTable.updateTransaction(transaction);
            DisplayTransactionsTab.getInstance().refreshTable();
//            StatisticsTab.getInstance().generateChart();
        });
        this.add(update, 1,4);
    }
}
