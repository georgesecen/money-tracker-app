package org.example.moneytrackerapp.pojo;

/**
 * Represents a category that has a unique id, name, and int trans_type
 * @author Wania Sharif
 */
public class Category extends DatabaseItem {

    private int id;
    private String name;
    private int trans_type;

    /**
     * No args constructor
     */
//    public Category() {}

    /**
     * Constructor for Category
     * @param id unique id
     * @param name Category name
     * @param trans_type integer represents type of transaction
     */
    public Category(int id, String name, int trans_type) {
        super(id);
        this.id = id;
        this.name = name;
        this.trans_type = trans_type;
    }

    /**
     * Constructor excluding id
     * @param name Category name
     * @param trans_type integer represents type of transaction
     */
    //TODO do not need this constructor
//    public Category(String name, int trans_type) {
////        super(id);
//        this.name = name;
//        this.trans_type = trans_type;
//    }

    /**
     * @return unique id of a Category
     */
    public int getId() {
        return id;
    }

    /**
     * @param id sets the id, must be unique
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Category name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name sets Category name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return An integer representing type of transaction
     */
    public int getTrans_type() {
        return trans_type;
    }

    /**
     * @param trans_type sets int transaction type
     */
    public void setTrans_type(int trans_type) {
        this.trans_type = trans_type;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
