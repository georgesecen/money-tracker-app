package org.example.moneytrackerapp.tables;

import org.example.moneytrackerapp.dao.TransactionDAO;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.pojo.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.example.moneytrackerapp.database.DBConst.TABLE_TRANSACTIONS;

/**
 * TransactionTable class represents a single table of Transactions.
 * @author Wania Sharif
 */
public class TransactionTable {
    Database db = Database.getInstance();
    ArrayList<Transaction> transactions;

    
}
