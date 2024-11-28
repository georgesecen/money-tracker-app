package org.example.moneytrackerapp.dao;


import org.example.moneytrackerapp.pojo.Category;

import java.util.ArrayList;

public interface CategoryDAO {
    public ArrayList<Category> getAllCategories();
    public ArrayList<Category> getAllIncomeCategories();
    public ArrayList<Category> getAllExpenseCategories();
<<<<<<< HEAD
    public Category getCategory(int id);
    public void addCategory(Category category);
    public void deleteCategory(int id);
=======
    //TODO uncomment getCategory
//    public Category getCategory(int id);
>>>>>>> development

}