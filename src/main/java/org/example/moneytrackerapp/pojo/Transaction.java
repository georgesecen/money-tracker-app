package org.example.moneytrackerapp.pojo;

public class Transaction {

    private int id;
    private double amt;
    private String desc = "";
    private String date;
    private int cat_id;

    public Transaction(int id, double amt, String desc, String date, int cat_id){
        this.id = id;
        this.amt = amt;
        this.desc = desc;
        this.date = date;
        this.cat_id = cat_id;
    }

    // Constructor excluding id
    public Transaction(double amt, String desc, String date, int cat_id){
        this.amt = amt;
        this.desc = desc;
        this.date = date;
        this.cat_id = cat_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }
}
