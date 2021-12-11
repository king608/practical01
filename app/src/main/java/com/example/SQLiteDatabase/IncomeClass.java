package com.example.SQLiteDatabase;

public class IncomeClass {
    private int id;
    private int uid;
    private String time;
    private String amount;
    private String category;
    private String fuKuanRen;
    private String beiZhu;

    public IncomeClass(int uid, String time, String amount, String category, String fuKuanRen, String beiZhu) {
        this.uid = uid;
        this.time = time;
        this.amount = amount;
        this.category = category;
        this.fuKuanRen = fuKuanRen;
        this.beiZhu = beiZhu;
    }

    public IncomeClass(int id, int uid, String time, String amount, String category, String fuKuanRen, String beiZhu) {
        this.id = id;
        this.uid = uid;
        this.time = time;
        this.amount = amount;
        this.category = category;
        this.fuKuanRen = fuKuanRen;
        this.beiZhu = beiZhu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFuKuanRen() {
        return fuKuanRen;
    }

    public void setFuKuanRen(String fuKuanRen) {
        this.fuKuanRen = fuKuanRen;
    }

    public String getBeiZhu() {
        return beiZhu;
    }

    public void setBeiZhu(String beiZhu) {
        this.beiZhu = beiZhu;
    }
}

