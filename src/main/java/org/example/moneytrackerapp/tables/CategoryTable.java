package org.example.moneytrackerapp.tables;

import org.example.moneytrackerapp.dao.CategoryDAO;
import org.example.moneytrackerapp.database.Database;
import org.example.moneytrackerapp.pojo.Category;

import java.util.ArrayList;

public class CategoryTable implements CategoryDAO {
    Database db = Database.getInstance();
    @Override
    public ArrayList<Category> getAllCategories() {
        return null;
    }

    @Override
    public ArrayList<Category> getAllIncomeCategories() {
        return null;
    }

    @Override
    public ArrayList<Category> getAllExpenseCategories() {
        return null;
    }

    @Override
    public Category getCategory(int id) {
        return null;
    }
}
