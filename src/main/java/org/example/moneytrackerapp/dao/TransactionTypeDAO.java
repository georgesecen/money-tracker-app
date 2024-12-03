package org.example.moneytrackerapp.dao;


import org.example.moneytrackerapp.pojo.TransactionType;

/**
 * Interface represents the DAO pattern for a TransactionType
 * for data retrieval
 */
public interface TransactionTypeDAO {
    /**
     * Returns the Transaction Type object with given id
     * @param id int unique identifier
     * @return TransactionType
     */
    public TransactionType getTransactionType(int id);
}
