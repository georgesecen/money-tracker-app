package org.example.moneytrackerapp.tabs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.moneytrackerapp.pojo.DisplayItem;
import org.example.moneytrackerapp.pojo.Transaction;
import org.example.moneytrackerapp.tables.TransactionTable;

/**
 * DisplayTransactionsTab class shows as a tab in the
 * main pane. It displays table information ordered by date
 *
 * @author Cameron McRae
 */
public class DisplayTransactionsTab extends Tab {
    public TableView tableView;
    private static DisplayTransactionsTab instance;
    /**
     * Class constructor responsible for displaying table
     * and EditTransactionPane
     */
    public DisplayTransactionsTab() {
        TransactionTable transaction = TransactionTable.getInstance();
        tableView = new TableView();
        // Table Column Amount
        TableColumn<DisplayItem, String> column1 =
                new TableColumn<>("Amount");
        column1.setCellValueFactory(
                e-> new SimpleStringProperty(e.getValue().getAmount()));
        // Table Column Description
        TableColumn<DisplayItem, String> column2 =
                new TableColumn<>("Description");
        column2.setCellValueFactory(
                e-> new SimpleStringProperty(e.getValue().getDescription()));
        // Table Column Date
        TableColumn<DisplayItem, String> column3 =
                new TableColumn<>("Date");
        column3.setCellValueFactory(
                e-> new SimpleStringProperty(e.getValue().getDate()));
        // Table Column Category
        TableColumn<DisplayItem, String> column4 =
                new TableColumn<>("Category");
        column4.setCellValueFactory(
                e-> new SimpleStringProperty(e.getValue().getCategory()));
        // Set Tab Title
        this.setText("Display Transactions");
        BorderPane root  = new BorderPane();
        // Set Tab Header
        Text title = new Text("Transactions");
        title.getStyleClass().add("title-text");
        // Add Columns and Formatted Items to Table
        tableView.getColumns().addAll(column1, column2, column3, column4);
        tableView.getItems().addAll(transaction.getFancyItems());
        root.setCenter(tableView);
        // VBox for Transaction Content
        VBox content = new VBox(title);
        content.setPadding(new Insets(0, 0, 20, 0));
        content.setAlignment(Pos.CENTER);
        root.setTop(content);
        this.setContent(root);
        // Create Remove Button
        Button removeItem = new Button("Remove Item");
        VBox buttonContainer = new VBox(removeItem);
        buttonContainer.setPadding(new Insets(20,0,0,0));
        removeItem.getStyleClass().addAll("light-themed-button", "button-dimensions");
        // Set Column Widths
        column1.setMinWidth(120);
        column2.setMinWidth(190);
        column3.setMinWidth(120);
        column4.setMinWidth(190);
        /**
         * Deletes transaction from DB and refreshes table
         */
        removeItem.setOnAction(e->{
            DisplayItem remove = (DisplayItem) tableView.getSelectionModel().getSelectedItem();
            transaction.deleteTransaction(remove.getId());
            refreshTable();
            tableView.getItems().clear();;
            tableView.getItems().addAll(transaction.getFancyItems());

            // Update charts after deleting item
            StatisticsTab.getInstance().generateCharts(30);
        });
        /**
         * Change listener
         */
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue != null){
                    Transaction selectedItem = transaction.getTransaction(((DisplayItem) newValue).getId());
                    EditTransactionPane pane = new EditTransactionPane(selectedItem);
                    root.setRight(pane);
                }
            }
        });
        root.setBottom(buttonContainer);
        this.setContent(root);
    }
    /**
     * refreshTable method clears table and re-populates with updated values
     */
    public void refreshTable(){
        TransactionTable table = TransactionTable.getInstance();
        tableView.getItems().clear();
        tableView.getItems().addAll(table.getFancyItems());
    }
    /**
     * Reaches out to database for new instance of DisplayTransactionsTab
     * @return instance;
     */
    public static DisplayTransactionsTab getInstance(){
        if(instance == null){
            instance = new DisplayTransactionsTab();
        }
        return instance;
    }
}
