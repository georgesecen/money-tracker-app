package org.example.moneytrackerapp.tables;

import org.example.moneytrackerapp.dao.TransactionDAO;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.pojo.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.example.moneytrackerapp.database.DBConst.*;

public class TransactionTable implements TransactionDAO {
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
// TODO add logic for method
    @Override
    public Transaction getTransaction(int transID) {
        return null;
    }
// TODO add logic for method
    @Override
    public void updateTransaction(Transaction transaction) {

    }
// TODO add logic for method
    @Override
    public void deleteTransaction(int transID) {

    }
// TODO add logic for method
    @Override
    public void createTransaction(Transaction transaction) {

    }
}
