package org.example.moneytrackerapp.database;
import java.sql.*;

import static org.example.moneytrackerapp.database.Const.*;
import static org.example.moneytrackerapp.database.DBConst.*;

public class Database {
    /*
     * This class is using a singleton pattern.
     */
    private static Database instance;
    private Connection connection;
    private Database() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost/" + DB_NAME + "?serverTimeZone=UTC",
                DB_USER, DB_PASS);
                System.out.println("Connection Successfully Created");
                createTable(TABLE_TRANSACTION_TYPES, CREATE_TABLE_TRANSACTION_TYPES, connection);
                createTable(TABLE_CATEGORIES, CREATE_TABLE_CATEGORIES, connection);
                createTable(TABLE_TRANSACTIONS, CREATE_TABLE_TRANSACTIONS, connection);
            } catch(Exception e) {
                e.printStackTrace();
            }
    }
    /**
     * @return connection
     */
    public Connection getConnection() {
        return connection;
    }
    /**
     * @return DB Instance
     */
    public static Database getInstance(){
        if(instance == null){
            //TODO Replace constructor values with values read from file
            instance = new Database();
        }
        return instance;
    }
    /**
     * @param tableName
     * @param tableQuery
     * @param connection
     * @throws SQLException
     */
    public void createTable(String tableName, String tableQuery, Connection connection) throws SQLException {
        Statement createTable;
        DatabaseMetaData md = connection.getMetaData();
        ResultSet resultSet = md.getTables("cmcraemd", null, tableName, null);
        if(resultSet.next()){
            System.out.println(tableName + " table already exists");
        }
        else {
            createTable = connection.createStatement();
            createTable.execute(tableQuery);
            System.out.println("The " + tableName + " table has been created");
        }
    }
}
