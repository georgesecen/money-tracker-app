package org.example.moneytrackerapp.tables;
import org.example.moneytrackerapp.dao.TransactionDAO;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.pojo.DisplayItem;
import org.example.moneytrackerapp.pojo.Transaction;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static org.example.moneytrackerapp.database.DBConst.*;

/**
 * TransactionTable class represents a table that holds
 * methods responsible for CRUD operations
 *
 * @author Cameron McRae
 */
public class TransactionTable implements TransactionDAO {
    private static TransactionTable instance;

    /**
     * Private constructor that grabs the table instance
     * from the database
     */
    private TransactionTable(){
        db = Database.getInstance();
    }
    Database db = Database.getInstance();
    ArrayList<Transaction> transactions;
    /**
     * Method to get all transactions from database
     * @return transactions
     */
    @Override
    public ArrayList<Transaction> getAllTransactions() {
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS;
        transactions = new ArrayList<>();
        try {
            Statement statement = db.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                    transactions.add(new Transaction(
                        resultSet.getInt(TRANS_COLUMN_ID),
                        resultSet.getDouble(TRANS_COLUMN_AMOUNT),
                        resultSet.getString(TRANS_COLUMN_DESC),
                        resultSet.getDate(TRANS_COLUMN_DATE),
                        resultSet.getInt(TRANS_COLUMN_CAT)
                    ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }
    /**
     * Method to get a single transaction from database
     * @param transID;
     * @return transaction
     */
    @Override
    public Transaction getTransaction(int transID) {
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE " + TRANS_COLUMN_ID + " = " + transID;
        try {
            Statement statement = db.getConnection().createStatement();
            ResultSet data = statement.executeQuery(query);
            if(data.next()) {
                Transaction transaction = new Transaction(
                    data.getInt(TRANS_COLUMN_ID),
                    data.getDouble(TRANS_COLUMN_AMOUNT),
                    data.getString(TRANS_COLUMN_DESC),
                    data.getDate(TRANS_COLUMN_DATE),
                    data.getInt(TRANS_COLUMN_CAT)
                );
                return transaction;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Method to update a transaction from database
     * @param transaction;
     */
    @Override
    public void updateTransaction(Transaction transaction) {
        String query = "UPDATE " + TABLE_TRANSACTIONS + " SET "
                + TRANS_COLUMN_AMOUNT + " = " + transaction.getAmt()
                + ", " + TRANS_COLUMN_DESC + " = '" + transaction.getDesc()
                + "', " + TRANS_COLUMN_DATE + " = '" + transaction.getDate()
                + "', " + TRANS_COLUMN_CAT + " = " + transaction.getCat_id()
                + " WHERE " + TRANS_COLUMN_ID + " = " + transaction.getId();
        try {
            Statement statement = db.getConnection().createStatement();
            statement.executeUpdate(query);
            System.out.println("Record Updated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Method to delete a transaction from database
     * @param transID;
     */
    @Override
    public void deleteTransaction(int transID) {
        String query = "DELETE FROM " + TABLE_TRANSACTIONS + " WHERE " + TRANS_COLUMN_ID + " = " + transID;
        try {
            db.getConnection().createStatement().execute(query);
            System.out.println("item deleted");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Remove all transactions in table with the chosen Category id
     * @param cat_id Category id
     */
    public void deleteTransactionByCategory(int cat_id) {
        String query = "DELETE FROM " + TABLE_TRANSACTIONS + " WHERE " + TRANS_COLUMN_CAT + " = " + cat_id;
        try {
            Statement statement = db.getConnection().createStatement();
            statement.execute(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Method to insert a transaction into database
     * @param transaction;
     */
    @Override
    public void createTransaction(Transaction transaction) {
        String query = "INSERT INTO " + TABLE_TRANSACTIONS +
                "(" + TRANS_COLUMN_ID + ", "
                + TRANS_COLUMN_AMOUNT + ", "
                + TRANS_COLUMN_DESC + ", "
                + TRANS_COLUMN_DATE + ", "
                + TRANS_COLUMN_CAT + ") VALUES ("
                + transaction.getId() + ", "
                + transaction.getAmt() + ", '"
                + transaction.getDesc() + "', '"
                + transaction.getDate() + "', " + transaction.getCat_id() + ")";
        System.out.println(query);
        try {
            db.getConnection().createStatement().execute(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Method to display all formatted transactions from database
     * ordered by date
     * @return items
     */
    public ArrayList<DisplayItem> getFancyItems(){
        ArrayList<DisplayItem> items = new ArrayList<>();
        String query = "SELECT t.id, " +
                " t.amount, " +
                " t.description, " +
                " t.date, " +
                " c.cat_name " +
                " FROM Transactions as t " +
                "JOIN Categories as c on t.cat_id = c.id " +
                "ORDER BY t.date ASC";
        try {
            Statement getItems = db.getConnection().createStatement();
            ResultSet data = getItems.executeQuery(query);
            while(data.next()) {
                items.add(new DisplayItem(
                        data.getInt("id"),
                        data.getString("amount"),
                        data.getString("description"),
                        data.getString("date"),
                        data.getString("cat_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    /**
     * Method to create a transaction instance from database
     */
    public static TransactionTable getInstance(){
        if(instance == null){
            instance = new TransactionTable();
        }
        return instance;
    }
}
