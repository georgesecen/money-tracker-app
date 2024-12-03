package org.example.moneytrackerapp.dao;


import org.example.moneytrackerapp.pojo.Transaction;

import java.util.ArrayList;

/**
 * This interface represents a DAO for the Transactions table used
 * for data retrieval
 *
 * @author Wania Sharif
 */
public interface TransactionDAO {
    /**
     * Gets all records in the table
     *
     * @return ArrayList of all records as Transaction objects
     */
    public ArrayList<Transaction> getAllTransactions();

    /**
     * Gets a record by its id
     *
     * @param transID int unique id
     * @return matching record as Transaction object
     */
    public Transaction getTransaction(int transID);

    /**
     * Updates existing record
     *
     * @param transaction transaction record to be updated
     */
    public void updateTransaction(Transaction transaction);

    /**
     * Delete a record
     *
     * @param transID int unique id of record to be deleted
     */
    public void deleteTransaction(int transID);

    /**
     * Inserts a new record using the given Transaction object's
     * properties
     *
     * @param transaction Transaction object
     */
    public void createTransaction(Transaction transaction);

}
