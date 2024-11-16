package org.example.moneytrackerapp.tabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import org.example.moneytrackerapp.pojo.Transaction;
import org.example.moneytrackerapp.tables.TransactionTable;

import java.util.ArrayList;
import java.util.HashMap;

public class StatisticsTab extends Tab {

    public StatisticsTab(){
        this.setText("Statistics");
        BorderPane root = new BorderPane();

        PieChart chart = new PieChart();

        // Get all the transactions from the transactions table
        TransactionTable transactionTable = TransactionTable.getInstance();
        ArrayList<Transaction> transactions = transactionTable.getAllTransactions();

        // Get the total incomes for each category {categoryId:total}
        HashMap<Integer, Double> totalIncomes = new HashMap<>();
        for (Transaction transaction : transactions){
            int catId = transaction.getCat_id();
            double amount = transaction.getAmt();

            // If transaction is an income
            if (amount > 0){
                // If the category does not exist in map add it
                if (!totalIncomes.containsKey(catId)){
                    totalIncomes.put(catId, 0.0);
                }
                // Add transaction amount to its corresponding category
                totalIncomes.put(catId, totalIncomes.get(catId) + amount);
            }
        }

    }

}
