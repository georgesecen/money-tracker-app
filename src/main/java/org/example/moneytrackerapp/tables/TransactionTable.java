package org.example.moneytrackerapp.tables;

import org.example.moneytrackerapp.dao.TransactionDAO;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.pojo.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


import static org.example.moneytrackerapp.database.DBConst.*;

public class TransactionTable implements TransactionDAO {
    private static TransactionTable instance;
    private TransactionTable(){
        db = Database.getInstance();
    }
    Database db = Database.getInstance();
    ArrayList<Transaction> transactions;

    @Override
    public ArrayList<Transaction> getAllTransactions() {
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS;
        transactions = new ArrayList<>();
        try {
            Statement statement = db.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                if(resultSet.next()) {
                    transactions.add(new Transaction(
                        resultSet.getInt(TRANS_COLUMN_ID),
                        resultSet.getDouble(TRANS_COLUMN_AMOUNT),
                        resultSet.getString(TRANS_COLUMN_DESC),
                        resultSet.getString(TRANS_COLUMN_DATE),
                        resultSet.getInt(TRANS_COLUMN_CAT)
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }
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
                    data.getString(TRANS_COLUMN_DATE),
                    data.getInt(TRANS_COLUMN_CAT)
                );
                return transaction;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void updateTransaction(Transaction transaction) {
        String query = "UPDATE " + TABLE_TRANSACTIONS + " SET " + TRANS_COLUMN_ID + " = " + transaction.getId()
                + ", " + TRANS_COLUMN_AMOUNT + " = " + transaction.getAmt()
                + ", " + TRANS_COLUMN_DESC + " = " + transaction.getDesc()
                + ", " + TRANS_COLUMN_DATE + " = " + transaction.getDate()
                + ", " + TRANS_COLUMN_CAT + " = " + transaction.getCat_id()
                + " WHERE " + TRANS_COLUMN_ID + " = " + transaction.getId();
        try {
            Statement statement = db.getConnection().createStatement();
            statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteTransaction(int transID) {
        String query = "DELETE FROM " + TABLE_TRANSACTIONS + " WHERE " + TRANS_COLUMN_ID + " = " + transID;
        try {
            Statement statement = db.getConnection().createStatement();
            statement.executeQuery(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
// TODO are brackets necessary around VALUES???
    @Override
    public void createTransaction(Transaction transaction) {
        String query = "INSERT INTO " + TABLE_TRANSACTIONS + " VALUES ("
                + transaction.getId() + ", " + transaction.getAmt()
                + ", " + transaction.getDesc() + ", " + transaction.getDate()
                + ", " + transaction.getCat_id() + ")";
    }

    public static TransactionTable getInstance(){
        if(instance == null){
            instance = new TransactionTable();
        }
        return instance;
    }
}
