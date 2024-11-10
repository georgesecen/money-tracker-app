package org.example.moneytrackerapp.pojo;

/**
 * Class represents a transaction which has a unique id, amount transacted,
 * optional description, date of transaction, and category id
 * @author Wania Sharif
 */
public class Transaction {

    private int id;
    private double amt;
    private String desc = "";
    private String date;
    private int cat_id;

    /**
     * Constructor for a Transaction
     * @param id unique integer id
     * @param amt double amt
     * @param desc (optional) string description
     * @param date string date
     * @param cat_id int represents category
     */
    //TODO make desc optional
    public Transaction(int id, double amt, String desc, String date, int cat_id){
        this.id = id;
        this.amt = amt;
        this.desc = desc;
        this.date = date;
        this.cat_id = cat_id;
    }

    /**
     * Constructor for Transaction excluding id
     * @param amt unique integer id
     * @param desc (optional) string description
     * @param date string date
     * @param cat_id int represents category
     */
    public Transaction(double amt, String desc, String date, int cat_id){
        this.amt = amt;
        this.desc = desc;
        this.date = date;
        this.cat_id = cat_id;
    }

    /**
     * @return id of transaction
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
     * @return amount of transaction
     */
    public double getAmt() {
        return amt;
    }

    /**
     * @param amt sets amount, can be negative or positive
     */
    public void setAmt(double amt) {
        this.amt = amt;
    }

    /**
     * @return string description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc sets description
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date set date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return category
     */
    public int getCat_id() {
        return cat_id;
    }

    /**
     * @param cat_id set category
     */
    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }
}
