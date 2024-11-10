package org.example.moneytrackerapp.tables;

import org.example.moneytrackerapp.dao.TransactionTypeDAO;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.pojo.TransactionType;

import java.sql.ResultSet;
import java.sql.Statement;

import static org.example.moneytrackerapp.database.DBConst.*;

public class TransactionTypeTable implements TransactionTypeDAO {
    Database db = Database.getInstance();
    TransactionType transactionType;
    @Override
    public TransactionType getTransactionType(int id) {
        String query = "SELECT * FROM " + TABLE_TRANSACTION_TYPES + " WHERE id = " + TYPE_COLUMN_ID;
        try {
            Statement statement = db.getConnection().createStatement();
            ResultSet data = statement.executeQuery(query);
            if (data.next()) {
                transactionType = new TransactionType(data.getInt(TYPE_COLUMN_ID), data.getString(TYPE_COLUMN_NAME));
                return transactionType;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
