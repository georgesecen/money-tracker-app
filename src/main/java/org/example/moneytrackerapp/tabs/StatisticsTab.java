package org.example.moneytrackerapp.tabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.stage.PopupWindow;
import javafx.util.Duration;
import org.example.moneytrackerapp.pojo.Category;
import org.example.moneytrackerapp.pojo.Transaction;
import org.example.moneytrackerapp.tables.CategoryTable;
import org.example.moneytrackerapp.tables.TransactionTable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

public class StatisticsTab extends Tab {

    private PieChart incomesPieChart = new PieChart();
    private PieChart expensesPieChart = new PieChart();

    public StatisticsTab(){
        this.setText("Statistics");
        BorderPane root = new BorderPane();

        // Get all the transactions from the transactions table
        TransactionTable transactionTable = TransactionTable.getInstance();
        ArrayList<Transaction> transactions = transactionTable.getAllTransactions();

        // Sort all transactions by date in ascending order
        transactions.sort(Collections.reverseOrder(this::compareTransactionDates));

        // Get the total incomes/expenses for each category {categoryId:total} (for pie charts)
        HashMap<Integer, Double> incomeCategoryTotals = new HashMap<>();
        HashMap<Integer, Double> expenseCategoryTotals = new HashMap<>();

        // Keep track of data series for rolling income/expense totals (for line chart)
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        double rollingIncomes = 0;
        double rollingExpenses = 0;

        for (Transaction transaction : transactions){

            // Get transaction category id and absolute value of transaction amount in case its negative
            int catId = transaction.getCat_id();
            double amount = Math.abs(transaction.getAmt());

            // If transaction has a positive amount it's an income, otherwise it's an expense
            if (transaction.getAmt() > 0){
                rollingIncomes += amount;

                // If category does not exist in totalIncomes add it
                if (!incomeCategoryTotals.containsKey(catId)){
                    incomeCategoryTotals.put(catId, 0.0);
                }

                // Add transaction amount to its corresponding category
                incomeCategoryTotals.put(catId, incomeCategoryTotals.get(catId) + amount);
            }
            else{
                rollingExpenses += amount;

                // If category does not exist in totalExpenses add it
                if (!expenseCategoryTotals.containsKey(catId)){
                    expenseCategoryTotals.put(catId, 0.0);
                }

                // Add transaction amount to its corresponding category
                expenseCategoryTotals.put(catId, expenseCategoryTotals.get(catId) + amount);
            }

            // Add new rolling income/expense to line chart data series
            incomeSeries.getData().add(new XYChart.Data<>(transaction.getDate().toString(), rollingIncomes));
            expenseSeries.getData().add(new XYChart.Data<>(transaction.getDate().toString(), rollingExpenses));
        }


        // Generate expenses and incomes pie charts
        incomesPieChart.setTitle("Incomes By Category");
        generatePieChart(incomesPieChart, incomeCategoryTotals);
        expensesPieChart.setTitle("Expenses By Category");
        generatePieChart(expensesPieChart, expenseCategoryTotals);

        root.setLeft(incomesPieChart);
        root.setRight(expensesPieChart);
        this.setContent(root);

    }


    /**
     * Generates a pie chart from transaction category data.
     * @param chart PieChart you want to generate data for.
     * @param categoryTotals HashMap of {categoryId:total} which you want to add to the pie chart.
     */
    public void generatePieChart(PieChart chart, HashMap<Integer, Double> categoryTotals){

        // Add the total incomes/expenses for each category to the pie chart
        ArrayList<PieChart.Data> data = new ArrayList<>();
        CategoryTable categoryTable = CategoryTable.getInstance();
        for (int catId : categoryTotals.keySet()) {
            Category category = categoryTable.getCategory(catId);

            // Add the actual category name to the pie chart with its total amount
            data.add(new PieChart.Data(category.getName(), categoryTotals.get(catId)));
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


    /**
     * Compares transactions by date to check which transaction occurred first.
     * @param transaction1 First transaction to compare.
     * @param transaction2 Second transaction to compare.
     * @return 1, -1, or 0 if the date of the first transaction is before, after, or
     * equal to the date of the second transaction.
     */
    public int compareTransactionDates(Transaction transaction1, Transaction transaction2){

        // If transaction 1 is before transaction 2
        if (transaction1.getDate().before(transaction2.getDate())){
            return 1;
        }
        // If transaction 1 is after transaction 2
        else if (transaction1.getDate().after(transaction2.getDate())) {
            return -1;
        }
        // If both transactions occurred on the same date
        else{
            return 0;
        }
    }

}
