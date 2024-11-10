package org.example.moneytrackerapp.tables;

import org.example.moneytrackerapp.dao.TransactionDAO;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.pojo.Transaction;

import java.sql.ResultSet;
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

        transactions = new ArrayList<>();
        try {
            Statement getTransactions = db.getConnection().createStatement();
            ResultSet resultSet = getTransactions.executeQuery(query);

            while (resultSet.next()) {
                resultSet.getInt(TRANS_COLUMN_ID);
                resultSet.getDouble(TRANS_COLUMN_AMOUNT);
                resultSet.getString(TRANS_COLUMN_DESC);
                resultSet.getString(TRANS_COLUMN_DATE);
                resultSet.getInt(TRANS_COLUMN_CAT);
            }
            
        } catch(Exception e){
            e.printStackTrace();
        }
        return transactions;
    }
}
