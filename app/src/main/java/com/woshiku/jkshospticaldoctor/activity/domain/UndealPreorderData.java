package com.woshiku.jkshospticaldoctor.activity.domain;

/**
 * Created by admin on 2017-04-19.
 */

public class UndealPreorderData {
    private String id,name,icon,date,sex,amount;
    private boolean isChoose = false;

    public UndealPreorderData(String date, boolean isChoose) {
        this.date = date;
        this.isChoose = isChoose;
    }

    public UndealPreorderData(String id, String name, String icon, String date, String sex, String amount) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.date = date;
        this.sex = sex;
        this.amount = amount;
    }

    public UndealPreorderData(String id, String name, String icon, String date, String sex, String amount, boolean isChoose) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.date = date;
        this.sex = sex;
        this.amount = amount;
        this.isChoose = isChoose;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
