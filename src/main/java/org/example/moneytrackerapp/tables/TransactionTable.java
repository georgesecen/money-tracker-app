package org.example.moneytrackerapp.tables;

import org.example.moneytrackerapp.dao.TransactionDAO;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.pojo.Transaction;

import java.util.ArrayList;

public class TransactionTable implements TransactionDAO {
    Database db = Database.getInstance();
    @Override
    public ArrayList<Transaction> getAllTransactions() {
        return null;
    }

    @Override
    public Transaction getTransaction(int transID) {
        return null;
    }

    @Override
    public void updateTransaction(Transaction transaction) {

    }

    @Override
    public void deleteTransaction(int transID) {

    }

    @Override
    public void createTransaction(Transaction transaction) {

    }
}
