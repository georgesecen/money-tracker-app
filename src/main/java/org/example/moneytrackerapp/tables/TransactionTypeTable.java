package org.example.moneytrackerapp.tables;

import org.example.moneytrackerapp.dao.TransactionTypeDAO;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.pojo.TransactionType;

public class TransactionTypeTable implements TransactionTypeDAO {
    Database db = Database.getInstance();
    @Override
    public TransactionType getTransactionType(int id) {
        return null;
    }
}
