package org.example.moneytrackerapp.tabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.moneytrackerapp.pojo.Category;
import org.example.moneytrackerapp.pojo.Transaction;
import org.example.moneytrackerapp.tables.CategoryTable;
import org.example.moneytrackerapp.tables.TransactionTable;

import java.time.LocalDate;
import java.util.*;

/**
 * Contains functionality to display transaction data and history to the user.
 */
public class StatisticsTab extends Tab {

    private static StatisticsTab instance;

    private PieChart incomesPieChart;
    private PieChart expensesPieChart;
    private LineChart<String, Number> lineChart;

    // TODO: Change text to grading system
    private Text previousTimeframeText;

    /**
     * Displays income/expense pie charts and line chart.
     */
    public StatisticsTab(){
        this.setText("Statistics");
        BorderPane root = new BorderPane();

        // TODO: Change from text to grading system
        previousTimeframeText = new Text();

        // Create the income/expense pie charts
        incomesPieChart = new PieChart();
        incomesPieChart.setTitle("Incomes by Category");
        expensesPieChart = new PieChart();
        expensesPieChart.setTitle("Expenses by Category");

        // Create the line chart
        CategoryAxis xAxis = new CategoryAxis(); // CategoryAxis is used to show date labels on x-axis
        NumberAxis yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis, yAxis);

        // Add buttons which control what timeframe data is shown for
        HBox timeframesContainer = new HBox();
        Button month = new Button("30 Days");
        Button quarter = new Button("90 Days");
        Button year = new Button("365 Days");
        Button allTime = new Button("All Time");
        timeframesContainer.getChildren().addAll(month, quarter, year, allTime);

        // Generate charts for different timeframes according to what buttons were clicked
        month.setOnAction(e-> generateCharts(30));
        quarter.setOnAction(e-> generateCharts(90));
        year.setOnAction(e->generateCharts(365));
        allTime.setOnAction(e->generateCharts(9999));

        // Default timeframe shown to user is 30 days
        generateCharts(30);

        // Add all charts and data to border pane
        root.setTop(timeframesContainer);
        root.setLeft(incomesPieChart);
        root.setRight(expensesPieChart);
        root.setCenter(lineChart);
        root.setBottom(previousTimeframeText);
        this.setContent(root);

    }

    /**
     * Gets the singleton instance of the StatisticsTab class.
     * @return StatisticsTab class instance.
     */
    public static StatisticsTab getInstance(){
        if(instance == null){
            instance = new StatisticsTab();
        }
        return instance;
    }

    /**
     * Generates the income/expense pie charts and line chart.
     * @param days Number of previous days you want to generate chart data for.
     */
    public void generateCharts(int days){

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
        incomeSeries.setName("Incomes");
        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("Expenses");
        double rollingIncomes = 0;
        double rollingExpenses = 0;

        // Get days before current date
        LocalDate date = LocalDate.now().minusDays(days);

        // Get the starting index of where date should be in the transactions array
        int start = getIndexByTransactionDate(transactions, date);

        // If next index after start is not equal to the exact date which our timeframe begins then add the date
        // at the exact start of the timeframe so our line chart period is correct
        // (if first transaction starts 5 days ago in our 30 days timeframe it will look wrong)
        if (start + 1 < transactions.size() && !transactions.get(start + 1).getDate().toLocalDate().equals(date)){
            incomeSeries.getData().add(new XYChart.Data<>(date.toString(), 0));
            expenseSeries.getData().add(new XYChart.Data<>(date.toString(), 0));
        }

        for (int i = start + 1; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);

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

            // If we have calculated total income/expense sum for date (could be multiple transactions on same date)
            // add new rolling income/expense to line chart data series for that date
            if (i + 1 >= transactions.size() || !transaction.getDate().equals(transactions.get(i + 1).getDate())){
                incomeSeries.getData().add(new XYChart.Data<>(transaction.getDate().toString(), rollingIncomes));
                expenseSeries.getData().add(new XYChart.Data<>(transaction.getDate().toString(), rollingExpenses));
            }
        }

        // Get the starting index of where the last timeframe before this timeframe started
        int previousTimeframeStart = getIndexByTransactionDate(transactions, date.minusDays(days * 2));

        // Get data for previous timeframe up until the start of the current already calculated timeframe
        double previousIncomes = 0;
        double previousExpenses = 0;
        for (int i = previousTimeframeStart + 1; i < start; i++) {
            // Get transaction at index and absolute value of amount in case its negative
            Transaction transaction = transactions.get(i);
            double amount = Math.abs(transaction.getAmt());

            // If transaction has a positive amount it's an income, otherwise it's an expense
            if (transaction.getAmt() > 0){
                previousIncomes += amount;
            }
            else{
                previousExpenses += amount;
            }
        }

        // TODO: Compare previous timeframes data with current timeframe and give the user a grade on performance compared to last timeframe
        // Add text to display previous timeframes data
        previousTimeframeText.setText(String.format("You spent a total of %.2f expenses and %.2f incomes " +
                "the last %d days before %s", previousExpenses, previousIncomes, days, date));

        // Add series to line chart
        lineChart.getData().setAll(incomeSeries, expenseSeries);

        // Add the data from income/expense category totals to pie charts
        generatePieChart(incomesPieChart, incomeCategoryTotals);
        generatePieChart(expensesPieChart, expenseCategoryTotals);
    }

    /**
     * Gets index in transactions of where target date should be.
     * @param transactions Transactions array to search. (must be sorted by date)
     * @param target LocalDate which you want to find index of in transactions.
     * @return int index in transactions where target date should be.
     */
    private int getIndexByTransactionDate(ArrayList<Transaction> transactions, LocalDate target){

        int left = 0;
        int right = transactions.size() - 1;

        while (left <= right){
            // Calculate mid point
            int mid = (right + left) / 2;

            // Get the date of transaction at mid
            LocalDate date = transactions.get(mid).getDate().toLocalDate();

            // If date is after or the same as our target shift right pointer, otherwise shift left pointer
            // to reduce search range
            if (date.isAfter(target) || date.isEqual(target)){
                right = mid - 1;
            }
            else{
                left = mid + 1;
            }
        }

        return right;
    }

    /**
     * Generates a pie chart from transaction category data.
     * @param chart PieChart you want to generate data for.
     * @param categoryTotals HashMap of {categoryId:total} which you want to add to the pie chart.
     */
    private void generatePieChart(PieChart chart, HashMap<Integer, Double> categoryTotals){

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
    private int compareTransactionDates(Transaction transaction1, Transaction transaction2){

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
