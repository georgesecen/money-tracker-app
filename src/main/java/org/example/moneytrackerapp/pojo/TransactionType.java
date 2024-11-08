package org.example.moneytrackerapp.pojo;

public class TransactionType {
    private int id;
    private String type;

    public TransactionType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    // Constructor excluding id
    public TransactionType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    /**
     *
     * @param type (set as either "income" or "expense")
     */
    public void setType(String type) {
        this.type = type;
    }
}
