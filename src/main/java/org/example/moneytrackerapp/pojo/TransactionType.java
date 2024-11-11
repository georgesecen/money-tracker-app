package org.example.moneytrackerapp.pojo;

/**
 * Class represents a Transaction Type. Has a unique id and type
 */
public class TransactionType {
    private int id;
    private String type;

    /**
     * No args constructor
     */
    public TransactionType() {}

    /**
     * Constructor for TransactionType
     * @param id integer id
     * @param type string type
     */
    public TransactionType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    /**
     * Constructor for TransactionType (excludes id)
     * @param type string type
     */
    public TransactionType(String type) {
        this.type = type;
    }

    /**
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id sets a unique id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return string type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type set type
     */
    public void setType(String type) {
        this.type = type;
    }
}
