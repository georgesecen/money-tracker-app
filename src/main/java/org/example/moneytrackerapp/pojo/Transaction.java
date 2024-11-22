package org.example.moneytrackerapp.pojo;

import java.sql.Date;

/**
 * Class represents a transaction which has a unique id, amount transacted,
 * optional description, date of transaction, and category id
 * @author Wania Sharif
 */
public class Transaction {

    private int id;
    private double amt;
    private String desc = "";
    private Date date;
    private int cat_id;

    /**
     * No args constructor
     */
    public Transaction() {}

    /**
     * Constructor for a Transaction
     * @param id unique integer id
     * @param amt double amt
     * @param desc (optional) string description
     * @param date SqlDate date
     * @param cat_id int represents category
     */
    //TODO make desc optional
    public Transaction(int id, double amt, String desc, Date date, int cat_id){
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
     * @param date SqlDate date
     * @param cat_id int represents category
     */
    public Transaction(double amt, String desc, Date date, int cat_id){
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
     * Gets the transaction date.
     * @return SqlDate
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the transaction date.
     * @param date SqlDate to set.
     */
    public void setDate(Date date) {
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
    public String toString(){ return getCat_id() + " "; }
}
