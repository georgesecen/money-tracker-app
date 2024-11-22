package org.example.moneytrackerapp.tabs;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.moneytrackerapp.pojo.DisplayItem;
import org.example.moneytrackerapp.tables.TransactionTable;

public class DisplayTransactionsTab extends Tab {
    public TableView tableView;
    private static DisplayTransactionsTab instance;
    public DisplayTransactionsTab() {
        TransactionTable transaction = TransactionTable.getInstance();
        tableView = new TableView();

        //Amount
        TableColumn<DisplayItem, String> column1 =
                new TableColumn<>("Amount");
        column1.setCellValueFactory(
                e-> new SimpleStringProperty(e.getValue().getAmount()));
        // Description
        TableColumn<DisplayItem, String> column2 =
                new TableColumn<>("Description");
        column2.setCellValueFactory(
                e-> new SimpleStringProperty(e.getValue().getDescription()));
        // Date
        TableColumn<DisplayItem, String> column3 =
                new TableColumn<>("Date");
        column3.setCellValueFactory(
                e-> new SimpleStringProperty(e.getValue().getDate()));
        // Category
        TableColumn<DisplayItem, String> column4 =
                new TableColumn<>("Category");

        column4.setCellValueFactory(
                e-> new SimpleStringProperty(e.getValue().getCategory()));

        // Set Tab Title
        this.setText("Display Transactions");
        BorderPane root  = new BorderPane();
        // Set Text Title
        Text title = new Text("Transactions");

        tableView.getColumns().addAll(column1, column2, column3, column4);
        tableView.getItems().addAll(transaction.getFancyItems());
        root.setCenter(tableView);
        // VBox for transaction content
        VBox content = new VBox(title);
        content.setAlignment(Pos.CENTER);
        root.setTop(content);
        this.setContent(root);

        Button removeItem = new Button("Remove Item");
        removeItem.setOnAction(e->{
            DisplayItem remove = (DisplayItem) tableView.getSelectionModel().getSelectedItem();
            transaction.deleteTransaction(remove.getId());
            refreshTable();
            tableView.getItems().clear();;
            tableView.getItems().addAll(transaction.getFancyItems());
            //TODO implement after charts are merged
//            StatisticsTab.getInstance().generateChart();
        });
        root.setBottom(removeItem);

    }
    public void refreshTable(){
        TransactionTable table = TransactionTable.getInstance();
        tableView.getItems().clear();
        tableView.getItems().addAll(table.getFancyItems());
    }
    public static DisplayTransactionsTab getInstance(){
        if(instance == null){
            instance = new DisplayTransactionsTab();
        }
        return instance;
    }
}
