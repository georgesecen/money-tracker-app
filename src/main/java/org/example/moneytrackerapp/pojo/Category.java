package org.example.moneytrackerapp.pojo;

public class Category {

    private int id;
    private String name;

    private int trans_type;

    public Category(int id, String name, int trans_type) {
        this.id = id;
        this.name = name;
        this.trans_type = trans_type;
    }

    // Constructor excluding id
    public Category(String name, int trans_type) {
        this.name = name;
        this.trans_type = trans_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(int trans_type) {
        this.trans_type = trans_type;
    }

    @Override
    public String toString() {
        return name;
    }
}
