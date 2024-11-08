package org.example.moneytrackerapp.dao;


import org.example.moneytrackerapp.pojo.Transaction;

import java.util.ArrayList;

public interface TransactionDAO {
    public ArrayList<Transaction> getAllTransactions();
    public Transaction getTransaction(int transID);
    public void updateTransaction(Transaction transaction);
    public void deleteTransaction(int transID);
    public void createTransaction(Transaction transaction);

}
