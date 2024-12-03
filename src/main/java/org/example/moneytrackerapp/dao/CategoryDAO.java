package org.example.moneytrackerapp.dao;

import org.example.moneytrackerapp.pojo.Category;
import java.util.ArrayList;

/**
 * Interface represents DAO for the table Categories.
 * Can be used to retrieve data.
 *
 * @author Wania Sharif
 */
public interface CategoryDAO {
    /**
     * Returns all category records in an ArrayList
     * of Category objects
     *
     * @return ArrayList of categories
     */
    public ArrayList<Category> getAllCategories();

    /**
     * Returns all category records that have a Transaction Type
     * of Income
     *
     * @return ArrayList of matching categories
     */
    public ArrayList<Category> getAllIncomeCategories();

    /**
     * Returns all categoru records with a Transaction Type
     * of expense
     *
     * @return ArrayList of matching categories
     */
    public ArrayList<Category> getAllExpenseCategories();

    /**
     * Gets a category record by its id
     *
     * @param id int unique identifier
     * @return Category object found
     */
    public Category getCategory(int id);

    /**
     * Inserts a record using the given Category object's
     * properties
     *
     * @param category Category object
     */
    public void addCategory(Category category);

    /**
     * Deletes a record by its id
     *
     * @param id int unique identifier
     */
    public void deleteCategory(int id);

}