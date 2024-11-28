package org.example.moneytrackerapp.pojo;

public class DatabaseItem {
    private int id;

    public DatabaseItem(int id ){
        this.id = id;
    }

    public int getDBId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
