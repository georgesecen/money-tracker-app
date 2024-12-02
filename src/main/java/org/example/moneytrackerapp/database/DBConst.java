package org.example.moneytrackerapp.database;

/**
 * This class represents the database structure. It contains
 * all database table and column names, creation queries for
 * each table, and insert queries for default records.
 *
 * @author Wania Sharif
 */
public class DBConst {
    /**
     * TRANSACTIONS TABLE
     */
        public static final String TABLE_TRANSACTIONS = "Transactions";
        public static final String TRANS_COLUMN_ID = "id";
        public static final String TRANS_COLUMN_AMOUNT = "amount";
        public static final String TRANS_COLUMN_DESC = "description";
        public static final String TRANS_COLUMN_DATE = "date";
        public static final String TRANS_COLUMN_CAT = "cat_id";
    /**
     * CATEGORIES TABLE
     */
        public static final String TABLE_CATEGORIES = "Categories";
        public static final String CAT_COLUMN_ID = "id";
        public static final String CAT_COLUMN_NAME = "cat_name";
        public static final String CAT_COLUMN_TRANS_ID = "trans_id";
    /**
     * TRANSACTION TYPES TABLE
     */
        public static final String TABLE_TRANSACTION_TYPES = "Transaction_types";
        public static final String TYPE_COLUMN_ID = "id";
        public static final String TYPE_COLUMN_NAME = "type";
    /**
     * CREATE TABLE STATEMENTS
     */
    public static final String CREATE_TABLE_TRANSACTION_TYPES =
            " CREATE TABLE " + TABLE_TRANSACTION_TYPES + " (" +
                    TYPE_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
                    TYPE_COLUMN_NAME + " VARCHAR(15), " +
                    "PRIMARY KEY(" + TYPE_COLUMN_ID + "));";

    public static final String CREATE_TABLE_CATEGORIES =
        "CREATE TABLE " + TABLE_CATEGORIES + "(" +
                CAT_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
                CAT_COLUMN_NAME + " VARCHAR(30) NOT NULL, " +
                CAT_COLUMN_TRANS_ID + " int NOT NULL, " +
                "PRIMARY KEY(" + CAT_COLUMN_ID + "), " +
                "FOREIGN KEY(" + CAT_COLUMN_TRANS_ID + ") REFERENCES " + TABLE_TRANSACTION_TYPES + "(" + TRANS_COLUMN_ID + "))";
    public static final String CREATE_TABLE_TRANSACTIONS =
            "CREATE TABLE " + TABLE_TRANSACTIONS + "(" +
                    TRANS_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
                    TRANS_COLUMN_AMOUNT + " decimal(10,2) NOT NULL, " +
                    TRANS_COLUMN_DESC + " VARCHAR(60), " +
                    TRANS_COLUMN_DATE + " DATE NOT NULL, " +
                    TRANS_COLUMN_CAT + " int NOT NULL, " +
                    "PRIMARY KEY(" + TRANS_COLUMN_ID + "), " +
                    "FOREIGN KEY(" + TRANS_COLUMN_CAT + ") REFERENCES " + TABLE_CATEGORIES + "(" + CAT_COLUMN_ID + "))";

    /**
     * INSERT INTO STATEMENTS
     */
    public static final String INSERT_DEFAULT_TRANS_TYPES =
            "INSERT INTO " + TABLE_TRANSACTION_TYPES
            + " VALUES (1, 'Income'), "
            + "(2, 'Expense');";

    public static final String INSERT_DEFAULT_CATEGORIES =
            "INSERT INTO " + TABLE_CATEGORIES
                    + " VALUES (1, 'Income', 1), "
                    + "(2, 'Bills', 2), "
                    + "(3, 'Groceries', 2), "
                    + "(4, 'Misc', 2);";
}
