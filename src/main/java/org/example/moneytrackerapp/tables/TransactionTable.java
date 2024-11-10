package org.example.moneytrackerapp.tables;

import org.example.moneytrackerapp.dao.TransactionDAO;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.pojo.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.example.moneytrackerapp.database.DBConst.*;

/**
 * TransactionTable class represents a single table of Transactions.
 * @author Wania Sharif
 */
public class TransactionTable {
    Database db = Database.getInstance();
    ArrayList<Transaction> transactions;

    /**
     * This method creates and returns an ArrayList of the records in Transactions Table
     * @return ArrayList consisting of the transactions in Transactions table
     */
    public ArrayList<Transaction> getAllTransactions() {
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS;

        transactions = new ArrayList<Transaction>();
        try {
            Statement getTransactions = db.getConnection().createStatement();
            ResultSet resultSet = getTransactions.executeQuery(query);

            while (resultSet.next()) {
//                resultSet.getInt(TRANS_COLUMN_ID);
//                resultSet.getDouble(TRANS_COLUMN_AMOUNT);
//                resultSet.getString(TRANS_COLUMN_DESC);
//                resultSet.getString(TRANS_COLUMN_DATE);
//                resultSet.getInt(TRANS_COLUMN_CAT);
                resultSet.getInt(TRANS_COLUMN_ID);
                resultSet.getInt(TRANS_COLUMN_AMOUNT);
                resultSet.getInt(TRANS_COLUMN_DESC);
                resultSet.getInt(TRANS_COLUMN_DATE);
                resultSet.getInt(TRANS_COLUMN_CAT);
            }
            
        } catch(SQLException e){
            e.printStackTrace();
        }
        return transactions;
    }

    /**
     * Method for retrieving a record of transaction by its id.
     * Creates a SQL query using id to filter the result to one record.
     * If the record is not found, it will throw an SQL Exception.
     *
     * @param id int id to identify the record
     * @return Transaction
     */
    public Transaction getTransaction(int id) {
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE " +
                TRANS_COLUMN_ID + " = " + id;
        Transaction transaction = new Transaction();
        try {
            Statement getTransaction = db.getConnection().createStatement();
            ResultSet data = getTransaction.executeQuery(query);
            data.next();
            transactions.add(new Transaction(
                            data.getInt(TRANS_COLUMN_ID),
                            data.getDouble(TRANS_COLUMN_AMOUNT),
                            data.getString(TRANS_COLUMN_DESC),
                            data.getString(TRANS_COLUMN_DATE),
                            data.getInt(TRANS_COLUMN_CAT)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaction;
    }
}
