package org.example.moneytrackerapp.tables;

import org.example.moneytrackerapp.dao.CategoryDAO;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.pojo.Category;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static org.example.moneytrackerapp.database.DBConst.*;

public class CategoryTable implements CategoryDAO {
    private static CategoryTable instance;
    Database db = Database.getInstance();
    ArrayList<Category> categories;
    private CategoryTable(){
        db = Database.getInstance();
    }
    /**
     * GetAllCategories
     * returns all categories
     */
    @Override
    public ArrayList<Category> getAllCategories() {
        String query = "SELECT * FROM " + TABLE_CATEGORIES;
        categories = new ArrayList<>();
        try{
            Statement statement = db.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                categories.add(new Category(
                    resultSet.getInt(CAT_COLUMN_ID),
                    resultSet.getString(CAT_COLUMN_NAME),
                    resultSet.getInt(CAT_COLUMN_TRANS_ID)
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public ArrayList<Category> getAllIncomeCategories() {
        //TODO no static ID's
        String query = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + CAT_COLUMN_TRANS_ID + " = 1";
        categories = new ArrayList<>();
        try {
            Statement statement = db.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                categories.add(new Category(
                        resultSet.getInt(CAT_COLUMN_ID),
                        resultSet.getString(CAT_COLUMN_NAME),
                        resultSet.getInt(CAT_COLUMN_TRANS_ID)
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public ArrayList<Category> getAllExpenseCategories() {
        String query = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + CAT_COLUMN_TRANS_ID + " = 2";
        categories = new ArrayList<>();
        try {
            Statement statement = db.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                categories.add(new Category(
                        resultSet.getInt(CAT_COLUMN_ID),
                        resultSet.getString(CAT_COLUMN_NAME),
                        resultSet.getInt(CAT_COLUMN_TRANS_ID)
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * GetCategory
     * returns a single category
     */
    @Override
    public Category getCategory(int id) {
        String query = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE " + CAT_COLUMN_ID + " = " + id;
        try {
            Statement statement = db.getConnection().createStatement();
            ResultSet data = statement.executeQuery(query);
            if(data.next()) {
                Category category = new Category(
                    data.getInt(CAT_COLUMN_ID),
                    data.getString(CAT_COLUMN_NAME),
                    data.getInt(CAT_COLUMN_TRANS_ID)
                );
                return category;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Adds a record into the Category table
     * @param category Category object to be added
     */
    @Override
    public void addCategory(Category category) {
        String query = "INSERT INTO " + TABLE_CATEGORIES +
                "(" + CAT_COLUMN_ID + ", "
                + CAT_COLUMN_NAME + ", "
                + CAT_COLUMN_TRANS_ID + ") VALUES ("
                + category.getId() + ", '"
                + category.getName() + "', "
                + category.getTrans_type() + ");";
        try {
            db.getConnection().createStatement().execute(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a record in the Categories table
     * @param catID id of category to be deleted
     */
    @Override
    public void deleteCategory(int catID) {
        String query = "DELETE FROM " + TABLE_CATEGORIES + " WHERE " + CAT_COLUMN_ID + " = " + catID;
        try {
            Statement statement = db.getConnection().createStatement();
            statement.execute(query);
            System.out.println("Successfully deleted catagory.");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error deleting category");
        }
    }

    public static CategoryTable getInstance(){
        if(instance == null){
            instance = new CategoryTable();
        }
        return instance;
    }
}
