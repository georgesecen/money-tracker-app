package org.example.moneytrackerapp.database;

import java.sql.*;

import static org.example.moneytrackerapp.database.DBConst.*;

public class Database {
    /*
     * This class is using a singleton pattern.
     */
    private static Database instance;
    private Connection connection;

    // Database credentials
    private static String dbName;
    private static String dbUser;
    private static String dbPass;

    private Database() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost/" + dbName + "?serverTimeZone=UTC",
                dbUser, dbPass);
                System.out.println("Connection Successfully Created");
                createTable(TABLE_TRANSACTION_TYPES, CREATE_TABLE_TRANSACTION_TYPES, connection);
                createTable(TABLE_CATEGORIES, CREATE_TABLE_CATEGORIES, connection);
                createTable(TABLE_TRANSACTIONS, CREATE_TABLE_TRANSACTIONS, connection);
                insertDefaultRecords(INSERT_DEFAULT_TRANS_TYPES, connection);
                insertDefaultRecords(INSERT_DEFAULT_CATEGORIES, connection);
            } catch(Exception e) {
                System.out.println("Error connecting or creating database in Database.java");
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
        ResultSet resultSet = md.getTables(dbName, null, tableName, null);
        if(resultSet.next()){
            System.out.println(tableName + " table already exists");
        }
        else {
            createTable = connection.createStatement();
            createTable.execute(tableQuery);
            System.out.println("The " + tableName + " table has been created");
        }
    }

    /**
     * Method to run insert queries
     * @param query insert query to be executed
     * @param connection database connection
     */
    public void insertDefaultRecords(String query, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
        }  catch (SQLIntegrityConstraintViolationException ex){
            System.out.println("Entries already exist");

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the dbName, dbUser, and dbPass for Database class.
     * @param name Database name.
     * @param username Database username.
     * @param password Database password.
     */
    public static void setDbCredentials(String name, String username, String password){
        dbName = name;
        dbUser = username;
        dbPass = password;
    }

    /**
     * Sets instance to null. Used in case instance does not have a valid
     * connection to a database.
     */
    public static void resetInstance(){
        instance = null;
    }
}
