package org.example.moneytrackerapp.tabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.PopupWindow;
import javafx.util.Duration;
import org.example.moneytrackerapp.pojo.Category;
import org.example.moneytrackerapp.pojo.Transaction;
import org.example.moneytrackerapp.tables.CategoryTable;
import org.example.moneytrackerapp.tables.TransactionTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class StatisticsTab extends Tab {

    public StatisticsTab(){
        this.setText("Statistics");
        BorderPane root = new BorderPane();

        PieChart chart = new PieChart();

        // Get all the transactions from the transactions table
        TransactionTable transactionTable = TransactionTable.getInstance();
        ArrayList<Transaction> transactions = transactionTable.getAllTransactions();

        chart.setTitle("Working!");
        generatePieChart(chart, transactions);



        root.setCenter(chart);
        this.setContent(root);

    }


    /**
     * Generates a pie chart from transaction data.
     * @param chart PieChart you want to generate data for.
     * @param transactions All income or expense transactions you want to generate pie chart for.
     */
    public void generatePieChart(PieChart chart, ArrayList<Transaction> transactions){

        // Get the total incomes/expenses for each category {categoryId:total}
        HashMap<Integer, Double> totalIncomes = new HashMap<>();
        for (Transaction transaction : transactions){
            int catId = transaction.getCat_id();

            // Get absolute value of transaction amount in case its negative
            double amount = Math.abs(transaction.getAmt());

            // If the category does not exist in map add it
            if (!totalIncomes.containsKey(catId)){
                totalIncomes.put(catId, 0.0);
            }

            // Add transaction amount to its corresponding category
            totalIncomes.put(catId, totalIncomes.get(catId) + amount);

        }

        // Add the total incomes/expenses for each category to the pie chart
        ArrayList<PieChart.Data> data = new ArrayList<>();
        CategoryTable categoryTable = CategoryTable.getInstance();
        for (int catId : totalIncomes.keySet()) {
            Category category = categoryTable.getCategory(catId);

            // Add the actual category name to the pie chart with its total amount
            data.add(new PieChart.Data(category.getName(), totalIncomes.get(catId)));
        }

        // Add the data to the pie chart
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(data);
        chart.setData(chartData);

        // Add tooltips to every slice in the pie chart
        for (PieChart.Data slice : chartData){
            Tooltip tooltip = new Tooltip();
            // Tooltip displays the pie value which is total amount in that category
            tooltip.setText(String.format("$%.2f", slice.getPieValue()));
            tooltip.setShowDelay(Duration.millis(150));
            Tooltip.install(slice.getNode(), tooltip);
        }
    }

}
